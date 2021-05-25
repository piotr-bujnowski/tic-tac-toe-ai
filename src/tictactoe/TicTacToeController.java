package tictactoe;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller
 */

public class TicTacToeController {

    private TicTacToe view;
    private TicTacToeModel model;

    private int clickCount;
    private boolean gameEnded = false;

    public TicTacToeController(TicTacToe view, TicTacToeModel model) {
        this.view = view;
        this.model = model;

        addCellsListener();
        addStartResetBtnListener();
        addPlayerChoiceBtnsListener();
        addMenuListener();
    }

    private void addCellsListener() {
        view.getCells().forEach(e -> e.addActionListener(listener -> {
            String player1Choice = view.getPlayerChoice1Btn().getText();
            String player2Choice = view.getPlayerChoice2Btn().getText();

            if (player1Choice.equals("Human") && player2Choice.equals("Human")) {
                if ((clickCount % 2) == 0) {
                    e.setText("X");
                } else {
                    e.setText("O");
                }
                e.setEnabled(false);
                clickCount++;
            } else if (player1Choice.equals("Human") && player2Choice.equals("Robot")
                    || player1Choice.equals("Robot") && player2Choice.equals("Human")) {
                if ((clickCount % 2) == 0) {
                    e.setText("X");
                } else {
                    e.setText("O");
                }
                e.setEnabled(false);
                clickCount++;

                try {
                    Thread.sleep(2);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                if (!gameEnded) {
                    if (player1Choice.equals("Robot")) {
                        view.insertIntoRandomNotOccupiedCell("X");
                    } else {
                        view.insertIntoRandomNotOccupiedCell("O");
                    }
                    clickCount++;
                }
            }
        }));
    }

    public void addStartResetBtnListener() {
        view.getStartResetBtn().addActionListener(e -> {
            String btnText = view.getStartResetBtnText();

            if (btnText.equals("Start")) {
                startGame();

            } else if (btnText.equals("Reset")) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        gameEnded = false;
        view.setStatusLabelText("Game is not started");
        view.getStartResetBtn().setText("Start");
        clickCount = 0;

        view.getMenu().setEnabled(true);

        view.getCells().forEach(cell -> {
            cell.setEnabled(true);
            cell.setText(" ");
        });

        view.disableAllCells();
        view.getPlayerChoice1Btn().setEnabled(true);
        view.getPlayerChoice2Btn().setEnabled(true);
    }

    private void startGame() {
        startCheckingGameProgress();
        view.setStatusLabelText("Game in progress");
        view.getStartResetBtn().setText("Reset");
        view.getPlayerChoice1Btn().setEnabled(false);
        view.getPlayerChoice2Btn().setEnabled(false);
        view.enableAllCells();

        view.getMenu().setEnabled(false);

        String player1Choice = view.getPlayerChoice1Btn().getText();
        String player2Choice = view.getPlayerChoice2Btn().getText();

        if (player1Choice.equals("Robot") && player2Choice.equals("Human")) {
            view.insertIntoRandomNotOccupiedCell("X");
            clickCount++;
        }

        if (player1Choice.equals("Robot") && player2Choice.equals("Robot")) {
            view.disableAllCells();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (gameEnded) {
                        timer.cancel();
                        timer.purge();
                    } else {
                        view.insertIntoRandomNotOccupiedCell(clickCount % 2 == 0 ? "X" : "O");
                        clickCount++;
                    }
                }
            };
            timer.schedule(task, 0, 500);
        }
    }

    public void addPlayerChoiceBtnsListener() {
        view.getPlayerChoice1Btn().addActionListener(e -> {
            String player1ChoiceBtnText = view.getPlayerChoice1Btn().getText();

            if (player1ChoiceBtnText.equals("Human")) {
                view.getPlayerChoice1Btn().setText("Robot");
            } else {
                view.getPlayerChoice1Btn().setText("Human");
            }
        });

        view.getPlayerChoice2Btn().addActionListener(e -> {
            String player2ChoiceBtnText = view.getPlayerChoice2Btn().getText();

            if (player2ChoiceBtnText.equals("Human")) {
                view.getPlayerChoice2Btn().setText("Robot");
            } else {
                view.getPlayerChoice2Btn().setText("Human");
            }
        });
    }

    public void addMenuListener() {
        view.getMenuHumanHuman().addActionListener(e -> {
            view.getPlayerChoice1Btn().setText("Human");
            view.getPlayerChoice2Btn().setText("Human");
            startGame();
        });

        view.getMenuHumanRobot().addActionListener(e -> {
            view.getPlayerChoice1Btn().setText("Human");
            view.getPlayerChoice2Btn().setText("Robot");
            startGame();
        });

        view.getMenuRobotHuman().addActionListener(e -> {
            view.getPlayerChoice2Btn().setText("Robot");
            view.getPlayerChoice1Btn().setText("Human");
            startGame();
        });

        view.getMenuRobotRobot().addActionListener(e -> {
            view.getPlayerChoice1Btn().setText("Robot");
            view.getPlayerChoice2Btn().setText("Robot");
            startGame();
        });

        view.getMenuExit().addActionListener(e -> System.exit(0));
    }

    public void startCheckingGameProgress() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (gameEnded) {
                    timer.cancel();
                    timer.purge();
                } else {
                    if (model.isDraw(clickCount)) {
                        view.setStatusLabelText("Draw");
                        gameEnded = true;
                    }

                    if (model.checkRowColWin(view.convertBoardInto2dArray()) == 'X'
                            || model.checkForCrossWins(view.convertBoardInto2dArray()) == 'X') {
                        view.setStatusLabelText("X wins");
                        view.disableAllCells();
                        gameEnded = true;
                    } else if (model.checkRowColWin(view.convertBoardInto2dArray()) == 'O'
                            || model.checkForCrossWins(view.convertBoardInto2dArray()) == 'O') {
                        view.setStatusLabelText("O wins");
                        view.disableAllCells();
                        gameEnded = true;
                    }
                }
            }
        };

        timer.schedule(task, 0, 1);
    }
}
