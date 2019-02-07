import java.util.Stack;

public class AI_Minimax implements AI{

    @Override
    public int decideTurn(Board board) {
        return miniMax(board);

    }

    public static int miniMax(Board board) {
        Stack<Integer> posActions = board.possibleActions();
        int utility = -100;
        int move = 0;

        while(!posActions.isEmpty()) {
            int pAction = posActions.pop();
            Board cb = Board.copyBoard(board);
            cb = cb.dropChip(cb, pAction);
            int val = minV(cb); // Start with minVal because evaluating this states children

            if(val >= utility) {
                utility = val;
                move = pAction;
            }
        }
        return move;
    }

    public static int minV(Board b) {

        if(b.isTerminal()) {
            return utility(b);
        }

        Stack<Integer> pActions = b.possibleActions();
        int minimum = Integer.MAX_VALUE;
        while(!pActions.isEmpty()) {
            minimum = Math.min(minimum, maxV(Board.dropChip(b, pActions.pop())));
        }
        return minimum;
    }

    public static int maxV(Board b) {

        if(b.isTerminal()) {
            return utility(b);
        }

        Stack<Integer> pActions = b.possibleActions();
        System.out.println(pActions);
        int maximum = Integer.MIN_VALUE;
        while(!pActions.isEmpty()) {
            maximum = Math.max(maximum, minV(Board.dropChip(b, pActions.pop())));
        }

        return maximum;

    }


    private static int utility(Board b) {
        Player e = b.checkWin();

        if(e == Player.O) {
            return 1;
        } else if (e == Player.X) {
            return -1;
        } else {
            return 0;
        }

    }

}
