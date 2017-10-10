package com.pcbe.resources;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class NutritionValue {
    private float value;

    public float getValue() {
        return value;
    }

    @XmlValue
    public void setValue(float value) {
        this.value = value;
    }
}
