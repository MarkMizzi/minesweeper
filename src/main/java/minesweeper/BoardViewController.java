package minesweeper;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

public final class BoardViewController
        extends JPanel
        implements MouseListener, View, Controller {

    private Board board;

    private static Image tiles[];

    static {

        String[] filenames = {
                "uncovered.png",
                "1.png",
                "2.png",
                "3.png",
                "4.png",
                "5.png",
                "6.png",
                "7.png",
                "8.png",
                "bomb.png",
                "fatal-bomb.png",
                "covered.png",
        };

        tiles = new Image[filenames.length];

        // load tile images into memory
        // failing to load these is fatal.
        for (int i = 0; i < filenames.length; i++) {
            try {
                URL imageURL = ClassLoader.getSystemClassLoader().getResource(filenames[i]);

                if (imageURL == null) {
                    System.out.printf("Couldn't find resource file %s", filenames[i]);
                    System.exit(-1);
                }

                tiles[i] = ImageIO.read(imageURL);
            } catch (IOException e) {
                System.out.printf("Error while reading resource file %s", filenames[i]);
                System.exit(-1);
            }
        }
    }

    private static Image tile(int boardValue, boolean covered, boolean fatal) {

        if (covered) {
            return BoardViewController.tiles[11];
        }

        if (boardValue == Board.minePlaceholder) {
            if (fatal) {
                return BoardViewController.tiles[10];
            }
            return BoardViewController.tiles[9];
        }
        return BoardViewController.tiles[boardValue];
    }

    BoardViewController(Board board) {
        super();

        this.board = board;
        this.board.attach(this);

        this.addMouseListener(this);
        this.setBackground(App.bgColor);
    }

    public void newGame(Board newBoard) {
        this.board = newBoard;
        this.board.attach(this);
        this.update();
    }

    // observing side of Observer pattern
    public void update() {
        this.repaint();
    }

    // convert from board indices to locations/dimensions in the JPanel component
    private int tileWidth() {
        return this.getWidth() / this.board.cols();
    }

    private int tileHeight() {
        return this.getHeight() / this.board.rows();
    }

    private int tileX(int boardX) {
        return boardX * this.tileWidth();
    }

    private int tileY(int boardY) {
        return boardY * this.tileHeight();
    }

    // convert from component locations to board indices
    private int boardX(int componentX) {
        return componentX * this.board.cols() / this.getWidth();
    }

    private int boardY(int componentY) {
        return componentY * this.board.rows() / this.getHeight();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < board.cols(); x++) {
            for (int y = 0; y < board.rows(); y++) {
                g.drawImage(
                        tile(
                                board.boardValue(x, y),
                                board.covered(x, y),
                                x == board.getFatalX() && y == board.getFatalY()
                        ),
                        this.tileX(x),
                        this.tileY(y),
                        this.tileWidth(),
                        this.tileHeight(),
                        this
                );
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = boardX(e.getX()), y = boardY(e.getY());
        this.board.uncover(x, y);
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
