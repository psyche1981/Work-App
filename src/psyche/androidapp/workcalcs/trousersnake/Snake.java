package psyche.androidapp.workcalcs.trousersnake;

import java.util.ArrayList;
import java.util.Random;
import psyche.androidapp.workcalcs.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Snake extends SurfaceView implements Runnable
{
	public static final int BORDER_SIZE = 75;
	public static final int MAX_SEGMENTS = 6;
	public static final int MAX_SPEED = 16;
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	
	private SurfaceHolder surfaceHolder;
	private Canvas canvas;
	private Thread thread = null;
	private boolean isRunning, isPaused, gameOver, winner = false;
	private Rect gameRect, upRect, downRect, leftRect, rightRect, headRect, tailRect, targetRect;
	private Rect exitRect;
	private Rect[] bodyRects;
	
	public static Bitmap head_up, tail_up, down_arrow, up_arrow, left_arrow, right_arrow,
					head_down, head_left, head_right, tail_down, tail_left, tail_right, body, target;
	
	private float head_x, head_y, tail_x, tail_y; 
	private int target_x, target_y;
	private float[][] body_xy;
	private int speed, score;
	
	public static BodyPart head, tail;
	public static BodyPart[] body_segments;
	
	public static ArrayList<Turn> tail_turns;
	public static ArrayList<Turn> body_0_turns;
	
	
	
	public Snake(Context context)
	{
		super(context);
		surfaceHolder = getHolder();
		loadBitmaps();
		initVariables();
	}
	
	private void initVariables()
	{
		score = 0;
		body_xy = new float[MAX_SEGMENTS][2];
		tail_turns = new ArrayList<Turn>();
		body_0_turns = new ArrayList<Turn>();
		head = new BodyPart(BodyPart.HEAD, RIGHT, null);
		tail = new BodyPart(BodyPart.TAIL, RIGHT, tail_turns);
		body_segments = new BodyPart[MAX_SEGMENTS];
		body_segments[0] = new BodyPart(BodyPart.BODY, RIGHT, body_0_turns);
		
		tail_x = BORDER_SIZE + 2;
		tail_y = BORDER_SIZE + 2;
		body_xy[0][0] = tail_x + tail_right.getWidth();
		body_xy[0][1] = tail_y + (tail_right.getHeight() / 2 - body.getHeight() / 2);
		head_x = body_xy[0][0] + body.getWidth();
		head_y = body_xy[0][1];
		bodyRects = new Rect[MAX_SEGMENTS];
		
		target_x = 500;
		target_y = 300;
		
		speed = 5;
		
	}
	
	private void loadBitmaps()
	{
		head_up = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_up);
		tail_up = BitmapFactory.decodeResource(getResources(), R.drawable.snake_tail_up);
		head_down = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_down);
		tail_down = BitmapFactory.decodeResource(getResources(), R.drawable.snake_tail_down);
		head_left = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_left);
		tail_left = BitmapFactory.decodeResource(getResources(), R.drawable.snake_tail_left);
		head_right = BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_right);
		tail_right = BitmapFactory.decodeResource(getResources(), R.drawable.snake_tail_right);
		body = BitmapFactory.decodeResource(getResources(), R.drawable.body);
		down_arrow = BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow_40x40);
		up_arrow = BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow_40x40);
		left_arrow = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow_40x40);
		right_arrow = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow_40x40);
		target = BitmapFactory.decodeResource(getResources(), R.drawable.target);
		
	}
	
	
	
	public void update()
	{
		//head
		updateHeadPosition();
		//tail
		if(!tail_turns.isEmpty())
		{
			Turn t = tail_turns.get(0);
			Rect r = new Rect(tailRect.left + 5, tailRect.top + 5, tailRect.right - 5, tailRect.bottom - 5);
			if(r.contains((int) t.getx(), (int) t.gety()))
			{
				int dir = t.getDir();
				switch(dir)
				{
				case UP:
					tail_x = head_x - (tailRect.width() / 2 - headRect.width() / 2 );
					tail_y = head_y + headRect.height() + bodyRects[0].height();
					tail.setDirection(dir);
					tail.setImage(BodyPart.TAIL);
					break;
				case DOWN:
					tail_x = head_x - (tailRect.width() / 2 - headRect.width() / 2 );
					tail_y = head_y - bodyRects[0].height() - tailRect.height();
					tail.setDirection(dir);
					tail.setImage(BodyPart.TAIL);
					break;
				case LEFT:
					tail_x = head_x + headRect.width() + bodyRects[0].width();
					tail_y = head_y - (tailRect.width() / 2 - headRect.width() / 2 );
					tail.setDirection(dir);
					tail.setImage(BodyPart.TAIL);
					break;
				case RIGHT:
					tail_x = head_x - bodyRects[0].width() - tailRect.width();
					tail_y = head_y - (tailRect.width() / 2 - headRect.width() / 2 );
					tail.setDirection(dir);
					tail.setImage(BodyPart.TAIL);
					break;
				}
				
				tail_turns.remove(0);
			}
			else
			{
				updateTailPosition();
			}
		}
		else
		{
			updateTailPosition();
		}
		
		//bodies 
		if(bodyRects[0] != null)
		{				
			if(!body_0_turns.isEmpty())
			{
				Turn t = body_0_turns.get(0);
				if(bodyRects[0].contains((int)t.getx(), (int)t.gety()))
				{
					int dir = t.getDir();
					switch(dir)
					{
					case UP:
						body_xy[0][0] = head_x;
						body_xy[0][1] = head_y + bodyRects[0].height();
						break;
					case DOWN:
						body_xy[0][0] = head_x;
						body_xy[0][1] = head_y - bodyRects[0].height();
						break;
					case LEFT:
						body_xy[0][0] = head_x + bodyRects[0].width();
						body_xy[0][1] = head_y;
						break;
					case RIGHT:
						body_xy[0][0] = head_x - bodyRects[0].width();
						body_xy[0][1] = head_y;
						break;
					}
											
					body_segments[0].setDirection(t.getDir());
					body_0_turns.remove(0);
				}
				else
				{
					updateBodyPosition(0);
				}
			}
			else
			{
				updateBodyPosition(0);
			}
		}
		
		
		if(targetCollision())
			collectTarget();
		
		if(checkBoundaries())
			gameOver = true;
	
	}

	private void draw()
	{
		headRect = new Rect((int)head_x, (int)head_y, (int)head_x + head_up.getWidth(),(int) head_y + head_up.getHeight());
		tailRect = new Rect((int)tail_x, (int)tail_y,(int) tail_x + tail_up.getWidth(), (int)tail_y + tail_up.getHeight());
		
		targetRect = new Rect(target_x, target_y, target_x + head_up.getWidth(), target_y + head_up.getHeight());
		
		bodyRects[0] = new Rect((int)body_xy[0][0], (int)body_xy[0][1], (int)body_xy[0][0] + body.getWidth(), (int)body_xy[0][1] + body.getHeight());
				
		canvas.drawBitmap(head.getImage(), null, headRect, null);
		canvas.drawBitmap(tail.getImage(), null, tailRect, null);
		canvas.drawBitmap(body_segments[0].getImage(),  null, bodyRects[0], null );
		canvas.drawBitmap(target, null, targetRect, null);
				
	}
	
	private void drawGameOverScreen()
	{
		canvas.drawRGB(255, 255, 255);
		
		drawGameOverButtons();
		
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	
	private void drawScreen()
	{
		//draw constant screen features before game stuff
		canvas.drawRGB(255, 255, 255);
		
		canvas.drawText("" + score, 25, 30, TrouserSnake.game_scorePaint);
		
		gameRect = new Rect(BORDER_SIZE, 0, canvas.getWidth() - BORDER_SIZE, canvas.getHeight());
		canvas.drawRect(gameRect, TrouserSnake.blackPaint);
		
		drawGameButtons();
		draw();
		
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	public void pause()
	{
		isRunning = false;
		isPaused = true;
		
		while(isPaused)
		{
			try
			{
				thread.join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}
	
	public void resume()
	{
		isRunning = true;
		isPaused = false;
		thread = new Thread(this);
		thread.start();
	}
	
	

	@Override
	public void run() 
	{
		while(isRunning)
		{
			if(!surfaceHolder.getSurface().isValid())
				continue;
			
			canvas = surfaceHolder.lockCanvas();
			if(gameOver)
			{
				drawGameOverScreen();
			}
			else if(winner)
			{
				drawWinScreen();
			}
			else
			{
				drawScreen();
				update();
			}
			
		}
		
	}
	
	private void collectTarget()
	{
		score++;
		if(score == 100)
		{
			winner = true;
		}
		setSpeed();
		
		Random rand = new Random();
		target_x = BORDER_SIZE + rand.nextInt(gameRect.width() - BORDER_SIZE - head_up.getWidth());
		target_y = rand.nextInt(gameRect.height() - head_up.getHeight());

	}
	
	private void setSpeed()
	{
		if(score <= 10)
			speed = 5;
		else if(score <= 20)
			speed = 7;
		else if(score <= 30)
			speed = 9;
		else if(score <= 40)
			speed = 11;
		else if(score <= 50)
			speed = 12;
		else if(score <= 60)
			speed = 13;
		else if(score <= 70)
			speed = 14;
		else if(score > 90)
			speed = 15;
	}
	
	private void drawWinScreen()
	{
		canvas.drawRGB(255, 255, 255);
		
		drawWinButtons();
		
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	private void drawWinButtons()
	{
		int x = canvas.getWidth() / 2;
		int y = 3 * canvas.getHeight() / 5;
		
		exitRect = new Rect(x - 200, y, x + 200, y + 100);
		String exit_text = "Exit to Menu";
		canvas.drawText(exit_text, x, y + 50, TrouserSnake.go_scorePaint);
		
		
		String win_text = "WINNER!";
		y = canvas.getHeight() / 5;
		canvas.drawText(win_text, x, y, TrouserSnake.win_titlePaint);
		
		String score_text = "Score: " + score;
		y = canvas.getHeight() / 2;
		canvas.drawText(score_text, x, y, TrouserSnake.go_scorePaint);
	}
	
	
	private void drawGameButtons()
	{
		leftRect = new Rect(15, canvas.getHeight() / 2 + 50, 55, canvas.getHeight() / 2 + 90);
		upRect = new Rect(15, canvas.getHeight() / 2 - 90, 55 , canvas.getHeight() / 2 - 50);
		rightRect = new Rect(canvas.getWidth() - 55, canvas.getHeight() / 2 + 50, canvas.getWidth() - 15, canvas.getHeight() / 2 + 90);
		downRect = new Rect(canvas.getWidth() - 55, canvas.getHeight() / 2 - 90, canvas.getWidth() - 15 , canvas.getHeight() / 2 - 50);
		
		canvas.drawBitmap(up_arrow, null, upRect, null);
		canvas.drawBitmap(left_arrow, null, leftRect, null);
		canvas.drawBitmap(down_arrow, null, downRect, null);
		canvas.drawBitmap(right_arrow, null, rightRect, null);
		
	}
	
	private void drawGameOverButtons()
	{
		int x = canvas.getWidth() / 2;
		int y = 3 * canvas.getHeight() / 5;
		
		exitRect = new Rect(x - 200, y, x + 200, y + 100);
		String exit_text = "Exit to Menu";
		canvas.drawText(exit_text, x, y + 50, TrouserSnake.go_scorePaint);
		
		
		String go_text = "GAME OVER";
		y = canvas.getHeight() / 5;
		canvas.drawText(go_text, x, y, TrouserSnake.go_titlePaint);
		
		String score_text = "Score: " + score;
		y = canvas.getHeight() / 2;
		canvas.drawText(score_text, x, y, TrouserSnake.go_scorePaint);
	}
	
	
	
	private boolean checkBoundaries()
	{
		boolean boundary = false;
		if(head_x > gameRect.right - head_up.getWidth() ||
				head_x < gameRect.left ||
				head_y > gameRect.bottom - head_up.getHeight() ||
				head_y < gameRect.top)
		{
			boundary = true;
		}
		return boundary;
	}
	
	
	private boolean targetCollision()
	{
		boolean collided = false;
		if(targetRect.contains(headRect.left, headRect.top) ||
				targetRect.contains(headRect.right, headRect.top) ||
					targetRect.contains(headRect.right, headRect.bottom) ||
						targetRect.contains(headRect.left, headRect.bottom))
		{
			collided = true;
		}
			
		return collided;
	}
	
	
	private void updateBodyPosition(int index)
	{
	
		if(body_segments[index].getDirection() == RIGHT)
		{
			body_xy[0][0] += speed;
		}
		else if(body_segments[index].getDirection() == UP)
		{
			body_xy[0][1] -= speed;
		}
		else if(body_segments[index].getDirection() == LEFT)
		{
			body_xy[0][0] -= speed;
		}
		else if(body_segments[index].getDirection() == DOWN)
		{
			body_xy[0][1] += speed;
		}
		
	}
	
	private void updateHeadPosition()
	{
		if(head.getDirection() == UP)
		{
			head_y -= speed;
			
		}
		else if(head.getDirection() == DOWN)
		{
			head_y += speed;
		}
		else if(head.getDirection() == LEFT)
		{
			head_x -= speed;
		}
		else if(head.getDirection() == RIGHT)
		{
			head_x += speed;
		}
	}

	private void updateTailPosition()
	{
		if(tail.getDirection() == UP)
		{
			tail_y -= speed;
		}
		else if(tail.getDirection() == DOWN)
		{
			tail_y += speed;
		}
		else if(tail.getDirection() == LEFT)
		{
			tail_x -= speed;
		}
		else if(tail.getDirection() == RIGHT)
		{
			tail_x += speed;
		}
	}
	
	public Rect getUpRect() { return upRect; }
	public Rect getDownRect() { return downRect; }
	public Rect getLeftRect() { return leftRect; }
	public Rect getRightRect() { return rightRect; }
	public Rect getHeadRect() { return headRect; }
	public Rect getExitRect() { return exitRect; }
	
	public void setGameOver(boolean b) { gameOver = b; }
	public boolean getGameOver() { return gameOver; }
	public void setWinner(boolean b) { winner = b; }
	public boolean getWinner() { return winner; }
	
	
	
}
