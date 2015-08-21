package psyche.androidapp.workcalcs.trousersnake;

public class Turn 
{
	private float x, y;
	private int direction;
	
	public Turn(float x, float y, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public float getx() { return x; }
	public float gety() { return y; }
	public int getDir() { return direction; }
}
