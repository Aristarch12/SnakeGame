package oop.snakegame;

import oop.snakegame.cells.Cell;

public abstract class GameObject  implements Iterable<Cell>{
    abstract void update();
}
