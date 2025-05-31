package com.example.tictactoeaiminimax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button b1, b2, b3, b4, b5 ,b6, b7, b8, b9;
    @FXML
    private Label winLabel;
    private Button[][] buttonList;
    private final String maximiser = "O";
    private final String minimiser = "X";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonList = new Button[][] {
                { b1, b2, b3 },
                { b4, b5, b6 },
                { b7, b8, b9 }
        };
    }

    @FXML
    private void setOnClick(ActionEvent e) {
        for (Button[] buttons : buttonList) {
            for (Button button : buttons) {
                if(e.getSource() == button && button.getText().equals("")) {

                    if (!winLabel.getText().isEmpty()) {
                        winLabel.setText("");
                    } else {
                        button.setText(minimiser);
                        String result = checkWin(buttonList);

                        //Checks if the human has won, if not the AI plays
                        if (result != null) {
                            restart(result);
                        } else {
                            handleAIMove(true);
                        }
                    }
                    return;
                }
            }
        }
    }



    //AI -----------------------------------------------------------------------------------
    public void handleAIMove(boolean pruning) {
        int bestScore = Integer.MIN_VALUE;
        int row = -1;
        int column = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(buttonList[i][j].getText().equals("")) {
                    buttonList[i][j].setText(maximiser);
                    int score = pruning ?
                            minimax(buttonList, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false) :
                            minimax(buttonList, 0, false);
                    buttonList[i][j].setText("");
                    if(score > bestScore) {
                        bestScore = score;
                        row = i;
                        column = j;
                    }
                }
            }
        }
        buttonList[row][column].setText(maximiser);
        String result = checkWin(buttonList);
        if(result != null) {
            restart(result);
        }
    }
    private int minimax(Button[][] board, int depth, boolean isMaxing) {

        String result = checkWin(board);
        if(result != null) {
            if (result.equals(maximiser)) return 100 - depth;
            if (result.equals(minimiser)) return depth - 100;
            if (result.equals("D")) return 0;
        }

        if(isMaxing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(board[i][j].getText().equals("")) {
                        board[i][j].setText(maximiser);
                        int score = minimax(board, depth + 1, false);
                        board[i][j].setText("");
                        bestScore = Math.max(score, bestScore); //Locating the least deap best possible move
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(minimiser);
                        int score = minimax(board, depth + 1, true);
                        board[i][j].setText("");
                        bestScore = Math.min(score, bestScore); //Locating the most deap best possible move
                    }
                }
            }
            return bestScore;
        }
    }

    private int minimax(Button[][] board, int depth, int alpha, int beta, boolean isMaxing) {

        String result = checkWin(board);
        if(result != null) {
            if (result.equals(maximiser)) return 10 - depth;
            if (result.equals(minimiser)) return depth - 10;
            if (result.equals("D")) return 0;
        }

        if(isMaxing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(board[i][j].getText().equals("")) {
                        board[i][j].setText(maximiser);
                        int score = minimax(board, depth + 1, alpha, beta, false);
                        board[i][j].setText("");
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(bestScore, alpha); //Locating the least deap best possible move
                        if(beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(minimiser);
                        int score = minimax(board, depth + 1, alpha, beta, true);
                        board[i][j].setText("");
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(bestScore, beta); //Locating the most deap best possible move
                        if(beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        }
    }


    //Win Conditions -----------------------------------------------------------------------------------
    private String checkWin(Button[][] board) {
        for (int i = 0; i < 3; i++) {
            //Checking Rows
            if(!buttonList[i][0].getText().isEmpty() && checkLineEqual(buttonList[i][0], buttonList[i][1], buttonList[i][2])) {
                return buttonList[i][0].getText();
            }
        }

        for (int i = 0; i < 3; i++) {
            //Checking Columns
            if(!buttonList[0][i].getText().isEmpty() && checkLineEqual(buttonList[0][i], buttonList[1][i], buttonList[2][i])) {
                return buttonList[0][i].getText();
            }
        }

        //Check Diagonals
        if(!board[1][1].getText().isEmpty() && (checkLineEqual(board[0][0], board[1][1], board[2][2])
                || checkLineEqual(board[0][2], board[1][1], board[2][0]))) {
            return buttonList[1][1].getText();
        }

        //Check Draw
        if (Arrays.stream(board).flatMap(Arrays::stream).noneMatch(button -> button.getText().isEmpty())) {
            return "D";
        }

        return null;
    }

    private boolean checkLineEqual(Button b1, Button b2, Button b3) {
        return b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText());
    }
    private void restart(String result) {

        if (result.equals("D")) {
            winLabel.setText("DRAW");
        } else {
            winLabel.setText(result + ": WINS");
        }

        Arrays.stream(buttonList)
                .flatMap(Arrays::stream)
                .forEach(button -> button.setText(""));
    }
}