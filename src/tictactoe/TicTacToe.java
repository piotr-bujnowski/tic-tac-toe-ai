package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends JFrame {

    private TicTacToeModel ticTacToeModel;
    private List<JButton> cells = new ArrayList<>();
    private int clickCount;

    public TicTacToe() {
        super("Tic Tac Toe");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        initComponents();
        this.ticTacToeModel = new TicTacToeModel(this);

        setVisible(true);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        JPanel gameBoard = crateGameBoard();
        add(gameBoard, BorderLayout.CENTER);

        JPanel statusBar = new JPanel();
        statusBar.setLayout(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel statusLabel = new JLabel("Game is not started");
        statusLabel.setName("LabelStatus");

        JButton resetBtn = new JButton("Reset");
        resetBtn.setName("ButtonReset");

        statusBar.add(statusLabel, BorderLayout.LINE_START);
        statusBar.add(resetBtn, BorderLayout.LINE_END);
        add(statusBar, BorderLayout.PAGE_END);

        int counter = 0;
        this.cells.forEach(e -> e.addActionListener(listener -> {
            statusLabel.setText("Game in progress");
            if ((clickCount % 2) == 0) {
                e.setText("X");
            } else {
                e.setText("O");
            }
            e.setEnabled(false);

            this.clickCount++;

            if (clickCount == 9) {
                statusLabel.setText("Draw");
            }

            if (this.ticTacToeModel.checkRowColWin() == 'X' || this.ticTacToeModel.checkForCrossWins() == 'X') {
                statusLabel.setText("X wins");
                disableAllCells();
            }else if (this.ticTacToeModel.checkRowColWin() == 'O' || this.ticTacToeModel.checkForCrossWins() == 'O') {
                statusLabel.setText("O wins");
                disableAllCells();
            }
            System.out.println(clickCount);
        }));

        resetBtn.addActionListener(e -> {
            statusLabel.setText("Game is not started");
            clickCount = 0;
            this.cells.forEach(cell -> {
                cell.setEnabled(true);
                cell.setText(" ");
            });
        });
    }

    private JPanel crateGameBoard() {
        JPanel boardPane = new JPanel();
        boardPane.setLayout(new GridLayout(3, 3));
        Font font = new Font(Font.DIALOG, Font.BOLD, 40);

        for (int i = 3; i > 0; i--) {
            for (char ch : new char[]{'A', 'B', 'C'}) {
                JButton btn = new JButton(" ");
                btn.setName("Button" + ch + i);
                btn.setFont(font);
//                btn.setBackground(Color.GRAY);
//                btn.setForeground(Color.DARK_GRAY);
                btn.setFocusPainted(false);
                boardPane.add(btn);

                this.cells.add(btn);
            }
        }
        return boardPane;
    }

    private void disableAllCells() {
        this.cells.forEach(e -> e.setEnabled(false));
    }

    public List<JButton> getCells() {
        return cells;
    }
}