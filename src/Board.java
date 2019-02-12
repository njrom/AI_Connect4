
import java.util.ArrayList;
import java.util.Arrays;
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

    private boolean isManTurn = true;
    public Player currentPlayer = Player.O;


    public Board (int width, int height, int nToWin) {
        this.width = width;
        this.height = height;
        this.nToWin = nToWin;
        this.currentPlayer = isManTurn ? Player.X : Player.O;
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

    public ArrayList<Integer> possibleActions() {

        ArrayList<Integer> playableColumns = new ArrayList<>();
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
        if (this.scoreBoard() <= -100000000 || this.scoreBoard() >= 100000000 ) { // TODO: Reformat Win condition
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
        // TODO: Need to rewrite so that it is continuous, not discrete (-100, 100) means win for AI or Man
        int boardScore = 0;
        // Vertical Check
        for (int j = 0; j<getHeight()-2 ; j++ ){

            for (int i = 0; i<getWidth(); i++){
                // TODO: Track The number of players in a row, and if we saw an empty space
                if ((this.data[i][j] == this.data[i][j+1]) && (this.data[i][j+2] == this.data[i][j+1]) && (this.data[i][j] != Player.E)){
                    return this.data[i][j];
                }
            }
        }

        // Horizontal Check
        for (int j = 0; j<this.getHeight(); j++) {
            Player p = null;
            boolean seenEmpty = false;
            int streak = 0;
            for (int i = 0; i<this.getWidth()-2 ; i++ ) {

                // TODO: Need to track same player streak and if we saw a blank yet
                if (p != null) {
                    if (p == data[i][j]) {
                        int sign = (p == Player.O) ? 1 : -1;
                        streak = streak + 1*sign;

                    } else {
                        if(seenEmpty || data[i][j] == Player.E) { // if next is empty
                            // TODO: add weight to boardscore based on what the streak was
                        }
                        streak = 0; // Reset streak;
                    }
                }
                p = data[i][j];
                seenEmpty = p == Player.E;


//
//                if ((this.data[i][j] == this.data[i+1][j]) && (this.data[i+2][j] == this.data[i+1][j]) && (this.data[i][j] != Player.E)){
//                    return this.data[i][j] ;
//                }
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
        Board newB = new Board(b.getWidth(), b.getHeight(), b.getnToWin());
        newB.setManTurn(b.getManTurn());
        for(int h = 0; h < b.getHeight(); h++)
        {
            for(int w = 0; w < b.getWidth(); w++)
            {
                newB.setDataVal(h, w, b.getData()[w][h]);
            }
        }
        return newB;
    }

    public static Board reflectBoard(Board b) {
        //Loop thru array and copy ints
        Board newB = new Board(b.getWidth(), b.getHeight(), b.getnToWin());
        newB.setManTurn(b.getManTurn());
        for(int h = 0; h < b.getHeight(); h++)
        {
            for(int w = 0; w < b.getWidth(); w++)
            {
                newB.setDataVal(h, b.getWidth()-(1+w), b.getData()[h][w]);
            }
        }
        return newB;
    }

    public static int scoreLine(String line){
        // TODO: Implement checking for each different case of possible configs that count for points
        // TODO: Remove part of string that I have scored, so a ___000 doesn't get points for _0, _00 and _000
        // check cases _0 0_ _00 00_ _000 000_ 0000
        final int fourScore = 1000000000;
        final int threeDEScore = 500;
        final int threeScore = 300;
        final int twoScore = 20;
        final int oneScore = 1;
        int score = 0;
        if(line.contains("OOOO")){
            line = line.replaceFirst("OOOO","");
            score = fourScore;
            return score;
        }
        if(line.contains("XXXX")){
            line = line.replaceFirst("XXXX","");
            score = -fourScore;
            return score;
        }

        // Threes
        while(line.contains(" OOO ") ) {
            score += threeDEScore;
            line = line.replaceFirst(" OOO ", "");
        }
        while(line.contains(" XXX ") ) {
            score -= threeDEScore;
            line = line.replaceFirst(" XXX ", "");
        }
        while(line.contains(" OOO") ) {
            score += threeScore;
            line = line.replaceFirst(" OOO", "");
        }
        while(line.contains(" XXX") ) {
            score -= threeScore;
            line = line.replaceFirst(" XXX", "");
        }
        while(line.contains("OOO ") ) {
            score += threeScore;
            line = line.replaceFirst("OOO ", "");
        }
        while(line.contains("XXX ") ) {
            score -= threeScore;
            line = line.replaceFirst("XXX ", "");
        }

        // Twos
        while(line.contains(" OO")){
            score += twoScore;
            line = line.replaceFirst(" OO","");
        }
        while(line.contains(" XX")){
            score -= twoScore;
            line = line.replaceFirst(" XX","");
        }
        while(line.contains("OO ")){
            score += twoScore;
            line = line.replaceFirst("OO ","");
        }
        while(line.contains("XX ")){
            score -= twoScore;
            line = line.replaceFirst("XX ","");
        }
//        Ones
        while(line.contains(" O")){
            score += oneScore;
            line = line.replaceFirst(" O","");
        }
        while(line.contains(" X")){
            score -= oneScore;
            line = line.replaceFirst(" X","");
        }
        while(line.contains("O ")){
            score += oneScore;
            line = line.replaceFirst("O ","");
        }
        while(line.contains("X ")){
            score -= oneScore;
            line = line.replaceFirst("X ","");
        }
        return score;
    }

    public void setDataVal(int h, int w, Player e)
    {
        data[w][h] = e;
    }

    public int scoreBoard() {

        int boardScore = 0;
        if(this.width ==3 && this.height == 3) {
            Player p = this.checkWin();
            if(p == Player.O) {
                boardScore = 1000000000;
                return boardScore;
            } else if(p == Player.X ){
                boardScore = -1000000000;
                return boardScore;
            } else {
                return boardScore;
            }

        }
        // Horizontal
        String line = "";
        for(int j = 0; j < this.height; j ++) {
            for (int i = 0; i < this.width; i++)
                line = line + this.data[i][j].toString();
            boardScore += scoreLine(line);
            line = "";
        }
        // Diagonal
        int maxLength = Math.max(this.width, this.height);
        for( int k = 0 ; k <= 2*(maxLength - 1); k++ ) {
            for( int j = this.height-1; j >= 0; j--) {
                int i = k - j;

                if(i >= 0 && i < this.width) {
                    line = line + this.data[i][j].toString();
                }
            }
            boardScore += scoreLine(line);
            line = "";
        }

        for( int k = 0 ; k <= 2*(maxLength - 1); k++ ) {
            for( int j = this.height-1; j >= 0; j--) {
                int i = k - (this.height - j);

                if(i >= 0 && i < this.width) {
                    line = line + this.data[i][j].toString();
                }
            }
            boardScore += scoreLine(line);
            line = "";
        }


        // Vertical
        for(int j = 0; j < this.width; j ++) {
            for (int i = 0; i < this.height; i++)
                line = line + this.data[j][i].toString();
            boardScore += scoreLine(line);
            line = "";
        }

        return boardScore;
    }

    public static void main(String[] args) {
        Board b = new Board(7, 7 , 4);
        b.initBoard();
        b.data[3][3] = Player.O;
        b.data[5][5] = Player.O;

        b.data[3][6] = Player.X;
        b.data[4][6] = Player.X;


        GameIO.printBoard(b);


    }

}
