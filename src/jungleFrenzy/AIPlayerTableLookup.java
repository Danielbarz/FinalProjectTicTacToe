package jungleFrenzy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AIPlayerTableLookup extends Player {
    private Map<String, int[]> winningMoves;
    private Random random;

    public AIPlayerTableLookup(Seed seed) {
        super(seed);
        initializeWinningMoves();
        random = new Random();
    }

    private void initializeWinningMoves() {
        winningMoves = new HashMap<>();
        // Predefined winning moves for AI
        winningMoves.put("---------", new int[]{0, 0}); // Example: AI can take the top-left corner
        // Add more predefined moves based on board states
    }

    @Override
    public void makeMove(TicTacToe game) {
        String boardState = getBoardState(game);
        if (winningMoves.containsKey(boardState)) {
            int[] move = winningMoves.get(boardState);
            game.placeMove(move[0], move[1], seed);
            System.out.println("AI placed " + seed.getDisplayName() + " at (" + move[0] + ", " + move[1] + ")");
        } else {
            // If no winning move, fallback to random move
            int row, col;
            do {
                row = random.nextInt(game.getSize());
                col = random.nextInt(game.getSize());
            } while (!game.isValidMove(row, col));
            game.placeMove(row, col, seed);
            System.out.println("AI placed " + seed.getDisplayName() + " at (" + row + ", " + col + ")");
        }
    }

    private String getBoardState(TicTacToe game) {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                Seed cell = game.getBoard()[i][j];
                state.append(cell == Seed.NO_SEED ? '-' : cell.getDisplayName());
            }
        }
        return state.toString();
    }
}
