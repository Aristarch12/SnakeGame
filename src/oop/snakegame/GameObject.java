package oop.snakegame;

import oop.snakegame.cells.Cell;

import java.util.stream.Stream;

public abstract class GameObject  implements Iterable<Cell>{
    abstract void update();
    abstract Stream<Cell> stream();
}
