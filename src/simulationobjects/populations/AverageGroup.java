package simulationobjects.populations;

import simulationobjects.location.Location;
import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.HashMap;
import java.util.Map;

public class AverageGroup extends PopulationGroup implements ConsumerProducer {

    public AverageGroup(int size, Location location, ResourceTypes productionType) {
        super(size, location);

        super.setNeed(ResourceTypes.FRUIT, 0.3f);
        super.setNeed(ResourceTypes.GRAIN, 1f);
        super.setNeed(ResourceTypes.MEAT, 0.1f);

        super.setProductionNeeds(super.getProductionDemands(productionType));

        super.setProductionCapability(productionType, 1f);

    }

    /**
     * Consumes resources
     * @param resources resources to consume from
     * @return Map containing a percentage of satisfied needs based on availability.
     */
    public HashMap<ResourceTypes, Float> consume(HashMap<Resource, ResourceGroup> resources) {
        return super.consume(resources, super.getNeeds());
    }

    /**
     * Produces resources based on production type of class instance and availability of resources for production.
     * @param resources Resources that the values are added to.
     * @return How much was produced for each resource.
     */
    public HashMap<ResourceTypes, Float> produce(HashMap<Resource, ResourceGroup> resources) {
        //Consume production resources
        HashMap<ResourceTypes, Float> resourceSatisfaction = super.consume(resources, super.getProductionNeeds());


        //Calculate Production capability by taking satisfaction (%) * Production Capability
        HashMap<ResourceTypes, Float> productionCapability = super.getProductionCapability();
        HashMap<ResourceTypes, Float> adjustedCapability = new HashMap<>();
        for (Map.Entry<ResourceTypes, Float> entry: resourceSatisfaction.entrySet()) {
            adjustedCapability.put(entry.getKey(), entry.getValue()*productionCapability.get(entry.getKey()));
        }


        return super.produce(resources, adjustedCapability);
    }


}
