package oop.snakegame.primitives;

public class Location {
    public final int x;
    public final int y;
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Location addOffset(Offset offset) {
        return new Location(x + offset.x, y + offset.y);
    }

    public Offset getOffset(Location newLocation) {
        return new Offset(newLocation.x - x, newLocation.y - y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return x == location.x && y == location.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString(){
        return String.format("Location(%1$d, %2$d)", x, y);
    }
}
