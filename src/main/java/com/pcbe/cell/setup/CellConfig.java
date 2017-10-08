package com.pcbe.cell.setup;

public class CellConfig {
    public static int FULL_TIME_SECONDS;
    public static int STARVE_TIME_SECONDS;

    public int getFullTimeSeconds() {
        return FULL_TIME_SECONDS;
    }

    public void setFullTimeSeconds(int seconds) {
        FULL_TIME_SECONDS = seconds;
    }

    public int getStarveTimeSeconds() {
        return STARVE_TIME_SECONDS;
    }

    public void setStarveTimeSeconds(int seconds) {
        STARVE_TIME_SECONDS = seconds;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        stringBuilder.append("fullTime=" + FULL_TIME_SECONDS + ", ");
        stringBuilder.append("starveTime=" + STARVE_TIME_SECONDS);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
