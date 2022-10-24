package minesweeper;

import java.util.Arrays;

public class Board {
    static public final int bombPlaceholder = -1;

    private int[][] boardElems;
    // indicate which cells in the board are covered.
    private boolean[][] coveredElems;

    private ViewController viewController;

    private int selectedX, selectedY;

    Board(int[][] board) {
        this.boardElems = board;
        this.coveredElems = new boolean[this.cols()][this.rows()];

        for (int x = 0; x < this.cols(); x++) {
            Arrays.fill(this.coveredElems[x], true);
        }
    }

    void select(int x, int y) {
        this.selectedX = x;
        this.selectedY = y;
    }
    void moveLeft() {
        this.selectedX = (this.selectedX - 1) % this.cols();
    }
    void moveRight() {
        this.selectedX = (this.selectedX + 1) % this.cols();
    }
    void moveUp() {
        this.selectedY = (this.selectedY - 1) % this.rows();
    }
    void moveDown() {
        this.selectedY = (this.selectedY + 1) % this.rows();
    }

    private void recursiveUncover(int x, int y) {
        if (this.boardValue(x, y) != bombPlaceholder) {
            this.coveredElems[x][y] = false;

            // recursively uncover adjacent cells
            if (x != this.cols() - 1)
                this.recursiveUncover(x + 1, y);

            if (x != 0)
                this.recursiveUncover(x - 1, y);

            if (y != this.rows() - 1)
                this.recursiveUncover(x, y + 1);

            if (y != 0)
                this.recursiveUncover(x, y - 1);
        }
    }

    GameStatus uncover() {

        if (this.boardValue(this.selectedX, this.selectedY) == bombPlaceholder) {

            // uncover the rest of the board
            for (int x = 0; x < this.cols(); x++) {
                Arrays.fill(this.coveredElems[x], false);
            }

            // draw the board one last time
            this.updateView();

            return GameStatus.LOSE;
        }

        // recursively uncover more cells until we hit boundaries.
        this.recursiveUncover(this.selectedX, this.selectedY);
        // draw the updated board.
        this.updateView();

        // did we win? This is only the case if all board cells that are not bombs have been uncovered.
        for (int x = 0; x < this.cols(); x++) {
            for (int y = 0; y < this.rows(); y++) {
                if (this.boardValue(x, y) != bombPlaceholder && this.covered(x, y)) {
                    return GameStatus.CONTINUE;
                }
            }
        }

        return GameStatus.WIN;
    }

    private void updateView() {
        this.viewController.update();
    }

    int rows() {
        return this.coveredElems[0].length;
    }
    int cols() {
        return this.coveredElems.length;
    }
    boolean covered(int x, int y) {
        return this.coveredElems[x][y];
    }
    int boardValue(int x, int y) {
        return this.boardElems[x][y];
    }

}
