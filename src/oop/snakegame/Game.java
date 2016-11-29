package oop.snakegame;

import oop.snakegame.cells.Cell;
import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.controllers.IController;

import java.util.Arrays;

enum GameState{
    Active, Finished
}

public class Game {

    private Level level;
    private GameState state = GameState.Active;
    final Player[] players;
    private IController[] controllers;

    Game(int playersCount){
        players = new Player[playersCount];
        for(int i = 0; i < playersCount; i++)
            players[i] = new Player();
    }

    void tick() {
        for (IController controller: controllers){
            controller.controlGame(this);
        }
        level.updateStateActiveBonuses();
        try {
            for (Player player : players) {
                UpdatePlayerState(player);
            }
        } catch (GameException e){
            e.printStackTrace();
        }
        if (Arrays.stream(players).allMatch(player -> player.getState() == PlayerState.Dead))
            state = GameState.Finished;
    }

    private void UpdatePlayerState(Player player) throws GameException {
        Snake snake = player.getSnake();
        if (snake.isDead())
            return;
        snake.move();
        if (!level.field.isCorrectLocation(snake.getHead().location)) {
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

    GameState getState(){
        return state;
    }

    void loadLevel(Level level) throws GameException {
        state = GameState.Active;
        this.level = level;
        if (level.snakes.length > players.length)
            throw new GameException("not all snakes have players");
        for (int i = 0; i < level.snakes.length; i++){
            players[i].setSnake(level.snakes[i]);
        }
    }

    public Level getLevel(){
        return level;
    }

    void setControllers(IController[] controllers) {
        this.controllers = controllers;
    }
}
