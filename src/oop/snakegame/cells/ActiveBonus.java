package oop.snakegame.cells;
import oop.snakegame.Level;
import oop.snakegame.primitives.Location;

public abstract class ActiveBonus extends Bonus {
    ActiveBonus(Location location, int cost) {
        super(location, cost);
    }

    public abstract void UpdateGameState(Level level);
}
