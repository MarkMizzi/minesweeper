package minesweeper;

public class Game {
    private Board board;
    private ViewController viewController;

    Game(Board board, ViewController viewController) {
        this.board = board;
        this.viewController = viewController;
    }
}
