import java.util.Stack;

public class AI_Minimax implements AI{

    @Override
    public int decideTurn(Board board) {
        return 0;
    }

    public static int miniMax(Board board) {
        Stack<Integer> posActions = board.possibleActions();
        int untility = Integer.MIN_VALUE;
        int move = 0;

        while(!posActions.isEmpty()) {
            int pAction = posActions.pop();
            int val =
        }
    }


}
