import java.awt.*;
import javax.swing.*;

// Code based of a SampleSplashScreen named "SplashScreen.java"
public class SnakeSplash extends JWindow {
  private int duration;
  public SnakeSplash(int d) {
    duration = d;
  }

  public void showSplash() {
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.BLACK);

    int width = 500;
    int height =120;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);

	// draw forground
    JLabel lblSnake = new JLabel("Snake-Game", JLabel.CENTER);
    JLabel lblName = new JLabel("Kevin Yu Fu - Y49fu");
    JLabel lblInstructions = new JLabel
      ("Arrow Keys to move, 'p' to pause, 's' to restart after death", JLabel.CENTER);
    lblSnake.setFont(new Font("Sans-Serif", Font.BOLD, 30));
	lblSnake.setForeground(Color.GRAY);
    lblName.setFont(new Font("Sans-Serif", Font.BOLD, 14));
	lblName.setForeground(Color.GRAY);
    lblInstructions.setFont(new Font("Sans-Serif", Font.BOLD, 14));
	lblInstructions.setForeground(Color.GRAY);
    content.add(lblSnake, BorderLayout.CENTER);
    content.add(lblName, BorderLayout.NORTH);
    content.add(lblInstructions, BorderLayout.SOUTH);

	// draw background
    content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));

    // Display
    setVisible(true);

    try { Thread.sleep(duration); } catch (Exception e) {}

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();
  }

}
