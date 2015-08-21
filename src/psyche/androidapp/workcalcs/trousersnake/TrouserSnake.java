package psyche.androidapp.workcalcs.trousersnake;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TrouserSnake extends Activity implements OnTouchListener
{
	public static Snake snake;
	private Typeface sin_font, unit_font;
	public static Paint go_titlePaint, go_scorePaint, blackPaint, redPaint, win_titlePaint, game_scorePaint;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		snake = new Snake(this);
		setContentView(snake);
		initActivity();
		initPaints();
	}
	
	private void initActivity()
	{
		snake.setOnTouchListener(this);
		
		sin_font = Typeface.createFromAsset(getAssets(), "Sin City.ttf");
		unit_font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
		
		
	}
	
	private void initPaints()
	{
		go_scorePaint = new Paint();
		go_scorePaint.setColor(Color.BLACK);
		go_scorePaint.setTextSize(40);
		go_scorePaint.setTextAlign(Align.CENTER);
		go_scorePaint.setTypeface(unit_font);
		
		game_scorePaint = new Paint();
		game_scorePaint.setColor(Color.BLACK);
		game_scorePaint.setTextSize(25);
		game_scorePaint.setTextAlign(Align.CENTER);
		game_scorePaint.setTypeface(unit_font);
		
		go_titlePaint = new Paint();
		go_titlePaint.setColor(Color.RED);
		go_titlePaint.setTextSize(65);
		go_titlePaint.setTextAlign(Align.CENTER);
		go_titlePaint.setTypeface(sin_font);
		
		win_titlePaint = new Paint();
		win_titlePaint.setColor(Color.BLUE);
		win_titlePaint.setTextSize(65);
		win_titlePaint.setTextAlign(Align.CENTER);
		win_titlePaint.setTypeface(unit_font);
		
		blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);
		blackPaint.setStyle(Style.STROKE);
		
		redPaint = new Paint();
		redPaint.setColor(Color.RED);
		redPaint.setStyle(Style.STROKE);
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		Rect r_up = new Rect(snake.getUpRect().left - 10, snake.getUpRect().top - 10, snake.getUpRect().right + 10, snake.getUpRect().bottom + 10);
		Rect r_down = new Rect(snake.getDownRect().left - 10, snake.getDownRect().top - 10, snake.getDownRect().right + 10, snake.getDownRect().bottom + 10);
		Rect r_left = new Rect(snake.getLeftRect().left - 10, snake.getLeftRect().top - 10, snake.getLeftRect().right + 10, snake.getLeftRect().bottom + 10);
		Rect r_right = new Rect(snake.getRightRect().left - 10, snake.getRightRect().top - 10, snake.getRightRect().right + 10, snake.getRightRect().bottom + 10);
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		if(snake.getGameOver() || snake.getWinner())
		{
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				if(snake.getExitRect().contains(x, y))
				{
					finish();
				}
				break;
			}
		}
		else
		{
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				if(r_up.contains(x, y))
				{
					if(Snake.head.getDirection() == Snake.DOWN)
						snake.setGameOver(true);
					
					Snake.head.setDirection(Snake.UP);
					Snake.head.setImage(BodyPart.HEAD);
					Snake.tail_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.UP));
					Snake.body_0_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.UP));				
				}
				else if(r_down.contains(x, y))
				{
					if(Snake.head.getDirection() == Snake.UP)
						snake.setGameOver(true);
					
					Snake.head.setDirection(Snake.DOWN);
					Snake.head.setImage(BodyPart.HEAD);
					Snake.tail_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.DOWN));
					Snake.body_0_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.DOWN));
				}
				else if(r_left.contains(x, y))
				{
					if(Snake.head.getDirection() == Snake.RIGHT)
						snake.setGameOver(true);
					
					Snake.head.setDirection(Snake.LEFT);
					Snake.head.setImage(BodyPart.HEAD);
					Snake.tail_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.LEFT));
					Snake.body_0_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.LEFT));
				}
				else if(r_right.contains(x, y))
				{
					if(Snake.head.getDirection() == Snake.LEFT)
						snake.setGameOver(true);
					
					Snake.head.setDirection(Snake.RIGHT);
					Snake.head.setImage(BodyPart.HEAD);
					Snake.tail_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.RIGHT));
					Snake.body_0_turns.add(new Turn(snake.getHeadRect().exactCenterX(),snake.getHeadRect().exactCenterY(), Snake.RIGHT));
				}
				
				break;
			}
		}
		
		
		
		return false;
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		snake.pause();
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		snake.resume();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
