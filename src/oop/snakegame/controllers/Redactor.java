package oop.snakegame.controllers;


import oop.snakegame.Game;
import oop.snakegame.GameAction;
import oop.snakegame.cells.Cell;
import oop.snakegame.cells.LifeCell;

import java.util.ArrayList;
import java.util.List;

abstract class Redactor implements IGameController {
    Redactor() {
        gameActions = new ArrayList<>();
    }
    List<GameAction> gameActions;

    @Override
    synchronized public void controlGame(Game game) {
        gameActions.forEach(action -> action.action(game));
        gameActions.clear();
    }
}
