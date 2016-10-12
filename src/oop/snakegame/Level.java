package oop.snakegame;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Level implements Iterable<Cell> {

    final Field field;
    final Snake[] snakes;

    final Random random;

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
        for (Snake snake: snakes)
            for (Cell cell : getCells(snake.getHead().location))
                if (cell != snake.getHead())
                    cell.interactWithSnake(snake, this);
    }

    private List<Cell> getCells(Location location) {
        return stream().filter(cell -> cell.location.equals(location)).collect(Collectors.toList());
    }

    List<Location> getFreeLocations() {
        HashSet<Location> result = new HashSet<>();
        for (int x = 0; x < field.width; x++)
            for (int y = 0; y < field.height; y++)
                result.add(new Location(x, y));
        for (Cell cell : this) {
            result.remove(cell.location);
        }
        return new ArrayList<>(result);
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
