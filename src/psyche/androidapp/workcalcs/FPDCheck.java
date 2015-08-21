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
import static psyche.androidapp.workcalcs.Utilities.*;




public class FPDCheck extends Activity implements View.OnClickListener
{
	private Button bGetFPD;
	private EditText etFpd1, etFpd2, etFpd3, etVol1, etVol2, etVol3;
	private TextView tvResults;
	private float fpds[] = new float[3];
	private float vols[] = new float[3];
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.fpd_check);
		initialise();
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater menInf = getMenuInflater();
		menInf.inflate(R.menu.popup_fpdcheck, menu);
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.popup_fpd_info:
			Intent i = new Intent("psyche.androidapp.workcalcs.FPDINFO");
			startActivity(i);
			break;
		}
		return false;
	}



	private void initialise()
	{
		bGetFPD = (Button)findViewById(R.id.bGetFPD);
		etFpd1 = (EditText)findViewById(R.id.etFPD1);
		etFpd2 = (EditText)findViewById(R.id.etFPD2);
		etFpd3 = (EditText)findViewById(R.id.etFPD3);
		etVol1 = (EditText)findViewById(R.id.etVol1);
		etVol2 = (EditText)findViewById(R.id.etVol2);
		etVol3 = (EditText)findViewById(R.id.etVol3);
		tvResults = (TextView)findViewById(R.id.tvFPDRes);
		
		bGetFPD.setOnClickListener(this);
		
	}
	
	private void initFloatArrays()
	{
		fpds[0] = Float.valueOf(etFpd1.getText().toString());
		fpds[1] = Float.valueOf(etFpd2.getText().toString());
		fpds[2] = Float.valueOf(etFpd3.getText().toString());
		vols[0] = Float.valueOf(etVol1.getText().toString());
		vols[1] = Float.valueOf(etVol2.getText().toString());
		vols[2] = Float.valueOf(etVol3.getText().toString());
		
	}

	public void onClick(View v) 
	{
		initFloatArrays();
		tvResults.setTextColor(Color.RED);
		tvResults.setTextSize(30);
		tvResults.setText("The FPD is " + Utilities.toString2dp(fpd(fpds, vols)) + '\n' +
							"The Volume is " + toInt(sumArray(vols)));
	}
	
	

}
