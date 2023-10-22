public class Brick extends Sprite {
  
  private boolean destroyed;
  
  public Brick(int x, int y, int width, int height) {
    super(x, y, width, height);
    destroyed = false;
  }
  
  public void destroy() {
    destroyed = true;
  }
    
  public boolean isDestroyed() {
    return destroyed;
  }
}