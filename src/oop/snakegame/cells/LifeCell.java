package oop.snakegame.cells;


import oop.snakegame.*;
import oop.snakegame.primitives.Location;

import java.util.function.Function;

public class LifeCell extends Cell {
    public LifeCell(Location location) {
        super(location);
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        level.getLife().killCell(this);
    }

    @Override
    public void interactWithCell(Cell cell) {
        if (cell instanceof SnakeBlock)
        {
            this.actions.add((Game game) -> {
                game.getLevel().getLife().killCell(this);
            });
        }
    }


    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
