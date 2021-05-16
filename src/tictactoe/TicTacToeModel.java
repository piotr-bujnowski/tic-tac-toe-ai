package tictactoe;

import javax.swing.*;
import java.util.Iterator;

public class TicTacToeModel {

    private TicTacToe ticTacToe;

    public TicTacToeModel(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    // check for horizontal and vertical wins
    public char checkRowColWin() {
        JButton[][] boardArray = convertInto2dArray();
        char whoWins = 0;

        for (int i = 0; i < boardArray.length; i++) {
            int horizontal = 0;
            int vertical = 0;

            for (int j = 0; j < boardArray[0].length; j++) {
                horizontal += boardArray[i][j].getText().charAt(0);
                vertical += boardArray[j][i].getText().charAt(0);

                if (horizontal == 264 || vertical == 264) {
                    whoWins = 'X';
                }else if (horizontal == 237 || vertical == 237) {
                    whoWins = 'O';
                }
            }
        }
        return whoWins;
    }

    // check for cross wins
    public char checkForCrossWins() {
        JButton[][] boardArray = convertInto2dArray();
        char whoWins = 0;

        int leftCross = boardArray[0][0].getText().charAt(0)
                      + boardArray[1][1].getText().charAt(0)
                      + boardArray[2][2].getText().charAt(0);

        int rightCross = boardArray[0][2].getText().charAt(0)
                       + boardArray[1][1].getText().charAt(0)
                       + boardArray[2][0].getText().charAt(0);

        if (leftCross == 264 || rightCross == 264) {
            whoWins = 'X';
        }else if (leftCross == 237 || rightCross == 237) {
            whoWins = 'O';
        }
        return whoWins;
    }

    // convert into 2d array for manipulating data
    private JButton[][] convertInto2dArray() {
        JButton[][] boardArray = new JButton[3][3];
        Iterator<JButton> cellsIterator = ticTacToe.getCells().iterator();

        while (cellsIterator.hasNext()) {
            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[0].length; j++) {
                    boardArray[i][j] = cellsIterator.next();
                }
            }
        }
        return boardArray;
    }
}
