package oop.snakegame.cells;

import oop.snakegame.GameException;
import oop.snakegame.Level;
import oop.snakegame.Player;
import oop.snakegame.primitives.Location;

public abstract class SolidCell extends Cell {
    SolidCell(Location location) {
        super(location);
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        player.getSnake().destroy();
    }
}
