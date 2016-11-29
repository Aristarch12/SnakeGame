package oop.snakegame.controllers;

import oop.snakegame.Game;
import oop.snakegame.PlayerAction;
import oop.snakegame.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerController implements IController{
    private Player player;

    protected List<PlayerAction> actions = new ArrayList<>();

    PlayerController(Player player){
        this.player = player;
    }

    public void controlPlayer(){
        actions.forEach(action -> action.action(player));
        actions.clear();
    }

    @Override
    public void controlGame(Game game) {
        controlPlayer();
    }
}
