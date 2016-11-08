package oop.snakegame;

import oop.snakegame.cells.*;
import oop.snakegame.primitives.Location;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Level implements Iterable<Cell> {

    public final Field field;
    final Snake[] snakes;

    public final Random random;

    Level(Field field, Snake[] snakes) {
        this.field = field;
        this.snakes = snakes;
        this.random = new Random();
    }

    void handleTick() throws GameException {
        for (Snake snake : Arrays.stream(snakes).filter(s -> !s.isDead()).collect(Collectors.toList())) {
            snake.move();
            if (!field.isCorrectLocation(snake.getHead().location)) {
                snake.destroy();
            }
        }
        for (Cell cell : this)
        {
            if (cell instanceof TemporaryBonus)
            {
                TemporaryBonus temporaryBonus = (TemporaryBonus)cell;
                temporaryBonus.doAction(this);
            }
            if (cell instanceof MovingBonus)
            {
                MovingBonus movingBonus = (MovingBonus)cell;
                movingBonus.doAction(this);
            }
        }
        for (Snake snake: snakes) {

            label : while (true) {
                SnakeBlock head = snake.getHead();
                for (Cell cell : getCells(snake.getHead().location)) {
                    if (cell != snake.getHead()) {
                        cell.interactWithSnake(snake, this);
                        if (snake.getHead() != head) {
                            continue label;
                        }
                    }
                }
                break;

            }

        }

    }

    private List<Cell> getCells(Location location) {
        return stream().filter(cell -> cell.location.equals(location)).collect(Collectors.toList());
    }


    public List<Location> getFreeLocations() {
        HashSet<Location> result = new HashSet<>();
        for (int x = 0; x < field.width; x++)
            for (int y = 0; y < field.height; y++)
                result.add(new Location(x, y));
        for (Cell cell : this) {
            result.remove(cell.location);
        }
        return new ArrayList<>(result);
    }

    public ArrayList<Location> getFreeNeighbors(Location location) {
        ArrayList<Location> result = new ArrayList<>();
        List<Location> freeLocations = getFreeLocations();
        for (Location freeLocation : freeLocations)
            if (Math.abs(freeLocation.x - location.x) + Math.abs(freeLocation.y - location.y) == 1)
                result.add(new Location(freeLocation.x, freeLocation.y));
        return result;
    }

    public Location getFreeRandomLocation()
    {
        List<Location> freeLocations = getFreeLocations();
        int index = random.nextInt(freeLocations.size());
        return freeLocations.get(index);
    }

    Stream<Cell> stream(){
        return Stream.concat(
                Stream.of(field.stream()),
                Arrays.stream(snakes).filter(snake -> !snake.isDead()).map(Snake::stream)
        ).reduce(Stream::concat).orElseGet(Stream::empty).map(cell -> (Cell)cell);
    }

    @Override
    public Iterator<Cell> iterator() {
        return stream().collect(Collectors.toList()).iterator();
    }
}
