package minesweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class App {

    static final int rows = 16, cols = 16, mines = 40;

    // UI parameters
    // gap between elements
    static final int HGAP = 10, VGAP = 10;
    static final int BORDER_THICKNESS = 10;

    static final String TITLE = "Minesweeper";

    static final Color BG_COLOR = new Color(192, 192, 192);

    // default width and height
    static final int DEFAULT_WIDTH = 720;
    static final int DEFAULT_HEIGHT = 800;

    private Board board;
    private final BoardView boardView;
    private final BoardController boardController;
    private final NewGameButton newGameButton;

    private static Board defaultBoard() {
        return new Board(App.cols, App.rows, App.mines);
    }

    App() {
        this.board = App.defaultBoard();
        this.boardView = new BoardView(this, this.board);
        this.boardController = new BoardController(this.board, this.boardView);
        // add controller to view.
        this.boardView.addMouseListener(this.boardController);

        this.newGameButton = new NewGameButton(this);

        // set up game bar

        FlowLayout gameBarLayout = new FlowLayout(FlowLayout.CENTER);

        JPanel gameBar = new JPanel();

        gameBar.setLayout(gameBarLayout);

        gameBar.add(this.newGameButton);

        gameBar.setBackground(App.BG_COLOR);

        // set up game board
        JPanel gameBoard = new JPanel(new FlowLayout());
        gameBoard.add(this.boardView);

        gameBoard.setBackground(App.BG_COLOR);

        gameBoard.revalidate();
        gameBoard.repaint();

        // set up main content pane

        BorderLayout contentLayout = new BorderLayout(App.HGAP, App.VGAP);

        Border border = BorderFactory.createLineBorder(App.BG_COLOR, App.BORDER_THICKNESS);

        JPanel content = new JPanel();

        content.setLayout(contentLayout);
        content.add(gameBoard, BorderLayout.CENTER);
        content.add(gameBar, BorderLayout.NORTH);

        content.setBackground(App.BG_COLOR);
        content.setBorder(border);

        // set up window

        JFrame window = new JFrame(App.TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(content);

        window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        window.setLocation(100,100);
        window.setVisible(true);
    }

    void newGame() {
        this.board = App.defaultBoard();
        this.boardView.setBoard(this.board);
        this.boardController.setBoard(this.board);
    }
}
