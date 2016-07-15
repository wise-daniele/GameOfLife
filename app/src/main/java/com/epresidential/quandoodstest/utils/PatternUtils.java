package com.epresidential.quandoodstest.utils;

import android.content.Context;
import android.util.Log;

import com.epresidential.quandoodstest.R;

import java.util.Random;

/**
 * Created by daniele on 11/07/16.
 */
public class PatternUtils {

    private static final String LOG_TAG = PatternUtils.class.getSimpleName();

    public static int[][] randomPattern(Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        Random rand = new Random();
        int pattern[][] = new int[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                pattern[i][j] = rand.nextInt(2);
            }
        }
        return pattern;
    }

    public static int[][] noPattern(Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        int pattern[][] = new int[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                pattern[i][j] = 0;
            }
        }
        return pattern;
    }

    public static boolean isPatternDead(int[][] currentStep, Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                if(currentStep[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    public static int[][] nextStep(int[][] currentStep, Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        int[][] nextStep = new int[gridSize][gridSize];
        boolean constantPattern = true;
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                int alive = getAlive(currentStep, i, j, context);
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

    public static int getAlive(int[][] currentStep, int row, int column, Context context){
        int count = 0;
        int position = getGridPosition(row, column, context);
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

    public static int getGridPosition(int row, int column, Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        if(row == 0 && column == 0){
            return Constants.TOP_LEFT;
        }
        else if(row == 0 && column == gridSize - 1){
            return Constants.TOP_RIGHT;
        }
        else if(row == gridSize - 1 && column == 0){
            return Constants.BOTTOM_LEFT;
        }
        else if(row == gridSize - 1 && column == gridSize - 1){
            return Constants.BOTTOM_RIGHT;
        }
        else if(row == 0){
            return Constants.TOP;
        }
        else if(column == 0){
            return Constants.LEFT;
        }
        else if(row == gridSize - 1){
            return Constants.BOTTOM;
        }
        else if(column == gridSize - 1){
            return Constants.RIGHT;
        }
        else{
            return Constants.CENTER;
        }
    }

    //Does not work for grids with size less than 17x17
    public static int[][] pulsarPattern(Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        int pattern[][] = new int[gridSize][gridSize];
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

    public static int[][] blockConstantPattern(Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        int pattern[][] = new int[gridSize][gridSize];
        int half_size = gridSize/2;
        pattern[half_size][half_size] = 1;
        pattern[half_size][half_size + 1] = 1;
        pattern[half_size + 1][half_size] = 1;
        pattern[half_size + 1][half_size + 1] = 1;
        return pattern;
    }

    public static int[][] beaconPattern(Context context){
        int gridSize = context.getResources().getInteger(R.integer.grid_size);
        int pattern[][] = new int[gridSize][gridSize];
        int half_size = gridSize/2;
        pattern[half_size - 1][half_size - 1] = 1;
        pattern[half_size - 1][half_size] = 1;
        pattern[half_size][half_size - 1] = 1;
        pattern[half_size][half_size] = 1;
        pattern[half_size + 1][half_size + 1] = 1;
        pattern[half_size + 1][half_size + 2] = 1;
        pattern[half_size + 2][half_size + 1] = 1;
        pattern[half_size + 2][half_size + 2] = 1;
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
