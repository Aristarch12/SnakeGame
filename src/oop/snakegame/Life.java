package oop.snakegame;

import com.sun.org.apache.xpath.internal.operations.Equals;
import oop.snakegame.cells.LifeCell;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Life implements Iterable<LifeCell>  {

    private List<LifeCell> aliveCells;

    public Life() {
        aliveCells = new ArrayList<>();
    }

    public void reviveCell(Location location) {
        if (aliveCells.stream().allMatch(cell -> cell.location != location)) {
            LifeCell lifeCell = new LifeCell(location);
            aliveCells.add(lifeCell);
        }
    }


    public void addCell(LifeCell lifeCell) {
        aliveCells.add(lifeCell);
    }

    public void update() {
        List<LifeCell> newAliveCells = new ArrayList<>();
        for (Location location : GetPotentiallyAliveCells()) {
            if (willSurvive(location)) {
                newAliveCells.add(new LifeCell(location));
            }
        }
        aliveCells = newAliveCells;
    }

    private HashSet<Location> GetPotentiallyAliveCells() {
        HashSet<Location> result = new HashSet<>();
        for (LifeCell lifeCell : aliveCells) {
            for (Location location : GetNeighbors(lifeCell.location))
                result.add(location);
        }
        return result;
    }

    private List<Location> GetNeighbors(Location location) {
        ArrayList<Location> neighbors = new ArrayList<>();
        for (int x = location.x - 1; x <= location.x + 1; x++)
            for (int y = location.y - 1; y <= location.y + 1; y++) {
                if (x == location.x && y == location.y)
                    continue;
                neighbors.add(new Location(x, y));
            }
        return neighbors;
    }

    private boolean willSurvive(Location location) {
        int neighborsCount = 0;
        for (Location neighbor : GetNeighbors(location)) {
            if (aliveCells.stream().anyMatch(cell -> cell.location.equals(neighbor)))
                neighborsCount++;
        }
        return neighborsCount == 3 ||
                (neighborsCount == 2 && aliveCells.stream().anyMatch(cell -> cell.location.equals(location)));
    }

    @Override
    public Iterator<LifeCell> iterator() {
        return aliveCells.iterator();
    }

    Stream<LifeCell> stream(){
        return aliveCells.stream();
    }

    public void killCell(LifeCell lifeCell) {
        aliveCells.remove(lifeCell);
    }
}
