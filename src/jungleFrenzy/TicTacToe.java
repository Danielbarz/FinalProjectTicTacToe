package jungleFrenzy;

public class TicTacToe {
    private Seed[][] board;
    private int size;
    private Player playerX;
    private Player playerO;

    public TicTacToe(int size) {
        this.size = size;
        board = new Seed[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = Seed.NO_SEED; // Inisialisasi kosong
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Seed[][] getBoard() {
        return board;
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && board[row][col] == Seed.NO_SEED;
    }

    public void placeMove(int row, int col, Seed seed) {
        board[row][col] = seed;
    }


    public boolean checkForWin() {
        // Periksa kemenangan pada baris, kolom, dan diagonal
        for (int i = 0; i < size; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }
        return checkDiagonal();
    }

    private boolean checkRow(int row) {
        Seed first = board[row][0];
        if (first == Seed.NO_SEED) return false;
        for (int col = 1; col < size; col++) {
            if (board[row][col] != first) return false;
        }
        return true;
    }

    private boolean checkColumn(int col) {
        Seed first = board[0][col];
        if (first == Seed.NO_SEED) return false;
        for (int row = 1; row < size; row++) {
            if (board[row][col] != first) return false;
        }
        return true;
    }

    private boolean checkDiagonal() {
        Seed first = board[0][0];
        if (first != Seed.NO_SEED) {
            boolean win = true;
            for (int i = 1; i < size; i++) {
                if (board[i][i] != first) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        first = board[0][size - 1];
        if (first != Seed.NO_SEED) {
            for (int i = 1; i < size; i++) {
                if (board[i][size - 1 - i] != first) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Seed.NO_SEED) return false;
            }
        }
        return true;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public void setPlayerX(Player playerX) {
        this.playerX = playerX;
    }

    public void setPlayerO(Player playerO) {
        this.playerO = playerO;
    }
}
