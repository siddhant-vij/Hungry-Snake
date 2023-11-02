package com.hungrysnake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
  private List<GameObject> snakeParts = new ArrayList<>();
  private static final String HEAD_SIGN = "\uD83D\uDC7E";
  private static final String BODY_SIGN = "\u26AB";
  public boolean isAlive = true;
  private Direction direction = Direction.DOWN;

  public Snake(int x, int y) {
    snakeParts.add(new GameObject(x, y));
    snakeParts.add(new GameObject(x + 1, y));
    snakeParts.add(new GameObject(x + 2, y));
  }

  public void draw(Game game) {
    for (int i = 0; i < snakeParts.size(); i++) {
      GameObject part = snakeParts.get(i);
      if (i == 0) {
        game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, !isAlive ? Color.RED : Color.BLACK, 75);
      } else {
        game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, !isAlive ? Color.RED : Color.BLACK, 75);
      }
    }
  }

  public void setDirection(Direction direction) {
    if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT)
        && snakeParts.get(0).x == snakeParts.get(1).x) {
      return;
    }
    if ((this.direction == Direction.UP || this.direction == Direction.DOWN)
        && snakeParts.get(0).y == snakeParts.get(1).y) {
      return;
    }

    if ((direction == Direction.UP && this.direction == Direction.DOWN)
        || (direction == Direction.LEFT && this.direction == Direction.RIGHT)
        || (direction == Direction.RIGHT && this.direction == Direction.LEFT)
        || (direction == Direction.DOWN && this.direction == Direction.UP))
      return;

    this.direction = direction;
  }

  public void move(Apple apple) {
    GameObject newHead = createNewHead();
    if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
      isAlive = false;
      return;
    }
    if (checkCollision(newHead)) {
      isAlive = false;
      return;
    }
    snakeParts.add(0, newHead);
    if (newHead.x == apple.x && newHead.y == apple.y) {
      apple.isAlive = false;
    } else {
      removeTail();
    }
  }

  public GameObject createNewHead() {
    GameObject newHead;
    switch (direction) {
      case LEFT:
        newHead = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        break;
      case RIGHT:
        newHead = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        break;
      case UP:
        newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        break;
      case DOWN:
        newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + direction);
    }
    return newHead;
  }

  public void removeTail() {
    snakeParts.remove(snakeParts.size() - 1);
  }

  public boolean checkCollision(GameObject gameObject) {
    for (GameObject part : snakeParts) {
      if (part.x == gameObject.x && part.y == gameObject.y) {
        return true;
      }
    }
    return false;
  }

  public int getLength() {
    return snakeParts.size();
  }
}
