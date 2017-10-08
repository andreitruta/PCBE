package com.pcbe.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestXMLLoader {
    private String testString;

    public String getTestString() {
        return testString;
    }

    @XmlElement
    public void setTestString(String testString) {
        this.testString = testString;
    }
}
