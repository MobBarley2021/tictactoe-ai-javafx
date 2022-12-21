package com.example.tictactoewithui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class TicTacToeAI {
    private int playerTurn = 0;
    private int[] board = new int[9];


    public TicTacToeAI() {
        for(int i=0; i < 9; i++){
            board[i] = 1;
        }
    }

    public static int getExtremeIndex(boolean max, ArrayList<Integer> options){
        if(max){
            int maxOption = 0;
            for(int i = 1; i < options.size(); i++){
                if(options.get(i) > options.get(maxOption)){
                    maxOption = i;
                }
            }
            return maxOption;

        } else {
            int minOption = 0;
            for(int i = 1; i < options.size(); i++){
                if(options.get(i) < options.get(minOption)){
                    minOption = i;
                }
            }
            return minOption;
        }
    }


    public void take_turn(int loc) {
        board[loc] = playerTurn;
    }


    public void take_turn(int x, int y) {
        if(!(x<4 && y<4 && x>0 && y>0)){
            throw new IndexOutOfBoundsException();
        }
        else if(board[3 * (y - 1) + x-1] != 1){
            throw new IllegalArgumentException();
        } else {
            board[3 * (y - 1) + x-1] = playerTurn;
        }
    }


    public void showBoard(){
        String[] letteredBoard = new String[9];
        for(int i = 0; i<9; i++){
            switch(board[i]){
                case 0:
                    letteredBoard[i] = "X";
                    break;
                case 1:
                    letteredBoard[i] = " ";
                    break;
                case 2:
                    letteredBoard[i] = "O";
                    break;
            }
        }
        System.out.println(letteredBoard[0]+" | "+letteredBoard[1]+" | "+letteredBoard[2]+"\n"
                +letteredBoard[3]+" | "+letteredBoard[4]+" | "+letteredBoard[5]+"\n"+
                letteredBoard[6]+" | "+letteredBoard[7]+" | "+letteredBoard[8]);
    }


    public static int checkBoardState(int[] board){
        for(int i=0; i<=6; i+=3){
            int row = board[i] + board[i+1] + board[i+2];
            if(row == 0){
                return 1;
            }
            else if(row == 6){
                return -1;
            }
        }
        for(int i=0; i<3; i++){
            int row = board[i] + board[i+3] + board[i+6];
            if(row == 0){
                return 1;
            }
            else if(row==6){
                return -1;
            }
        }

        int row = board[0] + board[4] + board[8];
        if(row == 0){
            return 1;
        }
        else if(row == 6){
            return -1;
        }

        row = board[2] + board[4] + board[6];
        if(row == 0){
            return 1;
        }
        else if(row == 6){
            return -1;
        }

        for(int tick : board){
            if(tick==1){
                return 3;
            }
        }
        return 0;
    }


    public static int AIMakeMove(int[] inputBoard, int AITurn, int recursionLevel){
        boolean emptinessTest = true;
        for(int input : inputBoard){
            if(input != 1){
                emptinessTest = false;
            }
        }

        if(emptinessTest){
            Random rand = new Random();
            return rand.nextInt(5)*2;
        }

        int[] AIBoard = Arrays.copyOf(inputBoard,9);
        int nextTurn;
        int result = checkBoardState(AIBoard);
        ArrayList<Integer> possibleStates;

        if(AITurn == 0){
            nextTurn = 2;
        } else {
            nextTurn = 0;
        }

        if(result != 3){
            return result;
        } else {
            possibleStates = new ArrayList<>();
            if(recursionLevel == 1) {
                ArrayList<Integer> nullTicks = new ArrayList<>();

                for (int i = 0; i < AIBoard.length; i++) {
                    if (AIBoard[i] == 1) {
                        nullTicks.add(i);
                        AIBoard[i] = AITurn;
                        possibleStates.add(AIMakeMove(AIBoard, nextTurn, recursionLevel + 1));
                        AIBoard = Arrays.copyOf(inputBoard, 9);
                    }
                }

                if(AITurn == 0){
                    return nullTicks.get(getExtremeIndex(true, possibleStates));
                } else {
                    return nullTicks.get(getExtremeIndex(false, possibleStates));
                }

            } else {
                for (int i = 0; i < AIBoard.length; i++) {
                    if (AIBoard[i] == 1) {
                        AIBoard[i] = AITurn;
                        possibleStates.add(AIMakeMove(AIBoard, nextTurn, recursionLevel + 1));
                        AIBoard = Arrays.copyOf(inputBoard, 9);
                    }
                }
            }

            if(AITurn == 0){
                return Collections.max(possibleStates);
            } else {
                return Collections.min(possibleStates);
            }
        }
    }


    public int[] getBoard() {
        return board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn){
        this.playerTurn = playerTurn;
    }
}
