package simulationobjects.resources;

import simulationobjects.Group;
import simulationobjects.location.Location;

public abstract class ResourceGroup implements Group {

    private float quantity;
    private ResourceType resource;
    private Location location;

    ResourceGroup(float quantity, ResourceType resource, Location location) {
        this.quantity = quantity;
        this.resource = resource;
        this.location = location;

        if(this.quantity < 0) this.quantity = 0;
    }

    @Override
    public float add(float amount) {
        quantity = quantity + amount;
        if(quantity < 0) quantity = 0;
        return quantity;
    }

    @Override
    public float multiply(float amount) {
        quantity = quantity * amount;
        if(quantity < 0) quantity = 0;
        return quantity;
    }

    @Override
    public float setQuantity(float amount) {
        quantity = amount;
        if(quantity < 0) quantity = 0;
        return quantity;
    }

    @Override
    public float getQuantity() {
        return quantity;
    }

    public ResourceType getResource() {
        return resource;
    }

    public Location getLocation() {
        return location;
    }
}
