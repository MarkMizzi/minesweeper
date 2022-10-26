package minesweeper;

import javax.imageio.ImageIO;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class NewGameButton extends Button {

    public static final int PREFERRED_WIDTH = 52, PREFERRED_HEIGHT = 52;

    private static Image buttonImage;

    // load image of smiley (buttonImage)
    static {
        String filename = "smiley.png";

        try {
            URL imageURL = ClassLoader.getSystemClassLoader().getResource(filename);

            if (imageURL == null) {
                System.out.printf("Couldn't find resource file %s", filename);
                System.exit(-1);
            }

            buttonImage = ImageIO.read(imageURL);
        } catch (
                IOException e) {
            System.out.printf("Error while reading resource file %s", filename);
            System.exit(-1);
        }
    }

    NewGameButton(App app) {
        super();

        this.setPreferredSize(new Dimension(
                NewGameButton.PREFERRED_WIDTH,
                NewGameButton.PREFERRED_HEIGHT
        ));

        this.addActionListener(
                (ActionEvent e) -> {
                    app.newGame();
                }
        );
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buttonImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
