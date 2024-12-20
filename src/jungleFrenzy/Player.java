package jungleFrenzy;

public abstract class Player {
    protected char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public abstract void makeMove(TicTacToe game);
}