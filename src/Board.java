
import java.util.Stack;

public class Board {

    // Class Variables
    private int height;
    private int width;
    private int nToWin;
    private Player[][] data;
    private boolean isManTurn = false;
    private Player currentPlayer = Player.X;
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

    public boolean isManTurn() {
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

    public Board dropChip(Board b, int column) {
        int columnToDrop = column - 1; // Regular players are not going to want to count from 0 to n-1
        currentPlayer = isManTurn ? Player.X : Player.O;

        // Check if invalid move (for stupid humans)
        if(data[column][0] != Player.E) {
            System.out.println("Invalid Move Try Again");
            return b; // Returns the board, but doesn't change who's turn it is
        }
        for(int ch = 0; ch < b.getHeight(); ch++) {
            if (ch + 1 == b.getHeight()) {
                b.data[columnToDrop][ch] = currentPlayer;
            } else if (b.data[columnToDrop][ch + 1] != Player.E) {
                b.data[columnToDrop][ch] = currentPlayer;
            }
        }

        if(b.checkWin() != Player.E) {
            System.out.printf("Game over %s wins! \n", currentPlayer);
        }
        isManTurn = !isManTurn;

        return b;
    }


    public boolean isTerminal() {
        // Checks if endgame (either board full or someone won)
        if (checkWin() != Player.E) {
            return true;
        } else {
            for(int i = 0; i < width; i++) {
                
            }
        }
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



}
