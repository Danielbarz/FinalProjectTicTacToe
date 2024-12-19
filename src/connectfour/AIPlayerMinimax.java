package connectfour;

/** AIPlayer using Minimax algorithm */
public class AIPlayerMinimax extends Player {

    public AIPlayerMinimax(char symbol) {
        super(symbol);
    }

    @Override
    public void makeMove(TicTacToe game) {
        int[] bestMove = minimax(game, symbol);
        game.placeMove(bestMove[0], bestMove[1], symbol);
        System.out.println("AI placed " + symbol + " at (" + bestMove[0] + ", " + bestMove[1] + ")");
    }

    private int[] minimax(TicTacToe game, char player) {
        if (game.checkForWin()) {
            return new int[]{-1, -1}; // Game over
        }
        if (game.isBoardFull()) {
            return new int[]{-1, -1}; // Tie
        }

        int bestScore = (player == symbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.isValidMove(i, j)) {
                    game.placeMove(i, j, player);
                    int score = minimaxScore(game, player);
                    game.placeMove(i, j, '-'); // Undo move

                    if (player == symbol && score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    } else if (player != symbol && score < bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimaxScore(TicTacToe game, char player) {
        if (game.checkForWin()) {
            return (player == symbol) ? -1 : 1; // Return score based on who won
        }
        if (game.isBoardFull()) {
            return 0; // Tie
        }

        int bestScore = (player == symbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.isValidMove(i, j)) {
                    game.placeMove(i, j, player);
                    int score = minimaxScore(game, (player == symbol) ? 'O' : 'X');
                    game.placeMove(i, j, '-'); // Undo move

                    if (player == symbol) {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }
}
