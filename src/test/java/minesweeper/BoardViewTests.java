package minesweeper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class BoardViewTests {

    @Test
    public void testPreferredSizeFitsParent() {
        BoardView boardView = new BoardView(
                null,
                new Board(16, 16, 40)
        );

        JPanel parent = new JPanel(new FlowLayout());
        parent.setSize(1024, 768);

        parent.add(boardView);

        parent.revalidate();

        int parentHeight = parent.getHeight(), parentWidth = parent.getWidth();
        Dimension preferredSize = boardView.getPreferredSize();

        Assertions.assertEquals(
                Math.min(parentHeight, parentWidth),
                Math.min(preferredSize.height, preferredSize.width)
        );
    }
}
