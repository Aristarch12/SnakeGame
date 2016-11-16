package oop.snakegame;

import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.primitives.Direction;
import oop.snakegame.primitives.Location;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {

//    @Test
//    public void testSimpleStep() throws Throwable {
//        String[] map = new String[]{
//            "####",
//            "#  #",
//            "#R #",
//            "####"
//        };
//
//        Level level = LevelCreator.create(map);
//        level.handleTick();
//        assertEquals(new Location(2, 2), level.snakes[0].getHead().location);
//    }
//
//    @Test
//    public void testWallCollision() throws Throwable {
//        String[] map = new String[]{
//            "#####",
//            "#   #",
//            "# D #",
//            "# # #",
//            "#####"
//        };
//        Level level = LevelCreator.create(map);
//        level.handleTick();
//        Assert.assertTrue(level.snakes[0].isDead());
//    }
//
//    @Test
//    public void testSelfCollision() throws Throwable {
//        String[] map = new String[]{
//                "#####",
//                "#  D#",
//                "#  4#",
//                "#   #",
//                "#####"
//        };
//        Level level = LevelCreator.create(map);
//        Direction[] directions = new Direction[]{
//            Direction.Down, Direction.Down, Direction.Left, Direction.Up, Direction.Right
//        };
//        for (Direction direction : directions) {
//            level.snakes[0].setNextHeadDirection(direction);
//            level.handleTick();
//        }
//        Assert.assertTrue(level.snakes[0].isDead());
//    }
//
//    @Test
//    public void testMapExit() throws Throwable {
//        String[] map = new String[]{
//                " R"
//        };
//        Level level = LevelCreator.create(map);
//        level.handleTick();
//        Assert.assertTrue(level.snakes[0].isDead());
//    }
//
//    @Test
//    public void testBonus() throws Throwable {
//        String[] map = new String[]{
//            "#####",
//            "#D  #",
//            "#1  #",
//            "#   #",
//            "#####"
//        };
//        Level level = LevelCreator.create(map);
//        level.handleTick();
//        assertEquals(new Location(1, 2), level.snakes[0].getHead().location);
//        assertEquals(1, level.snakes[0].getLength());
//        level.handleTick();
//        assertArrayEquals(new SnakeBlock[]{
//            new SnakeBlock(new Location(1, 3),  0),
//            new SnakeBlock(new Location(1, 2), 0)
//        }, level.snakes[0].toArray());
//    }
}