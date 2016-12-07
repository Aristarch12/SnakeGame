package oop.snakegame;

import oop.snakegame.cells.*;
import oop.snakegame.primitives.Location;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Level implements Iterable<Cell> {

    public final Random random;
    public List<GameObject> gameObjects;

    Level(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        this.random = new Random();
    }



    public List<Cell> getCells(Location location) {
        return stream().filter(cell -> cell.location.equals(location)).collect(Collectors.toList());
    }


    Stream<Cell> stream(){
        Stream<Cell> resultStream = null;
        for (GameObject gameObject : gameObjects) {
            if (resultStream == null) {
                resultStream = gameObject.stream();
            }
            else {
                resultStream = Stream.concat(
                        resultStream,
                        gameObject.stream()
                );
            }
        }
        return resultStream;
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
        return gameObjects.stream().filter((obj) -> obj instanceof Snake).toArray(Snake[]::new);

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
