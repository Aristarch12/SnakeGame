package oop.snakegame.cells;


import oop.snakegame.Game;
import oop.snakegame.IVisitor;
import oop.snakegame.Player;
import oop.snakegame.Snake;
import oop.snakegame.primitives.Direction;
import oop.snakegame.primitives.Location;

public class SnakeBlock extends SolidCell {
    public final int id;

    public SnakeBlock(Location location, int id) {
        super(location);
        this.id = id;
    }

    @Override
    public void interactWithCell(Cell cell) {
        if (cell instanceof SnakeBlock) {
            SnakeBlock snakeBlock = (SnakeBlock)cell;
            if (this.id == snakeBlock.id) {
                this.actions.add((Game game) -> {
                    Snake snake = game.getPlayerThroughSnakeBlock(snakeBlock).getSnake();
                    snake.destroy();
                });
            }
            else
            {
                this.actions.add((Game game)->{
                    Snake snake = game.getPlayerThroughSnakeBlock(this).getSnake();
                    if (snake.getHead() == this)
                        snake.destroy();
                });
                cell.actions.add((Game game)->{
                    Snake snake = game.getPlayerThroughSnakeBlock(snakeBlock).getSnake();
                    if (snake.getHead() == snakeBlock)
                        snake.destroy();
                });
            }
        }
        if (cell instanceof Bonus)
        {
            this.actions.add((Game game)->{
                Player player = game.getPlayerThroughSnakeBlock(this);
                player.addScore(((Bonus)cell).cost);
            });
        }
        if (cell instanceof SizeBonus)
        {
            this.actions.add((Game game)->{
                Player player = game.getPlayerThroughSnakeBlock(this);
                player.getSnake().extend(((SizeBonus)cell).sizeIncrement);
            });
        }
        if (cell instanceof Wall)
        {
            this.actions.add((Game game)->{
                Snake snake = game.getPlayerThroughSnakeBlock(this).getSnake();
                snake.destroy();
            });
        }
        if (cell instanceof Teleport)
        {
            this.actions.add((Game game)->{
                Snake snake = game.getPlayerThroughSnakeBlock(this).getSnake();
                Direction snakeDirection = snake.getNextHeadDirection();
                Location newLocation = ((Teleport)cell).exitPoint.addOffset(snakeDirection.getOffset());
                snake.setHeadLocation(newLocation);
            });
        }
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
