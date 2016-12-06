package oop.snakegame;

import oop.snakegame.cells.Cell;
import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.primitives.Direction;
import oop.snakegame.primitives.Location;
import oop.snakegame.primitives.Offset;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Snake extends GameObject {
    private Direction lastHeadDirection;
    private Direction nextHeadDirection;
    private LinkedList<SnakeBlock> blocks;
    private int extensionCount;
    private boolean death;
    final int id;

    Snake(Location location, Direction headDirection, int id) {
        this.id = id;
        this.nextHeadDirection = headDirection;
        blocks = new LinkedList<>();
        blocks.addFirst(new SnakeBlock(location, id));
        death = false;
    }

    void setNextHeadDirection(Direction direction) {
        if (getLength() > 1 && direction == lastHeadDirection.opposite())
            return;
        synchronized (this){
            nextHeadDirection = direction;
        }
    }

    public void setHeadLocation(Location newLocation) {
        blocks.set(0, new SnakeBlock(newLocation, id));
    }

    public void destroy() {
        death = true;
    }

    public boolean containsLocation(Location location) {
        return stream().anyMatch(snakeBlock -> snakeBlock.location.equals(location));
    }
    public Direction getNextHeadDirection() {
        synchronized (this) {
            return nextHeadDirection;
        }
    }

    void reverse(){
        Collections.reverse(blocks);
        if (blocks.size() > 1) {
            SnakeBlock firstBlock = blocks.get(0);
            SnakeBlock secondBlock = blocks.get(1);
            Offset offset = secondBlock.location.getOffset(firstBlock.location);
            nextHeadDirection = Direction.fromOffset(offset);
        } else {
            nextHeadDirection = nextHeadDirection.opposite();
        }
        lastHeadDirection = nextHeadDirection;
    }

//    @Override
//    public Iterator<Cell> iterator() {
//        return blocks.iterator();
//    }

    public void extend(int increment) {
        extensionCount += increment;
    }

    boolean isDead()  {
        return death;
    }

    void move() {
        appendHead();
        if (extensionCount == 0) {
            reduceTail();
        } else {
            extensionCount--;
        }
    }

    private void appendHead() {
        synchronized (this) {
            SnakeBlock newHead = new SnakeBlock(getHead().location.addOffset(nextHeadDirection.getOffset()), id);
            blocks.addFirst(newHead);
            lastHeadDirection = nextHeadDirection;
        }
    }

    private void reduceTail() {
        blocks.removeLast();
    }

    private int getLength() {
        return blocks.size();
    }

    SnakeBlock[] toArray() {
        return blocks.toArray(new SnakeBlock[0]);
    }

    Stream<SnakeBlock> stream(){
        return blocks.stream();
    }



    SnakeBlock getHead() {
        return blocks.getFirst();
    }


    @Override
    void update() {

    }

    @Override
    public Iterator<Cell> iterator() {
        return null;
    }
}
