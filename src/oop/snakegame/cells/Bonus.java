package oop.snakegame.cells;


import oop.snakegame.GameException;
import oop.snakegame.Level;
import oop.snakegame.Player;
import oop.snakegame.primitives.Location;

public abstract class Bonus extends Cell {

    protected int cost;

    Bonus(Location location, int cost) {
        super(location);
        this.cost = cost;
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        player.addScore(cost);
        level.field.removeCell(this);
        regenerate(level);
    }

    abstract void regenerate(Level level);
}
