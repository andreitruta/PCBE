package com.pcbe.resources;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "resource")
public class ResourceType {
    private String name;
    private NutritionValue nutritionValue;

    public NutritionValue getNutritionValue() {
        return nutritionValue;
    }

    @XmlElement
    public void setNutritionValue(NutritionValue nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
