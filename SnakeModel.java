//import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.*;

enum Direction {
	UP, DOWN, LEFT, RIGHT
}

class SnakeModel {
	ArrayList<Point2d> pieces;
	Point2d food;
	Direction dir = Direction.DOWN;
	Direction dirBuffer = Direction.DOWN;
	int blockSize;
	int xSize;
	int ySize;
	int xOffset;
	int yOffset;
	int fps;
	int speed;
	int score = 0;
	boolean gameOver = false;
	boolean pause = false;


	public SnakeModel(int x, int y, int blockSize, int xOffset, int yOffset, int fps, int speed){
		this.xSize = x;
		this.ySize = y;
		this.blockSize = blockSize;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.fps = fps;
		this.speed = speed;
		pieces = new ArrayList<Point2d>();
		this.addPiece(0, 0);
		newFood();
	}

	public void reset(){
		pieces = new ArrayList<Point2d>();
		this.addPiece(0, 0);
		newFood();
		score = 0;
		gameOver = false;
		pause = false;
		this.dir = Direction.DOWN;
		this.dirBuffer = Direction.DOWN;
	}

	public int getScore(){
		return score;
	}

	public int getFPS(){
		return fps;
	}

	public int getSpeed(){
		return speed;
	}

	public boolean getGameOver(){
		return gameOver;
	}

	public boolean getPause(){
		return pause;
	}

	public void togglePause(){
		pause = (pause) ? false : true;
	}

	private void addPiece(double x, double y){
		this.pieces.add(new Point2d(x, y));
	}

	private void addTail(){
		Point2d p = pieces.get(pieces.size()-1);
		addPiece(p.x, p.y);
	}

	private void newFood(){
		Random rand = new Random();
		boolean overlap = true;

		while (overlap){
			overlap = false;
			this.food = new Point2d(rand.nextInt(xSize), rand.nextInt(ySize));
			for (int i = 0; i < pieces.size(); ++i){
				if ((pieces.get(i).x == food.x) && (pieces.get(i).y == food.y)){
					overlap = true;
				}
			}
		}
	}

	private boolean outBound(Point2d p){
		if ((p.x < 0)||(p.y < 0)||(p.x >= xSize)||(p.y >= ySize)){
			return true;
		}
		for (int i = 1; i < pieces.size(); ++i){
			if ((p.x ==  pieces.get(i).x) && (p.y ==  pieces.get(i).y)){
				return true;
			}
		}
		return false;
	}

	private void eat(){
		Point2d p = pieces.get(0);
		if ((p.x == food.x) && (p.y == food.y)){
			++score;
			newFood();
			addTail();
		}
	}

	public char getDirection(){
		char e = 'n';
		switch(dir){
			case UP:
				e = 'u';
				break;
			case DOWN:
				e = 'd';
				break;
			case LEFT:
				e = 'l';
				break;
			case RIGHT:
				e = 'r';
				break;
		}
		return e;
	}

	public void setDirection(char e){
		switch(e){
			case 'u':
				dirBuffer = Direction.UP;
				break;
			case 'd':
				dirBuffer = Direction.DOWN;
				break;
			case 'l':
				dirBuffer = Direction.LEFT;
				break;
			case 'r':
				dirBuffer = Direction.RIGHT;
				break;
		}
	}

	public ArrayList<Point2d> getSnake(){
		return this.pieces;
	}

	public Point2d getFood(){
		return this.food;
	}

	public void move(){
		this.dir = this.dirBuffer;

		Point2d newHead;
		switch (dir) {
			case UP:
				newHead = new Point2d(pieces.get(0).x, pieces.get(0).y - 1);
				break;
			case DOWN:
				newHead = new Point2d(pieces.get(0).x, pieces.get(0).y + 1);
				break;
			case LEFT:
				newHead = new Point2d(pieces.get(0).x - 1, pieces.get(0).y);
				break;
			default: //RIGHT
				newHead = new Point2d(pieces.get(0).x + 1, pieces.get(0).y);
				break;
		}
		if (outBound(newHead)){
			gameOver = true;
			pause = true;
			return;
		}

		for (int i = pieces.size()-1; i > 0; --i){
			pieces.set(i, pieces.get(i-1));
		}
		pieces.set(0, newHead);

		this.eat();
	}
}
