package minesweeper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTests {
    @Test
    public void testIllegalBoardConstruction() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Board(10, 10, 10*10));
    }

    @Test
    public void testRowsMethod() {
        Board b = new Board(16, 32, 10);
        Assertions.assertEquals(32, b.rows());
    }

    @Test
    public void testColsMethod() {
        Board b = new Board(32, 16, 10);
        Assertions.assertEquals(32, b.cols());
    }

    @Test
    public void testNumberOfMines() {
        Board b = new Board(16, 16, 10);

        int mines = 0;
        for (int x = 0; x < b.cols(); x++) {
            for (int y = 0; y < b.rows(); y++) {
                if (b.boardValue(x, y) == Board.MINE_PLACEHOLDER) {
                    mines++;
                }
            }
        }

        Assertions.assertEquals(10, mines);
    }

    @Test
    public void testEverythingCoveredAtFirst() {
        Board b = new Board(16, 16, 10);

        for (int x = 0; x < b.cols(); x++) {
            for (int y = 0; y < b.rows(); y++) {
                if (!b.covered(x, y)) {
                    Assertions.fail();
                }
            }
        }
    }

    @Test
    public void testNothingFlaggedAtFirst() {
        Board b = new Board(16, 16, 10);

        for (int x = 0; x < b.cols(); x++) {
            for (int y = 0; y < b.rows(); y++) {
                if (b.flagged(x, y)) {
                    Assertions.fail();
                }
            }
        }
    }

    @Test
    public void testLoseOnBomb() {
        Board b = new Board(16, 16, 10);

        for (int x = 0; x < b.cols(); x++) {
            for (int y = 0; y < b.rows(); y++) {
                Board boardCopy = new Board(b);
                boardCopy.uncover(x, y);
                Assertions.assertEquals(
                        b.boardValue(x, y) == Board.MINE_PLACEHOLDER,
                        boardCopy.getStatus() == GameStatus.LOSE
                );
            }
        }
    }

    @Test
    public void testContinueOrWinOnNotBomb() {
        Board b = new Board(16, 16, 10);

        for (int x = 0; x < b.cols(); x++) {
            for (int y = 0; y < b.rows(); y++) {
                Board boardCopy = new Board(b);
                boardCopy.uncover(x, y);
                Assertions.assertEquals(
                        b.boardValue(x, y) != Board.MINE_PLACEHOLDER,
                        (boardCopy.getStatus() == GameStatus.CONTINUE || boardCopy.getStatus() == GameStatus.WIN)
                );
            }
        }
    }
}
