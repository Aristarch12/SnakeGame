package oop.snakegame;

import oop.snakegame.playercontrollers.PlayerController;

import java.util.Arrays;

enum GameState{
    Active, Finished
}

class Game {

    private Level level;
    private GameState state = GameState.Active;
    final Player[] players;
    private PlayerController[] controllers;

    Game(int playersCount){
        players = new Player[playersCount];
        for(int i = 0; i < playersCount; i++)
            players[i] = new Player();
    }

    void tick() {
        for (PlayerController controller: controllers){
            controller.controlPlayer();
        }
        try {
            level.handleTick();
        } catch (GameException e){
            e.printStackTrace();
        }
        if (Arrays.stream(players).allMatch(player -> player.getState() == PlayerState.Dead))
            state = GameState.Finished;
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

    Level getLevel(){
        return level;
    }

    void setControllers(PlayerController[] controllers) {
        this.controllers = controllers;
    }
}
