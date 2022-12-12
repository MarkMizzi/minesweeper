package minesweeper;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardController implements MouseListener {

    Board board;
    BoardView view;

    void setBoard(Board board) {
        this.board = board;
    }

    BoardController(Board board, BoardView view) {
        this.board = board;
        this.view = view;
    }

    public void mouseClicked(MouseEvent e) {
        int x = view.boardX(e.getX()), y = view.boardY(e.getY());

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
