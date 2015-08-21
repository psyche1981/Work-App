package psyche.androidapp.workcalcs;
//TODO improve the overall look of this activity
//TODO add more photos
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class ShameGallery extends Activity implements OnTouchListener
{

	private ImageButton shame_image;
	private int current_image;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.shame_gallery);
		initActivity();
	}
	
	private void initActivity()
	{
		current_image = 0;
		shame_image = (ImageButton) findViewById(R.id.ib_shame);
		shame_image.setOnTouchListener(this);
	}
	
	private int getCurrentImage()
	{
		current_image++;
		if(current_image > 2)
			current_image = 0;
				
		return current_image;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			int image = getCurrentImage();			
			if(image == 0)
				shame_image.setImageResource(R.drawable.speedy_02);
			else if(image == 1)
				shame_image.setImageResource(R.drawable.gav_w_cropped);
			else if(image == 2)
				shame_image.setImageResource(R.drawable.speedy_cropped);
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater menInf = getMenuInflater();
		menInf.inflate(R.menu.popup_shame, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.popup_shame_info:
			Intent i = new Intent("psyche.androidapp.workcalcs.SHAMEINFO");
			startActivity(i);
			break;
		}
		return false;
	}
	
	
	

}
