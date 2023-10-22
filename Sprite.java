import java.awt.Rectangle;

public class Sprite {
  
  protected double x, y;
  protected int width, height;
  
  public Sprite(double x, double y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public int getX() {
    return (int)x;
  }
  
  public int getY() {
    return (int)y;
  }
  
  public void setX(int new_x) {
    x = new_x;
  }
  
  public void setY(int new_y) {
    y = new_y;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public Rectangle getRect() {
    return new Rectangle(getX(), getY(), width, height);
  }
}