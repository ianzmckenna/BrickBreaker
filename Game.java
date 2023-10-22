import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;

public class Game extends JFrame {
  
  // window values
  static int WINDOW_WIDTH = 1000;
  static int WINDOW_HEIGHT = 600;
  static Color WINDOW_COLOR = Color.WHITE;
  
  // window border values
  static int BORDER_THICKNESS = 1;
  static Color BORDER_COLOR = Color.GRAY;
  
  // ball values
  static int BALL_WIDTH = 30;
  static int BALL_HEIGHT = 30;
  static int BALL_MAX_ANGLE = 60;
  static double BALL_MOVEMENT_SPEED = 1;
  static Color BALL_COLOR = Color.YELLOW;
  
  // paddle values
  static int PADDLE_WIDTH = 150;
  static int PADDLE_HEIGHT = 20;
  static double PADDLE_MOVEMENT_SPEED = 2;
  static int PADDLE_SPACE_ABOVE_FLOOR = 100;
  static Color PADDLE_COLOR = Color.BLUE;
  
  // brick values
  static int BRICK_ROWS = 3;
  static int BRICK_COLUMNS = 4;
  static int BRICK_WIDTH = 100;
  static int BRICK_HEIGHT = 50;
  static int BRICK_SPACE_BETWEEN = 2;
  static int BRICK_SPACE_BELOW_CEILING = 50;
  static Color BRICK_COLOR = Color.GREEN;
  
  // the game's font
  static String FONT_NAME = "TimesRoman";
  static int FONT_STYLE = Font.PLAIN;
  static int FONT_SIZE = 100;
  
  public Game() {
    super("Brick Breaker");
    
    GamePanel game = new GamePanel();
    ControlPanel controls = new ControlPanel(game);
    
    add(game, BorderLayout.NORTH);
    add(controls, BorderLayout.SOUTH);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    pack();
  }
  
  public static void main(String[] args) {
    Game game = new Game();
    game.setLocationRelativeTo(null);
    game.setVisible(true);
  }
}
