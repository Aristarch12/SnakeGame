package oop.snakegame.cells;


import oop.snakegame.Field;
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
    public void UpdateGameState(Field field) {
        timeToLive--;
        if (timeToLive == 0) {
            field.removeCell(this);
            regenerate(field);
            timeToLive = startTime;
        }
    }


    @Override
    public void regenerate(Field field) {
        int time = field.random.nextInt(20) + 5;
        field.addCell(new TemporaryBonus(field.getFreeRandomLocation(), time, cost));
    }

    @Override
    public void accept(IVisitor visitor) { visitor.visit(this); }
}
