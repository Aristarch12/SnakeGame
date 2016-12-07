package oop.snakegame.cells;

import oop.snakegame.*;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Cell {
    public final Location location;
    public ArrayList<Consumer<Game>> actions;

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

    public abstract void interactWithCell(Cell cell);

    public abstract void accept(IVisitor visitor);
}
