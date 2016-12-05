package oop.snakegame.controllers;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import oop.snakegame.cells.LifeCell;
import oop.snakegame.cells.MovingBonus;
import oop.snakegame.cells.Wall;
import oop.snakegame.primitives.Location;

public class MouseRedactorController extends Redactor implements EventHandler<MouseEvent> {

    private final int cellSize;
    public MouseRedactorController(int cellSize) {
        super();
        this.cellSize = cellSize;
    }

    @Override
    public void handle(MouseEvent event) {
        int column = (int) (event.getX() / cellSize);
        int row = (int) (event.getY() / cellSize);
        Location location = new Location(column, row);
        if (event.getButton() == MouseButton.PRIMARY) {
            potentialCells.add(new LifeCell(location));
        }
    }
}
