package oop.snakegame.cells;

import oop.snakegame.*;
import oop.snakegame.primitives.Location;


public class SizeBonus extends Bonus {

    private int sizeIncrement;

    public SizeBonus(Location location, int sizeIncrement) {
        super(location, sizeIncrement);
        this.sizeIncrement = sizeIncrement;
    }

    @Override
    public void interactWithPlayer(Player player, Level level) throws GameException {
        super.interactWithPlayer(player, level);
        player.getSnake().extend(sizeIncrement);
    }

    @Override
    public void regenerate(Level level) {
        int increment = level.random.nextInt(4) + 1;
        level.field.addCell(new SizeBonus(level.getFreeRandomLocation(), increment));
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
