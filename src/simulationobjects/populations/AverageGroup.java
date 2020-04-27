package simulationobjects.populations;

import simulationobjects.location.Location;
import simulationobjects.resources.ResourceType;

public class AverageGroup extends PopulationGroup {

    public AverageGroup(int size, Location location) {
        super(size, location);

        super.setNeed(ResourceType.FRUIT, 0.3f);
        super.setNeed(ResourceType.GRAIN, 1f);
        super.setNeed(ResourceType.MEAT, 0.1f);
    }
}
