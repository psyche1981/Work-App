package psyche.androidapp.workcalcs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static psyche.androidapp.workcalcs.Calcs.*; 

public class Adjust extends Activity implements View.OnClickListener
{
	private Button button;
	private EditText curFat, curVol, intFat, adjFat;
	private TextView results;
	private float cFat, cVol, iFat, aFat;
	
	
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.adjust_silo);
		initialise();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater menInf = getMenuInflater();
		menInf.inflate(R.menu.popup_adjust, menu);
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.popup_adjust_info:
			Intent i = new Intent("psyche.androidapp.workcalcs.ADJUSTINFO");
			startActivity(i);
			break;
		}
		return false;
	}



	private void initialise()
	{
		button = (Button)findViewById(R.id.bAdjust);
		curFat = (EditText)findViewById(R.id.etCurFat);
		curVol = (EditText)findViewById(R.id.etCurVol);
		intFat = (EditText)findViewById(R.id.etIntFat);
		adjFat = (EditText)findViewById(R.id.etAdjFat);
		results = (TextView)findViewById(R.id.tvAdjRes);
		
		button.setOnClickListener(this);
		
		
	}
	
	private void initValues()
	{
		try
		{
			cFat = Float.valueOf(curFat.getText().toString());
			cVol = Float.valueOf(curVol.getText().toString());
			iFat = Float.valueOf(intFat.getText().toString());
			aFat = Float.valueOf(adjFat.getText().toString());
		}
		catch(Exception e)
		{
			cFat = 0;
			cVol = 0;
			iFat = 0;
			aFat = 0;
		}
	}

	public void onClick(View v) 
	{
		initValues();
		results.setTextSize(30);
		if(cFat == 0 && cVol == 0 &&
				iFat == 0 && aFat == 0)
		{
			results.setText("Please fill in all boxes");
		}
		else
		{
			
			if(cFat < iFat)
				results.setTextColor(Color.BLUE);
			else
				results.setTextColor(Color.RED);
			
			results.setText("" + adjustFatString(cFat, cVol, iFat, aFat));
		}
		
	}
	
}
