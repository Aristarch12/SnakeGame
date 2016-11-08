package oop.snakegame.cells;

import oop.snakegame.GameException;
import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.Snake;
import oop.snakegame.primitives.Direction;
import oop.snakegame.primitives.Location;

public class Teleport extends Cell{


    private Location exitPoint;
    public Teleport(Location location) {
        super(location);
    }
    public void setExitPoint(Location exitPoint) {
        this.exitPoint = exitPoint;
    }

    @Override
    public void interactWithSnake(Snake snake, Level level) throws GameException {
        if (exitPoint == null)
            throw new NullPointerException("exit point is not installed");

        Direction snakeDirection = snake.getNextHeadDirection();
        Location newLocation = exitPoint.addOffset(snakeDirection.getOffset());
        snake.setHeadLocation(newLocation);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
