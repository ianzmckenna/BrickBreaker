import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
  
  Timer timer = new Timer(1, this);
  private MovingBall ball;
  private Paddle paddle;
  private Brick[][] bricks = new Brick[Game.BRICK_ROWS][Game.BRICK_COLUMNS];
  private int bricksRemaining;
  private boolean running, paused, ended;
  
  public GamePanel() {
    
    addKeyListener(this);
    
    setBackground(Game.WINDOW_COLOR);
    setBorder(new LineBorder(Game.BORDER_COLOR, Game.BORDER_THICKNESS));
    setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
    
    setupGame();
  }
  
  public void setupGame() {
    ball = new MovingBall((Game.WINDOW_WIDTH - Game.BALL_WIDTH) / 2,
                          Game.WINDOW_HEIGHT - Game.BORDER_THICKNESS - Game.PADDLE_SPACE_ABOVE_FLOOR - Game.PADDLE_HEIGHT - Game.BALL_HEIGHT - 1,
                          Game.BALL_WIDTH,
                          Game.BALL_HEIGHT);
    
    paddle = new Paddle((Game.WINDOW_WIDTH - Game.PADDLE_WIDTH) / 2,
                        Game.WINDOW_HEIGHT - Game.BORDER_THICKNESS - Game.PADDLE_SPACE_ABOVE_FLOOR - Game.PADDLE_HEIGHT,
                        Game.PADDLE_WIDTH,
                        Game.PADDLE_HEIGHT);
    
    int totalBrickWidth = Game.BRICK_WIDTH * Game.BRICK_COLUMNS + (Game.BRICK_SPACE_BETWEEN + 1) * (Game.BRICK_COLUMNS - 1);
    int bricksStartingX = (Game.WINDOW_WIDTH - totalBrickWidth) / 2;
    for (int r = 0; r < Game.BRICK_ROWS; r++) {
      for (int c = 0; c < Game.BRICK_COLUMNS; c++) {
        bricks[r][c] = new Brick(bricksStartingX + c * (Game.BRICK_WIDTH + Game.BRICK_SPACE_BETWEEN + 1),
                                 Game.BORDER_THICKNESS + Game.BRICK_SPACE_BELOW_CEILING + r * (Game.BRICK_HEIGHT + Game.BRICK_SPACE_BETWEEN + 1),
                                 Game.BRICK_WIDTH,
                                 Game.BRICK_HEIGHT);
      }
    }
    bricksRemaining = Game.BRICK_ROWS * Game.BRICK_COLUMNS;
  }
  
  public void actionPerformed(ActionEvent e) {
    ball.move();
    paddle.move();
    checkCollisions();
    repaint();
  }
  
  public void keyPressed(KeyEvent e) {
    paddle.setDirection(e);
  }
  
  public void keyReleased(KeyEvent e) {
    paddle.stop();
  }
  
  public void keyTyped(KeyEvent e) {}
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draw ball
    g.setColor(Game.BALL_COLOR);
    g.fillRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
    g.setColor(Color.BLACK);
    g.drawRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
    
    // draw paddle
    g.setColor(Game.PADDLE_COLOR);
    g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
    g.setColor(Color.BLACK);
    g.drawRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
    
    // draw bricks
    for (int r = 0; r < Game.BRICK_ROWS; r++) {
      for (int c = 0; c < Game.BRICK_COLUMNS; c++) {
        if (!bricks[r][c].isDestroyed()) {
          g.setColor(Game.BRICK_COLOR);
          g.fillRect(bricks[r][c].getX(), bricks[r][c].getY(), bricks[r][c].getWidth(), bricks[r][c].getHeight());
          g.setColor(Color.BLACK);
          g.drawRect(bricks[r][c].getX(), bricks[r][c].getY(), bricks[r][c].getWidth(), bricks[r][c].getHeight());
        }
      }
    }
    
    // draw win/lose message if game is over
    g.setFont(new Font(Game.FONT_NAME, Game.FONT_STYLE, Game.FONT_SIZE));
    FontMetrics fm = g.getFontMetrics();
    int messageHeight = fm.getHeight();
    if (bricksRemaining == 0) {
      int messageWidth = fm.stringWidth("You win!");
      g.drawString("You win!", (Game.WINDOW_WIDTH  - messageWidth) / 2, 
                               (Game.WINDOW_HEIGHT - messageHeight) / 2 + fm.getAscent());
      timer.stop();
      running = false;
      ended = true;
    }
    else if (ball.getY() + ball.getHeight() >= Game.WINDOW_HEIGHT - Game.BORDER_THICKNESS) {
      int messageWidth = fm.stringWidth("You lose...");
      g.drawString("You lose...", (Game.WINDOW_WIDTH  - messageWidth) / 2, 
                                  (Game.WINDOW_HEIGHT - messageHeight) / 2 + fm.getAscent());
      timer.stop();
      running = false;
      ended = true;
    }
  }
  
  public void checkCollisions() {
    
    // check if paddle is hitting a wall
    if (paddle.getX() < Game.BORDER_THICKNESS) {
      paddle.setX(paddle.getX() + (Game.BORDER_THICKNESS - paddle.getX()));
    }
    else if (paddle.getX() + paddle.getWidth() > Game.WINDOW_WIDTH - Game.BORDER_THICKNESS) {
      paddle.setX(paddle.getX() - (paddle.getX() + paddle.getWidth() - Game.WINDOW_WIDTH + Game.BORDER_THICKNESS));
    }
    
    // check if ball is hitting a wall
    if (ball.getX() + ball.getWidth() >= Game.WINDOW_WIDTH - Game.BORDER_THICKNESS || ball.getX() <= Game.BORDER_THICKNESS) {
      ball.setXDir(-ball.getXDir());
      return;
    }
    if (ball.getY() <= Game.BORDER_THICKNESS) {
      ball.setYDir(-ball.getYDir());
      return;
    }
    
    // check if ball is hitting the paddle
    if (ball.getRect().intersects(paddle.getRect())) {
      int maxDiff = (ball.getWidth() + paddle.getWidth()) / 2;
      int diff = (ball.getX() + ball.getWidth() / 2) - (paddle.getX() + paddle.getWidth() / 2);
      double bearingRadians = Math.toRadians(Game.BALL_MAX_ANGLE * ((double)diff / maxDiff));
      ball.setXDir(Math.sin(bearingRadians) * Math.sqrt(Game.BALL_MOVEMENT_SPEED * Game.BALL_MOVEMENT_SPEED * 2));
      ball.setYDir(-Math.cos(bearingRadians) * Math.sqrt(Game.BALL_MOVEMENT_SPEED * Game.BALL_MOVEMENT_SPEED * 2));
      return;
    }
    
    // check if ball is hitting a brick
    for (int r = 0; r < Game.BRICK_ROWS; r++) {
      for (int c = 0; c < Game.BRICK_COLUMNS; c++) {
        if (bricks[r][c].isDestroyed() == false) {
          if (ball.getRect().intersects(bricks[r][c].getRect())) {
            ball.bounceOffBrick(bricks[r][c]);
            bricks[r][c].destroy();
            bricksRemaining--;
            return;
          }
        }
      }
    }
  }
    
  public void start() {
    timer.start();
    running = true;
  }
  
  public void reset() {
    timer.stop();
    running = false;
    paused = false;
    ended = false;
    setupGame();
    repaint();
  }
  
  public void pause() {
    timer.stop();
    paused = true;
  }
  
  public void resume() {
    timer.start();
    paused = false;
  }
  
  public boolean isRunning() {
    return running;
  }
  
  public boolean isPaused() {
    return paused;
  }
  
  public boolean isEnded() {
    return ended;
  }
}