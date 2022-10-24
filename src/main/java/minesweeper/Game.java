package minesweeper;

public class Game {
    private Board board;
    private View view;
    private Controller controller;

    Game(Board board, View view, Controller controller) {
        this.board = board;
        this.view = view;
        this.controller = controller;
    }
}
