/**
 * client end which handle the request sending and response to server command
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */


import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements Runnable, ReversiProtocol{

    private static final int RIGHT_WIDTH = 300;
    private static final int BUFF = 50;
    private static final String SOUNDS_DIR = "sounds/";
    private static final String IMAGES_DIR = "images/";
    private static final String WRONG_SOUND = SOUNDS_DIR + "OHNO.wav";
    private static final String PLAY_SOUND = SOUNDS_DIR + "PLAYSOUND.wav";
    private static final String WIN_SOUND = SOUNDS_DIR + "WINSOUND.wav";

    private static final String GRAY_ICON = IMAGES_DIR + "gray.png";
    private static final String BLACK_ICON = IMAGES_DIR + "black.png";
    private static final String WHITE_ICON = IMAGES_DIR + "white.png";

    private ImageIcon grayIcon;
    private ImageIcon blackIcon;
    private ImageIcon whiteIcon;

    private Socket socket;
    private String host;

    private JFrame clientFrame;
    private JFrame loginFrame;
    private BoardComponent boardComponent;
    private JLabel colorLabel;
    private JLabel msgLabel;
    private JLabel nameLabel;
    private JLabel scoreLabel;
    private JButton btnNewGame;
    private JButton btnResign;
    private JButton btnQuit;
    private JButton btnEnterRoom;
    private JTextField nameTextFiled;
    private JTextArea nameListTextArea;
    ArrayList<String> listOfNames;

    private JPanel centerPanel;
    private JPanel loginPanel;

    private JPanel rightPanel;
    private JPanel playersPanel;
    private JLabel lbPl1;
    private JLabel lbPl2;
    private JLabel lbBlackStone;
    private JLabel lbWhiteStone;

    private JScrollPane atsScrollPane;

    private JScrollPane chatScrollPane;
    private JTextArea chatArea;
    private JTextField chatInputTextField;
    private JButton btnSendMsg;

    private boolean done;

    private int color;
    private int opponentColor;
    private String clientName;

    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private ClientAction clientAction;

    private static int ID = 1;
    private int clientId;

    public Client(String host){
        listOfNames = new ArrayList<>();
        blackIcon = new ImageIcon(BLACK_ICON);
        whiteIcon = new ImageIcon(WHITE_ICON);
        grayIcon = new ImageIcon(GRAY_ICON);

        this.clientAction = new ClientAction();
        genLoginGui();
        genClientGui();

        this.host = host;
//        connect(host);

    }

    private void connect(){
        openConnection(host);

        color = 0;

        if (socket != null && !socket.isClosed()){
            done = true;
            new Thread( this ).start();
        }else{
            report("Cannot connect to host");
        }
        clientId = ID;
        ID ++;

        this.done = false;
    }

    @Override
    public void run() {
        try {
            responseToServer();
        } catch (ReversiException e) {
            report(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    /**
     * manipulate the response to server based on the server command
     * @throws IOException
     */
    private void responseToServer() throws ReversiException {
        try {
            while (!done) {
                int msg = fromServer.readInt();
                report("Msg from server: " + msg);
                switch (msg) {
                    case ENTERROOM:
                        othersEnterRoom();
                        break;
                    case ASSIGN:
                        assign();
                        break;
                    case TURN:
                        turn();
                        break;
                    case DRAWBLACK:
                        drawBlack();
                        break;
                    case DRAWWHITE:
                        drawWhite();
                        break;
                    case READY:
                        ready();
                        break;
                    case INVALIDTRY:
                        invalidTry();
                        break;
                    case INVALID:
                        invalidMove();
                        break;
                    case WIN:
                        win();
                        break;
                    case RESIGN:
                        resign();
                        break;
                    case NEWGAME:
                        startGame();
                        break;
                    case QUIT:
                        othersQuit();
                        break;
                    case DONE:
                        doQuit();
                        break;
                    case RETRIEVE:
                        retrieve();
                        break;
                    case CHAT:
                        showMessage();
                        break;
                    default:
                        report("uncaught: " + msg);
                }
            }
        } catch (IOException e){

            throw new ReversiException("Quit, unsolved exception catch.");

        }
    }

    /**
     * calculate the score
     * @return score
     */
    private int calScore(int color){
        int[][] board = boardComponent.getBoard();
        int count = 0;
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                if(board[i][j] == color)
                    count++;
            }
        }
        return count;
    }

    /******************************************** SEND REQUEST TO SERVER ********************************************/


    /**
     * player send PLAY command
     * when user tries to play a stone on the board[i][j], tell server the position
     * @param i position x in board
     * @param j position y in board
     */
    public void play(int i, int j) throws IOException {
        report("Client --> server: PLAY: "+i+","+j);
        toServer.writeInt(PLAY);
        toServer.writeInt(i);
        toServer.writeInt(j);
    }

    /**
     * client send ENTERROOM request to server
     */
    private void enterRoom() {
        connect();

        clientName = nameTextFiled.getText();
        try {
            toServer.writeInt(ENTERROOM);
            toServer.writeUTF(clientName);
            toServer.flush();
            report("Client ----> Server: " + ENTERROOM + " " + clientId + " " + nameTextFiled.getText());

            clientFrame.setTitle(clientName);
            btnNewGame.setEnabled(true);

//            loginPanel.setVisible(false);
//            centerPanel.setVisible(true);
            // disable the login frame component
            nameTextFiled.removeActionListener(clientAction);
            nameTextFiled.setEnabled(false);
            btnEnterRoom.removeActionListener(clientAction);
            btnEnterRoom.setEnabled(false);

            // close the login frame
            loginFrame.setVisible(false);
            loginFrame.dispose();

            // open the client frame
            clientFrame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * client send READY request to server
     */
    private void newGame() {
        try {
            toServer.writeInt(READY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * client send RESIGN request
     */
    private void resign(){
        try {
            // send the command and the color
            toServer.writeInt(RESIGN);
            toServer.writeInt(color);
        } catch (IOException e) {
            e.printStackTrace();
        }
        report("Client --> server: RESIGN " + this.color);

        endGame();
    }

    /**
     * client send QUIT request
     */
    private void quit() {
        try {
            toServer.writeInt(QUIT);
            doQuit();
            report("Client --> server: QUIT ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * the connection will be closed on server side,
     * here just disable
     */
    private void doQuit() throws IOException {
        report("client quits");
        this.done = true;
        toServer.close();
        fromServer.close();
        socket.close();

        boardComponent.setBoard(new int[SIDE][SIDE]);
        boardComponent.repaint();

        chatInputTextField.setEditable(false);
        btnNewGame.setEnabled(false);
        btnResign.setEnabled(false);
        btnQuit.setEnabled(false);
        btnSendMsg.setEnabled(false);

        btnNewGame.removeActionListener(clientAction);
        btnResign.removeActionListener(clientAction);
        btnSendMsg.removeActionListener(clientAction);
        btnQuit.removeActionListener(clientAction);
        btnSendMsg.removeActionListener(clientAction);

        boardComponent.setEnabled(false);

        clientFrame.setTitle(clientName + " - DISCONNECTED");
        setMsgText("You quited.");

    }

    private void chat(){
        report("Chatting");
        String msg = chatInputTextField.getText().trim();
        if(msg == null || msg.equals("")){
            report("empty message");
        }else{
            try {
                toServer.writeInt(CHAT);
                toServer.writeUTF(clientName + ": " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        chatInputTextField.setText("");
    }

    /******************************************** RESPONSE TO SERVER ********************************************/

    /**
     * retrieve the game date
     */
    private void retrieve() throws IOException {
        String data = fromServer.readUTF();
//        report("data: " + data);
        String[] datas = data.split(COMMAND_DELIMITER);
        int isFull = Integer.parseInt(datas[0]);

        if(isFull == 1) {
            // points
            String[] points = datas[1].split(POINTS_DELIMITER);
            int[][] board = new int[SIDE][SIDE];
            for (int i = 0; i < points.length; i++) {
                board[i / SIDE][i % SIDE] = Integer.parseInt(points[i]);
            }
            boardComponent.setBoard(board);
            boardComponent.repaint();

            // players
            String[] players = datas[2].split(POINTS_DELIMITER);
            lbPl1.setText(players[0]);
            lbPl2.setText(players[1]);
            lbBlackStone.setIcon(blackIcon);
            lbWhiteStone.setIcon(whiteIcon);


            colorLabel.setText("You are a viewer.");
            setButtons(false);
            btnNewGame.setEnabled(false);
        }

        // attendants
        try {
            String[] attendants = datas[3].split(POINTS_DELIMITER);
            for (String str : attendants) {
                listOfNames.add(str);
                nameListTextArea.append(str + "\n");
            }
//            nameListTextArea.update(nameListTextArea.getGraphics());
//            atsScrollPane.setViewportView(nameListTextArea);
        }catch(Exception e){
            // when the first player enter the room, there is no existed attendants
        }

    }

    private void othersEnterRoom() {
        String msg;
        try {
            msg = fromServer.readUTF();
            String[] msgs = msg.trim().split(" ");
            int cId = Integer.parseInt(msgs[0]);
            String name = msgs[1];
            listOfNames.add(name);
            report("from server: Other enters: " + cId + " " + name);
            updateAttendantsList(name);
        } catch (IOException e) {
            report("Others enter room fails" );
            e.printStackTrace();
        }
    }

    /**
     * response to server READY command
     */
    private void ready(){
        setButtons(false);
        setMsgText("You are READY.");
    }

    /**
     * response to server INVALIDTRY command
     * if there are already 2 players in game, cannot join game
     */
    private void invalidTry(){
        setButtons(false);
        setMsgText("There are 2 players in game, you cannot join in now.");
    }

    /**
     * response to server NEWGAME command
     * after 2 players are in READY for game, then the game will start
     * for the other attendants, display message as "viewer".
     */
    private void startGame(){
        try {
            String playersName = fromServer.readUTF();
            lbPl1.setText(playersName.split(POINTS_DELIMITER)[0]);
            lbPl2.setText(playersName.split(POINTS_DELIMITER)[1]);
            lbWhiteStone.setIcon(whiteIcon);
//            lbWhiteStone.setText(playersName.split(POINTS_DELIMITER)[1]);
            lbBlackStone.setIcon(blackIcon);
//            lbBlackStone.setText(playersName.split(POINTS_DELIMITER)[0]);

            boardComponent.initiate();
            boardComponent.repaint();
            setScoreText();
            if(this.color == 0) {
                setMsgText("");
                colorLabel.setText("You are a viewer.");
                setButtons(false);
                btnNewGame.setEnabled(false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * response to server ASSIGN command
     * @throws IOException
     */
    private void assign() throws IOException, ReversiException {
        this.color = fromServer.readInt();
        if(this.color == BLACK){
            opponentColor = WHITE;
            colorLabel.setText("YOU ARE BLACK");
            boardComponent.setEnabled(true);
            setMsgText("Game start! It's your turn");
        }else if (this.color == WHITE){
            opponentColor = BLACK;
            colorLabel.setText("YOU ARE WHITE");
            setMsgText("Game start! Waiting for your opponents");
        }else {
            report("Something wrong in the color assign");
        }

        // display text
        setScoreText();

        // active the buttons
        setButtons(true);

        // after assign, keep listening to the NEWGAME command
        responseToServer();
    }

    /**
     * response to TURN command
     * take turns, if the turn is the current player's color, then it's current players turn, otherwise not
     * @throws IOException
     */
    private void turn() throws IOException {
        // get the color of turn
        int turn = fromServer.readInt();

        if(turn == this.color){
            boardComponent.setEnabled(true);
            setMsgText("It's your turn");
        } else if (turn == this.opponentColor) {
            boardComponent.setEnabled(false);
            setMsgText("Waiting");
        } else {

        }
    }

    /**
     * response to DRAWBLACK command
     * @throws IOException
     */
    private void drawBlack() throws IOException, ReversiException {
        playSound(PLAY_SOUND);
        // get the list of stones need to be changed
        String list = fromServer.readUTF();
        // draw black the list of stones
        boardComponent.drawBlack(list);

        setScoreText();

        // followed by the TURN command
        responseToServer();
    }

    /**
     * response to DRAWWHITE command
     * @throws IOException
     */
    private void drawWhite() throws IOException, ReversiException {
        playSound(PLAY_SOUND);
        // get the list of stones need to be changed
        String list = fromServer.readUTF();
        // draw white the list of stones
        boardComponent.drawWhite(list);
        setScoreText();

        // followed by the TURN command
        responseToServer();
    }

    /**
     * play the sound file
     * @param soundFile sound file path
     */
    private void playSound(String soundFile){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * response to INVALID command
     * the move is not valid
     */
    private void invalidMove(){
        setMsgText("That's a invalid move");
        playSound(WRONG_SOUND);
    }

    /**
     * response to WIN command
     * @throws IOException
     */
    private void win() throws IOException {
        // get the color who wins
        int color = fromServer.readInt();

        if(color == this.color){
            playSound(WIN_SOUND);
            setMsgText("You win!");
        } else if (color == this.opponentColor){
            // opponent wins
            setMsgText("You lose.");
        } else {
            playSound(WIN_SOUND);
            // if the client is a viewer
            if(color == BLACK){
                setMsgText("BLACK wins!");
            }else{
                setMsgText("WHITE wins!");
            }
        }

        // end game
        endGame();
    }

    /**
     * end game
     */
    private void endGame(){
        setButtons(false);
        boardComponent.setEnabled(false);
        color = 0;
    }


    /**
     *
     */
    private void othersQuit() throws IOException {
        String name = fromServer.readUTF();

        if(listOfNames.contains(name)) {
            listOfNames.remove(name);
        }

        StringBuilder str = new StringBuilder();
        for(String s: listOfNames){
            str.append(s).append("\n");
        }
        nameListTextArea.setText(str.toString());
        report("client: " + name + " quit.");
    }

    /**
     * open connection to the server
     * @param host
     */
    private void openConnection(String host){
        try {
            this.socket = new Socket(host, PORT);
            this.fromServer = new DataInputStream(socket.getInputStream());
            this.toServer = new DataOutputStream(socket.getOutputStream());

        } catch (SecurityException e){
            report("Connect is not allowed");
        } catch (UnknownHostException e) {
            report("the ip address is not found.");
        } catch (IOException e){
            report("Can not connect to the server\""+host+"\"");
        }
    }

    /**
     * close the connection to server
     */
    private void closeConnection(){
        report("Close connection");
        try{
            if(socket != null && !socket.isClosed()) {
                report("Client: Close socket");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        report("End");
    }

    /**
     * display other client message
     * @throws IOException
     */
    private void showMessage() throws IOException {
        String msg = fromServer.readUTF();
        chatArea.append(msg + "\n");
        // set the chat area always keep focus at bottom
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    /**
     * print client log
     * @param s
     */
    public void report(String s){
        System.out.println(s);
    }

    /********************************************* CLIENT GUI *********************************************/

    /**
     * generate login gui
     */
    private void genLoginGui(){
        loginFrame = new JFrame("Log in");

        // login panel, inputs name
        loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());
        nameLabel = new JLabel("Name");
        nameTextFiled = new JTextField();
        nameTextFiled.setPreferredSize(new Dimension(100, 20));
        nameTextFiled.addActionListener(clientAction);
        nameTextFiled.setActionCommand("ENTERROOM");

        btnEnterRoom = new JButton("Enter Room");
        btnEnterRoom.addActionListener(clientAction);
        btnEnterRoom.setActionCommand("ENTERROOM");

        loginPanel.add(nameLabel);
        loginPanel.add(nameTextFiled);
        loginPanel.add(btnEnterRoom);

        loginFrame.add(loginPanel, BorderLayout.CENTER);

        loginFrame.setSize(700, 500);
        loginFrame.setLocation(0, 200);
        loginFrame.add(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    /**
     * Generate a client GUI
     */
    private void genClientGui(){

        // client Frame
        clientFrame = new JFrame();

        colorLabel = new JLabel("Color:");
        msgLabel = new JLabel();
        scoreLabel = new JLabel("Score: " );
        boardComponent = new BoardComponent(this);

        btnNewGame = new JButton("New Game");
        btnQuit = new JButton("Quit");
        btnResign = new JButton("Resign");

        // top panel, contains the color, score and message labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1));
        topPanel.add(colorLabel);
        topPanel.add(scoreLabel);
        topPanel.add(msgLabel);

        // bottom panel, contains buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnResign);
        bottomPanel.add(btnQuit);



        // cneter panel, contains the game board
        centerPanel = new JPanel();
        centerPanel.add(boardComponent);
        centerPanel.setVisible(true);

        generateRightPanel();

        initiateClient();

        // add everything to the frame
        clientFrame.setSize(700, 500);
        clientFrame.setLocation(0, 200);
        clientFrame.setLayout(new BorderLayout());
        clientFrame.add(topPanel, BorderLayout.NORTH);
        clientFrame.add(centerPanel, BorderLayout.CENTER);
        clientFrame.add(bottomPanel, BorderLayout.SOUTH);
        clientFrame.add(rightPanel, BorderLayout.EAST);
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setVisible(false);

    }

    /**
     * generate right panel
     *
     */
    private void generateRightPanel(){
        // right panel, contains the players info, attendant list and chat implementation
        rightPanel = new JPanel();
//        rightPanel.setPreferredSize(new Dimension(300, 400));
        rightPanel.setLayout(new GridLayout(3,1));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));


        // right top part
        JPanel pplPanel = new JPanel();
        pplPanel.setPreferredSize(new Dimension(RIGHT_WIDTH, 80));
        pplPanel.setLayout(new GridLayout(2,1));


        // players and attendants panel

        JPanel leftP = new JPanel();
        leftP.setLayout(new FlowLayout());
        JPanel rightP = new JPanel();
        rightP.setLayout(new FlowLayout());
        lbBlackStone = new JLabel();
        lbWhiteStone = new JLabel();
        lbBlackStone.setIcon(grayIcon);
        lbWhiteStone.setIcon(grayIcon);

        lbPl1 = new JLabel();
        lbPl2 = new JLabel();
        leftP.add(lbBlackStone);
        leftP.add(lbPl1);
        rightP.add(lbWhiteStone);
        rightP.add(lbPl2);

        playersPanel = new JPanel();
        playersPanel.setPreferredSize(new Dimension(RIGHT_WIDTH, 40));
        playersPanel.setLayout(new GridLayout(1,2));
        playersPanel.add(leftP);
        playersPanel.add(rightP);

        pplPanel.add(playersPanel);
        // END OF right top

        // right mid part
        // attendant list
        JPanel attendantsPanel = new JPanel();
        attendantsPanel.setPreferredSize(new Dimension(RIGHT_WIDTH, 60));
        attendantsPanel.add(new JLabel("Attendants List:"));
        nameListTextArea = new JTextArea();
        nameListTextArea.setEditable(false);

        atsScrollPane = new JScrollPane(nameListTextArea);
        atsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        atsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        atsScrollPane.setPreferredSize(new Dimension(RIGHT_WIDTH - BUFF, 80));
        attendantsPanel.add(atsScrollPane);
        //END OF right mid

        // right bottom part
        // chat panel
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new FlowLayout());

        JLabel msgLabel = new JLabel("Message: ");

        chatPanel.setPreferredSize(new Dimension(RIGHT_WIDTH, 100));
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setPreferredSize(new Dimension(RIGHT_WIDTH - BUFF, 60));

        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(RIGHT_WIDTH - BUFF , 200));
        chatInputTextField = new JTextField();
        chatInputTextField.setPreferredSize(new Dimension(RIGHT_WIDTH - 2*BUFF - 15 , 30));
        chatInputTextField.addActionListener(clientAction);
        chatInputTextField.setActionCommand("CHAT");
        btnSendMsg = new JButton("Send");
        btnSendMsg.setPreferredSize(new Dimension(55, 30));
        inputPanel.add(chatInputTextField);
        inputPanel.add(btnSendMsg);

        chatPanel.add(msgLabel);
        chatPanel.add(chatScrollPane);
        chatPanel.add(inputPanel);
        // END OF right bottom

        rightPanel.add(pplPanel);
        rightPanel.add(attendantsPanel);
        rightPanel.add(chatPanel);
    }

    /**
     * initiate actions and buttons
     */
    private void initiateClient(){
        btnNewGame.addActionListener(clientAction);
        btnNewGame.setActionCommand("NEWGAME");
        btnQuit.addActionListener(clientAction);
        btnQuit.setActionCommand("QUIT");
        btnResign.addActionListener(clientAction);
        btnResign.setActionCommand("RESIGN");
        btnSendMsg.addActionListener(clientAction);
        btnSendMsg.setActionCommand("CHAT");

        setButtons(false);
        btnNewGame.setEnabled(false);
        boardComponent.initiate();
    }

    /**
     * set all the buttons enable attribute
     * @param en true/false
     */
    private void setButtons(boolean en){
        btnQuit.setEnabled(!en);
        btnResign.setEnabled(en);
        btnNewGame.setEnabled(!en);
    }

    /**
     * set score label text
     */
    private void setScoreText(){
        if(color != BLACK && color != WHITE){
            scoreLabel.setText("Score: Black - " + calScore(BLACK) + " White - " + calScore(WHITE));
        }else {
            scoreLabel.setText("Score: " + calScore(this.color));
        }
    }

    /**
     * set message label text
     * @param msg
     */
    public void setMsgText(String msg){
        msgLabel.setText(msg);
    }

    /**
     * update the
     */
    private void updateAttendantsList(String name){
        nameListTextArea.append(name + "\n");
//        nameListTextArea.update(nameListTextArea.getGraphics());
    }

    /**
     * Button actions
     */
    class ClientAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("RESIGN")){
                resign();
            }else if(e.getActionCommand().equals("QUIT")){
                quit();
            }else if(e.getActionCommand().equals("ENTERROOM")){
                String name = nameTextFiled.getText();
                // name can not be empty
                if(name != null && !name.equals("")) {
                    enterRoom();
                }
            }else if(e.getActionCommand().equals("NEWGAME")){
                newGame();
            }else if(e.getActionCommand().equals("CHAT")){
                report("clicked chat");
                chat();
            }
        }
    }

    public static void main(String[] args) {
//        args = new String[]{"-host","localhost"};
        if (args.length > 0) {
            String arg = args[0];
            if (arg.equals("-help")) {
                displayHelp();
            } else if (arg.equals("-host")) {

                String host = args[1];
                playGame(host);

            } else if (arg.equals("-sound")) {
                displaySoundDir();
            } else if (arg.equals("-images")) {
                displayImagesDir();
            }

        } else {
            displayHelp();
        }
    }

    /**
     * play game
     * @param host
     */
    private static void playGame(String host){
        new Client(host);
    }

    /**
     * display the help information
     */
    private static void displayHelp(){
        System.out.println("-host hostaddress: \n" +
                            "      connect the host and run the game \n" +
                            "-help: \n" +
                            "      display the help information \n" +
                            "-images: \n" +
                            "      display the images directory \n" +
                            "-sound: \n" +
                            "      display the sounds file directory \n"

        );
    }

    /**
     * display the sounds file directory
     */
    private static void displaySoundDir(){
        System.out.println(SOUNDS_DIR);
    }

    /**
     * display the sounds file directory
     */
    private static void displayImagesDir(){
        System.out.println(IMAGES_DIR);
    }


}
