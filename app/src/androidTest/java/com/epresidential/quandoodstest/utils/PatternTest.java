package com.epresidential.quandoodstest.utils;

import android.test.AndroidTestCase;

/**
 * Created by daniele on 14/07/16.
 */
public class PatternTest extends AndroidTestCase {

    public static final String LOG_TAG = PatternTest.class.getSimpleName();

    /*
     * (10,10)-(11,11) block
     * If next step is null then the pattern is constant.
     * This has been done in the actual code in order to fasten the AsynkTask termination
     */
    public void testSquareConstant(){
        int[][] blockPattern = PatternUtils.blockConstantPattern();
        assertNotNull(blockPattern);
        int[][] nextStep = PatternUtils.nextStep(blockPattern);
        assertNull(nextStep);
    }

    public void testBeacon(){
        int[][] beaconPattern = PatternUtils.beaconPattern();
        assertNotNull(beaconPattern);
        int[][] firstStep = PatternUtils.nextStep(beaconPattern);
        assertNotNull(firstStep);
        int[][] secondStep = PatternUtils.nextStep(firstStep);
        assertNotNull(secondStep);
        assertEquals(beaconPattern[9][9], firstStep[9][9]);
        assertEquals(beaconPattern[9][10], firstStep[9][10]);
        assertEquals(beaconPattern[10][9], firstStep[10][9]);
        assertFalse(beaconPattern[10][10] == firstStep[10][10]);
        assertFalse(beaconPattern[11][11] == firstStep[11][11]);
        assertEquals(beaconPattern[11][12], firstStep[11][12]);
        assertEquals(beaconPattern[12][11], firstStep[12][11]);
        assertEquals(beaconPattern[12][12], firstStep[12][12]);

        assertEquals(beaconPattern[9][9], secondStep[9][9]);
        assertEquals(beaconPattern[9][10], secondStep[9][10]);
        assertEquals(beaconPattern[10][9], secondStep[10][9]);
        assertEquals(beaconPattern[10][10], secondStep[10][10]);
        assertEquals(beaconPattern[11][11], secondStep[11][11]);
        assertEquals(beaconPattern[11][12], secondStep[11][12]);
        assertEquals(beaconPattern[12][11], secondStep[12][11]);
        assertEquals(beaconPattern[12][12], secondStep[12][12]);
        //todo: assert that boundary is always equals, both in the first and the second step
        // (not necessary for the moment)
    }
}
