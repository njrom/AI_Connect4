import java.util.Scanner;

public class GameMaster {

    public static void main(String[] args) {
        System.out.println("Welcome to Connect Four AI");
        System.out.println("__________________________");
        Scanner s = new Scanner(System.in);
        Board board = new Board(3,3,3);
        while(!board.isTerminal()) {
            GameIO.printBoard(board);
            System.out.printf("Your Move %s \n", board.currentPlayer);
            String input = s.next();

            try {
                int columnChoice = Integer.parseInt(input);

                //Column number is within 0->width
                if (columnChoice < 0 || columnChoice >= board.getWidth()) {
                    System.out.println("Please enter a valid column number.");
                    //If board doesn't complain that column is full
                } else if (!board.columnPlayable(columnChoice)) {
                    System.out.println("That column is full");
                    //Otherwise continue
                } else {
                    board = board.dropChip(board, columnChoice);
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid column number.");
            }
        }
        s.close();

        GameIO.printBoard(board);
        if(board.checkWin() == Player.X)
        {
            System.out.println("\nYou've won! You're smarter than your Computer");
        } else if (board.checkWin() == Player.O)
        {
            System.out.println("\n Your Computer is smarter than you! \n Sleep with one eye open from now on!");
        } else
        {
            System.out.println("Cat's Game! \n It's a tie!");
        }
    }
}
