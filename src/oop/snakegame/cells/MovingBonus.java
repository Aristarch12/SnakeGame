package oop.snakegame.cells;


import oop.snakegame.*;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;

public class MovingBonus extends ActiveBonus{
    public MovingBonus(Location location, int cost) {
        super(location, cost);
    }

    @Override
    public void UpdateGameState(Level level) {
        level.getField().removeCell(this);
        ArrayList<Location> freeLocations = level.getFreeNeighbors(location);
        freeLocations.add(location);
        Location newLocation = freeLocations.get(level.random.nextInt(freeLocations.size()));
        level.getField().addCell(new MovingBonus(newLocation, cost));
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        super.interactWithPlayer(player, level);
    }

    @Override
    public void regenerate(Level level) {
        level.getField().addCell(new MovingBonus(level.getFreeRandomLocation(), cost));
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
