package simulationobjects.resources;

import simulationobjects.location.Location;

import java.util.Objects;

public class Resource {

    private ResourceType type;
    private Location location;

    public Resource(ResourceType type, Location location) {
        this.type = type;
        this.location = location;
    }

    public ResourceType getType() {
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
