package psyche.androidapp.workcalcs;


import static psyche.androidapp.workcalcs.Utilities.*;
import static psyche.androidapp.workcalcs.STD.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MakeSTD extends Activity implements OnClickListener
{
	private TabHost tHost;
	private TabSpec tSpec;
	
	private Button bMakeSTD;
	private RadioButton rbForty, rbFifty, rbSixty, rbCustom, rbOnSkim, rbOnFCM;
	private EditText etR1F, etR1V, etR2F, etI1F, etI1V, etI2F, etI2V, etCustom, etSkimVol;
	
	
	private float siloVol;
	private float raw1Fat;
	private float raw1Vol;
	private float[] rawFats = {0,0};
	private float[] intFats = {0,0};
	private float[] intVols = {0,0};
	private int skimVol;
	
	private float intPercentage;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.make_std);
		initActivity();
		initTabs();
	}
	
	private void initActivity()
	{
		tHost = (TabHost)findViewById(R.id.tabhost_std);
		
		rbForty = (RadioButton)findViewById(R.id.rbSTD40k);
		rbFifty = (RadioButton)findViewById(R.id.rbSTD50k);
		rbSixty = (RadioButton)findViewById(R.id.rbSTD60k);
		rbCustom = (RadioButton)findViewById(R.id.rbSTDCustom);
		rbOnSkim = (RadioButton)findViewById(R.id.rbSTDOnSkim);
		rbOnFCM = (RadioButton)findViewById(R.id.rbSTDOnFCM);
		
		etR1F = (EditText)findViewById(R.id.etSTDRaw1Fat);
		etR1V = (EditText)findViewById(R.id.etSTDRaw1Vol);
		etR2F = (EditText)findViewById(R.id.etSTDRaw2Fat);
		etI1F = (EditText)findViewById(R.id.etSTDInt1Fat);
		etI1V = (EditText)findViewById(R.id.etSTDInt1Vol);
		etI2F = (EditText)findViewById(R.id.etSTDInt2Fat);
		etI2V = (EditText)findViewById(R.id.etSTDInt2Vol);
		etCustom = (EditText)findViewById(R.id.etSTDCustom);
		etSkimVol = (EditText)findViewById(R.id.etSTDSkimVol);
		
		bMakeSTD = (Button)findViewById(R.id.bMakeSTD);
		bMakeSTD.setOnClickListener(this);
	}
	
	private void initTabs()
	{
		tHost.setup();
		//tab 1
		tSpec = tHost.newTabSpec("tab 1");
		tSpec.setContent(R.id.std_tab_2);//switched the order of the tabs.
		tSpec.setIndicator("Page 1");
		tHost.addTab(tSpec);
		//tab 2
		tSpec = tHost.newTabSpec("tab 2");
		tSpec.setContent(R.id.std_tab_1);
		tSpec.setIndicator("Page 2");
		tHost.addTab(tSpec);
		
	}
	
	private void initVariables()
	{
		//silo volume
		if(rbForty.isChecked())
			siloVol = 40000;
		else if(rbFifty.isChecked())
			siloVol = 50000;
		else if(rbSixty.isChecked())
			siloVol = 60000;
		else if(rbCustom.isChecked())
			siloVol = Float.valueOf(etCustom.getText().toString());
		
		//raw milk data
		String s;
		s = etR2F.getText().toString();
		if(s.equals(""))
		{
			//1 raw
			s = etR1F.getText().toString();
			if(s.equals(""))
				raw1Fat = 0;
			else
				raw1Fat = Float.valueOf(s);
		}
		else
		{
			//2 raw - populate array
			s = etR1F.getText().toString();
			if(s.equals(""))
				rawFats[0] = 0;
			else
				rawFats[0] = Float.valueOf(s);
			
			s = etR2F.getText().toString();
			if(s.equals(""))
				rawFats[1] = 0;
			else
				rawFats[1] = Float.valueOf(s);
			
			s = etR1V.getText().toString();
			if(s.equals(""))
				raw1Vol = 0;
			else
				raw1Vol = Float.valueOf(s);
		}
		
		//int fats
		s = etI1F.getText().toString();
		if(s.equals(""))
				intFats[0] = 0;
		else
			intFats[0] = Float.valueOf(s);
		
		s = etI2F.getText().toString();
		if(s.equals(""))
				intFats[1] = 0;
		else
			intFats[1] = Float.valueOf(s);
		
		// int vols
		s = etI1V.getText().toString();
		if(s.equals(""))
			intVols[0] = 0;
		else
			intVols[0] = Float.valueOf(s);
		
		s = etI2V.getText().toString();
		if(s.equals(""))
			intVols[1] = 0;
		else
			intVols[1] = Float.valueOf(s);
		
		//int percentage
		if(intVols[0] != 0)
			intPercentage = (sumArray(intVols) / siloVol) * 100;
		else
			intPercentage = 0;
		
		//start  of silo skim volume
		s = etSkimVol.getText().toString();
		if(rbOnSkim.isChecked())
		{
			if(s.equals(""))
				skimVol = 0;
			else
				skimVol = Integer.valueOf(s);
		}
		else if(rbOnFCM.isChecked())
			skimVol = 0;
		
	}
	
	private void resetVariables()
	{
		etR1F.setText("");
		etR1V.setText("");
		etR2F.setText("");
		etI1F.setText("");
		etI1V.setText("");
		etI2F.setText("");
		etI2V.setText("");
		etCustom.setText("");
		etSkimVol.setText("");
		
		siloVol = 0;
		raw1Fat = 0;
		raw1Vol = 0;
		rawFats[0] = 0;
		rawFats[1] = 0;
		intFats[0] = 0;
		intFats[1] = 0;
		intVols[0] = 0;
		intVols[1] = 0;
		intPercentage = 0;
		skimVol = 0;
	}
	
	private int makeSTD()
	{
		initVariables();
		int rawRequired = 0;
		
		if(rawFats[0] == 0)//1 raw
		{
			if(raw1Fat == 0)
				return 0;
			if(intFats[0] == 0)
				rawRequired = (int) newSTD(siloVol, raw1Fat);
			else
				rawRequired = (int) newSTD(siloVol, raw1Fat, intFats, intVols);
		}
		else// 2 raw
		{
			if(intFats[0] == 0)
				rawRequired = (int) newSTD(siloVol, rawFats, raw1Vol);
			else
				rawRequired = (int) newSTD(siloVol, rawFats, raw1Vol, intFats, intVols);
		}
			
		return rawRequired;
	}

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bMakeSTD:
			
			int fcmRequired = makeSTD();
			
			String intMsg = "";
			if(intPercentage > 10)
				intMsg = "The amount of interface in this silo is > 10%. Is that correct?\n";		
			
			String rawVolText = Integer.toString(fcmRequired) + " litres\n\n";
			String rawMsg = "The total raw milk required is: \n";
			String handlesMsg = "Change the seps on to skim when the flow meter reads:\n";
			String warning = "";
			int handles;
			if(rbOnFCM.isChecked())
				handles = fcmRequired + (int)sumArray(intVols) - 1200;
			else
				handles = fcmRequired + (int)sumArray(intVols) + skimVol;
			
			String handlesVol = Integer.toString(handles) + " litres\n";
			
			if(handles + 1200 > siloVol)
				warning = "\nThere is too much interface. You will have to go over the intended silo volume to get the correct fat percentage.";
			
			
			Bundle b = new Bundle();
			b.putString("raw vol text", rawVolText);
			b.putString("raw msg", rawMsg);
			b.putString("int msg", intMsg);
			b.putString("handles msg", handlesMsg);
			b.putString("handles vol", handlesVol);
			b.putString("warning", warning);
			
			//bundle messages
			Intent i = new Intent(this, STDResults.class);
			i.putExtras(b);
			resetVariables();
			startActivity(i);
			break;
		}
	}

}
