/**
 * Class to handle the client request and send the command to client
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ReversiService implements Runnable, ReversiProtocol {

    private static int SERVICEID = 0;
    private Socket socket;
    private DataInputStream fromClient;
    private DataOutputStream toClient;

    private int[][] board;
    private ArrayList<Integer> valPattern;
    private String changedStones;
    private int turn;   // which color's turn
    private int clientId;
    private String serviceName;
    private boolean running;
    private Room room;


    public ReversiService(Socket socket, Room room ){
        this.socket = socket;
        this.room = room;
        SERVICEID ++;
        clientId = SERVICEID;
        initiate();
    }

    // initiate the game elements and logic
    public void initiate(){

        board = ReversiProtocol.initBoard();
        turn = BLACK;
        running = true;
        clearTempData();

    }

    @Override
    public void run() {
        try{
            fromClient = new DataInputStream(socket.getInputStream());
            toClient = new DataOutputStream(socket.getOutputStream());

            while(running){
                // where receive the request from client and action
                executeCmd();
            }

        }catch (IOException e){
//            e.printStackTrace();
            sLog("Unexpected quit 3");
        }finally {
            try {
                if(socket != null & !socket.isClosed()) {
                    fromClient.close();
                    toClient.close();
                    socket.close();
                }
            } catch (IOException e) {
                sLog("Unexpected quit 4");
                e.printStackTrace();
            }
        }
    }

    private void executeCmd(){
        int msg;
        try {
            msg = fromClient.readInt();

//            sLog("Received from client: ");
            switch (msg) {
                case ENTERROOM:
                    sLog("Received from client: ENTERROOM");
                    enterRoom();
                    break;
                case READY:
                    sLog("Received from client: READY");
                    beReady();
                    break;
                case ASSIGN:
                    sLog("Received from client: ASSIGN");
                    assign();
                    break;
                case PLAY:
                    sLog("Received from client: PLAY");
                    play(turn);
                    break;
                case RESIGN:
                    sLog("Received from client: RESIGN");
                    resign();
                    break;
                case QUIT:
                    sLog("Received from client: QUIT");
                    quit();
                    break;
                case CHAT:
                    sLog("Received from client: CHAT");
                    chat();
                    break;
                default:
                    throw new ReversiException("The unexpected command received from client");
            }
        } catch (IOException e) {
            sLog("Unexpected quit 1");
        } catch (ReversiException e){
            sLog("Unexpected quit 2");
        } catch (Exception e){
            sLog("Unexpected quit 3");
        }
    }

    /* ****************************************** EXECUTE COMMANDS ******************************************  */

    /**
     * new game, initiate game
     */
    private void newGame(){
        initiate();
    }

    /**
     * player enter the room
     */
    private void enterRoom() throws IOException {
        serviceName = fromClient.readUTF();
        sLog(clientId + "'thn client is " + serviceName.trim() + " who enters the room");

        room.enterRoom(this);
        sLog("Server -> client: ENTERROOM");
        room.sendToAll(ENTERROOM, " " + clientId + " " + serviceName);

    }

    /**
     * server send READY response
     * received READY request from client, add client into the player list
     * if there are 2 players in the list, then assign them color and start the game
     * @throws IOException
     */
    private void beReady() throws IOException{

        // when user click the "new game", everything initiate
        initiate();

        // if there are already 2 players, then the request will be blocked
        if(room.isFull()){
            // There are 2 players, you cannot join in
            sLog("Server -> client: INVALIDTRY");
            toClient.writeInt(INVALIDTRY);
            toClient.writeUTF("There are 2 players, please find other opponents.");
            return;
        }

        // delegate the room to add the client to players list
        room.beReady(this);

        //if there are 2 players, assign color and start game
        if(room.isFull()){
            assign();
        }
    }

    /**
     * server send ASSIGN response
     * assign color to 2 players and send the color info to other attendants
     */
    private void assign() throws IOException {
//        newGame();
        String msg;
        Random rand = new Random();
        int c = rand.nextInt(2);
        sLog("Server -> client: ASSIGN");
        if(c == 0 ){
            toClient.writeInt(ASSIGN);
            toClient.writeInt(BLACK);
            room.sendToFirst(ASSIGN, WHITE);
            room.setPlayers(new String[]{this.serviceName, room.getFirstName()});
            msg = this.serviceName + POINTS_DELIMITER + room.getFirstName();
        }else{
            toClient.writeInt(ASSIGN);
            toClient.writeInt(WHITE);
            room.sendToFirst(ASSIGN, BLACK);
            room.setPlayers(new String[]{room.getFirstName(), this.serviceName});
            msg = room.getFirstName() + POINTS_DELIMITER + this.serviceName;
        }
        room.setBoard(board);
        // send to others attendants

        sLog("Server -> client: NEWGAME");
        room.sendToAll(NEWGAME, msg);
    }

    /**
     * server send PLAY response
     * server get the play move from client, and takes the color of turn,
     * check the validation of move
     * send the draw stone command to all the clients, so all the game board
     * get updates
     * if there is no valid move for next player, then don't do turn()
     *
     * @param color the color is current turn
     * @throws IOException
     */
    private void play(int color) throws IOException {
        // get the x, y of the play move
        int i = fromClient.readInt();
        int j = fromClient.readInt();

//        printBoard();

        // check the validation of the move
        if(!chkValidMove(i, j, color)){

            invalidMove(i, j);

        }else {

            changedStones = "";

            // update current player board, collect the updated stones
            updateBoardStone(i,j, color);

            // update other players board
            // needs to update the service in the other sockets
            room.udpateOthersBoard(clientId, changedStones, color);

            // update the board in room
            // in order to display the same board to the new attendants
            room.setBoard(board);

            // delegate room to send the draw command to all the attendants
            if(color == BLACK) {
                room.sendToAll(DRAWBLACK, changedStones);
                sLog("Server -> client: DRAWBLACK ");
            }else {
                room.sendToAll(DRAWWHITE, changedStones);
                sLog("Server -> client: DRAWWHITE ");
            }
            // clear the changedStones and valPattern avoid messy when play again
            clearTempData();

            // check if game ends after each move
            if(!chkValidMove(WHITE) && !chkValidMove(BLACK)){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calResult();
                endGame();
                return;
            }else if(color == BLACK && !chkValidMove(WHITE) ){
                // if the next player can not find a valid move choice
                return;
            }else if(color == WHITE && !chkValidMove(BLACK) ){
                // if the next player can not find a valid move choice
                return;
            }


            // change turn
            room.sendToAll(TURN, "");
            sLog("Server -> client: TURN ");

        }
    }

    /**
     * clear temp data
     */
    private void clearTempData(){
        changedStones = "";
        valPattern = new ArrayList<>();
    }

    /**
     * SERVER send INVALID command to client, to inform the move is not valid
     *
     * @param i index x of the board
     * @param j index y of the board
     * @throws IOException
     */
    private void invalidMove(int i, int j) throws IOException {
        toClient.writeInt(INVALID);
        sLog("Server -> client: INVALID move: "+ i + " " + j);
    }

    /**
     * server send TURN command
     * delegate room send the request to all the client
     *
     */
    public void turn() throws IOException {
        if(turn == BLACK){
            turn = WHITE;
        }else if(turn == WHITE){
            turn = BLACK;
        }else {
            sLog("Server: color turn is error");
        }
        toClient.writeInt(TURN);
        toClient.writeInt(turn);
        sLog("Server -> client: TURN " + turn);
    }

    /**
     * server send RESIGN command
     * player resign the game, but stay in the room
     * delegate room send the request to all the client
     */
    private void resign(){
        int color = 0;
        try {
            // the color of the player who resigned
            color = fromClient.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        room.sendToAll(WIN, "" + (0 - color) );
        sLog("Server -> client: WIN " + (0 - color) );
        endGame();
    }

    /**
     * server send QUIT command
     *
     * player quit, leave the room and close the socket
     */
    private void quit() throws IOException{
        this.running = false;
        toClient.writeInt(DONE);
        room.quit(this);

        sLog("Server -> client: done " + serviceName + " quits");
    }

    /**
     * end the game, clean players list
     */
    private void endGame(){
        sLog("Game is end.");
        room.removeAll();
    }

    /**
     * server send WIN command
     * if the stones of one color is more than half of the total cells, then the color wins
     */
    private void calResult(){
        if(calScore(BLACK) > SIDE * SIDE / 2){
            room.sendToAll(WIN, "" + BLACK);
            sLog("Server -> client: WIN " + BLACK);
        }else{
            room.sendToAll(WIN, "" + WHITE);
            sLog("Server -> client: WIN " + WHITE);
        }
    }

    /**
     * server send CHAT content to all the clients
     * @throws IOException
     */
    private void chat() throws IOException {
        String msg = fromClient.readUTF();
        room.sendToAll(CHAT, msg);
        sLog("Server -> client: CHAT ");
    }

    /* ****************************************** GAME LOGIC ******************************************  */


    /**
     * check if there are valid moves remain in the board
     * @param c color
     * @return true/false
     */
    private boolean chkValidMove(int c){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] != 0) continue;
                if(chkValidMove(i, j, c)) return true;
            }
        }
        return false;
    }

    /**
     * check if the move is validated
     * @param i index x in the board
     * @param j index y in the board
     * @return true/false
     */
    private boolean chkValidMove(int i, int j, int c){
        if(board[i][j] != 0) return false;
//        sLog("Print board");
//        printBoard();
        valPattern = new ArrayList<>();
        boolean valid = false;

        // move pattern is either 0 - stay, -1 - left or top, 1 - right or bottom
        int[] pattern = new int[]{0, -1, 1};
        int pi, pj;
        for(int m = 0; m < pattern.length; m++){
            for(int n = 0; n < pattern.length; n++){
                pi = pattern[m];
                pj = pattern[n];
                if(pi == 0 && pj == 0) continue;
                if(checkWithPattern(i, j, pi, pj, 0, c)){
                    valid = true;
                    valPattern.add( pi );
                    valPattern.add( pj );
                }
            }
        }

        return valid;
    }

    /**
     * check if there are other color stones in between via tracking the path followed by the move pattern
     *  example:
     *      pi = 0, pj = 1 => means the tracking path is straight up, will check if there are opponent's
     *      color in between with current color c
     *
     * @param i index x in the board
     * @param j index y in the board
     * @param pi pattern of x move
     * @param pj pattern of y move
     * @param n identify if there are different color before find the same color
     * @param c current player's color
     * @return true/false
     */
    private boolean checkWithPattern(int i, int j, int pi, int pj, int n, int c) {
        i = i + pi;
        j = j + pj;
        if (i < 0 || j < 0 || i > SIDE - 1 || j > SIDE - 1) {
            return false;
        }

        if (board[i][j] == c) {
            return n != 0; // if there are other color in between, n+1
        }else if(board[i][j] == 0){
            return false;
        }else{
            return checkWithPattern(i, j, pi, pj, n + 1, c);
        }
    }

    /**
     * update the color in the board that are in between after play a stone
     * @param i index x in the board
     * @param j index y in the board
     */
    private void updateBoardStone(int i, int j, int c){
        changedStones += i + XY_DELIMITER + j + POINTS_DELIMITER;
        board[i][j] = c;
        int pi, pj;
        for(int m = 0; m < valPattern.size(); m += 2 ){
            pi = valPattern.get(m);
            pj = valPattern.get(m + 1);
            updateStone(i + pi, j + pj, pi, pj, c);
        }
    }

    /**
     * update each stone color before find the same color
     * @param i index x in the board
     * @param j index y in the board
     * @param pi pattern of x move
     * @param pj pattern of y move
     */
    private void updateStone(int i, int j, int pi, int pj, int c){
        if(board[i][j] == c){
            return;
        }

        board[i][j] = c;
        changedStones += i + XY_DELIMITER + j + POINTS_DELIMITER;
        updateStone(i + pi, j + pj, pi, pj, c);
    }

    /**
     *
     * @param points
     * @param color
     */
    public void updateOpponentStone(String points, int color){
        String[] ps = points.split(POINTS_DELIMITER);
        int i;
        int j;
        for(int m = 0; m < ps.length; m++){
            i = Integer.parseInt(ps[m].split(XY_DELIMITER)[0]);
            j = Integer.parseInt(ps[m].split(XY_DELIMITER)[1]);
            board[i][j] = color;
        }
    }

    /**
     * calculate the score
     * @return score
     */
    private int calScore(int color){
        int count = 0;
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                if(board[i][j] == color)
                    count++;
            }
        }
        return count;
    }

    /**
     * return socket
     * @return socket
     */
    public Socket getSocket(){
        return socket;
    }

    /**
     * get client id
     * @return clientId
     */
    public int getClientId(){
        return clientId;
    }

    /**
     * get serviceName
     * @return serviceName
     */
    public String getServiceName(){
        return serviceName;
    }

    /**
     * print server log
     * @param msg
     */
    private void sLog(String msg){
//        System.out.println(msg);
        room.serverLog(msg);
    }

    /**
     * print the board,
     * it's just used for testing.
     */
    private void printBoard(){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
