package simulationobjects.populations;

import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.HashMap;

public interface ConsumerProducer {

    HashMap<ResourceTypes, Float> consume(HashMap<Resource, ResourceGroup> resources);

    HashMap<ResourceTypes, Float> produce(HashMap<Resource, ResourceGroup> resources);
}
