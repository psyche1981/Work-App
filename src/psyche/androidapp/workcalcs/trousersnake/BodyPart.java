package psyche.androidapp.workcalcs.trousersnake;

import java.util.ArrayList;
import android.graphics.Bitmap;



public class BodyPart 
{
	public static final int HEAD = 0;
	public static final int BODY = 1;
	public static final int TAIL = 2;
	
	private Bitmap bitmap;
	private int direction;
	private ArrayList<Turn> turns;
	
	public BodyPart(int type, int direction, ArrayList<Turn> turns)
	{
		this.direction = direction;
		
		if(type == HEAD)
		{
			turns = null;
			setImage(HEAD);
		}
		else if(type == BODY)
		{
			this.turns = turns;
			setImage(BODY);
		}
		else if(type == TAIL)
		{
			this.turns = turns;
			setImage(TAIL);
		}
	}
	
	
	public ArrayList<Turn> getTurns() { return turns; }
	public void setDirection(int dir) { direction = dir; }
	public int getDirection() { return direction; }
	public Bitmap getImage() { return bitmap; }
	public void setImage(int type)
	{
		if(type == HEAD)
		{
			if(direction == Snake.UP)
			{
				bitmap = Snake.head_up;
			}
			else if(direction == Snake.DOWN)
			{
				bitmap = Snake.head_down;
			}
			else if(direction == Snake.LEFT)
			{
				bitmap = Snake.head_left;
			}
			else if(direction == Snake.RIGHT)
			{
				bitmap = Snake.head_right;
			}
		}
		else if(type == BODY)
		{
			bitmap = Snake.body;
		}
		else if(type == TAIL)
		{
			if(direction == Snake.UP)
			{
				bitmap = Snake.tail_up;
			}
			else if(direction == Snake.DOWN)
			{
				bitmap = Snake.tail_down;
			}
			else if(direction == Snake.LEFT)
			{
				bitmap = Snake.tail_left;
			}
			else if(direction == Snake.RIGHT)
			{
				bitmap = Snake.tail_right;
			}
		}
	}

}