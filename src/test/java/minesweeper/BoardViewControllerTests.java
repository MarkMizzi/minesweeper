package minesweeper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class BoardViewControllerTests {

    @Test
    public void testPreferredSizeFitsParent() {
        BoardViewController boardViewController = new BoardViewController(
                null,
                new Board(16, 16, 40)
        );

        JPanel parent = new JPanel(new FlowLayout());
        parent.setSize(1024, 768);

        parent.add(boardViewController);

        parent.revalidate();

        int parentHeight = parent.getHeight(), parentWidth = parent.getWidth();
        Dimension preferredSize = boardViewController.getPreferredSize();

        Assertions.assertEquals(
                Math.min(parentHeight, parentWidth),
                Math.min(preferredSize.height, preferredSize.width)
        );
    }
}
