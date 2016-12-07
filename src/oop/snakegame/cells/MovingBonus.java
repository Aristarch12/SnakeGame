package oop.snakegame.cells;


import oop.snakegame.*;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;

public class MovingBonus extends ActiveBonus{
    public MovingBonus(Location location, int cost) {
        super(location, cost);
    }

    @Override
    public void UpdateGameState(Field field) {
        field.removeCell(this);
        ArrayList<Location> freeLocations = field.getFreeNeighbors(location);
        freeLocations.add(location);
        Location newLocation = freeLocations.get(field.random.nextInt(freeLocations.size()));
        field.addCell(new MovingBonus(newLocation, cost));
    }


    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        super.interactWithPlayer(player, level);
    }

    @Override
    public void regenerate(Field field) {
        field.addCell(new MovingBonus(field.getFreeRandomLocation(), cost));
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
