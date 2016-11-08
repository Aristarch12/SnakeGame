package oop.snakegame.cells;


import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.primitives.Location;

public class MovingBonus extends ActiveBonus{
    MovingBonus(Location location) {
        super(location);
    }

    @Override
    void doAction(Level level) {

    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
