package oop.snakegame.cells;


import oop.snakegame.*;
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
        level.getField().removeCell(this);
        regenerate(level);
    }

    @Override
    public void interactWithCell(Cell cell) {
        if (cell instanceof SnakeBlock)
        {
            this.actions.add((Game game)->{game.getLevel().getField().removeCell(this);});
            this.actions.add((Game game)->{regenerate(game.getLevel());});
        }
    }

    abstract void regenerate(Level level);
}
