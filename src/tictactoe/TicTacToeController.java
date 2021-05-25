package tictactoe;

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
                startCheckingGameProgress();
                view.setStatusLabelText("Game in progress");
                view.getStartResetBtn().setText("Reset");
                view.getPlayerChoice1Btn().setEnabled(false);
                view.getPlayerChoice2Btn().setEnabled(false);
                view.enableAllCells();

                String player1Choice = view.getPlayerChoice1Btn().getText();
                String player2Choice = view.getPlayerChoice2Btn().getText();

                if (player1Choice.equals("Robot") && player2Choice.equals("Human")) {
                    view.insertIntoRandomNotOccupiedCell("X");
                    clickCount++;
                }

                if (player1Choice.equals("Robot") && player2Choice.equals("Robot")) {
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

            } else if (btnText.equals("Reset")) {
                gameEnded = false;
                view.setStatusLabelText("Game is not started");
                view.getStartResetBtn().setText("Start");
                clickCount = 0;

                view.getCells().forEach(cell -> {
                    cell.setEnabled(true);
                    cell.setText(" ");
                });

                view.disableAllCells();
                view.getPlayerChoice1Btn().setEnabled(true);
                view.getPlayerChoice2Btn().setEnabled(true);
            }
        });
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
