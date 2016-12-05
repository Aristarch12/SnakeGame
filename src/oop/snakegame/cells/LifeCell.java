package oop.snakegame.cells;


import oop.snakegame.GameException;
import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.Player;
import oop.snakegame.primitives.Location;

public class LifeCell extends Cell {
    public LifeCell(Location location) {
        super(location);
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        level.life.killCell(this);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
