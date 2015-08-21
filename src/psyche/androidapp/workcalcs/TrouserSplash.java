package psyche.androidapp.workcalcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TrouserSplash extends Activity implements OnClickListener
{
	private Button bContinue;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.trouser_splash);
		initActivity();
	}
	
	private void initActivity()
	{
		bContinue = (Button) findViewById(R.id.bTrouserContinue);
		bContinue.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bTrouserContinue:
			Intent i = new Intent("psyche.androidapp.workcalcs.trousersnake.TROUSERSNAKE");
			startActivity(i);
			break;
		}
		
	}
	@Override
	public void onPause()
	{
		super.onPause();
		finish();
	}

	

}
