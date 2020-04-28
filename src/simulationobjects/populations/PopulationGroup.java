package simulationobjects.populations;

import simulationobjects.Group;
import simulationobjects.location.Location;
import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.ResourceType;

import java.util.HashMap;
import java.util.Map;

public abstract class PopulationGroup implements Group {

    private float size;
    private Location location;
    private HashMap<ResourceType, Float> needs;

    public PopulationGroup(float size, Location location) {
        this.size = size;
        this.location = location;
        needs = new HashMap<>();
    }

    @Override
    public float add(float amount) {
        size = size + (int) amount;
        if(size < 0) size = 0;
        return size;
    }

    @Override
    public float multiply(float amount) {
        size = size * (int) amount;
        if(size < 0) size = 0;
        return size;
    }

    @Override
    public float setQuantity(float amount) {
        size = (int) amount;
        if(size < 0) size = 0;
        return size;
    }

    @Override
    public float getQuantity() {
        return size;
    }

    protected void setNeed(ResourceType type, float cycleQuantity) {
        if(cycleQuantity < 0) cycleQuantity = 0;
        needs.put(type, cycleQuantity);
    }

    public HashMap<ResourceType, Float> getNeeds() {
        return needs;
    }

    /**
     * Makes the PopulationGroup try to consume resources
     * @param resources Map of available resources
     * @return Map for each resource and the fraction the pop was able to consume
     */
    public HashMap<ResourceType, Float> consume(HashMap<Resource, ResourceGroup> resources) {

        HashMap<ResourceType, Float> results = new HashMap<>();

        //For every need the group has
        for (Map.Entry<ResourceType, Float> entry : needs.entrySet()) {
            //Get ResourceGroup of specific type on PopulationGroup's location.
            ResourceGroup currentSource = resources.get(new Resource(entry.getKey(), location));
            results.put(entry.getKey(), 0f);

            if(currentSource != null) {
                float need = entry.getValue() * size;
                float supply = currentSource.getQuantity();
                float surplus = supply - need;

                currentSource.add(-need);

                if (surplus > 0) {
                    results.put(currentSource.getResource(), 1f);
                } else {
                    results.put(currentSource.getResource(), supply / need);
                }
            }
        }
        return results;
    }
}
