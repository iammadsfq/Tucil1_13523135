public class Board {
    private int rows, cols;
    private char[][] grid; //pakai '*' untuk kosong

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '*';
            }
        }
    }

    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public boolean canPlacePiece(Piece piece, int row, int col) {
        boolean[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] && (row + i >= rows || col + j >= cols || grid[row + i][col + j] != '*')) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placePiece(Piece piece, int row, int col) {
        boolean[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    grid[row + i][col + j] = piece.getId();
                }
            }
        }
    }
    public void removePiece(Piece piece, int row, int col) {
        boolean[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    grid[row + i][col + j] = '*';
                }
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '*') {
                    return false;
                }
            }
        }
        return true;
    }
    public void printBoard() {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}