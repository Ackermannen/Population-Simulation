package simulationobjects.resources;

import simulationobjects.location.Location;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.Objects;

/**
 * Identifier for a resource group
 */
public class Resource {

    private ResourceTypes type;
    private Location location;

    public Resource(ResourceTypes type, Location location) {
        this.type = type;
        this.location = location;
    }

    public ResourceTypes getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return type == resource.type &&
                Objects.equals(location, resource.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, location);
    }
}
