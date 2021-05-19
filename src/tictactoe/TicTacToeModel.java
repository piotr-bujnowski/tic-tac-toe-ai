package tictactoe;

import javax.swing.*;

/**
 * Model
 */

public class TicTacToeModel {

    /**
     * Check for horizontal and vertical wins
     */
    public char checkRowColWin(JButton[][] boardArray) {
        char whoWins = 0;

        for (int i = 0; i < boardArray.length; i++) {
            int horizontal = 0;
            int vertical = 0;

            for (int j = 0; j < boardArray[0].length; j++) {
                horizontal += boardArray[i][j].getText().charAt(0);
                vertical += boardArray[j][i].getText().charAt(0);

                whoWins = checkWin(horizontal, vertical, whoWins);
            }
        }
        return whoWins;
    }

    /**
     * Check for cross wins
     */
    public char checkForCrossWins(JButton[][] boardArray) {
        char whoWins = 0;

        int leftCross = boardArray[0][0].getText().charAt(0)
                      + boardArray[1][1].getText().charAt(0)
                      + boardArray[2][2].getText().charAt(0);

        int rightCross = boardArray[0][2].getText().charAt(0)
                       + boardArray[1][1].getText().charAt(0)
                       + boardArray[2][0].getText().charAt(0);

        return checkWin(leftCross, rightCross, whoWins);
    }

    /**
     * Check who won based on calculations
     * X + X + X = 264, O + O + O = 237
     * @return X or O
     */
    public char checkWin(int horizontal, int vertical, char whoWins) {
        if (horizontal == 264 || vertical == 264) {
            whoWins = 'X';
        }else if (horizontal == 237 || vertical == 237) {
            whoWins = 'O';
        }
        return whoWins;
    }

    public boolean isDraw(int clickCount) {
        return clickCount == 9;
    }


}
