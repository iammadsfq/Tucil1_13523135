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