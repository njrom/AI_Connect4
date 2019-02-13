import java.util.Scanner;

public class GameMaster {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Connect Four AI");
        System.out.println("__________________________");
        System.out.println("What would you like to play?");
        System.out.println("a) Tiny 3x3x3 Connect-Three Game");
        System.out.println("b) Standard 6x7x4 Connect-Three Game");
        System.out.printf(":");
        String choice = s.next();
        while(!choice.equals("a") && !choice.equals("A") && !choice.equals("b") && !choice.equals("B")) {
            System.out.println("Invalid Input Try Again");
            choice = s.next();
        }
        Board board;
        if(choice.equals("a") || choice.equals("A")) {
            board = new Board(3,3,3);
        } else {
            board = new Board(6,7,4);
        }
        AI ai = new AI_Minimax();
        boolean playerTurn = getRandomBoolean();
        board.setManTurn(playerTurn);
        if(playerTurn) {
            System.out.println("You Get to make the first move!");
        } else {
            System.out.println("The AI was selected to make the first move");
        }

        while(!board.isTerminal()) {
            GameIO.printBoard(board);
            if(board.getManTurn()) {
                System.out.printf("Your Move %s \n :", board.currentPlayer);
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
                        board.setManTurn(false);

                    }

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid column number.");
                }
            } else {
                System.out.println("The AI is thinking...+-¬");
                int cdcd = ai.decideTurn(board);

                board = board.dropChip(board, cdcd);
                board.setManTurn(true);
            }
        }
        s.close();

        GameIO.printBoard(board);
        if(board.scoreBoard() <= -10000000)
        {
            System.out.println("\nYou've won! You're smarter than your Computer");
        } else if (board.scoreBoard() >= 10000000)
        {
            System.out.println("\n Your Computer is smarter than you! \n Sleep with one eye open from now on!");
        } else
        {
            System.out.println("Cat's Game! \n It's a tie!");
        }
    }
    private static boolean getRandomBoolean() {
        return Math.random() < 0.5;
        // I tried another approaches here, still the same result
    }
}
