package psyche.androidapp.workcalcs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SemiResults extends Activity implements OnClickListener
{
	private static TextView tvResults;
	private String rawMsg, rawVol, flowMsg, flowVol, intWarning;
	private Button bBack;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.semi_results);
		initActivity();
		processBundle();
	}
	
	private void initActivity()
	{
		tvResults = (TextView)findViewById(R.id.tvSemiResResults);
		bBack = (Button)findViewById(R.id.bSemiResBack);
		bBack.setOnClickListener(this);
		rawMsg = "";
		rawVol = "";
		flowMsg = "";
		flowVol = "";
		intWarning = "";
	}
	
	private void processBundle()
	{
		Bundle extras = getIntent().getExtras();
		rawMsg = extras.getString("raw msg");
		rawVol = extras.getString("raw required");
		flowMsg = extras.getString("handle msg");
		flowVol = extras.getString("total vol");
		intWarning = extras.getString("intWarning");
		tvResults.setText(rawMsg + rawVol + flowMsg + flowVol + intWarning);
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.bSemiResBack:
			finish();
			break;
		}
	}

}
