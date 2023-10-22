import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ControlPanel extends JPanel implements ActionListener {

  //private GamePanel game;
  private Timer timer = new Timer(1, this);
  private GamePanel game;
  private JButton startButton = new JButton("Start");
  private JButton pauseButton = new JButton("Pause");
  private JButton infoButton  = new JButton("Instructions");
  private JButton credButton  = new JButton("Credits");
  
  // Constructor
  public ControlPanel(GamePanel g) {
    game = g;
    
    startButton.addActionListener(this);
    pauseButton.addActionListener(this);
    infoButton.addActionListener(this);
    add(startButton);
    add(pauseButton);
    add(infoButton);
    
    pauseButton.setEnabled(false);
  }

  // Called when a button is clicked
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startButton) {
      if (game.isRunning() == false && game.isEnded() != true) {
        game.start();
        timer.start();
        game.requestFocusInWindow();
        startButton.setText("Reset");
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        infoButton.setEnabled(false);
      }
      else {
        game.reset();
        startButton.setText("Start");
        pauseButton.setText("Pause");
        pauseButton.setEnabled(false);
      }
    }
    else if (e.getSource() == pauseButton) {
      if (game.isPaused() == false) {
        game.pause();
        pauseButton.setText("Resume");
        startButton.setEnabled(true);
        infoButton.setEnabled(true);
      }
      else {
        game.resume();
        game.requestFocusInWindow();
        pauseButton.setText("Pause");
        startButton.setEnabled(false);
        infoButton.setEnabled(false);
      }
    }
    else if (e.getSource() == infoButton) {
      JOptionPane.showMessageDialog(null,
      "How to Play:"
  + "\nMove the paddle left and right,"
  + "\nand try to break all of the bricks!"
  + "\n"
  + "\nControls:"
  + "\nArrow Left = Paddle Left"
  + "\nArrow Right = Paddle Right"
  + "\n"
  + "\nSomething to note:"
  + "\nThe angle of ball changes based on"
  + "\nwhere on the paddle it hits. The  "
  + "\nfarther from the middle, the wider"
  + "\nthe angle (a max of " + Game.BALL_MAX_ANGLE + " degrees)");
    }
    else {
      if (game.isEnded() == true) {
        startButton.setText("Reset");
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        infoButton.setEnabled(true);
      }
    }
  }
}