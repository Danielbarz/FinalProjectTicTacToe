package jungleFrenzy;

/**
 * Computer move based on simple table lookup of preferences
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AIPlayerTableLookup extends Player {
    private Map<String, int[]> winningMoves;
    private Random random;

    public AIPlayerTableLookup(char symbol) {
        super(symbol);
        initializeWinningMoves();
        random = new Random(); // Initialize the Random instance
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
            game.placeMove(move[0], move[1], symbol);
            System.out.println("AI placed " + symbol + " at (" + move[0] + ", " + move[1] + ")");
        } else {
            // If no winning move, fallback to random move
            int row, col;
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!game.isValidMove(row, col));
            game.placeMove(row, col, symbol);
            System.out.println("AI placed " + symbol + " at (" + row + ", " + col + ")");
        }
    }

    private String getBoardState(TicTacToe game) {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state.append(game.getBoard()[i][j]);
            }
        }
        return state.toString();
    }
}
