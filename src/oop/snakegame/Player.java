package oop.snakegame;

enum PlayerState{
    Alive, Dead
}

public class Player {
    private int score = 0;
    private Snake snake;

    public Snake getSnake(){
        return snake;
    }
    public void addScore(int increment) {
        score += increment;
    }

    public int getScore() { return score; }
    void setSnake(Snake snake){
        this.snake = snake;
    }

    PlayerState getState(){
        return snake.isDead() ? PlayerState.Dead : PlayerState.Alive;
    }
}
