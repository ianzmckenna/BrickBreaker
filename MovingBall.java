import java.awt.Rectangle;

public class MovingBall extends Sprite {

  private double xdir;
  private double ydir;
  
  public MovingBall(double x, double y, int width, int height) {
    super(x, y, width, height);
    double bearingRadians = Math.toRadians(Game.BALL_MAX_ANGLE * (Math.random() * 2 - 1));
    xdir =  Math.sin(bearingRadians) * Math.sqrt(Game.BALL_MOVEMENT_SPEED * Game.BALL_MOVEMENT_SPEED * 2);
    ydir = -Math.cos(bearingRadians) * Math.sqrt(Game.BALL_MOVEMENT_SPEED * Game.BALL_MOVEMENT_SPEED * 2);
  }
  
  public double getXDir() {
    return xdir;
  }
  
  public void setXDir(double new_xdir) {
    xdir = new_xdir;
  }
  
  public double getYDir() {
    return ydir;
  }
  
  public void setYDir(double new_ydir) {
    ydir = new_ydir;
  }
  
  public void move() {
    x = x + xdir;
    y = y + ydir;
  }
  
  public void bounceOffBrick(Brick brick) {
    Rectangle intersection = this.getRect().intersection(brick.getRect());
    if (intersection.width >= intersection.height) {
      ydir = -ydir;
    }
    else {
      xdir = -xdir;
    }
  }
}