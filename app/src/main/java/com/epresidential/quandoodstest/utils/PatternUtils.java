package com.epresidential.quandoodstest.utils;

import android.util.Log;

import java.util.Random;

/**
 * Created by daniele on 11/07/16.
 */
public class PatternUtils {

    private static final String LOG_TAG = PatternUtils.class.getSimpleName();

    public static int[][] randomPattern(){
        Random rand = new Random();
        int pattern[][] = new int[20][20];
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                pattern[i][j] = rand.nextInt(2);
            }
        }
        return pattern;
    }

    public static int[][] noPattern(){
        int pattern[][] = new int[20][20];
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                pattern[i][j] = 0;
            }
        }
        return pattern;
    }

    public static boolean isPatternDead(int[][] currentStep){
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(currentStep[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    public static int[][] nextStep(int[][] currentStep){
        int[][] nextStep = new int[20][20];
        boolean constantPattern = true;
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                int alive = getAlive(currentStep, i, j);
                if(alive == Constants.STATE_BORN) {
                    constantPattern = false;
                    nextStep[i][j] = 1;
                }
                else if(alive == Constants.STATE_ALIVE){
                    nextStep[i][j] = 1;
                }
                else if(alive == Constants.STATE_DEAD){
                    constantPattern = false;
                }
                else if(alive == Constants.STATE_LIFELESS){
                }
            }
        }
        if(constantPattern){
            return null;
        }
        else{
            return nextStep;
        }
    }

    public static int getAlive(int[][] currentStep, int row, int column){
        int count = 0;
        int position = getGridPosition(row, column);
        switch(position){
            case Constants.TOP_RIGHT:
                count = currentStep[row][column - 1] + currentStep[row + 1][column] +
                        currentStep[row + 1 ][column - 1];
                break;
            case Constants.TOP_LEFT:
                count = currentStep[row + 1][column] + currentStep[row][column + 1] +
                        currentStep[row + 1][column + 1];
                break;
            case Constants.BOTTOM_RIGHT:
                count = currentStep[row - 1][column] + currentStep[row][column - 1] +
                        currentStep[row - 1][column - 1];
                break;
            case Constants.BOTTOM_LEFT:
                count = currentStep[row - 1][column] + currentStep[row][column + 1] +
                        + currentStep[row - 1][column + 1];
                break;
            case Constants.LEFT:
                count = currentStep[row - 1][column] + currentStep[row + 1][column] +
                        currentStep[row][column + 1] + currentStep[row + 1][column + 1] +
                        currentStep[row - 1][column + 1];
                break;
            case Constants.RIGHT:
                count = currentStep[row - 1][column] + currentStep[row][column - 1] +
                        currentStep[row - 1][column - 1] + currentStep[row + 1][column] +
                        currentStep[row + 1 ][column - 1];
                break;
            case Constants.TOP:
                count = currentStep[row][column - 1] + currentStep[row + 1][column] +
                        currentStep[row][column + 1] + currentStep[row + 1][column + 1] +
                        currentStep[row + 1 ][column - 1];
                break;
            case Constants.BOTTOM:
                count = currentStep[row - 1][column] + currentStep[row][column - 1] +
                        currentStep[row - 1][column - 1] + currentStep[row][column + 1] +
                        currentStep[row - 1][column + 1];
                break;
            case Constants.CENTER:
                count = currentStep[row - 1][column] + currentStep[row][column - 1] +
                        currentStep[row - 1][column - 1] + currentStep[row + 1][column] +
                        currentStep[row][column + 1] + currentStep[row + 1][column + 1] +
                        currentStep[row + 1 ][column - 1] + currentStep[row - 1][column + 1];
                break;

        }
        if(currentStep[row][column] == 1 && (count == 2 || count == 3)) {
            //living with 2 or 3
            return Constants.STATE_ALIVE;
        }
        else if(currentStep[row][column] == 0 && count == 3){
            //dead with exactly 3
            return Constants.STATE_BORN;
        }
        else if(currentStep[row][column] == 0 && count != 3){
            //living with more than 3 and fewer than 2, dead with no 3 neighbors
            return Constants.STATE_LIFELESS;
        }
        else if(currentStep[row][column] == 1 && (count < 2 || count > 3)){
            //living with more than 3 and fewer than 2, dead with no 3 neighbors
            return Constants.STATE_DEAD;
        }
        else {
            //living with more than 3 and fewer than 2, dead with no 3 neighbors
            return Constants.STATE_UNDEFINED;
        }
    }

    public static int getGridPosition(int row, int column){
        if(row == 0 && column == 0){
            return Constants.TOP_LEFT;
        }
        else if(row == 0 && column == 19){
            return Constants.TOP_RIGHT;
        }
        else if(row == 19 && column == 0){
            return Constants.BOTTOM_LEFT;
        }
        else if(row == 19 && column == 19){
            return Constants.BOTTOM_RIGHT;
        }
        else if(row == 0){
            return Constants.TOP;
        }
        else if(column == 0){
            return Constants.LEFT;
        }
        else if(row == 19){
            return Constants.BOTTOM;
        }
        else if(column == 19){
            return Constants.RIGHT;
        }
        else{
            return Constants.CENTER;
        }
    }

    public static int[][] pulsarPattern(){
        int pattern[][] = new int[20][20];
        int i = 4;
        while(i<=16){
            if(i == 4 || i == 9 || i == 11 || i == 16 ){
                pattern[i][6] = 1;
                pattern[i][7] = 1;
                pattern[i][8] = 1;
                pattern[i][12] = 1;
                pattern[i][13] = 1;
                pattern[i][14] = 1;
            }
            else if(i == 6 || i == 7 || i == 8 || i == 12 || i == 13 || i == 14 ){
                pattern[i][4] = 1;
                pattern[i][9] = 1;
                pattern[i][11] = 1;
                pattern[i][16] = 1;
            }
            i++;
        }
        return pattern;
    }

    public static int[][] blockConstantPattern(){
        int pattern[][] = new int[20][20];
        pattern[10][10] = 1;
        pattern[10][11] = 1;
        pattern[11][10] = 1;
        pattern[11][11] = 1;
        return pattern;
    }

    public static int[][] beaconPattern(){
        int pattern[][] = new int[20][20];
        pattern[9][9] = 1;
        pattern[9][10] = 1;
        pattern[10][9] = 1;
        pattern[10][10] = 1;
        pattern[11][11] = 1;
        pattern[11][12] = 1;
        pattern[12][11] = 1;
        pattern[12][12] = 1;
        return pattern;
    }

    public static int[][] switchNegative(int[][] pattern){

        for(int i = 0; i < pattern.length; i++){
            for(int j = 0; j < pattern[0].length; j++){
                if(pattern[i][j] == 0){
                    pattern[i][j] = 1;
                }
                else{
                    pattern[i][j] = 0;
                }
            }
        }
        return pattern;
    }
}
