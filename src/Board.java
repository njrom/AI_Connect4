
import java.util.Stack;

public class Board {
    // Class Variables
    private final int height;
    private final int width;
    private final int nToWin;
    private Player[][] data;

    public void setManTurn(boolean manTurn) {
        isManTurn = manTurn;
        currentPlayer = isManTurn ? Player.X : Player.O;
    }

    private boolean isManTurn = false;
    public Player currentPlayer = Player.O;
    private Integer[] colScore = {0, 0, 0, 0, 0, 0, 0};

    public Board (int width, int height, int nToWin) {
        this.width = width;
        this.height = height;
        this.nToWin = nToWin;
        initBoard();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getnToWin() {
        return nToWin;
    }

    public boolean getManTurn() {
        return isManTurn;
    }

    public Player[][] getData() {
        return data;
    }


    private void initBoard() {

        Player[][] data = new Player[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                data[i][j] = Player.E;
            }
        }
        this.data = data;
    }

    public Stack<Integer> possibleActions() {

        Stack<Integer> playableColumns = new Stack<>();
        for(int i = 0; i < width; i++) {
            if(data[i][0] == Player.E){
                playableColumns.add(i);
            }
        }
        return playableColumns;
    }

    public static Board dropChip(Board b, int column) {
        int columnToDrop = column; // Regular players are not going to want to count from 0 to n-1

        for(int ch = 0; ch < b.getHeight(); ch++) {
            if (ch + 1 == b.getHeight()) {
                b.data[columnToDrop][ch] = b.currentPlayer;
                break;
            } else if (b.data[columnToDrop][ch + 1] != Player.E) {
                b.data[columnToDrop][ch] = b.currentPlayer;
                break;
            }
        }
        b.setManTurn(!b.isManTurn);
        return b;
    }


    public boolean isTerminal() {
        // Checks if endgame (either board full or someone won)
        if (checkWin() != Player.E) {
            return true;
        } else {
            for(int i = 0; i < width; i++) {
                if(data[i][0] == Player.E) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean columnPlayable(int column) {

        return data[column][0] == Player.E; // false means column is full
    }

    public Player checkWin() {
        // TODO: Need to Generalize, currently only works for 3x3x3

        // horizontalCheck
        for (int j = 0; j<getHeight()-2 ; j++ ){
            for (int i = 0; i<getWidth(); i++){
                if ((this.data[i][j] == this.data[i][j+1]) && (this.data[i][j+2] == this.data[i][j+1]) && (this.data[i][j] != Player.E)){
                    return this.data[i][j];
                }
            }
        }
        // verticalCheck
        for (int i = 0; i<getWidth()-2 ; i++ ){
            for (int j = 0; j<this.getHeight(); j++) {
                if ((this.data[i][j] == this.data[i+1][j]) && (this.data[i+2][j] == this.data[i+1][j]) && (this.data[i][j] != Player.E)){
                    return this.data[i][j] ;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i=2; i<getWidth(); i++){
            for (int j=0; j<getHeight()-2; j++) {
                if ((this.data[i][j] == this.data[i-1][j+1]) && (this.data[i-2][j+2] == this.data[i-1][j+1]) && (this.data[i][j] != Player.E)) {
                    return this.data[i][j];
                }
            }
        }
        // descendingDiagonalCheck
        for (int i=2; i<getWidth(); i++){
            for (int j=2; j<getHeight(); j++) {
                if ((this.data[i][j] == this.data[i-1][j-1]) && (this.data[i-2][j-2] == this.data[i-1][j-1] ) && (this.data[i][j] != Player.E)) {
                    return this.data[i][j];
                }
            }
        }
        return Player.E;
    }

    public static Board copyBoard(Board b) {
        //Loop thru array and copy ints
        Board newB = new Board(b.getnToWin(),b.getHeight(),b.getWidth());
        newB.setManTurn(b.getManTurn());
        for(int h = 0; h < b.getHeight(); h++)
        {
            for(int w = 0; w < b.getWidth(); w++)
            {
                newB.setDataVal(h, w, b.getData()[h][w]);
            }
        }
        return newB;
    }

    public void setDataVal(int h, int w, Player e)
    {
        data[h][w] = e;
    }


}
