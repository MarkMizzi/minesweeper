package minesweeper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board extends Model {
    static final int MINE_PLACEHOLDER = -1;
    static final int BLANK_CELL = 0;

    private int[][] boardElems;
    // indicate which cells in the board are covered.
    private boolean[][] coveredElems;
    private boolean[][] flaggedElems;

    // indicate which cell was fatal (caused game over)
    private int fatalX = -1;
    private int fatalY = -1;

    private GameStatus status = GameStatus.CONTINUE;

    // deep copy constructor, useful for tests
    // note that the copy does not have any views attached.
    Board(Board b) {
        this.boardElems = new int[b.boardElems.length][];
        for (int i = 0; i < b.boardElems.length; i++) {
            this.boardElems[i] = b.boardElems[i].clone();
        }

        this.coveredElems = new boolean[b.coveredElems.length][];
        for (int i = 0; i < b.coveredElems.length; i++) {
            this.coveredElems[i] = b.coveredElems[i].clone();
        }
    }

    Board(int cols, int rows, int mines) {

        if (mines >= cols * rows)
            throw new IllegalArgumentException("Mines value is greater than number of cells in the board.");

        this.boardElems = new int[cols][rows];

        Random rand = new Random();

        // get all positions for a mine.
        // Since we don't have a tuple type, we pick a number in the range [0, rows*cols)
        //      and then interpret it as a flattened 2D position x*rows + y
        Set<Integer> minePoints = new HashSet<>();
        while (minePoints.size() < mines)
            minePoints.add(rand.nextInt(0, rows*cols));

        // fill in the bombs
        for (Integer minePoint : minePoints) {
            // "delinearize" the 2D position
            int x = minePoint / rows, y = minePoint % rows;
            this.boardElems[x][y] = MINE_PLACEHOLDER;
        }

        // deep copy of the board for next step.
        int[][] boardCopy = new int[boardElems.length][];
        for (int i = 0; i < boardCopy.length; i++) {
            boardCopy[i] = this.boardElems[i].clone();
        }

        // fill in around the bombs.
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (boardCopy[x][y] != MINE_PLACEHOLDER) {
                    // sum over neighbour.
                    for (int nx = Math.max(x - 1, 0); nx <= Math.min(x + 1, cols - 1); nx++)
                        for (int ny = Math.max(y - 1, 0); ny <= Math.min(y + 1, rows - 1); ny++)
                            this.boardElems[x][y] += boardCopy[nx][ny];

                    // because bombPlaceholder = -1, the number of adjacent bombs is just inverse of the neighbours sum:
                    this.boardElems[x][y] = -this.boardElems[x][y];
                }
            }
        }

        // everything is covered at first.
        this.coveredElems = new boolean[cols][rows];
        for (int x = 0; x < this.cols(); x++) {
            Arrays.fill(this.coveredElems[x], true);
        }

        // nothing is flagged at first.
        this.flaggedElems = new boolean[cols][rows];
        for (int x = 0; x < this.cols(); x++) {
            Arrays.fill(this.flaggedElems[x], false);
        }
    }

    void toggleFlag(int x, int y) {
        this.flaggedElems[x][y] = !this.flaggedElems[x][y];
        this.notifyViews();
    }

    private void recursiveUncover(int x, int y) {
        // we've already uncovered this.
        if (!this.coveredElems[x][y]) {
            return;
        }

        if (this.boardValue(x, y) != MINE_PLACEHOLDER) {
            this.coveredElems[x][y] = false;

            // recursively uncover adjacent cells if this one is blank
            if (this.boardValue(x, y) == BLANK_CELL) {
                // uncover all the neighbours
                for (int nx = Math.max(x - 1, 0); nx <= Math.min(x + 1, this.cols() - 1); nx++)
                    for (int ny = Math.max(y - 1, 0); ny <= Math.min(y + 1, this.rows() - 1); ny++)
                        this.recursiveUncover(nx, ny);
            }
        }
    }

    void uncover(int x, int y) {

        if (this.status != GameStatus.CONTINUE) {
            // game is finished
            return;
        }

        if (this.boardValue(x, y) == MINE_PLACEHOLDER) {
            this.fatalX = x;
            this.fatalY = y;

            this.status = GameStatus.LOSE;
        } else {

            // recursively uncover more cells until we hit boundaries.
            this.recursiveUncover(x, y);

            this.status = GameStatus.WIN;
            // did we win? This is only the case if all board cells that are not bombs have been uncovered.
            for (int col = 0; col < this.cols(); col++) {
                for (int row = 0; row < this.rows(); row++) {
                    if (this.boardValue(col, row) != MINE_PLACEHOLDER && this.covered(col, row)) {
                        this.status = GameStatus.CONTINUE;
                        break;
                    }
                }
            }
        }

        if (this.status == GameStatus.WIN || this.status == GameStatus.LOSE) {
            // uncover the rest of the board
            for (int col = 0; col < this.cols(); col++) {
                Arrays.fill(this.coveredElems[col], false);
            }
        }

        // draw the updated board.
        this.notifyViews();
    }

    int getFatalX() { return this.fatalX; }
    int getFatalY() { return this.fatalY; }

    GameStatus getStatus() { return this.status; }

    int rows() {
        return this.coveredElems[0].length;
    }
    int cols() {
        return this.coveredElems.length;
    }
    boolean flagged(int x, int y) { return this.flaggedElems[x][y]; }
    boolean covered(int x, int y) {
        return this.coveredElems[x][y];
    }
    int boardValue(int x, int y) {
        return this.boardElems[x][y];
    }

}
