package tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    public TicTacToe() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);


        initComponents();

        setVisible(true);
        setLayout(null);
    }

    private void initComponents() {
        JPanel gameBoard = crateGameBoard();
        add(gameBoard);
    }

    private JPanel crateGameBoard() {
        JPanel boardPane = new JPanel();
        boardPane.setLayout(new GridLayout(3, 3));

        for (int i = 3; i > 0; i--) {
            for (char ch : new char[]{'A', 'B', 'C'}) {
                JButton btn = new JButton(ch + "" + i);
                btn.setName("Button" + ch + i);
                boardPane.add(btn);
            }
        }
        return boardPane;
    }
}