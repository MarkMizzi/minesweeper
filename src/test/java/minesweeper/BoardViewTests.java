package minesweeper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class BoardViewTests {

    @Test
    public void testPreferredSizeFitsHorizontalParent() {
        BoardView boardView = new BoardView(
                null,
                new Board(16, 16, 40)
        );

        JPanel parent = new JPanel(new FlowLayout());
        parent.setSize(1024, 768);

        parent.add(boardView);

        parent.revalidate();

        int parentHeight = parent.getHeight();
        Dimension preferredSize = boardView.getPreferredSize();

        Assertions.assertEquals(parentHeight, preferredSize.height);
    }

    @Test
    public void testPreferredSizeFitsVerticalParent() {
        BoardView boardView = new BoardView(
                null,
                new Board(16, 16, 40)
        );

        JPanel parent = new JPanel(new FlowLayout());
        parent.setSize(768, 1024);

        parent.add(boardView);

        parent.revalidate();

        int parentWidth = parent.getWidth();
        Dimension preferredSize = boardView.getPreferredSize();

        Assertions.assertEquals(parentWidth, preferredSize.height);
    }

    @Test
    public void testAspectRatioScalingForVerticalBoard() {

        Board board = new Board(16, 20, 40);

        BoardView boardView = new BoardView(
                null,
                board
        );

        JPanel parent = new JPanel(new FlowLayout());
        parent.setSize(1024, 768);

        parent.add(boardView);

        parent.revalidate();

        Dimension preferredSize = boardView.getPreferredSize();

        Assertions.assertEquals(preferredSize.height / board.rows(), preferredSize.width / board.cols());
    }

    @Test
    public void testAspectRatioScalingForHorizontalBoard() {

        Board board = new Board(20, 16, 40);

        BoardView boardView = new BoardView(
                null,
                board
        );

        JPanel parent = new JPanel(new FlowLayout());
        parent.setSize(1024, 768);

        parent.add(boardView);

        parent.revalidate();

        Dimension preferredSize = boardView.getPreferredSize();

        Assertions.assertEquals(preferredSize.height / board.rows(), preferredSize.width / board.cols());
    }
}
