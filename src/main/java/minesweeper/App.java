package minesweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class App {

    public static final int rows = 16, cols = 16, mines = 50;

    // UI parameters
    // gap between elements
    public static final int HGAP = 10, VGAP = 10;
    public static final int BORDER_THICKNESS = 10;

    public static final Color BG_COLOR = new Color(192, 192, 192);

    // default width and height
    public static final int DEFAULT_WIDTH = 720;
    public static final int DEFAULT_HEIGHT = 800;

    private BoardViewController boardViewController;
    private NewGameButton newGameButton;

    App() {
        this.boardViewController = new BoardViewController(new Board(16, 16, 50));
        this.newGameButton = new NewGameButton(this);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(App.HGAP);
        borderLayout.setVgap(App.VGAP);

        Border border = BorderFactory.createLineBorder(App.BG_COLOR, App.BORDER_THICKNESS);

        JPanel content = new JPanel();

        content.setLayout(borderLayout);
        content.add(boardViewController, BorderLayout.CENTER);
        content.add(newGameButton, BorderLayout.NORTH);

        content.setBackground(App.BG_COLOR);
        content.setBorder(border);

        JFrame window = new JFrame("Minesweeper");
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
