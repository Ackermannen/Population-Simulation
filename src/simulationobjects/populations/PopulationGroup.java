package simulationobjects.populations;

import simulationobjects.Group;
import simulationobjects.location.Location;
import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.HashMap;
import java.util.Map;

public abstract class PopulationGroup implements Group {

    private float size;
    private Location location;
    private HashMap<ResourceTypes, Float> needs;
    private HashMap<ResourceTypes, Float> productionNeeds;
    private HashMap<ResourceTypes, Float> productionCapability;


    public PopulationGroup(float size, Location location) {
        this.size = size;
        this.location = location;
        needs = new HashMap<>();
        productionNeeds = new HashMap<>();
        productionCapability = new HashMap<>();
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

    protected void setNeed(ResourceTypes type, float cycleQuantity) {
        if(cycleQuantity < 0) cycleQuantity = 0;
        needs.put(type, cycleQuantity);
    }

    protected void setProductionNeeds(ResourceTypes type, float cycleQuantity) {
        if(cycleQuantity < 0) cycleQuantity = 0;
        productionNeeds.put(type, cycleQuantity);
    }

    protected void setProductionNeeds(HashMap<ResourceTypes, Float> needs) {
        for (Map.Entry<ResourceTypes, Float> need : needs.entrySet()) {
            float cycleQuantity = need.getValue();
            ResourceTypes type = need.getKey();

            if(cycleQuantity < 0) cycleQuantity = 0;
            productionNeeds.put(type, cycleQuantity);
        }
    }

    protected void setProductionCapability(ResourceTypes type, float cycleQuantity) {
        if(cycleQuantity < 0) cycleQuantity = 0;
        productionCapability.put(type, cycleQuantity);
    }

    public HashMap<ResourceTypes, Float> getNeeds() {
        return needs;
    }

    public HashMap<ResourceTypes, Float> getProductionNeeds() {
        return productionNeeds;
    }

    public HashMap<ResourceTypes, Float> getProductionCapability() {
        return productionCapability;
    }

    /**
     * Makes the PopulationGroup try to consume resources
     * @param resources Map of available resources
     * @return Map for each resource and the fraction the pop was able to consume
     */
    protected HashMap<ResourceTypes, Float> consume(HashMap<Resource, ResourceGroup> resources, HashMap<ResourceTypes, Float> needs) {

        HashMap<ResourceTypes, Float> results = new HashMap<>();

        //For every need the group has
        for (Map.Entry<ResourceTypes, Float> entry : needs.entrySet()) {
            //Get ResourceGroup of specific type on PopulationGroup's location.
            Resource currentResource = new Resource(entry.getKey(), location);
            ResourceGroup group = resources.get(currentResource);

            results.put(entry.getKey(), 0f);

            if(group != null) {
                float need = entry.getValue() * size;
                float supply = group.getQuantity();
                float surplus = supply - need;

                group.add(-need);

                if (surplus > 0) {
                    results.put(currentResource.getType(), 1f);
                } else {
                    results.put(currentResource.getType(), supply / need);
                }
            }
        }
        return results;
    }

    /**
     * Makes the PopulationGroup produce resources
     * @param resources Map of available resources
     * @return Map of how much of each resource the PopulationGroup was able to produce.
     */
    protected HashMap<ResourceTypes, Float> produce(HashMap<Resource, ResourceGroup> resources, HashMap<ResourceTypes, Float> production) {

        HashMap<ResourceTypes, Float> results = new HashMap<>();

        //For every need the group has
        for (Map.Entry<ResourceTypes, Float> entry : production.entrySet()) {
            //Get ResourceGroup of specific type on PopulationGroup's location.
            Resource currentResource = new Resource(entry.getKey(), location);
            ResourceGroup group = resources.get(currentResource);

            results.put(entry.getKey(), 0f);

            if(group != null) {
                float productionAmount = entry.getValue() * size;
                group.add(productionAmount);
                results.put(currentResource.getType(), productionAmount);
            }
        }
        return results;
    }

    public HashMap<ResourceTypes, Float> getProductionDemands(ResourceTypes type) {

        HashMap<ResourceTypes, Float> results = new HashMap<>();

        switch(type) {
            case CLOTHING:
                results.put(ResourceTypes.WOOL, 1f);
                break;
            case LUXURY_CLOTHING:
                results.put(ResourceTypes.CLOTHING, 1f);
                results.put(ResourceTypes.SILK, 1f);
                break;
            case FURNITURE:
                results.put(ResourceTypes.LUMBER, 1f);
                break;
            case LUXURY_FURNITURE:
                results.put(ResourceTypes.FURNITURE, 1f);
                results.put(ResourceTypes.MAHOGANY, 1f);
                break;
        }
        return results;
    }
}
