/**
 * A resource is food consumed by cells. (Immutable)
 */
public /*abstract*/ class Resource {
    private ResourceType resourceType;

    public Resource(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public float getNutritionValue() {
        return resourceType.getNutritionValue().getValue();
    }

    public String getName() {
        return resourceType.getName();
    }
}
