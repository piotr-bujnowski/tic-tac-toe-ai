package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * View
 */

public class TicTacToe extends JFrame {

    private JPanel whoVersusWhoPanel;
    private JPanel gameBoardPanel;
    private JPanel statusBarPanel;
    private JLabel statusLabel;
    private JButton startResetBtn;
    private JButton playerChoice1Btn;
    private JButton playerChoice2Btn;

    private List<JButton> cells = new ArrayList<>();

    private Random random;

    public TicTacToe() {
        super("Tic Tac Toe");
        random = new Random();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        initComponents();
        disableAllCells();

        setVisible(true);
        setLayout(new BorderLayout());
    }

    public void initComponents() {
        playerChoice1Btn = new JButton("Human");
        playerChoice1Btn.setName("ButtonPlayer1");

        startResetBtn = new JButton("Start");
        startResetBtn.setName("ButtonStartReset");

        playerChoice2Btn = new JButton("Human");
        playerChoice2Btn.setName("ButtonPlayer2");

        whoVersusWhoPanel = new JPanel();
        whoVersusWhoPanel.setLayout(new GridLayout(1, 3));
        whoVersusWhoPanel.add(playerChoice1Btn);
        whoVersusWhoPanel.add(startResetBtn);
        whoVersusWhoPanel.add(playerChoice2Btn);
        add(whoVersusWhoPanel, BorderLayout.PAGE_START);

        gameBoardPanel = crateGameBoard();
        add(gameBoardPanel, BorderLayout.CENTER);

        statusBarPanel = new JPanel();
        statusBarPanel.setLayout(new BorderLayout());
        statusBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("Game is not started");
        statusLabel.setName("LabelStatus");

        statusBarPanel.add(statusLabel, BorderLayout.LINE_START);
        add(statusBarPanel, BorderLayout.PAGE_END);
    }

    /**
     *  Creates JPanel with for tic tac toe board
     */
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

    /**
     * Convert board into 2d array for manipulating data
     */
    public JButton[][] convertBoardInto2dArray() {
        JButton[][] boardArray = new JButton[3][3];
        Iterator<JButton> cellsIterator = this.getCells().iterator();

        while (cellsIterator.hasNext()) {
            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[0].length; j++) {
                    boardArray[i][j] = cellsIterator.next();
                }
            }
        }
        return boardArray;
    }

    /**
     * Inserts @param whoseTurn (X or Y) to random cell on board
     * where button is empty " "
     */
    public void insertIntoRandomNotOccupiedCell(String whoseTurn) {
        int randomIndex = random.nextInt(9);
        JButton randomBtn = cells.get(randomIndex);

        while (!cells.get(randomIndex).getText().equals(" ")) {
            randomIndex = random.nextInt(9);
            randomBtn = cells.get(randomIndex);
        }
        randomBtn.setText(whoseTurn);
        randomBtn.setEnabled(false);
        cells.set(cells.indexOf(randomBtn), randomBtn);
    }

    public void disableAllCells() {
        this.cells.forEach(e -> e.setEnabled(false));
    }

    public void enableAllCells() {
        this.cells.forEach(e -> e.setEnabled(true));
    }

    public List<JButton> getCells() {
        return cells;
    }

    public void setStatusLabelText(String text) {
        this.statusLabel.setText(text);
    }

    public JButton getStartResetBtn() {
        return startResetBtn;
    }

    public String getStartResetBtnText() {
        return startResetBtn.getText();
    }

    public JButton getPlayerChoice1Btn() {
        return playerChoice1Btn;
    }

    public JButton getPlayerChoice2Btn() {
        return playerChoice2Btn;
    }
}