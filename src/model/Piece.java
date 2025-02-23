package model;

import java.util.Stack;

public class Piece {
    private char id;
    private boolean[][] shape;
    private int width, height;

    public Piece(char id, boolean[][] shape) {
        this.id = id;
        this.shape = shape;
        this.width = shape[0].length;
        this.height = shape.length;
    }

    public char getId() {
        return id;
    }
    public boolean[][] getShape() {
        return shape;
    }

    public Piece rotate() {
        boolean[][] result = new boolean[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[j][height - 1 - i] = shape[i][j];
            }
        }
        return new Piece(id, result);
    }

    public Piece flip() {
        boolean[][] result = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][width - 1 - j] = shape[i][j];
            }
        }
        return new Piece(id, result);
    }

    public boolean isValidPiece() {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        boolean[][] visited = new boolean[height][width];
        int startX = -1, startY = -1;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (shape[i][j]) {
                    startX = i;
                    startY = j;
                    break;
                }
            }
            if (startX != -1) break;
        }

        if (startX == -1) return false;
        int count = 0;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!stack.isEmpty()) {
            int[] pos = stack.pop();
            count++;

            for (int[] dir : directions) {
                int newX = pos[0] + dir[0];
                int newY = pos[1] + dir[1];

                if (newX >= 0 && newX < height && newY >= 0 && newY < width && shape[newX][newY] && !visited[newX][newY]) {
                    stack.push(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        int total = 0;
        for (boolean[] row : shape) {
            for (boolean cell : row) {
                if (cell) total++;
            }
        }

        return count == total;
    }


    public void printPiece() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (shape[i][j]) {
                    System.out.print(id);
                }
                else {
                    System.out.print("x");
                }
            }
            System.out.println();
        }
    }
}