package minesweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class App {

    public static final int rows = 16, cols = 16, mines = 50;

    // UI parameters
    // gap between elements
    public static final int hgap = 10, vgap = 10;
    public static final int borderThickness = 10;

    public static final Color bgColor = new Color(192, 192, 192);

    // default width and height
    public static final int defaultWidth = 720;
    public static final int defaultHeight = 800;

    private BoardViewController boardViewController;
    private NewGameButton newGameButton;

    App() {
        this.boardViewController = new BoardViewController(new Board(16, 16, 50));
        this.newGameButton = new NewGameButton(this);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(App.hgap);
        borderLayout.setVgap(App.vgap);

        Border border = BorderFactory.createLineBorder(App.bgColor, App.borderThickness);

        JPanel content = new JPanel();

        content.setLayout(borderLayout);
        content.add(boardViewController, BorderLayout.CENTER);
        content.add(newGameButton, BorderLayout.NORTH);

        content.setBackground(App.bgColor);
        content.setBorder(border);

        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(content);

        window.setSize(defaultWidth, defaultHeight);
        window.setLocation(100,100);
        window.setVisible(true);
    }

    void newGame() {
        this.boardViewController.newGame(new Board(App.cols, App.rows, App.mines));
    }
}
