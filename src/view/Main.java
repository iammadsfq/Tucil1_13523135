package view;

import model.InputReader;
import model.Solver;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solver puzzle = InputReader.readPuzzleFromFile("test_1.txt");
        long startTime = System.nanoTime();
        if (puzzle.solve(0)) {
            System.out.println("Solution found!");
        } else {
            System.out.println("No  ``  .");
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Execution time: " + duration + " ms");
        System.out.println("Total cases checked: " + puzzle.getCaseChecked());
    }
//    public static void main(String[] args) {
//        Board board = new Board(3, 3); // Ukuran papan 5x5
//        List<Piece> pieces = new ArrayList<>();
//
//        pieces.add(new Piece('A', new boolean[][] {
//                {true, true, true},
//                {false, true, false}
//        }));
//
//        pieces.add(new Piece('B', new boolean[][] {
//                {true, false},
//                {false, true}
//        }));
//
//        pieces.add(new Piece('C', new boolean[][] {
//                {true},
//                {true}
//        }));
//
//        Solver solver = new Solver(board, pieces);
//        if (solver.solve(0)) {
//            System.out.println("Solution found!");
//            board.printBoard();
//        } else {
//            System.out.println("No solution.");
//        }
//    }

}
