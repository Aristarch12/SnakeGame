package oop.snakegame;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

enum Direction {
    Up,
    Right,
    Down,
    Left;

    public Direction opposite() {
        switch (this) {
            case Down:
                return Up;
            case Left:
                return Right;
            case Right:
                return Left;
            default:
                return Down;
        }
    }

    public Offset getOffset() {
        switch (this) {
            case Down:
                return new Offset(0, 1);
            case Left:
                return new Offset(-1, 0);
            case Right:
                return new Offset(1, 0);
            default:
                return new Offset(0, -1);
        }
    }

    public static Direction fromChar(char c) {
        switch (c) {
            case 'L':
                return Direction.Left;
            case 'R':
                return Direction.Right;
            case 'U':
                return Direction.Up;
            case 'D':
                return Direction.Down;
            default:
                return null;
        }
    }
}

class Snake implements Iterable<SnakeBlock>, IControllableSnake {
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

    void destroy() {
        death = true;
    }

    boolean isDead()  {
        return death;
    }

    void extend(int increment) {
        extensionCount += increment;
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

    int getLength() {
        return blocks.size();
    }

    public void setNextHeadDirection(Direction direction) {
        if (getLength() > 1 && direction == lastHeadDirection.opposite())
            return;
        synchronized (this){
            nextHeadDirection = direction;
        }
    }

    public Iterator<SnakeBlock> iterator() {
        return blocks.iterator();
    }

    SnakeBlock[] toArray() {
        return blocks.toArray(new SnakeBlock[0]);
    }

    Stream<SnakeBlock> stream(){
        return blocks.stream();
    }

    Direction getNextHeadDirection() {
        synchronized (this) {
            return nextHeadDirection;
        }
    }

    SnakeBlock getHead() {
        return blocks.getFirst();
    }

    public void reverse(){
        throw new NotImplementedException();
    }
}
