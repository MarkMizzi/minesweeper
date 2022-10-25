package minesweeper;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class NewGameButton extends Button {

    NewGameButton(App app) {
        super("New Game");

        this.addActionListener(
                (ActionEvent e) -> {
                    app.newGame();
                }
        );
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
