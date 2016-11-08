package oop.snakegame.cells;


import oop.snakegame.GameException;
import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.Snake;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;
import java.util.List;

public class MovingBonus extends ActiveBonus{
    private int cost;

    public MovingBonus(Location location, int cost) {
        super(location, cost);
        this.cost = cost;
    }

    @Override
    public void doAction(Level level) {
        level.field.removeCell(this);
        ArrayList<Location> freeLocations = level.getFreeNeighbors(location);
        freeLocations.add(location);
        Location newLocation = freeLocations.get(level.random.nextInt(freeLocations.size()));
        level.field.addCell(new MovingBonus(newLocation, cost));
    }

    @Override
    public void interactWithSnake(Snake snake, Level level) throws GameException {
        super.interactWithSnake(snake, level);
    }

    @Override
    public void regenerate(Level level) {
        level.field.addCell(new MovingBonus(level.getFreeRandomLocation(), cost));
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
