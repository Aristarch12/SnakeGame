package oop.snakegame.controllers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import oop.snakegame.Player;
import oop.snakegame.PlayerAction;

import java.util.HashMap;

public class KeyboardPlayerController extends PlayerController implements EventHandler<KeyEvent> {

    private HashMap<KeyCode, PlayerAction> keyMap = new HashMap<>();

    public KeyboardPlayerController(Player player) {
        super(player);
    }

    public void setKeyMap(HashMap<KeyCode, PlayerAction> keyMap){
        this.keyMap = keyMap;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode key = event.getCode();
        if (keyMap.containsKey(key))
            actions.add(keyMap.get(key));
    }
}
