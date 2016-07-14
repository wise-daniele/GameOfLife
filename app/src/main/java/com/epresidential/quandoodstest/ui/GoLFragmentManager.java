package com.epresidential.quandoodstest.ui;

/**
 * Created by daniele on 13/07/16.
 */
public interface GoLFragmentManager {

    int changeState(int row, int column);

    int[][] changePattern(int pattern);

    int[][] generateNextStep(int[][] currentStep);

    boolean isPatternDead(int[][] currentStep);

    void activateGrid(boolean active);

}
