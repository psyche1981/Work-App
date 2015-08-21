package psyche.androidapp.workcalcs;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AppSplash extends Activity implements Runnable
{
	private  Thread thread = null;
	private MediaPlayer moo;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.app_splash);
		initActivity();
		
		thread.start();
	}
	
	private void initActivity()
	{
		moo = MediaPlayer.create(AppSplash.this, R.raw.cow_single_cow_mooing);
		thread = new Thread(this);
	}

	public void run() 
	{
		try
		{
			Thread.sleep(1250);
			moo.start();
			Thread.sleep(2000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Intent i = new Intent("psyche.androidapp.workcalcs.APPSPLASH2");
			startActivity(i);
		}
		
	}
	
	protected void onPause()
	{
		super.onPause();
		moo.release();
		finish();
	}

}
