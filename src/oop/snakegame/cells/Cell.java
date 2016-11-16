package oop.snakegame.cells;

import oop.snakegame.*;
import oop.snakegame.primitives.Location;

public abstract class Cell {
    public final Location location;

    Cell(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return location != null ? location.equals(cell.location) : cell.location == null;

    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }

    public abstract void interactWithPlayer(Player player, Level level) throws GameException;

    public abstract void accept(IVisitor visitor);
}
