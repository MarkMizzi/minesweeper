package minesweeper;

import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class App {

    public static final int rows = 16, cols = 16, mines = 50;

    // UI parameters
    // gap between elements
    public static final int HGAP = 10, VGAP = 10;
    public static final int BORDER_THICKNESS = 10;

    public static final String TITLE = "Minesweeper";

    public static final Color BG_COLOR = new Color(192, 192, 192);

    // default width and height
    public static final int DEFAULT_WIDTH = 720;
    public static final int DEFAULT_HEIGHT = 800;

    private BoardViewController boardViewController;
    private NewGameButton newGameButton;

    App() {
        this.boardViewController = new BoardViewController(new Board(16, 16, 50));
        this.newGameButton = new NewGameButton(this);

        // set up game bar

        FlowLayout gameBarLayout = new FlowLayout(FlowLayout.CENTER);

        JPanel gameBar = new JPanel();

        gameBar.setLayout(gameBarLayout);

        gameBar.add(this.newGameButton);

        gameBar.setBackground(App.BG_COLOR);

        // set up game board
        JPanel gameBoard = new JPanel(new FlowLayout());
        gameBoard.add(this.boardViewController);

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
        this.boardViewController.newGame(new Board(App.cols, App.rows, App.mines));
    }
}
