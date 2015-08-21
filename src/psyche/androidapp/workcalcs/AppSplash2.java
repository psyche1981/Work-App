package psyche.androidapp.workcalcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AppSplash2 extends Activity implements Runnable
{
	private Thread thread = null;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.app_splash_2);
		initActivity();
		thread.start();
	}
	
	private void initActivity()
	{
		thread = new Thread(this);
	}

	@Override
	public void run() 
	{
		try
		{
			Thread.sleep(1800);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Intent i = new Intent("psyche.androidapp.workcalcs.MENU");
			startActivity(i);
		}
		
	}
	
	protected void onPause()
	{
		super.onPause();
		finish();
	}
}
