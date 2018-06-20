package project.assign9;

public interface ReversiRule {
    boolean TESTFORWIN = false;
    int SIDE = 8;
    int BLACK = 1;
    int WHITE = -1;

    String POINTS_DELIMITER = ",";
    String XY_DELIMITER = "-";

    String[] cmd = new String[]{
//        "ENTERROOM",
        "ASSIGN",
        "TURN",
        "DRAWBLACK",
        "DRAWWHITE",
//        "INVALID",
        "WIN",
        "RESIGN",
//        "QUIT"
    };

    String ENTERROOM = "ENTERROOM";
    String ASSIGN    = "ASSIGN";
    String TURN      = "TURN";
    String DRAWBLACK = "DRAWBLACK";
    String DRAWWHITE = "DRAWWHITE";
    String INVALID   = "INVALID";
    String WIN       = "WIN";
    String RESIGN    = "RESIGN";
    String QUIT      = "QUIT";

    /**
     * initiate a board
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
