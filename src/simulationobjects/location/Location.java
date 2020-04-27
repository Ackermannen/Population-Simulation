package simulationobjects.location;

import java.util.Objects;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location getLocation() {
        return this;
    }

    public float getEuclidianDistanceTo(Location target) {
        double dist = Math.sqrt((this.x - target.x)^2 + (this.y - target.y)^2);
        return (float) (Math.round(dist * 100) * 0.01);
    }

    public float getManhattanDistanceTo(Location target) {
        float dist = Math.abs(this.x-target.x) + Math.abs(this.y-target.y);
        return (float) (Math.round(dist * 100) * 0.01);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
