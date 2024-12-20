package jungleFrenzy;

import java.util.Random;

public class AIPlayer extends Player {
    private final Random random;

    public AIPlayer(Seed seed) {
        super(seed);
        random = new Random();
    }

    @Override
    public void makeMove(TicTacToe game) {
        int row, col;
        do {
            row = random.nextInt(game.getSize());
            col = random.nextInt(game.getSize());
        } while (!game.isValidMove(row, col));
        game.placeMove(row, col, seed);
        System.out.println("AI placed " + seed.getDisplayName() + " at (" + row + ", " + col + ")");
    }
}
