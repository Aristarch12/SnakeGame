package oop.snakegame.cells;


import oop.snakegame.GameException;
import oop.snakegame.Level;
import oop.snakegame.primitives.Location;
import oop.snakegame.Snake;

import java.util.List;

public abstract class Bonus extends Cell {

    private int cost;

    Bonus(Location location, int cost) {
        super(location);
        this.cost = cost;
    }

    @Override
    public void interactWithSnake(Snake snake, Level level) throws GameException {
        level.field.removeCell(this);
        snake.extend(cost);
        regenerate(level);
    }

    abstract void regenerate(Level level);
}
