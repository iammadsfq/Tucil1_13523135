package view;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Board;
import model.FileHandler;
import model.Solver;
import java.io.File;

import static model.FileHandler.saveSceneAsImage;
import static model.FileHandler.saveToFile;

public class Main extends Application {
    private Stage primaryStage;
    private String selectedFile = null;
    private Solver puzzle;
    private Text fileLabel;
    private Button solveButton;
    private Font customFont;
    private boolean showSolutionSteps = false;
    private BoardView boardView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        customFont = loadFont("PressStart2P-Regular.ttf", 14);
        showHomePage();
    }

    private void showHomePage() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1A1A40, #3D3D8B);");

        Text title = new Text("IQ Puzzle Pro Solver");
        title.setFont(loadFont("PressStart2P-Regular.ttf", 24));
        title.setFill(Color.LIGHTYELLOW);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), title);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new javafx.geometry.Insets(20));

        fileLabel = new Text("Belum ada file yang dipilih.");
        fileLabel.setFont(customFont);
        fileLabel.setFill(Color.WHITE);
        fileLabel.setStyle("-fx-font-weight: bold;");

        Button selectFileButton = createStyledButton("Pilih File");
        solveButton = createStyledButton("Cari Solusi");
        solveButton.setDisable(true);

        selectFileButton.setOnAction(e -> selectFile());
        solveButton.setOnAction(e -> solvePuzzle());

        CheckBox showStepsCheckBox = new CheckBox("Tunjukkan Penyelesaian");
        showStepsCheckBox.setFont(customFont);
        showStepsCheckBox.setTextFill(Color.WHITE);
        showStepsCheckBox.setOnAction(e -> showSolutionSteps = showStepsCheckBox.isSelected());

        VBox centerBox = new VBox(20, fileLabel, selectFileButton, solveButton, showStepsCheckBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new javafx.geometry.Insets(20));

        layout.setTop(titleBox);
        layout.setCenter(centerBox);
        Scene scene = new Scene(layout, 500, 300);

        primaryStage.setTitle("IQ Puzzle Pro Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih File Puzzle");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            selectedFile = file.getAbsolutePath();
            fileLabel.setText("File dipilih: " + file.getName());
            solveButton.setDisable(false);
        }
    }

    private void solvePuzzle() {
        if (selectedFile == null) {
            fileLabel.setText("Pilih file terlebih dahulu!");
            return;
        }

        puzzle = FileHandler.readPuzzleFromFile(selectedFile);
        showProcessPage();

        Task<Boolean> solveTask = new Task<>() {
            @Override
            protected Boolean call() {
                long startTime = System.nanoTime();
                boolean found = puzzle.solve(0, showSolutionSteps ? this::updateBoardState : null);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1_000_000;
                puzzle.setSearchDuration(duration);
                updateMessage("Solusi ditemukan!\nWaktu pencarian: " + duration + " ms\nKasus ditinjau: " + puzzle.getCaseChecked());
                return found;
            }

            private void updateBoardState(Board board) {
                Platform.runLater(() -> {
                    boardView.updateBoard(board);
                });

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            }
        };

        solveTask.setOnSucceeded(e -> {
            boolean found = solveTask.getValue();
            if (found) {
                showSolutionPage(solveTask.getMessage());
            } else {
                fileLabel.setText("Solusi tidak ditemukan!");
            }
        });
        new Thread(solveTask).start();
    }

    private void showProcessPage() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #3D3D8B;");

        Text resultText = new Text("Mencari solusi...");
        resultText.setFont(customFont);
        resultText.setFill(Color.LIGHTYELLOW);

        boardView = new BoardView(puzzle.getBoard());
        VBox centerBox = new VBox(20, resultText, boardView.createBoardView());
        centerBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerBox);
        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
    }

    private void showSolutionPage(String message) {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1A1A40, #3D3D8B);");

        Text resultText = new Text(message);
        resultText.setFont(customFont);
        resultText.setFill(Color.LIGHTYELLOW);

        boardView.updateBoard(puzzle.getBoard());

        Button saveButton = createStyledButton("Simpan Teks");
        Button saveImageButton = createStyledButton("Simpan Gambar");
        Button backButton = createStyledButton("Kembali");

        saveButton.setOnAction(e -> saveSolution());
        saveImageButton.setOnAction(e -> FileHandler.saveSceneAsImage(primaryStage.getScene(), primaryStage));
        backButton.setOnAction(e -> showHomePage());

        HBox buttonBox = new HBox(15, saveButton, backButton, saveImageButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(20, resultText, boardView.createBoardView(), buttonBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new javafx.geometry.Insets(20));

        layout.setCenter(centerBox);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
    }

    private void saveSolution() {
        saveToFile("Solusi ditemukan!\n\n" + puzzle.getBoard().boardToString() +
                        "\n\nWaktu pencarian: " + puzzle.getSearchDuration() + " ms\nKasus ditinjau: " + puzzle.getCaseChecked(),
                primaryStage);
    }

    private Font loadFont(String fontFileName, double size) {
        return Font.loadFont(getClass().getClassLoader().getResourceAsStream(fontFileName), size);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(customFont);
        button.setStyle(
                "-fx-background-color: #FFD700; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-size: 14px; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 3px; " +
                        "-fx-padding: 10px; " +
                        "-fx-font-family: 'PressStart2P';"
        );
        return button;
    }
}
