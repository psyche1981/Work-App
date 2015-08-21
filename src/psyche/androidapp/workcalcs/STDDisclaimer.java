package psyche.androidapp.workcalcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class STDDisclaimer extends Activity implements OnClickListener
{
	private Button bContinue;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.std_disclaimer);
		initActivity();
		
	}
	
	private void initActivity()
	{
		bContinue = (Button)findViewById(R.id.bSTDDiscContinue);
		bContinue.setOnClickListener(this);
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.bSTDDiscContinue:
			Intent i = new Intent("psyche.androidapp.workcalcs.MAKESTD");
			startActivity(i);
			break;
		}
	}
	
	protected void onPause()
	{
		super.onPause();
		finish();
	}

}
