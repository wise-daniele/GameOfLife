package com.epresidential.quandoodstest.utils;

/**
 * Created by daniele on 13/07/16.
 */
public class Constants {

    //Interval between 2 ticks
    public static final int SLEEP_INTERVAL = 1000; // 1 second

    //Patterns
    public final static int NO_PATTERN = 0;
    public final static int RANDOM_PATTERN = 1;
    public final static int PULSAR_PATTERN = 2;

    //Grid Positions
    public static final int TOP_RIGHT = 0;
    public static final int TOP_LEFT = 1;
    public static final int BOTTOM_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;
    public static final int TOP = 4;
    public static final int LEFT = 5;
    public static final int RIGHT = 6;
    public static final int BOTTOM = 7;
    public static final int CENTER = 8;

    //Next Step state
    public final static int STATE_ALIVE = 0;
    public final static int STATE_BORN = 1;
    public final static int STATE_DEAD = 2;
    public final static int STATE_LIFELESS = 3;
    public final static int STATE_UNDEFINED = 4;

}
