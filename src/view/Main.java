package view;

import model.FileHandler;
import model.Solver;

import static model.FileHandler.saveToFile;

public class Main {
    public static void main(String[] args) {
        String filename = "lana_2.txt";
        Solver puzzle = FileHandler.readPuzzleFromFile(filename);
        long startTime = System.nanoTime();
        boolean found = puzzle.solve(0);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        if (found) {
            String output = "Solusi ditemukan!\n \n" + puzzle.getBoard().boardToString() + "\n \nWaktu pencarian: " + duration +
                    " ms\n" + "Banyak kasus ditinjau: " + puzzle.getCaseChecked();
            System.out.println(output);
            //FileHandler.saveToFile(output,"save.txt");
        } else {
            System.out.println("Solusi tidak ditemukan!");
        }
    }
}
