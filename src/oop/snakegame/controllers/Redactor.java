package oop.snakegame.controllers;


import oop.snakegame.Game;
import oop.snakegame.cells.Cell;
import oop.snakegame.cells.Wall;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;
import java.util.List;

abstract class Redactor implements IGameController {
    List<Cell> potentialWalls;
    Redactor() {
        potentialWalls = new ArrayList<>();
    }

    @Override
    public void controlGame(Game game) {
        for (Cell cell : potentialWalls) {

            if (game.getLevel().getFreeLocations().contains(cell.location)) {
                game.getLevel().field.addCell(cell);
            }
        }
        potentialWalls.clear();
    }
}
