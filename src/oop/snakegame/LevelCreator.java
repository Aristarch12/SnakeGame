package oop.snakegame;

import oop.snakegame.cells.*;
import oop.snakegame.primitives.Direction;
import oop.snakegame.primitives.Location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

class LevelCreator {

//    public static Level create(int width, int height, Random random){
//         Field field = ...
//         Snake snakes = ...
//         return Level(field, snakes, ...)
//    }

    static Level create(String filename) throws IOException, ParseException {
        return create(Files.readAllLines(Paths.get(filename)).toArray(new String[0]));
    }


    private static int nextId = 0;

    private static int getId() {
        nextId++;
        return nextId;
    }

    static HashSet<Character> symbolsSnake = new HashSet<Character>() {{
        add('U');
        add('D');
        add('R');
        add('L');
    }};

    private static boolean isSymbolTeleportation(char c) {
        return  ('a' <= c && c <= 'g');
    }

    static Level create(String[] cellMap) throws ParseException {
        Field field = new Field(cellMap[0].length(), cellMap.length);
        List<Snake> snakes = new ArrayList<>();
        Life life = new Life();
        HashMap<Character, List<Location>> locationTeleportes = new HashMap<Character, List<Location>>();
        for (int y = 0; y < field.height; y++) {

            if (cellMap[y].length() != field.width)
                throw new ParseException("lines have different length", y * field.width + 1);

            for (int x = 0; x < field.width; x++) {
                char currentCell = cellMap[y].charAt(x);
                Location location = new Location(x, y);
                if (symbolsSnake.contains(currentCell)) {
                    Snake snake = createSnake(location, currentCell);
                    if (snake != null)
                        snakes.add(snake);
                    continue;
                }
                Cell cell = createCell(location, currentCell);

                if (cell != null) {
                    if (cell instanceof LifeCell)
                        life.addCell((LifeCell) cell);
                    else
                        field.addCell(cell);
                }

                if (isSymbolTeleportation(currentCell)) {
                    if (locationTeleportes.containsKey(currentCell))
                        locationTeleportes.get(currentCell).add(location);
                    else
                        locationTeleportes.put(currentCell, new ArrayList<Location>() {{add(location);}});
                }
            }
        }
        for ( List<Location> locationList: locationTeleportes.values()) {
            if (locationList.size() != 2) {
                throw new IllegalArgumentException("wrong installation of teleports");
            }
            Teleport firstTeleport = new Teleport(locationList.get(0));
            Teleport secondTeleport = new Teleport(locationList.get(1));
            firstTeleport.setExitPoint(secondTeleport.location);
            secondTeleport.setExitPoint(firstTeleport.location);
            field.addCell(firstTeleport);
            field.addCell(secondTeleport);
        }
        if (snakes.isEmpty())
            throw new ParseException("no snakes on map", field.height * field.width);
        List<GameObject> gameObjects = new ArrayList<GameObject>() {
            {
                add(field);
                for (Snake snake : snakes)
                    add(snake);
                add(life);
            }
        };
        return new Level(gameObjects);
    }

    private static Cell createCell(Location location, char c) {
        if ('0' <= c && c <= '9')
            return new SizeBonus(location, Character.getNumericValue(c));
        else if (c == '*')
            return new TemporaryBonus(location, 20, 5);
        else if (c == '!')
            return new MovingBonus(location, 5);
        else if (c == '#')
            return new Wall(location);
        else if (c == '+')
            return new LifeCell(location);
        return null;
    }

    private static Snake createSnake(Location location, char c) {
        Direction direction = Direction.fromChar(c);
        if (direction == null)
            return null;
        else
            return new Snake(location, direction, getId());
    }

}
