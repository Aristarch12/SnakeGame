package oop.snakegame;

import oop.snakegame.cells.Cell;
import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.controllers.IGameController;
import oop.snakegame.primitives.Location;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private Level level;
    private GameState state = GameState.Active;
    final Player[] players;
    private IGameController[] controllers;

    Game(int playersCount){
        players = new Player[playersCount];
        for(int i = 0; i < playersCount; i++)
            players[i] = new Player();
    }

    void tick() {
        for (IGameController controller: controllers){
            controller.controlGame(this);
        }
        if (state != GameState.Pause) {
            for (GameObject gameObject : level.gameObjects) {
                gameObject.update();
            }
            handleObjectsCollision();

//            level.updateStateActiveBonuses();
//            level.life.update();
//            try {
//                for (Player player : players) {
//                    UpdatePlayerState(player);
//                }
//            } catch (GameException e){
//                e.printStackTrace();
//            }
        }
        if (Arrays.stream(players).allMatch(player -> player.getState() == PlayerState.Dead))
            state = GameState.Finished;
    }

    private void handleObjectsCollision() {
        for (Cell cell : level) {
            for (Cell cell1 : level.getCells(cell.location).stream().filter((c) -> !cell.equals(c)).collect(Collectors.toList())) {
                cell.interactWithCell(cell1);
            }
        }
    }


    private void UpdatePlayerState(Player player) throws GameException {
        Snake snake = player.getSnake();
        if (snake.isDead())
            return;
        snake.move();
        if (!level.getField().isCorrectLocation(snake.getHead().location)) {
            snake.destroy();
        } else  {
            handleInteractionWithObjects(player);
        }
    }

    private void handleInteractionWithObjects(Player player) throws GameException {
        Snake snake = player.getSnake();
        label : while (true) {
            SnakeBlock head = snake.getHead();
            for (Cell cell : level.getCells(snake.getHead().location)) {
                if (cell != snake.getHead() && cell != head) {
                    cell.interactWithPlayer(player, getLevel());
                }
                if (snake.getHead() != head) {
                    continue label;
                }
            }
            return;
        }
    }

    public GameState getState(){
        return state;
    }
    public void setState(GameState gameState) { state = gameState;}

    void loadLevel(Level level) throws GameException {
//        state = GameState.Active;
//        this.level = level;
//        if (level.snakes.length > players.length)
//            throw new GameException("not all snakes have players");
//        for (int i = 0; i < level.snakes.length; i++){
//            players[i].setSnake(level.snakes[i]);
//        }
        throw new NotImplementedException();
    }

    public Player getPlayerThroughSnakeBlock(SnakeBlock snakeBlock)
    {
        for(Player player : players)
        {
            if (player.getSnake().id == snakeBlock.id)
            {
                return player;
            }
        }
        return null;
    }

    public Level getLevel(){
        return level;
    }

    void setControllers(IGameController[] controllers) {
        this.controllers = controllers;
    }
}
