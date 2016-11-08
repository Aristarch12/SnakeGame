package oop.snakegame.cells;
import oop.snakegame.Level;
import oop.snakegame.primitives.Location;

public abstract class ActiveBonus extends Bonus {
    ActiveBonus(Location location) {
        super(location);
    }

    abstract void doAction(Level level);
}
