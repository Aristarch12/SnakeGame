package oop.snakegame;

import oop.snakegame.cells.*;

public interface IVisitor {
    void visit(Wall wall);
    void visit(SizeBonus bonus);
    void visit(SnakeBlock block);
    void visit(Teleport teleport);
    void visit(MovingBonus movingBonus);
    void visit(TemporaryBonus temporaryBonus);
}
