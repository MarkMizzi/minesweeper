package minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

public final class BoardView
        extends JPanel
        implements View {

    static final String WIN_MESSAGE = "YOU WON!!";
    private Board board;
    private App app;

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
                return BoardView.tiles[12];
            }
            return BoardView.tiles[11];
        }

        if (boardValue == Board.MINE_PLACEHOLDER) {
            if (fatal) {
                return BoardView.tiles[10];
            }
            return BoardView.tiles[9];
        }
        return BoardView.tiles[boardValue];
    }

    BoardView(App app, Board board) {
        super();

        this.app = app;

        this.board = board;
        this.board.attach(this);

        this.setBackground(App.BG_COLOR);
    }

    void setBoard(Board board) {
        this.board = board;
        this.board.attach(this);
        this.update();
    }

    // observing side of Observer pattern
    public void update() {

        this.repaint();

        if (this.board.getStatus() == GameStatus.WIN) {

            // open a new dialog to congratulate the user for winning.
            JFrame dialogWindow = new JFrame();
            JOptionPane.showMessageDialog(dialogWindow, WIN_MESSAGE);

            // When the dialog window closes, we reset the game.
            // This gives the user a chance to look at the board.
            dialogWindow.addWindowListener(new WindowAdapter(){
                // NOTE: This is a closure.
                public void windowClosing(WindowEvent e) {
                    app.newGame();
                }
            });

            this.app.newGame();
        }
    }

    // compute dimensions of a tile in the JPanel component
    private float tileWidth() { return ((float)this.getWidth()) / this.board.cols(); }
    private float tileHeight() { return ((float)this.getHeight()) / this.board.rows(); }

    // convert from board indices to locations in the JPanel component
    private int tileX(int boardX) { return (int)(boardX * this.tileWidth()); }
    private int tileY(int boardY) { return (int)(boardY * this.tileHeight()); }

    // convert from component locations to board indices
    int boardX(int componentX) { return (componentX * this.board.cols()) / this.getWidth(); }
    int boardY(int componentY) { return (componentY * this.board.rows()) / this.getHeight(); }

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
                        (int)this.tileWidth(),
                        (int)this.tileHeight(),
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
            return new Dimension(d.width, d.width * this.board.rows() / this.board.cols());
        }
    }
}
