package minesweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class App {

    public static final int rows = 16, cols = 16, mines = 50;

    public static final int gap = 10;

    public static final Color bgColor = new Color(192, 192, 192);

    private BoardViewController boardViewController;
    private NewGameButton newGameButton;

    App() {
        this.boardViewController = new BoardViewController(new Board(16, 16, 50));
        this.newGameButton = new NewGameButton(this);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(App.gap);
        borderLayout.setVgap(App.gap);

        Border border = BorderFactory.createLineBorder(App.bgColor, App.gap);

        JPanel content = new JPanel();

        content.setLayout(borderLayout);
        content.add(boardViewController, BorderLayout.CENTER);
        content.add(newGameButton, BorderLayout.NORTH);

        content.setBackground(App.bgColor);
        content.setBorder(border);

        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(content);

        window.setSize(250,100);
        window.setLocation(100,100);
        window.setVisible(true);
    }

    void newGame() {
        this.boardViewController.newGame(new Board(App.cols, App.rows, App.mines));
    }
}
