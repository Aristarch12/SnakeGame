package oop.snakegame.cells;

import oop.snakegame.GameException;
import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.Snake;
import oop.snakegame.primitives.Location;

import java.util.List;

public class TemporaryBonus extends ActiveBonus {
    private int startTime;
    private int timeToLive;
    private int cost;

    public TemporaryBonus(Location location, int timeToLive, int cost) {
        super(location, cost);
        startTime = timeToLive;
        this.timeToLive = timeToLive;
        this.cost = cost;
    }

    public int getTimeToLive() { return timeToLive; }

    @Override
    public void doAction(Level level) {
        timeToLive--;
        if (timeToLive == 0) {
            level.field.removeCell(this);
            regenerate(level);
            timeToLive = startTime;
        }
    }

    @Override
    public void interactWithSnake(Snake snake, Level level) throws GameException {
        super.interactWithSnake(snake, level);
    }

    @Override
    public void regenerate(Level level) {
        int time = level.random.nextInt(20) + 5;
        level.field.addCell(new TemporaryBonus(level.getFreeRandomLocation(), time, cost));
    }

    @Override
    public void accept(IVisitor visitor) { visitor.visit(this); }
}
