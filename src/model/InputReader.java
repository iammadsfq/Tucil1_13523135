import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InputReader {
    public static Solver readPuzzleFromFile(String fileName)
    {
        Solver puzzle = null;
        try {
            File file = new File("test/" + fileName);
            System.out.println("ppp");
            Scanner sc = new Scanner(file);
            String line = sc.nextLine();
            String[] values = line.trim().split("\\s+");
            if (values.length != 3) {
                System.out.println("Format file tidak valid!\n");
                return null;
            }
            line = sc.nextLine();
            if (Objects.equals(line, "CUSTOM")) {
                return null;
            }
            else if (!Objects.equals(line, "DEFAULT")){
                return null;
            }
            Board board = new Board(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            List<Piece> pieces = new ArrayList<>();
            int expected_length = Integer.parseInt(values[2]);
            char currentId = ' ';
            List<boolean[]> tempPiece = new ArrayList<>();
            int width = -1;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                char[] chars = line.toCharArray();
                if (width < chars.length) {
                    width = chars.length;
                }
                boolean[] row = new boolean[chars.length];
                char current_row_id = ' ';
                for (int i = 0; i < chars.length; i++) {
                    if (current_row_id != chars[i]) {
                        if (current_row_id == ' ') {
                            current_row_id = chars[i];
                        }
                        else {
                            System.out.println("beda\n");
                            return null;
                        }
                    }
                    if (chars[i] == currentId) {
                        row[i] = true;
                    }
                    else if (chars[i] == ' ') {
                        row[i] = false;
                    }
                    else {
                        if (chars[i] < 'A' || chars[i] > 'Z') {
                            System.out.println("diluar\n");
                            return null;
                        }
                        // Masukin piece yg lama, ganti currentId, refresh tempPiece
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
                            Piece piece = new Piece(currentId, piece_shape);
                            System.out.println("ID: " + currentId);
                            System.out.println("Height: " + height);
                            System.out.println("Width: " + width);
                            System.out.println("Shape: ");
                            piece.printPiece();
                            pieces.add(piece);
                        }
                        currentId = chars[i];
                        row[i] = true;
                        tempPiece.clear();
                    }
                }
                tempPiece.add(row);
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
            if (currentId != ' ') {
                Piece piece = new Piece(currentId, piece_shape);
                System.out.println("ID: " + currentId);
                System.out.println("Height: " + height);
                System.out.println("Width: " + width);
                System.out.println("Shape: ");
                piece.printPiece();
                pieces.add(piece);
            }
            if (expected_length != pieces.size()) {
                System.out.println(expected_length);
                System.out.println("g sesuai\n");
                System.out.println(pieces.size());
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
        System.out.println("masuk\n");
        return puzzle;
    }
}
