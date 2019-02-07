import java.util.*;


public class test {



    public static void main(String[] args) {
        GameIO game = new GameIO();
        Board board = new Board(9, 9,3);
        System.out.println();
        board = board.dropChip(board, 3);


        board = board.dropChip(board, 2);
        game.printBoard(board);
        board = board.dropChip(board, 3);
        game.printBoard(board);
        board = board.dropChip(board, 2);
        game.printBoard(board);
        board = board.dropChip(board, 3);
        game.printBoard(board);
        board.checkWin();
    }

}
