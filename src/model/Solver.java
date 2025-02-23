package model;

import java.util.List;
import java.util.function.Consumer;

public class Solver {
    private Board board;
    private List<Piece> pieces;
    private long searchDuration;
    private int caseChecked = 0;

    public Solver(Board board, List<Piece> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public int getCaseChecked() {
        return caseChecked;
    }

    public Board getBoard() {
        return board;
    }

    public boolean solve(int idx, Consumer<Board> stateCallback) { //idx bwt list pieces
        if (idx == pieces.size()) {
            return board.isBoardFull();
        }

        Piece piece = pieces.get(idx);
        Piece flipped_piece = piece.flip();
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                for (int rotation = 0; rotation < 4; rotation++) {
                    if (board.canPlacePiece(piece, row, col)) {
                        board.placePiece(piece, row, col);
                        caseChecked++;
                        if (stateCallback != null) {
                            stateCallback.accept(board);
                        }
//                         board.printBoard();
//                         System.out.println("\n");
                        if (solve(idx+1, stateCallback)) {
                            return true;
                        }
                        board.removePiece(piece, row, col);
                    }
                    else if (board.canPlacePiece(flipped_piece, row, col)) {
                        board.placePiece(flipped_piece, row, col);
//                         board.printBoard();
//                         System.out.println("\n");
                        if (solve(idx+1, stateCallback)) {
                            return true;
                        }
                        board.removePiece(flipped_piece, row, col);
                    }
                    piece = piece.rotate();
                    flipped_piece = piece.flip();
                }
            }
        }
        return false;
    }

    public void setSearchDuration(long duration) {
        this.searchDuration = duration;
    }

    public long getSearchDuration() {
        return searchDuration;
    }

}