package oop.snakegame.cells;


import oop.snakegame.IVisitor;
import oop.snakegame.Level;
import oop.snakegame.primitives.Location;

public class TemporaryBonus extends ActiveBonus {
    private int startTime;
    private int timeToLive;

    public TemporaryBonus(Location location, int timeToLive, int cost) {
        super(location, cost);
        startTime = timeToLive;
        this.timeToLive = timeToLive;
    }

    public int getTimeToLive() { return timeToLive; }

    @Override
    public void UpdateGameState(Level level) {
        timeToLive--;
        if (timeToLive == 0) {
            level.getField().removeCell(this);
            regenerate(level);
            timeToLive = startTime;
        }
    }


    @Override
    public void regenerate(Level level) {
        int time = level.random.nextInt(20) + 5;
        level.getField().addCell(new TemporaryBonus(level.getFreeRandomLocation(), time, cost));
    }

    @Override
    public void accept(IVisitor visitor) { visitor.visit(this); }
}
