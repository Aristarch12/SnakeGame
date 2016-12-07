package oop.snakegame;

import oop.snakegame.cells.Cell;
import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.controllers.IGameController;

import java.util.ArrayList;
import java.util.Arrays;
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
            applyCellActions();
            removeDeadSnakes();
        }
        if (Arrays.stream(players).allMatch(player -> player.getState() == PlayerState.Dead))
            state = GameState.Finished;
    }

    private void removeDeadSnakes() {
        for (GameObject gameObject : new ArrayList<>(getLevel().gameObjects)) {
            if (gameObject instanceof Snake) {
                if (((Snake) gameObject).isDead())
                    getLevel().gameObjects.remove(gameObject);
            }
        }
    }

    private void handleObjectsCollision() {
        for (Cell cell : level) {
            for (Cell otherCell : level.getCells(cell.location).stream().filter((c) -> !cell.equals(c)).collect(Collectors.toList())) {
                cell.interactWithCell(otherCell);
            }
        }
    }

    private void applyCellActions() {
        for (Cell cell : getLevel()) {
            cell.actions.forEach(action -> action.action(this));
            cell.actions.clear();
        }
    }

    public GameState getState(){
        return state;
    }
    public void setState(GameState gameState) { state = gameState;}

    void loadLevel(Level level) throws GameException {
        state = GameState.Active;
        this.level = level;
        if (level.getSnakes().length > players.length)
            throw new GameException("not all snakes have players");
        for (int i = 0; i < level.getSnakes().length; i++){
            players[i].setSnake(level.getSnakes()[i]);
        }
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
