package minesweeper;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public final class GUIViewController
        extends JPanel
        implements MouseListener, KeyListener, View, Controller {

    private Board board;

    GUIViewController(Board board) {
        this.board = board;
        this.board.attach(this);
    }

    public void update() {}

    protected void paintComponent(Graphics g) {}

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
