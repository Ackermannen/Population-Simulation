package simulationobjects.resources;

import simulationobjects.Group;

public class ResourceGroup implements Group {

    private float quantity;

    public ResourceGroup(float quantity) {
        this.quantity = quantity;

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
}
