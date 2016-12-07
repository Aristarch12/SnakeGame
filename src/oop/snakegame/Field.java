package oop.snakegame;

import oop.snakegame.cells.ActiveBonus;
import oop.snakegame.cells.Cell;
import oop.snakegame.primitives.Location;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Stream;

public class Field extends GameObject {

    private List<Cell> cells;
    final int width;
    final int height;
    public Random random;

    Field(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new ArrayList<>();
        random = new Random();
    }

    public Iterator<Cell> iterator() {
        return cells.iterator();
    }

    Stream<Cell> stream(){
        return cells.stream();
    }

    public void addCell(Cell cell) {
        if (isCorrectLocation(cell.location))
            cells.add(cell);
        else
            throw new InvalidParameterException("location is not on the field");
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }

    boolean isCorrectLocation(Location location) {
        return (location.x >= 0 && location.x < width &&
                location.y >= 0 && location.y < height);
    }

    public List<Location> getFreeLocations() {
        HashSet<Location> result = new HashSet<>();
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                result.add(new Location(x, y));
        for (Cell cell : this) {
            result.remove(cell.location);
        }
        return new ArrayList<>(result);
    }

    public ArrayList<Location> getFreeNeighbors(Location location) {
        ArrayList<Location> result = new ArrayList<>();
        List<Location> freeLocations = getFreeLocations();
        for (Location freeLocation : freeLocations)
            if (Math.abs(freeLocation.x - location.x) + Math.abs(freeLocation.y - location.y) == 1)
                result.add(new Location(freeLocation.x, freeLocation.y));
        return result;
    }

    public Location getFreeRandomLocation()
    {
        List<Location> freeLocations = getFreeLocations();
        int index = random.nextInt(freeLocations.size());
        return freeLocations.get(index);
    }


    @Override
    void update() {
        for (Cell cell : new ArrayList<>(cells)) {
            if (cell instanceof ActiveBonus) {
                ((ActiveBonus) cell).UpdateGameState(this);
            }
        }
    }
}
