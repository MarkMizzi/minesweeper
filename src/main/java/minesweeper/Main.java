package minesweeper;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(new BoardViewController(new Board(16, 16, 50)), BorderLayout.CENTER);

        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(content);
        window.setSize(250,100);
        window.setLocation(100,100);
        window.setVisible(true);
    }
}