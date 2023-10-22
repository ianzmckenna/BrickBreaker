import java.awt.event.KeyEvent;

public class Paddle extends Sprite {
  
  private Direction d = Direction.NULL;
  
  public Paddle(double x, double y, int width, int height) {
    super(x, y, width, height);
    this.width = width;
    this.height = height;
    
  }
    
  private enum Direction {
    LEFT, RIGHT, NULL;
  }
  
  public void move() {
    if (d == Direction.LEFT) {
      x = x - Game.PADDLE_MOVEMENT_SPEED;
    }
    else if (d == Direction.RIGHT) {
      x = x + Game.PADDLE_MOVEMENT_SPEED;
    }
  }
  
  public void setDirection(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      d = Direction.LEFT;
    }
    else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      d = Direction.RIGHT;
    }
  }
  
  public void stop() {
    d = Direction.NULL;
  }
}