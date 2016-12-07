package oop.snakegame.cells;

import oop.snakegame.*;
import oop.snakegame.primitives.Direction;
import oop.snakegame.primitives.Location;

public class Teleport extends Cell{
    protected Location exitPoint;
    public Teleport(Location location) {
        super(location);
    }
    public void setExitPoint(Location exitPoint) {
        this.exitPoint = exitPoint;
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        if (exitPoint == null)
            throw new NullPointerException("exit point is not installed");
        Snake snake = player.getSnake();
        Direction snakeDirection = snake.getNextHeadDirection();
        Location newLocation = exitPoint.addOffset(snakeDirection.getOffset());
        snake.setHeadLocation(newLocation);
    }

    @Override
    public void interactWithCell(Cell cell) {}

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
