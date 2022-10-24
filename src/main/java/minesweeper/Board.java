package minesweeper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Board extends Model {
    static public final int bombPlaceholder = -1;

    private int[][] boardElems;
    // indicate which cells in the board are covered.
    private boolean[][] coveredElems;

    private int selectedX, selectedY;

    Board(int cols, int rows, int mines) {

        if (mines >= cols * rows)
            throw new IllegalArgumentException("Mines value is greater than number of cells in the board.");

        this.boardElems = new int[cols][rows];

        Random rand = new Random();

        // get all the x positions for mines
        Set<Integer> mineXs = new TreeSet<>();
        while (mineXs.size() < mines) {
            mineXs.add(rand.nextInt(0, cols));
        }

        // get all the y positions for mines
        Set<Integer> mineYs = new TreeSet<>();
        while (mineYs.size() < mines) {
            mineYs.add(rand.nextInt(0, rows));
        }

        // fill in the bombs
        Iterator<Integer> xIt = mineXs.iterator(), yIt = mineYs.iterator();
        while (xIt.hasNext() && yIt.hasNext())
            this.boardElems[xIt.next()][yIt.next()] = bombPlaceholder;

        int[][] boardCopy = this.boardElems.clone();

        // fill in around the bombs.
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                // sum over neighbours
                for (int nx = Math.max(x-1, 0); nx < Math.min(x+1, cols); nx++)
                    for (int ny = Math.max(y-1, 0); ny < Math.min(y+1, rows); ny++)
                        boardCopy[x][y] += this.boardElems[nx][ny];

                // because bombPlaceholder = -1, the number of adjacent bombs is just inverse of the neighbours sum:
                this.boardElems[x][y] = -boardCopy[x][y];
            }
        }

        // everything is covered at first.
        this.coveredElems = new boolean[cols][rows];
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
            this.notifyViews();

            return GameStatus.LOSE;
        }

        // recursively uncover more cells until we hit boundaries.
        this.recursiveUncover(this.selectedX, this.selectedY);
        // draw the updated board.
        this.notifyViews();

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
