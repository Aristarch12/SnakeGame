package oop.snakegame.cells;

import oop.snakegame.*;
import oop.snakegame.primitives.Location;

public abstract class SolidCell extends Cell {
    SolidCell(Location location) {
        super(location);
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        player.getSnake().destroy();
    }

    @Override
    public void interactWithCell(Cell cell) {
        if (cell instanceof SnakeBlock)
        {
            cell.actions.add((Game game)->{
                Snake snake = game.getPlayerThroughSnakeBlock((SnakeBlock)cell).getSnake();
                snake.destroy();
            });
        }
    }
}
