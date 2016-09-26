import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;

public class Snake {
	static int blockSize = 40;
	static int xSize = 15;
	static int ySize = 10;
	static int xOffset = 100;
	static int yOffset = 100;
	
	public static void main(String[] args){
		SnakeSplash mySplash = new SnakeSplash(5000);
		mySplash.showSplashAndExit();
		int fps = 30;
		int speed = 4;

		if (args.length > 0){
			try {
				fps = Integer.parseInt(args[0]);
			} catch (NumberFormatException e){
				System.err.println("Argument" + args[0] + " is not an integer");
				System.exit(1);
			}
			if (args.length > 1){
				try {
					speed = Integer.parseInt(args[1]);
				} catch (NumberFormatException e){
					System.err.println("Argument" + args[0] + " is not an integer");
					System.exit(1);
				}
			}
		}

		JFrame f = new JFrame("Snake");
		SnakeModel mySnake = new SnakeModel(xSize, ySize, blockSize, xOffset, yOffset, fps, speed);
		SnakeBoard board = new SnakeBoard(xSize, ySize, blockSize, xOffset, yOffset, mySnake);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setBackground(Color.black);
		f.setLocation(100, 100);
		f.setResizable(false);
		f.setContentPane(board);
		f.setVisible(true);

		TimerTask paintTask = new TimerTask(){
			public void run(){
				board.repaint();
			}
		};
		Timer paintTimer = new Timer();
		paintTimer.scheduleAtFixedRate(paintTask, 0, 1000/fps);
		
		TimerTask modelTask = new TimerTask(){
			public void run(){
				if ((!mySnake.getGameOver())&&(!mySnake.getPause())){
					mySnake.move();
				}
			}
		};
		Timer modelTimer = new Timer();
		modelTimer.scheduleAtFixedRate(modelTask, 0, 1000/speed);
	}
}
