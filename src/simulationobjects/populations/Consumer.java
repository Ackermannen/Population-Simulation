package simulationobjects.populations;

import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.HashMap;

public interface Consumer {

    HashMap<ResourceTypes, Float> consume(HashMap<Resource, ResourceGroup> resources);
}
