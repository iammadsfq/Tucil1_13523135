package view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardView {
    private Board board;
    private Map<Character, Color> colorMap;
    private Rectangle[][] cells;
    private GridPane grid;

    public BoardView(Board board) {
        this.board = board;
        this.colorMap = generateColorMap();
        this.cells = new Rectangle[board.getRows()][board.getCols()];
    }

    public VBox createBoardView() {
        VBox container = new VBox(15);
        container.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        grid = createGrid();
        HBox gridWrapper = new HBox(grid);
        gridWrapper.setAlignment(Pos.CENTER); // Menjadikan grid berada di tengah

        HBox legend = createColorLegend();

        container.getChildren().addAll(gridWrapper, legend);
        container.setAlignment(Pos.CENTER); // Memastikan seluruh isi VBox berada di tengah
        return container;
    }


    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-hgap: 2px; -fx-vgap: 2px;");

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                char cell = board.getCell(i, j);
                Rectangle rect = new Rectangle(30, 30);
                rect.setFill(getColor(cell));
                rect.setStroke(Color.BLACK);
                grid.add(rect, j, i);
                cells[i][j] = rect;
            }
        }
        return grid;
    }

    public void updateBoard(Board newBoard) {
        this.board = newBoard;

        Platform.runLater(() -> {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                    char cell = board.getCell(i, j);
                    cells[i][j].setFill(getColor(cell));
                }
            }
        });
    }

    private Color getColor(char id) {
        if (id == '*') return Color.WHITE;
        if (id == '-') return Color.BLACK;
        return colorMap.getOrDefault(id, Color.GRAY);
    }

    private Map<Character, Color> generateColorMap() {
        Map<Character, Color> map = new HashMap<>();
        Color[] colors = {
                Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE, Color.PINK,
                Color.BROWN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.LIME, Color.TEAL,
                Color.VIOLET, Color.GOLD, Color.SALMON, Color.INDIGO, Color.OLIVE, Color.NAVY,
                Color.MAROON, Color.AQUA, Color.CORAL, Color.TURQUOISE, Color.TOMATO, Color.KHAKI,
                Color.DARKGREEN, Color.SIENNA
        };

        for (int i = 0; i < 26; i++) {
            map.put((char) ('A' + i), colors[i % colors.length]);
        }
        return map;
    }

    private HBox createColorLegend() {
        HBox legend = new HBox(10);
        legend.setStyle("-fx-alignment: center; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightgray;");

        Set<Character> usedPieces = getUsedPieces();
        if (usedPieces.isEmpty()) return legend;

        for (char id : usedPieces) {
            Rectangle rect = new Rectangle(20, 20);
            rect.setFill(colorMap.get(id));
            rect.setStroke(Color.BLACK);
            Text label = new Text(String.valueOf(id));

            VBox item = new VBox(5);
            item.setStyle("-fx-alignment: center;");
            item.getChildren().addAll(rect, label);
            legend.getChildren().add(item);
        }

        return legend;
    }

    private Set<Character> getUsedPieces() {
        Set<Character> used = new HashSet<>();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                char cell = board.getCell(i, j);
                if (cell != '*' && cell != '-') {
                    used.add(cell);
                }
            }
        }
        return used;
    }
}
