package tictactoe;

public class ApplicationRunner {
    public static void main(String[] args) {
        TicTacToe view = new TicTacToe();
        TicTacToeModel model = new TicTacToeModel();

        TicTacToeController controller = new TicTacToeController(view, model);
    }
}
