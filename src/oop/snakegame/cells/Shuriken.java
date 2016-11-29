package oop.snakegame.cells;

import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.primitives.Location;

public class Shuriken extends ActiveBonus {
    Shuriken(Location location, int cost) {
        super(location, cost);
    }

    @Override
    public void UpdateGameState(Level level) {

    }

    @Override
    void regenerate(Level level) {

    }

    @Override
    public void accept(IVisitor visitor) {

    }
}
