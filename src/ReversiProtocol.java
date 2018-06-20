/**
 * Interface that contain the common info of Reversi.
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */

public interface ReversiProtocol {
    boolean TESTFORWIN = false;
    int SIDE = 8;
    int BLACK = 1;
    int WHITE = -1;
    int PORT = 8888;

    String POINTS_DELIMITER = ",";
    String XY_DELIMITER = "-";
    String COMMAND_DELIMITER = "~";

    int[] cmd = new int[]{
            0,  // ENTERROOM
            1,  // ASSIGN
            2,  // TURN
            3,  // DRAWBLACK
            4,  // DRAWWHITE
            5,  // INVALID
            6,  // WIN
            7,  // RESIGN
            8,  // QUIT
            9,  // PLAY
            10, // READY
            11, // INVALIDTRY
            12, // NEWGAME
            13, // RETRIEVE
            14, // DONE
            15  // CHAT
    };

    /**
     * client send ENTERROOM request to server
     * if success, server would send ENTEROOOM to all the clients
     *
     */
    int ENTERROOM   = 0;

    /**
     * server send ASSIGN command to all clients
     * if the client is a player, then the color would be assigned with BLACK(1)
     * or WHITE(-1)
     * for the other viewer, the colors are 0
     *
     */
    int ASSIGN      = 1;

    /**
     * server send TURN command to all clients
     * if the client is a player, then the client is able to play
     * otherwise, not able to play
     */
    int TURN        = 2;

    /**
     * server send DRAWBLACK command to all clients
     * and followed by a String of list of changed stones
     * so the client can manipulate the data and draw black stones
     */
    int DRAWBLACK   = 3;

    /**
     * server send DRAWWHITE command to all clients
     * and followed by a String of list of changed stones
     * so the client can manipulate the data and draw white stones
     */
    int DRAWWHITE   = 4;

    /**
     * server receive the player's play move, and identifies it's INVALID
     * send INVALID command to the player, not to everyone, player will take
     * another move
     */
    int INVALID     = 5;

    /**
     * server send WIN command and followed by the color to all the clients
     */
    int WIN         = 6;

    /**
     * client send RESIGN request to server
     */
    int RESIGN      = 7;

    /**
     * client send QUIT request to server
     * server send QUIT command to all the clients
     */
    int QUIT        = 8;

    /**
     * client send PLAY request to server
     */
    int PLAY        = 9;

    /**
     * client send READY request to server
     */
    int READY       = 10;

    /**
     * server send INVALIDTRY command to client
     * if there are 2 players in game, server will send INVALIDTRY to the client
     */
    int INVALIDTRY  = 11;

    /**
     * server send NEWGAME to all clients, which notify all that the game starts
     */
    int NEWGAME     = 12;

    /**
     * server send RETRIEVE to new client.
     */
    int RETRIEVE    = 13;

    /**
     * server send DONE to the client
     */
    int DONE = 14;

    /**
     * client send CHAT request to the server
     * server send the CHAT content to all the clients
     */
    int CHAT = 15;

    /**
     * initiate a board
     * board is initiated with 4 stones, 2 black, 2 white
     *  in order to test the end of the game, set TESTFORWIN = true,
     *  then the game will be initiate with more stones
     *
     * @return  board
     */
    static int[][] initBoard(){
        int[][] board = new int[SIDE][SIDE];
        if(TESTFORWIN) {
            for (int i = 0; i < SIDE - 1; i++) {
                for (int j = 0; j < SIDE ; j++) {
                    if (i < SIDE / 2) {
                        board[i][j] = BLACK;
                    } else {
                        board[i][j] = WHITE;
                    }
                }
            }
            for(int j = 0 ; j < board[SIDE - 1].length - 4; j++){
                board[SIDE - 1][j] = BLACK;
            }
        }else{
            int tx = (SIDE - 1) / 2;
            board[tx][tx] = BLACK;
            board[tx + 1][tx + 1] = BLACK;
            board[tx][tx + 1] = WHITE;
            board[tx + 1][tx] = WHITE;
        }
        return board;
    }

}
