package psyche.androidapp.workcalcs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class STDResults extends Activity implements OnClickListener
{
	private Button bBack;
	private TextView tvResults;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.std_results);
		initActivity();
		processBundle();
	}
	
	private void initActivity()
	{
		tvResults = (TextView)findViewById(R.id.tvSTDResults);
		bBack = (Button)findViewById(R.id.bSTDResBack);
		bBack.setOnClickListener(this);
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.bSTDResBack:
			finish();
			break;
		}
	}
	
	private void processBundle()
	{
		Bundle extras = getIntent().getExtras();
		//get each message
		String rawVolText = extras.getString("raw vol text");
		String rawMsg = extras.getString("raw msg");
		String intMsg = extras.getString("int msg");
		String handlesMsg = extras.getString("handles msg");
		String handlesVol = extras.getString("handles vol");
		String warning = extras.getString("warning");
		tvResults.setText(rawMsg + rawVolText + handlesMsg+ handlesVol + intMsg + warning);
	}

}
