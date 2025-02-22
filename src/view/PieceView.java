package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

public class PieceView extends Pane {
    private static final int TILE_SIZE = 40;
    private Canvas canvas;

    public PieceView() {
        canvas = new Canvas(400, 200);  // Ukuran sementara untuk tampilan potongan
        getChildren().add(canvas);
        drawPiece();
    }

    private void drawPiece() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(40, 40, TILE_SIZE, TILE_SIZE);  // Contoh satu kotak potongan

        gc.setFill(Color.RED);
        gc.fillRect(80, 40, TILE_SIZE, TILE_SIZE);  // Kotak lain dari potongan
    }
}
