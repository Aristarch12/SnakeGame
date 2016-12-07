package oop.snakegame;

import oop.snakegame.cells.*;
import oop.snakegame.primitives.Location;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Level implements Iterable<Cell> {

    public List<GameObject> gameObjects;
    public final Random random;

    Level(Field field, Snake[] snakes, Life life) {
        gameObjects.add(field);
        gameObjects.add(life);
        for(Snake snake : snakes)
            gameObjects.add(snake);
        random = new Random();
    }

    void updateStateActiveBonuses() {
        for (Cell cell : this)
        {
            if (cell instanceof ActiveBonus) {
                ActiveBonus activeBonus = (ActiveBonus) cell;
                activeBonus.UpdateGameState(this);
            }
        }
    }


    public List<Cell> getCells(Location location) {
        return stream().filter(cell -> cell.location.equals(location)).collect(Collectors.toList());
    }


    public List<Location> getFreeLocations() {
        HashSet<Location> result = new HashSet<>();
        for (int x = 0; x < getField().width; x++)
            for (int y = 0; y < getField().height; y++)
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
//        return Stream.concat(
//                Stream.concat(Stream.of(life.stream()),Stream.of(field.stream())),
//                Arrays.stream(snakes).filter(snake -> !snake.isDead()).map(Snake::stream)
//        ).reduce(Stream::concat).orElseGet(Stream::empty).map(cell -> (Cell)cell);
        return null;
    }

    @Override
    public Iterator<Cell> iterator() {
        return stream().collect(Collectors.toList()).iterator();
    }

    public Field getField() {
        Optional<GameObject> result = gameObjects.stream().filter((obj) -> obj instanceof Field).findFirst();
        if (result.isPresent()){
            return (Field)result.get();
        }
        else
        {
            throw new RuntimeException("Field not found");
        }
    }

    public Snake[] getSnakes() {
        return (Snake[]) gameObjects.stream().filter((obj) -> obj instanceof Snake).toArray();

    }

    public Life getLife() {
        Optional<GameObject> r = gameObjects.stream().filter((obj) -> obj instanceof Life).findFirst();
        if (r.isPresent()) {
            return (Life) r.get();
        }
        else {
            throw new RuntimeException("life not found");
        }
    }
}
