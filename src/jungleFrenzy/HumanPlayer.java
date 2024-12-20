package jungleFrenzy;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(Seed seed) {
        super(seed);
    }

    @Override
    public void makeMove(TicTacToe game) {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.println("Enter your move (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (!game.isValidMove(row, col));
        game.placeMove(row, col, seed);
    }
}
