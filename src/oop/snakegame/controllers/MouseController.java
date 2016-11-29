package oop.snakegame.controllers;


import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import oop.snakegame.primitives.Location;

public class MouseController extends RedactorController implements EventHandler<MouseEvent> {

    private final int cellSize;
    public MouseController(int cellSize) {
        super();
        this.cellSize = cellSize;
    }

    @Override
    public void handle(MouseEvent event) {
        int column = (int) (event.getX() / cellSize);
        int row = (int) (event.getY() / cellSize);
        System.out.println(column + " " + row);
        potentialWalls.add(new Location(column, row));
        int e =3;
    }
}
