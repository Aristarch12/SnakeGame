package oop.snakegame.controllers;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import oop.snakegame.GameState;
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
    synchronized public void handle(MouseEvent event) {
        int column = (int) (event.getX() / cellSize);
        int row = (int) (event.getY() / cellSize);
        Location location = new Location(column, row);
        //            if (game.getLevel().getFreeLocations().contains(lifeCell.location)) {
//                game.getLevel().life.addCell(lifeCell);
//            }
        if (event.getButton() == MouseButton.PRIMARY) {
            gameActions.add((game) -> {
                if (game.getLevel().getFreeLocations().contains(location))
                game.getLevel().life.addCell(new LifeCell(location));
            });
        }
        if (event.getButton() == MouseButton.SECONDARY) {
            gameActions.add((game) ->  {
                if (game.getState() == GameState.Pause) {
                    game.setState(GameState.Active);
                }
                else if (game.getState() == GameState.Active) {
                    game.setState(GameState.Pause);
                }
            });
        }
    }
}
