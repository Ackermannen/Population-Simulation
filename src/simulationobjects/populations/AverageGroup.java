package simulationobjects.populations;

import simulationobjects.location.Location;
import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.HashMap;
import java.util.Map;

public class AverageGroup extends PopulationGroup implements Consumer, Producer {

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

        HashMap<ResourceTypes, Float> adjustedCapability = new HashMap<>();



        //For every production this pop does
        for (Map.Entry<ResourceTypes, Float> capability: super.getProductionCapability().entrySet()) {
            //Get demands for this type of resource
            HashMap<ResourceTypes, Float> productionDemands = super.getProductionDemands(capability.getKey());

            //Go through all demands, look at their satisfaction, take lowest satisfaction
            float lowestSatisfaction = 1.0f;

            for (Map.Entry<ResourceTypes, Float> demand: productionDemands.entrySet()) {
                float satisfaction = resourceSatisfaction.get(demand.getKey());
                if(satisfaction < lowestSatisfaction) {
                    lowestSatisfaction = satisfaction;
                }
            }

            adjustedCapability.put(capability.getKey(), lowestSatisfaction*capability.getValue());


        }


        return super.produce(resources, adjustedCapability);
    }


}
