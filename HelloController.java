package samplefx.tictactoe2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML
    private Label myLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;

    private Button[][] board;
    private char[][] boardState;
    private boolean playerTurn; // true for X, false for O
    private int moveCount;
    private Map<Button, Buttons> btnMap = new HashMap<>();

    Image RX = new Image(getClass().getResourceAsStream("R_X.png"));
    Image BX = new Image(getClass().getResourceAsStream("B_X.png"));
    Image RO = new Image(getClass().getResourceAsStream("R_O.jfif"));
    Image BO = new Image(getClass().getResourceAsStream("B_O.png"));

    public void initialize() {
        board = new Button[][]{
                {button1, button2, button3},
                {button4, button5, button6},
                {button7, button8, button9}
        }; //Here, board is an 2D array to store the Button on FXML
        // It is mainly used to make the Class in Buttons i.e Buttons(Button button)

        boardState = new char[3][3];

        for (Button[] row : board) {
            for (Button button : row) {
                btnMap.put(button, new Buttons(button));
            }
        }

        resetGame();
        startButtonLock(true);
    }

    public void startButtonLock(boolean val) {
        for (Button[] row : board) {
            for (Button button : row) {
                button.setDisable(val);
            }
        }
    }

    @FXML
    public void startGame() {
        startButton.setVisible(false);
        resetGame();
        myLabel.setText("Player X's Turn");
        startButtonLock(false);
    }

    @FXML
    public void clickButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int[] position = findButtonPosition(clickedButton);
        Buttons selectedButton = btnMap.get(clickedButton);

        if (position != null && boardState[position[0]][position[1]] == ' ' && !selectedButton.checkLock()) {
            boardState[position[0]][position[1]] = playerTurn ? 'X' : 'O';
            selectedButton.setLock();
            setButtonImg(clickedButton, 1);
            moveCount++;

            if (checkWinner(position[0], position[1])) {
                myLabel.setText((playerTurn ? "Player X" : "Player O") + " Wins!");
                startButtonLock(true);
                startButton.setVisible(true);
            } else if (moveCount == 9) {
                myLabel.setText("It's a Draw!");
                startButtonLock(true);
                startButton.setVisible(true);
            } else {
                playerTurn = !playerTurn;
                changeLabel();
            }
        }
    }

    @FXML
    public void mouseEnter(MouseEvent event) {
        Button selectedBtn = (Button) event.getSource();
        Buttons btnState = btnMap.get(selectedBtn);
        if (!btnState.checkLock()) {
            setButtonImg(selectedBtn, 0);
        }
    }

    @FXML
    public void mouseExit(MouseEvent event) {
        Button selectedBtn = (Button) event.getSource();
        Buttons btnState = btnMap.get(selectedBtn);
        if (!btnState.checkLock()) {
            selectedBtn.setGraphic(null);
        }
    }

    public void setButtonImg(Button selectedButton, int val) {
        ImageView imgX;
        if (val == 0) {
            imgX = new ImageView(playerTurn ? BX : BO);
        } else {
            imgX = new ImageView(playerTurn ? RX : RO);
        }
        imgX.setFitHeight(selectedButton.getHeight() - 1);
        imgX.setFitWidth(selectedButton.getWidth() - 1);
        imgX.setPreserveRatio(true);
        selectedButton.setPadding(Insets.EMPTY);
        selectedButton.setGraphic(imgX);
    }

    public void changeLabel() {
        myLabel.setText(playerTurn ? "Player X's Turn" : "Player O's Turn");
    }

    private int[] findButtonPosition(Button button) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == button) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private boolean checkWinner(int row, int col) {
        char currentPlayerSymbol = playerTurn ? 'X' : 'O';

        // Check row
        if (boardState[row][0] == currentPlayerSymbol && boardState[row][1] == currentPlayerSymbol && boardState[row][2] == currentPlayerSymbol) {
            return true;
        }

        // Check column
        if (boardState[0][col] == currentPlayerSymbol && boardState[1][col] == currentPlayerSymbol && boardState[2][col] == currentPlayerSymbol) {
            return true;
        }

        // Check main diagonal
        if (row == col && boardState[0][0] == currentPlayerSymbol && boardState[1][1] == currentPlayerSymbol && boardState[2][2] == currentPlayerSymbol) {
            return true;
        }

        // Check anti-diagonal
        if (row + col == 2 && boardState[0][2] == currentPlayerSymbol && boardState[1][1] == currentPlayerSymbol && boardState[2][0] == currentPlayerSymbol) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setGraphic(null);
                boardState[row][col] = ' ';
                btnMap.get(board[row][col]).setUnlock();
            }
        }
        playerTurn = true;
        moveCount = 0;
        startButtonLock(false);
        myLabel.setText("Tic-Tac-Toe");
    }
}
