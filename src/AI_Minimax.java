import java.util.ArrayList;


public class AI_Minimax implements AI{
    /*
    AI_Minimax uses alpha beta pruning on a heuristic minimax algorithm
     */
    static int depthLimit = 1;
    static int depth = 0;
    @Override
    public int decideTurn(Board board) {
        
        ArrayList<Integer> pActions = board.possibleActions();
        int maxCol = 0;
        int maxValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for(int i = 0; i < pActions.size(); i++) {
            Board cb = Board.copyBoard(board);
            cb = Board.dropChip(cb, pActions.get(i));
            // System.out.printf("Move on %s \n", pActions.get(i));
            if(maxValue <= minimax(cb, 5, alpha, beta, false)) {
                // System.out.printf("Max Col now %s \n", pActions.get(i));
                maxValue = minimax(cb, 5, alpha, beta, false); // Bug, if it isn't odd depth it will be the wrong choice
                maxCol = pActions.get(i);
                if(alpha >= beta) {
                    break;
                }
            }
        }

        return maxCol;
    }

    public int minimax(Board board, int depth, int alpha, int beta, boolean maxPlayer) {
        if( depth == 0 || board.isTerminal()) {
            // GameIO.printBoard(board);
            if(maxPlayer) {
                // System.out.printf("Max Board Score: %s \n", board.scoreBoard());
            } else {
                // System.out.printf("Min Board Score: %s \n", board.scoreBoard());
            }

            return board.scoreBoard();
        }
        if(maxPlayer) { // Max Player
           int val = Integer.MIN_VALUE;
           ArrayList<Integer> pactions = board.possibleActions();
           for(int i = 0; i < pactions.size(); i ++) {
               Board cb = Board.copyBoard(board);
               cb = Board.dropChip(cb, pactions.get(i));

               val = Math.max(val, minimax(cb, depth -1, alpha, beta, false));
               alpha = Math.max(alpha, val);
               if(alpha >= beta) {
                   break;
               }
           }
           return val;

        } else { // Min Player
            int val = Integer.MAX_VALUE;
            ArrayList<Integer> pactions = board.possibleActions();
            for(int i = 0; i < pactions.size(); i ++) {
                Board cb = Board.copyBoard(board);
                cb = Board.dropChip(cb, pactions.get(i));

                val = Math.min(val, minimax(cb, depth -1, alpha, beta, true));
                beta = Math.min(beta, val);
                if(alpha >= beta) {
                    break;
                }
            }
            return val;

        }
    }


}
