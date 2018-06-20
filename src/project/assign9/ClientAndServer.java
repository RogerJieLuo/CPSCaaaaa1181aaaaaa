package project.assign9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientAndServer implements ReversiRule{

    private BoardComponent boardComponent;
    private JLabel colorLabel;
    private JLabel msgLabel;
    private JLabel scoreLabel;
    private JComboBox<String> combo;
    private JTextField xTextField;
    private JTextField yTextField;
    private JButton btnResign;
    private JButton btnQuit;
    private ArrayList<String> listOfAttendant;

    private int[][] board;
    private ArrayList<Integer> valPattern;
    private String changedStones;
    private int color;

    public ClientAndServer(){

        board = ReversiRule.initBoard();

        genClientGUi();
        genServerGui();
    }

    /**
     * Generate a client GUI
     */
    private void genClientGUi(){
        JFrame clientFrame = new JFrame();

        colorLabel = new JLabel("Color:");
        msgLabel = new JLabel();
        scoreLabel = new JLabel("Score: " );
        boardComponent = new BoardComponent(this);

        btnQuit = new JButton("Quit");
        btnResign = new JButton("Resign");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1));
        topPanel.add(colorLabel);
        topPanel.add(scoreLabel);
        topPanel.add(msgLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.add(boardComponent);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnResign);
//        bottomPanel.add(btnQuit);
//
        initiateClient();

        clientFrame.setSize(500, 500);
        clientFrame.setLocation(0, 200);
        clientFrame.setLayout(new BorderLayout());
        clientFrame.add(topPanel, BorderLayout.NORTH);
        clientFrame.add(centerPanel, BorderLayout.CENTER);
        clientFrame.add(bottomPanel, BorderLayout.SOUTH);
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setVisible(true);
    }

    /**
     * initiate actions and buttons
     */
    private void initiateClient(){
        btnQuit.addActionListener(new ClientAction());
        btnResign.addActionListener(new ClientAction());
        setButtons(false);
        boardComponent.initiate();
    }

    /**
     * set all the buttons enable attribute
     * @param en true/false
     */
    private void setButtons(boolean en){
        btnQuit.setEnabled(en);
        btnResign.setEnabled(en);
    }

    /**
     * when user tries to play a stone on the board[i][j], tell server the position
     * @param i position x in board
     * @param j position y in board
     */
    public void play(int i, int j){
        System.out.println("Client --> server: PLAY: "+i+","+j);
    }

    /**
     * generate server gui
     */
    private void genServerGui(){
        JFrame serverFrame = new JFrame();

        combo = new JComboBox<>(cmd);

        JLabel xLabel = new JLabel("X: ");
        xTextField = new JTextField();
        xTextField.setPreferredSize(new Dimension(100, 20));
        JLabel yLabel = new JLabel("Y: ");
        yTextField = new JTextField();
        yTextField.setPreferredSize(new Dimension(100, 20));

        JButton btnExecute = new JButton("Execute");
        btnExecute.addActionListener(new ServerAction());

        serverFrame.add(combo);
        serverFrame.add(xLabel);
        serverFrame.add(xTextField);
        serverFrame.add(yLabel);
        serverFrame.add(yTextField);
        serverFrame.add(btnExecute);

        serverFrame.setSize(500, 100);
        serverFrame.setLayout(new FlowLayout());
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        serverFrame.setVisible(true);
    }

    class ClientAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnResign){
                cResign();
            }else if(e.getSource() == btnQuit){
                cQuit();
            }
        }
    }

    /**
     * client send RESIGN request
     */
    private void cResign(){
        System.out.println("Client --> server: RESIGN " + this.color);
    }

    /**
     * client send QUIT request
     */
    private void cQuit(){
        System.out.println("Client --> server: QUIT " + this.color);
    }

    class ServerAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = combo.getSelectedItem() + " " + xTextField.getText() ;
            if(!yTextField.getText().equals("")){
                cmd += XY_DELIMITER + yTextField.getText();
            }
            executeCommand(cmd);
        }

    }

    /**
     * server execute command based on the received request
     * @param cmd received request
     */
    private void executeCommand(String cmd){
        String c = cmd.split(" ")[0];
        String val = cmd.split(" ")[1];

        System.out.print("Server --> client: ");

        if(c.equals(ASSIGN)){

            assign(val);
            setScoreText();

        }else if(c.equals(DRAWBLACK)){

            drawBlack(val);
            setScoreText();

        }else if(c.equals(DRAWWHITE)){

            drawWhite(val);
            setScoreText();

        }else if(c.equals(TURN)){

            turn(val);

        }else if(c.equals(RESIGN)){

            resign(val);

        }else if(c.equals(WIN)){

            win(val);

        }

    }

    /**
     * server send ASSIGN response
     * @param col color
     */
    private void assign(String col){
        System.out.println(ASSIGN + " " + col);
//        initiateClient();
        if(Integer.parseInt(col)== BLACK ){

            color = BLACK;
            System.out.println("You are BLACk");
            colorLabel.setText("BLACK");

        }else{

            color = WHITE;
            System.out.println("You are WHITE");
            colorLabel.setText("WHITE");

        }
        setButtons(true);
    }

    /**
     * server send DRAWBLACK response
     * @param val message followed by the request
     */
    private void drawBlack(String val){
        int i = Integer.parseInt(val.split(XY_DELIMITER)[0]);
        int j = Integer.parseInt(val.split(XY_DELIMITER)[1]);

        if(!chkValidMove(i, j, BLACK)){

            invalidMove(val);

        }else {

            changedStones = "";
            updateBoardStone(i,j, BLACK);
            boardComponent.drawBlack(changedStones);
            System.out.println(DRAWBLACK + " " + changedStones.substring(0, changedStones.length() - 1));

        }
    }

    /**
     * server send DRAWWHITE response
     * @param val message followed by the request
     */
    private void drawWhite(String val){
        int i = Integer.parseInt(val.split(XY_DELIMITER)[0]);
        int j = Integer.parseInt(val.split(XY_DELIMITER)[1]);

        if(!chkValidMove(i, j, WHITE)){
            invalidMove(val);
        }else {
            changedStones = "";
            updateBoardStone(i,j, WHITE);
            boardComponent.drawWhite(changedStones);
            System.out.println("DRAWWHITE " + changedStones.substring(0, changedStones.length() - 1));

            // TODO: change turn
        }
    }

    /**
     * server send INVALID response
     * @param val message followed by the request
     */
    private void invalidMove(String val){
        setMsgText("INVALID move, please pick another position.");
        System.out.println(INVALID +" "+val);
    }

    /**
     * server send TURN request
     * @param val color
     */
    private void turn(String val){
        System.out.println(TURN + " " + val);

        if(Integer.parseInt(val) == color ){
            setMsgText("It's your turn");
            boardComponent.setEnabled(true);
        }else{
            setMsgText("Waiting");
            boardComponent.setEnabled(false);
        }
    }

    /**
     * server send RESIGN response
     * @param who color
     */
    private void resign(String who){
        System.out.println(RESIGN + " " + who);

        if(Integer.parseInt(who) == this.color){
            setMsgText("You RESIGN");
        }else {
            setMsgText("You WIN!");
        }
        setButtons(false);
        boardComponent.endGame();
    }

    /**
     * server send WIN response
     * @param who color
     */
    private void win(String who){
        System.out.println(WIN + " " + who);

        if(Integer.parseInt(who) == this.color){
            setMsgText("You WIN!");
        }else {
            setMsgText("You LOSE");
        }
        boardComponent.endGame();
    }

    /**
     * set score label text
     */
    private void setScoreText(){
        scoreLabel.setText("Score: " + calScore() );
    }

    /**
     * set message label text
     * @param msg
     */
    public void setMsgText(String msg){
        msgLabel.setText(msg);
    }

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
        System.out.println("UPDated color " + i + " " + j + " " + c);
        changedStones += i + XY_DELIMITER + j + POINTS_DELIMITER;
        updateStone(i + pi, j + pj, pi, pj, c);
    }

    /**
     * calculate the score
     * @return score
     */
    private int calScore(){
        int count = 0;
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                if(board[i][j] == this.color)
                    count++;
            }
        }
        return count;
    }

    private void printBoard(){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new ClientAndServer();
    }
}
