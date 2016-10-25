package oop.snakegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import oop.snakegame.cells.SizeBonus;
import oop.snakegame.cells.SnakeBlock;
import oop.snakegame.cells.Wall;
import oop.snakegame.primitives.Location;

import java.util.HashMap;

class Painter implements IVisitor {

    final static int cellSize = 15;
    final static Paint defaultColor = Color.PINK;

    private final HashMap<Integer, Paint> snakeIdToColor;
    private GraphicsContext gc;

    Painter(GraphicsContext gc, HashMap<Integer, Paint> idToColor){
        this.gc = gc;
        this.snakeIdToColor = idToColor;

    }

    @Override
    public void visit(Wall wall){
        fillCell(wall.location, Color.GRAY);
    }

    @Override
    public void visit(SizeBonus bonus){
        fillCell(bonus.location, Color.GREEN);
    }

    @Override
    public void visit(SnakeBlock snakeBlock){
        if (snakeIdToColor.containsKey(snakeBlock.id))
            fillCell(snakeBlock.location, snakeIdToColor.get(snakeBlock.id));
        else
            fillCell(snakeBlock.location, defaultColor);
    }

    private void fillCell(Location location, Paint p) {
        gc.setStroke(Color.BLACK);
        gc.setFill(p);
        gc.strokeRect(location.x * cellSize, location.y * cellSize, cellSize, cellSize);
        gc.fillRect(location.x * cellSize, location.y * cellSize, cellSize, cellSize);
    }
}
