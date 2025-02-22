package model;

import java.util.List;

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

    public Board(Board other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(other.grid[i], 0, this.grid[i], 0, cols);
        }
    }


    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public char getCell(int i,int j) {
        return grid[i][j];
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

    public String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            for (char cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public void modifyBoard(List<char[]> model) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (model.get(i)[j] == '.') grid[i][j] = '-';
            }
        }
    }
}