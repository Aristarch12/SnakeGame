package oop.snakegame.controllers;


import oop.snakegame.Game;
import oop.snakegame.cells.Cell;
import oop.snakegame.cells.LifeCell;

import java.util.ArrayList;
import java.util.List;

abstract class Redactor implements IGameController {
    List<LifeCell> potentialCells;
    Redactor() {
        potentialCells = new ArrayList<>();
    }

    @Override
    synchronized public void controlGame(Game game) {
        for (LifeCell lifeCell : potentialCells) {

            if (game.getLevel().getFreeLocations().contains(lifeCell.location)) {
                game.getLevel().life.addCell(lifeCell);
            }
        }
        potentialCells.clear();
    }
}
