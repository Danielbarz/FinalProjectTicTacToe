package jungleFrenzy;

public class AIPlayerMinimax extends Player {

    public AIPlayerMinimax(Seed seed) {
        super(seed);
    }

    @Override
    public void makeMove(TicTacToe game) {
        int[] bestMove = minimax(game, seed, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        game.placeMove(bestMove[1], bestMove[2], seed);
        System.out.println("AI placed " + seed.getDisplayName() + " at (" + bestMove[1] + ", " + bestMove[2] + ")");
    }

    private int[] minimax(TicTacToe game, Seed currentPlayer, boolean isMaximizing, int alpha, int beta) {
        if (game.checkForWin()) {
            return new int[]{isMaximizing ? -10 : 10, -1, -1};
        }
        if (game.isBoardFull()) {
            return new int[]{0, -1, -1};
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = {-1, -1, -1};

        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                if (game.isValidMove(i, j)) {
                    game.placeMove(i, j, currentPlayer);

                    // Gunakan Seed lawan
                    Seed opponentSeed = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    int score = minimax(game, opponentSeed, !isMaximizing, alpha, beta)[0];

                    game.placeMove(i, j, Seed.NO_SEED); // Undo move

                    if (isMaximizing) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = new int[]{score, i, j};
                        }
                        alpha = Math.max(alpha, score);
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove = new int[]{score, i, j};
                        }
                        beta = Math.min(beta, score);
                    }

                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
        }
        return bestMove;
    }
}
