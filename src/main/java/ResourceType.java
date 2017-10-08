public class ResourceType {
    private String name;
    private NutritionValue nutritionValue;

    public NutritionValue getNutritionValue() {
        return nutritionValue;
    }

    public void setNutritionValue(NutritionValue nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
