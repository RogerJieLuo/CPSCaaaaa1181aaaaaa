/**
 * Class to handle action that server send command to all the clients
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Room implements ReversiProtocol {
    private static int ID = 1;

    private int roomId;
    private ArrayList<ReversiService> players;
    private ArrayList<ReversiService> attendants;
    private GameData gameData;
    private ReversiServerFrame serverFrame;

    public Room(ReversiServerFrame serverFrame){
        this.serverFrame = serverFrame;
        this.roomId = ID;
        ID ++;
        players = new ArrayList<>();
        attendants = new ArrayList<>();
        gameData = new GameData(SIDE, SIDE);
    }

    /**
     * if there are 2 players, then no more client can join the game
     * but they can join the room as viewer
     * @return true/false
     */
    public boolean isFull(){
        return players.size() == 2;
    }

    /**
     * client enters room as viewer
     * @param rs the service socket
     */
    public void enterRoom(ReversiService rs){
        if(!attendants.contains(rs)) {
            attendants.add(rs);
        }
        rLog("attendant +1 ");

        // retrieve all the game info if the game already starts
        rLog("Sending game data");
        sendGameData(rs);

    }

    /**
     * encapsulate the game data and send to the new player
     * @param rs
     */
    private void sendGameData(ReversiService rs){
        try{
            DataOutputStream toClient = new DataOutputStream(rs.getSocket().getOutputStream());
            toClient.writeInt(RETRIEVE);

            StringBuilder data = new StringBuilder();

            if(isFull()) {
                data.append(1 + COMMAND_DELIMITER);
            }else {
                // if the game starts
                data.append(0 + COMMAND_DELIMITER);
            }
            int[][] board = gameData.getBoard();
            String[] players = gameData.getPlayers();

            // board data
            for(int i = 0; i<board.length;i++){
                for (int j = 0; j<board[i].length; j++){
                    data.append(board[i][j]).append(POINTS_DELIMITER);
                }
            }
            data.setLength(data.length() - 1);
//            rLog(data.toString());

            // players data
            data.append(COMMAND_DELIMITER);
            data.append(players[0]).append(POINTS_DELIMITER).append(players[1]);

            // attendants data
            data.append(COMMAND_DELIMITER);
            for(ReversiService reversiService : attendants){
                if(reversiService.getServiceName().equals(rs.getServiceName())) continue;
                data.append(reversiService.getServiceName()).append(POINTS_DELIMITER);
            }

            toClient.writeUTF(data.toString());
            toClient.flush();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * clean the players list,
     * happens when the game is finished
     */
    public void removeAll(){
        players = new ArrayList<>();
        rLog("End game, empty players");
    }

    /**
     * the player is ready for playing,
     * check if there are 2 players
     *  if yes, send 2 players message to all the attendants
     *  if no, send the READY command to the player
     * @param rs player service socket
     */
    public void beReady(ReversiService rs){
        DataOutputStream toClient;
        try {
            toClient = new DataOutputStream(rs.getSocket().getOutputStream());

            // if there are 2 players
            if(isFull()) {
                rLog("There are 2 players, you cannot join in.");
                toClient.writeInt(INVALIDTRY);
                toClient.writeUTF("There are 2 players, you cannot join in.");
                toClient.flush();
            }else{
                if(players.contains(rs))
                    return;
                // add to players list
                players.add(rs);

                rLog("Players +1 " + players.size());
                // send command to the player
                toClient.writeInt(READY);
            }
            toClient.flush();
//            toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * one attendant leave the room
     * remove from the attendant list and close the socket
     * @param rs
     */
    public void quit(ReversiService rs){
//        int clientId = rs.getClientId();
        String name = rs.getServiceName();
        // one player quits, means the game ends, so clear the player list
        attendants.remove(rs);

        // send message to all
        sendToAll(QUIT, name);

    }

    /**
     * after a player's move, update all other attendants' board
     * @param clientId
     * @param points
     * @param color
     */
    public void udpateOthersBoard(int clientId, String points, int color){
        // TODO: here is static for players
        for( ReversiService rs: players) {
            if(rs.getClientId() == clientId) continue;
            rs.updateOpponentStone(points, color);
        }
    }

    /**
     * send command to all attendants
     * @param cmd
     * @param msg
     */
    public void sendToAll(int cmd, String msg){
        sendTo(cmd, msg, attendants);
    }

    /**
     * send command to all attendants
     * @param cmd command
     * @param msg message
     * @param listOfPpl list of attendants
     */
    public void sendTo(int cmd, String msg, ArrayList<ReversiService> listOfPpl){
        DataOutputStream toClient;

        for( ReversiService rs: listOfPpl){
            rLog("Msg will be sent to client " + rs.getClientId() + ": " + cmd + " " + msg);
            try {
                toClient = new DataOutputStream(rs.getSocket().getOutputStream());
                if(cmd == TURN) {
                    rs.turn();
                }else if(cmd == WIN){
                    toClient.writeInt(cmd);
                    toClient.writeInt(Integer.parseInt(msg));
                } else if(cmd == NEWGAME){
                    toClient.writeInt(NEWGAME);
                    toClient.writeUTF(msg);
                } else {
                    // draw stones
                    toClient.writeInt(cmd);
                    if(msg != null && !msg.equals("")) {
                        toClient.writeUTF(msg);
                    }
                }

                toClient.flush();
//                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * after ASSIGN the color, the action is triggered by the second player,
     * so send the command to the first players
     * @param cmd command
     * @param color color
     */
    public void sendToFirst(int cmd, int color){
        ReversiService rs = players.get(0);
        try {
//            rs.initiate();
            DataOutputStream toClient = new DataOutputStream(rs.getSocket().getOutputStream());
            toClient.writeInt(cmd);
            toClient.writeInt(color);

            toClient.flush();
//            toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * get service name to send to all the clients
     * so they can display the current players on gui
     * @return serviceName
     */
    public String getFirstName(){
        ReversiService rs = players.get(0);
        return rs.getServiceName();
    }

    public void setBoard(int[][] board){
        gameData.setBoard(board);
    }

    public void setPlayers(String[] players){
        gameData.setPlayers(players);
    }

    /**
     * print room log
     * @param msg
     */
    private void rLog(String msg){
        System.out.println(msg);
    }

    public void serverLog(String msg){
        serverFrame.log(msg);
    }
}
