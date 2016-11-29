package oop.snakegame.controllers;


import oop.snakegame.Game;
import oop.snakegame.cells.Wall;
import oop.snakegame.primitives.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class RedactorController implements IController{
    List<Location> potentialWalls;
    public RedactorController() {
        potentialWalls = new ArrayList<>();
    }

    @Override
    public void controlGame(Game game) {
        System.out.println(potentialWalls.size());
        for (Location location : potentialWalls) {

            if (game.getLevel().getFreeLocations().contains(location)) {
                game.getLevel().field.addCell(new Wall(location));

            }
        }

        potentialWalls.clear();
    }
}
