package com.hungrysnake;

import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
  public static final int WIDTH = 15;
  public static final int HEIGHT = 15;
  private Snake snake;
  private Apple apple;
  private int turnDelay;
  private boolean isGameStopped;
  private static final int GOAL = 28;
  private int score;

  @Override
  public void initialize() {
    setScreenSize(WIDTH, HEIGHT);
    createGame();
  }

  private void createGame() {
    snake = new Snake(WIDTH / 2, HEIGHT / 2);
    createNewApple();
    isGameStopped = false;
    score = 0;
    setScore(score);
    drawScene();
    turnDelay = 300;
    setTurnTimer(turnDelay);
  }

  private void drawScene() {
    for (int row = 0; row < WIDTH; row++) {
      for (int col = 0; col < HEIGHT; col++) {
        setCellValueEx(row, col, Color.DARKSEAGREEN, "");
      }
    }
    snake.draw(this);
    apple.draw(this);
  }

  @Override
  public void onTurn(int step) {
    snake.move(apple);
    if (!apple.isAlive) {
      createNewApple();
      score += 5;
      setScore(score);
      turnDelay -= 10;
      setTurnTimer(turnDelay);
    }
    if (!snake.isAlive)
      gameOver();
    if (snake.getLength() > GOAL)
      win();
    drawScene();
  }

  @Override
  public void onKeyPress(Key key) {
    switch (key) {
      case UP:
        snake.setDirection(Direction.UP);
        break;
      case DOWN:
        snake.setDirection(Direction.DOWN);
        break;
      case LEFT:
        snake.setDirection(Direction.LEFT);
        break;
      case RIGHT:
        snake.setDirection(Direction.RIGHT);
        break;
      case SPACE:
        if (isGameStopped) {
          createGame();
        }
        break;
      default:
        break;
    }
  }

  private void createNewApple() {
    Apple newApple;
    do {
      int x = getRandomNumber(WIDTH);
      int y = getRandomNumber(HEIGHT);
      newApple = new Apple(x, y);
    } while (snake.checkCollision(newApple));
    apple = newApple;
  }

  private void gameOver() {
    stopTurnTimer();
    isGameStopped = true;
    showMessageDialog(Color.RED, "Game over", Color.BLACK, 75);
  }

  private void win() {
    stopTurnTimer();
    isGameStopped = true;
    showMessageDialog(Color.GREEN, "You win", Color.BLACK, 75);
  }
}
