package minesweeper;

public class Board {
    static public final int bombPlaceholder = -1;

    private int[][] boardElems;
    // indicate which cells in the board are covered.
    private boolean[][] coveredElems;

    private ViewController viewController;

    private int selectedX, selectedY;

    Board(int[][] board) {}

    void select(int x, int y) {}
    void moveLeft() {}
    void moveRight() {}
    void moveUp() {}
    void moveDown() {}

    GameStatus uncover() {}

    void updateView() {}

    int rows() {}
    int cols() {}
    boolean covered(int x, int y) {}
    int board_value(int x, int y) {}

}
