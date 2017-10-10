package com.pcbe.cell.setup;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CellConfig {
    public static int FULL_TIME_SECONDS;
    public static int STARVE_TIME_SECONDS;

    public int getFullTimeSeconds() {
        return FULL_TIME_SECONDS;
    }

    @XmlElement(name = "fullTimeSeconds")
    public void setFullTimeSeconds(int seconds) {
        FULL_TIME_SECONDS = seconds;
    }

    public int getStarveTimeSeconds() {
        return STARVE_TIME_SECONDS;
    }

    @XmlElement(name = "starveTimeSeconds")
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