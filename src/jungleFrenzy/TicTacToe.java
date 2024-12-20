package jungleFrenzy;

public class TicTacToe {
    private char[][] board;
    private int size;
    private Player playerX;
    private Player playerO;

    public TicTacToe(int size) {
        this.size = size;
        board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
    }

    public int getSize() {
        return size;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && board[row][col] == '-';
    }

    public void placeMove(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    public boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < size; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }
        return checkDiagonal();
    }

    private boolean checkRow(int row) {
        char first = board[row][0];
        if (first == '-') return false;
        for (int col = 1; col < size; col++) {
            if (board[row][col] != first) return false;
        }
        return true;
    }

    private boolean checkColumn(int col) {
        char first = board[0][col];
        if (first == '-') return false;
        for (int row = 1; row < size; row++) {
            if (board[row][col] != first) return false;
        }
        return true;
    }

    private boolean checkDiagonal() {
        char first = board[0][0];
        if (first != '-') {
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
        if (first != '-') {
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
                if (board[i][j] == '-') return false;
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
