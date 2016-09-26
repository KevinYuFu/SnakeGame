import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.Font;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.vecmath.*;

class SnakeBoard extends JComponent implements KeyListener{
	int blockSize;
	int xSize;
	int ySize;
	int xOffset;
	int yOffset;
	SnakeModel s;

	public SnakeBoard(int x, int y, int blockSize, int xOffset, int yOffset, SnakeModel s){
		this.xSize = x;
		this.ySize = y;
		this.blockSize = blockSize;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.s = s;

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void keyPressed(KeyEvent e){
		char cur = s.getDirection();
		int keyCode = e.getKeyCode();
		switch( keyCode ) {
			case KeyEvent.VK_UP:
				if (cur != 'd'){
					s.setDirection('u');
				}
				break;
			case KeyEvent.VK_DOWN:
				if (cur != 'u'){
					s.setDirection('d');
				}
				break;
			case KeyEvent.VK_LEFT:
				if (cur != 'r'){
					s.setDirection('l');
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (cur != 'l'){
					s.setDirection('r');
				}
				break;
		}
		//System.out.println("Pressed:" + e.getKeyChar());
	}
	public void keyTyped(KeyEvent e){
		if (e.getKeyChar() == 'p'){
			s.togglePause();
		}
		if ((e.getKeyChar() == 's')&&(s.getGameOver())){
			s.reset();
		}
		//System.out.println("Typed:" + e.getKeyChar());
	}

	public void keyReleased(KeyEvent e){}


	private void drawGrid(Graphics2D g2){
		int xEnd = xOffset + xSize*blockSize;
		int yEnd = yOffset + ySize*blockSize;

		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.GREEN);

		for (int i = 0; i<=ySize; ++i){
			g2.drawLine(xOffset, i*blockSize + yOffset, xEnd, i*blockSize + yOffset);
		}
		for (int i = 0; i<=xSize; ++i){
			g2.drawLine(i*blockSize + xOffset, yOffset, i*blockSize + xOffset, yEnd);
		}
	}

	private void drawBlock(Point2d p, Graphics2D g2) {
		double x = p.x;
		double y = p.y;
		g2.translate(xOffset + x*blockSize, yOffset + y*blockSize);
		g2.fillRect(1, 1, blockSize - 2, blockSize - 2);
		g2.translate(-(xOffset + x*blockSize), -(yOffset + y*blockSize));
	}

	public void drawSnake(Graphics2D g2){
		ArrayList<Point2d> pieces = this.s.getSnake();
		g2.setColor(Color.GRAY);
		for (int i = 0; i < pieces.size(); ++i){
			drawBlock(pieces.get(i), g2);
		}
	}

	public void drawFood(Graphics2D g2){
		g2.setColor(Color.RED);
		drawBlock(this.s.getFood(), g2);
	}

	public void drawText(Graphics2D g2){
		g2.setColor(Color.WHITE);

		// generate Strings
		String scoreText = "Score: " + s.getScore();
		String fpsText = "fps: " + s.getFPS();
		String speedText = "Snake Speed: " + s.getSpeed();
		
		// Display gameplay data
		g2.drawString(fpsText, getWidth() - 150, 30);
		g2.drawString(speedText, getWidth() - 150, 50);
		g2.drawString(scoreText, 50, 50);

		Font heldFont = g2.getFont();
		// set font for large messages
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Courier New", 1, 30));
		if (s.getGameOver()){
			g2.drawString("Game Over",310,300); 
			g2.setFont(new Font("Courier New", 1, 15));
			g2.drawString("Press 's' to play again!",310, 320);
		} else if (s.getPause()){
			g2.drawString("Pause",350,300); 
		}
		g2.setFont(heldFont);
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		this.drawGrid(g2);
		this.drawSnake(g2);
		this.drawFood(g2);
		this.drawText(g2);
	}
}
