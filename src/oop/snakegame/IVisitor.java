package oop.snakegame;

import oop.snakegame.cells.SizeBonus;
import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.cells.Teleport;
import oop.snakegame.cells.Wall;

public interface IVisitor {
    void visit(Wall wall);
    void visit(SizeBonus bonus);
    void visit(SnakeBlock block);
    void visit(Teleport teleport);
}
