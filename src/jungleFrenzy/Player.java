package jungleFrenzy;

public abstract class Player {
    protected Seed seed; // Menggunakan Seed alih-alih char

    // Konstruktor
    public Player(Seed seed) {
        this.seed = seed;
    }

    // Getter untuk Seed
    public Seed getSeed() {
        return seed;
    }

    // Metode abstrak untuk membuat langkah
    public abstract void makeMove(TicTacToe game);
}
