package psyche.androidapp.workcalcs;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SemiDisclaimer extends Activity implements OnClickListener
{
	private Button bContinue;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.semi_disclaimer);
		initActivity();
	}
	
	private void initActivity()
	{
		bContinue = (Button)findViewById(R.id.bSemiDiscCont);
		bContinue.setOnClickListener(this);
	}

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bSemiDiscCont:
			Intent i = new Intent("psyche.androidapp.workcalcs.MAKESEMI");
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
