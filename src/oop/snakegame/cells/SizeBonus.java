package oop.snakegame.cells;

import oop.snakegame.*;
import oop.snakegame.primitives.Location;

import java.util.List;

public class SizeBonus extends Bonus {

    private int sizeIncrement;

    public SizeBonus(Location location, int sizeIncrement) {
        super(location, sizeIncrement);
        this.sizeIncrement = sizeIncrement;
    }

    @Override
    public void interactWithSnake(Snake snake, Level level) throws GameException {
        super.interactWithSnake(snake, level);
    }

    @Override
    public void regenerate(Level level) {
        int increment = level.random.nextInt(4) + 1;
        level.field.addCell(new SizeBonus(level.getFreeRandomLocation(), increment));
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
