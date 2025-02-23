package model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileHandler {
    public static Solver readPuzzleFromFile(String fileName)
    {
        Solver puzzle = null;
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            String line = sc.nextLine();
            String[] values = line.trim().split("\\s+");
            if (values.length != 3) {
                System.out.println("Format file tidak valid!\n");
                return null;
            }
            int expected_row = Integer.parseInt(values[0]);
            int expected_col = Integer.parseInt(values[1]);
            Board board = new Board(expected_row, expected_col);
            line = sc.nextLine();
            if (Objects.equals(line, "CUSTOM")) {
                List<char[]> tempMatrix = new ArrayList<>();
                for (int i0 = 0; i0 < expected_row; i0++) {
                    line = sc.nextLine();
                    char[] board_row = line.toCharArray();
                    if (board_row.length != expected_col) {
                        return null;
                    }
                    else {
                        tempMatrix.add(board_row);
                    }
                }
                board.modifyBoard(tempMatrix);
            }
            else if (!Objects.equals(line, "DEFAULT")){
                return null;
            }

            List<Piece> pieces = new ArrayList<>();
            List<Character> pieces_id = new ArrayList<>();
            int expected_length = Integer.parseInt(values[2]);
            char currentId = '-';
            List<boolean[]> tempPiece = new ArrayList<>();
            int width = -1;
            boolean isNewPiece = true;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                char[] chars = line.toCharArray();
                boolean[] row = new boolean[chars.length];
                char current_row_id = '-';
                for (int i = 0; i < chars.length; i++) {
                    if (current_row_id != chars[i] && chars[i] != ' ') {
                        if (current_row_id == '-') {
                            current_row_id = chars[i];
                        }
                        else {
                            System.out.println("Format file tidak valid!\n");
                            return null;
                        }
                    }
                    if (currentId == '-') {
                        currentId = chars[i];
                    }
                    if (chars[i] == currentId) {
                        row[i] = true;
                    }
                    else if (chars[i] == ' ') {
                        row[i] = false;
                    }
                    else {
                        if (chars[i] < 'A' || chars[i] > 'Z') {
                            System.out.println("Format file tidak valid!\n");
                            return null;
                        }
                        int height = tempPiece.size();
                        boolean[][] piece_shape = new boolean[height][width];
                        for (int i1 = 0; i1 < height; i1++) {
                            boolean[] piece_row = tempPiece.get(i1);
                            int piece_row_length = piece_row.length;
                            for (int j1 = 0; j1 < width; j1++) {
                                if (j1 < piece_row_length) {
                                    piece_shape[i1][j1] = piece_row[j1];
                                }
                                else {
                                    piece_shape[i1][j1] = false;
                                }
                            }
                        }
                        if (currentId != '-') {
                            if (pieces_id.contains(currentId)) {
                                System.out.println("ID piece baru sudah pernah digunakan!");
                                return null;
                            }
                            Piece piece = new Piece(currentId, piece_shape);
//                            System.out.println("ID: " + currentId);
//                            System.out.println("Height: " + height);
//                            System.out.println("Width: " + width);
//                            System.out.println("Shape: ");
//                            piece.printPiece();
                            pieces.add(piece);
                            pieces_id.add(currentId);
                        }
                        width = -1;
                        currentId = chars[i];
                        row[i] = true;
                        tempPiece.clear();
                        isNewPiece = true;
                    }
                }
                if (isNewPiece) {
                    isNewPiece = false;
                    width = chars.length;
                }
                else {
                    if (width < chars.length) {
                        width = chars.length;
                    }
                }
                tempPiece.add(row);
            }
            sc.close();
            int height = tempPiece.size();
            boolean[][] piece_shape = new boolean[height][width];
            for (int i1 = 0; i1 < height; i1++) {
                boolean[] piece_row = tempPiece.get(i1);
                int piece_row_length = piece_row.length;
                for (int j1 = 0; j1 < width; j1++) {
                    if (j1 < piece_row_length) {
                        piece_shape[i1][j1] = piece_row[j1];
                    }
                    else {
                        piece_shape[i1][j1] = false;
                    }
                }
            }
            if (currentId != ' ') {
                if (pieces_id.contains(currentId)) {
                    System.out.println("ID piece baru sudah pernah digunakan!");
                    return null;
                }
                Piece piece = new Piece(currentId, piece_shape);
//                System.out.println("ID: " + currentId);
//                System.out.println("Height: " + height);
//                System.out.println("Width: " + width);
//                System.out.println("Shape: ");
//                piece.printPiece();
                pieces.add(piece);
                pieces_id.add(currentId);
            }
            if (expected_length != pieces.size()) {
                System.out.println("Jumlah piece tidak sesuai!\nJumlah piece yang diminta: " + expected_length +
                        "\nJumlah piece yang diterima: " + pieces.size());
                return null;
            }
            else {
                puzzle = new Solver(board, pieces);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + fileName + "' tidak ditemukan.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return puzzle;
    }

    public static void saveToFile(String output, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan Teks");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(output);
                System.out.println("Output berhasil disimpan ke: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
            }
        }
    }


    public static void saveSceneAsImage(Scene scene, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan Gambar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage image = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
                scene.snapshot(image);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                System.out.println("Gambar berhasil disimpan: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Gagal menyimpan gambar: " + e.getMessage());
            }
        }
    }
}
