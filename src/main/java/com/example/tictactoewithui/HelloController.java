package com.example.tictactoewithui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;


public class HelloController {
    private ArrayList<Button> buttonList = new ArrayList<>(9);
    private TicTacToeAI ai = new TicTacToeAI();
    private boolean playerIsX = true;
    private boolean gameOn = true;

    @FXML
    private Label turn;

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;

    private void resetBoard() {
        for(int i=0; i < ai.getBoard().length; i++) {
            ai.getBoard()[i] = 1;
            ai.setPlayerTurn(0);
        }

        for(Button button : buttonList) {
            Platform.runLater(() -> button.setText(""));
            playerIsX = !playerIsX;
        }
        Platform.runLater(() -> turn.setText("Tic Tac Toe"));
        if(!playerIsX) {
            int aiMove = TicTacToeAI.AIMakeMove(ai.getBoard(), ai.getPlayerTurn(), 1);
            playMove(!playerIsX, aiMove);
        }
        gameOn = true;
    }

    private boolean checkWinState() {
        int result = TicTacToeAI.checkBoardState(ai.getBoard());
        if(result == 1) {
            gameOn = false;
            Platform.runLater(() -> turn.setText("Player X Won!"));
            try {
                Thread.sleep(5000);
            } catch(InterruptedException a) {}
            resetBoard();
        }
        else if(result == -1) {
            gameOn = false;
            Platform.runLater(() -> turn.setText("Player O Won!"));
            try {
                Thread.sleep(5000);
            } catch(InterruptedException a) {}
            resetBoard();
        }
        else if(result == 0) {
            gameOn = false;
            Platform.runLater(() -> turn.setText("It was a Draw!"));
            try {
                Thread.sleep(5000);
            } catch(InterruptedException a) {}
            resetBoard();
        }
        else {
            return false;
        }
        return true;
    }

    private boolean playMove(boolean playerXTurn, int loc) {
        Platform.runLater(() -> buttonList.get(loc).setText(playerXTurn ? "X" : "O"));
        ai.take_turn(loc);
        ai.setPlayerTurn(playerXTurn ? 2 : 0);
        return checkWinState();
    }

    @FXML
    private void initialize() {
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);
        buttonList.add(button6);
        buttonList.add(button7);
        buttonList.add(button8);
        buttonList.add(button9);

        for (int i = 0; i < buttonList.size(); i++) {
            final int loc = i;
            buttonList.get(i).setOnAction(e ->
                new Thread(() -> {
                    if(buttonList.get(loc).getText().isEmpty() && gameOn) {
                        boolean won = playMove(playerIsX, loc);

                        if(!won) {
                            int aiMove = TicTacToeAI.AIMakeMove(ai.getBoard(), ai.getPlayerTurn(), 1);
                            playMove(!playerIsX, aiMove);
                        }
                    }
                }).start());
        }
    }
}