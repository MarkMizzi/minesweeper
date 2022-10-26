package minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
                "flag.png",
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

    private static Image tile(int boardValue, boolean covered, boolean flagged, boolean fatal) {

        if (covered) {
            if (flagged) {
                return BoardViewController.tiles[12];
            }
            return BoardViewController.tiles[11];
        }

        if (boardValue == Board.MINE_PLACEHOLDER) {
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
        this.setBackground(App.BG_COLOR);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < board.cols(); x++) {
            for (int y = 0; y < board.rows(); y++) {
                g.drawImage(
                        tile(
                                board.boardValue(x, y),
                                board.covered(x, y),
                                board.flagged(x, y),
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

    @Override
    public Dimension getPreferredSize() {
        // Assumes this is the only component in the parent.
        // Parent should use a layout that respects the preferred size.
        Dimension d = this.getParent().getSize();

        if (d.width > d.height) {
            return new Dimension(d.height * this.board.cols() / this.board.rows(), d.height);
        } else {
            return new Dimension(d.width, d.width * this.board.cols() / this.board.rows());
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = boardX(e.getX()), y = boardY(e.getY());

        if (SwingUtilities.isLeftMouseButton(e))
            this.board.uncover(x, y);
        else if (SwingUtilities.isRightMouseButton(e))
            this.board.toggleFlag(x, y);
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
