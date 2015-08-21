package psyche.androidapp.workcalcs;

import static psyche.androidapp.workcalcs.Utilities.*;
import static psyche.androidapp.workcalcs.Semi.*;
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

public class MakeSemi extends Activity implements OnClickListener
{
	private TabHost tabHost;
	private TabSpec tabSpec;
	
	private Button bMakeSemi;
	private RadioButton rbForty, rbFifty, rbSixty, rbCustom, rbOneHandle, rbTwoHandles;
	private EditText etCustom, etR1Fat, etR2Fat, etR1Vol, etInt1Fat, etInt1Vol, etInt2Vol, etInt2Fat, etSkimVol;
	
	private int siloVol;
	private float raw1Fat;
	private float raw1Vol;
	private float[] rawFats = {0,0};
	private float[] intFats = {0,0};
	private float[] intVols = {0,0};
	private float intPercentage;
	private float skimVol;
	
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.make_semi);
		initActivity();
		initTabs();
		
	}
	
	private void initActivity()
	{
		tabHost = (TabHost)findViewById(R.id.tabhost_semi);
		rbForty = (RadioButton)findViewById(R.id.rbSemi40k);
		rbFifty = (RadioButton)findViewById(R.id.rbSemi50k);
		rbSixty = (RadioButton)findViewById(R.id.rbSemi60k);
		rbCustom = (RadioButton)findViewById(R.id.rbSemiCustom);
		rbOneHandle = (RadioButton)findViewById(R.id.rbSemiOneHandle);
		rbTwoHandles = (RadioButton)findViewById(R.id.rbSemiTwoHandles);
		
		etCustom = (EditText)findViewById(R.id.etSemiCustom);
		etR1Fat = (EditText)findViewById(R.id.etSemiR1Fat);
		etR2Fat = (EditText)findViewById(R.id.etSemiR2Fat);
		etR1Vol = (EditText)findViewById(R.id.etSemiR1Vol);
		etInt1Fat = (EditText)findViewById(R.id.etSemiInt1Fat);
		etInt1Vol = (EditText)findViewById(R.id.etSemiInt1Vol);
		etInt2Fat = (EditText)findViewById(R.id.etSemiInt2Fat);
		etInt2Vol = (EditText)findViewById(R.id.etSemiInt2Vol);
		etSkimVol = (EditText)findViewById(R.id.etSemiHandle);
		
		bMakeSemi = (Button)findViewById(R.id.bMakeSemi);
		
		bMakeSemi.setOnClickListener(this);
		
	}
	
	private void initTabs()
	{
		tabHost.setup();
		//tab 1
		tabSpec = tabHost.newTabSpec("tabspec1");
		tabSpec.setContent(R.id.tab1Semi);
		tabSpec.setIndicator("Main Input");
		tabHost.addTab(tabSpec);
		//tab 2
		tabSpec = tabHost.newTabSpec("tabspec2");
		tabSpec.setContent(R.id.tab2Semi);
		tabSpec.setIndicator("Sep Config.");
		tabHost.addTab(tabSpec);
		
	}
	
	private void initVariables()
	{
		String s;
		//Silo volume
		if(rbForty.isChecked())
			siloVol = 40000;
		if(rbFifty.isChecked())
			siloVol = 50000;
		if(rbSixty.isChecked())
			siloVol = 60000;
		if(rbCustom.isChecked())
			siloVol = Integer.valueOf(etCustom.getText().toString());
		// raw fat percentage
		s = etR2Fat.getText().toString(); 
		if(s.equals(""))
		{
			//check to see if field is empty to prevent crash
			s = etR1Fat.getText().toString();
			if(s.equals(""))
				raw1Fat = 0;
			else
				raw1Fat = Float.valueOf(s);
		}
		else
		{
			s = etR1Fat.getText().toString();
			if(s.equals(""))
				rawFats[0] = 0;
			else
				rawFats[0] = Float.valueOf(s);
				
			s = etR2Fat.getText().toString();
			if(s.equals(""))
				rawFats[1] = 0;
			else
				rawFats[1] = Float.valueOf(s);
			
			s = etR1Vol.getText().toString();
			if(s.equals(""))
				raw1Vol = 0;
			else
				raw1Vol = Float.valueOf(s);
		}
		//int fats
		s = etInt1Fat.getText().toString(); 
		if(s.equals(""))
			intFats[0] = 0;
		else
			intFats[0] = Float.valueOf(s);
		
		s = etInt2Fat.getText().toString(); 
		if(s.equals(""))
			intFats[1] = 0;
		else
			intFats[1] = Float.valueOf(s);
		
		//int vols
		s = etInt1Vol.getText().toString(); 
		if(s.equals(""))
			intVols[0] = 0;
		else
			intVols[0] = Float.valueOf(s);
		
		s = etInt2Vol.getText().toString(); 
		if(s.equals(""))
			intVols[1] = 0;
		else
			intVols[1] = Float.valueOf(s);
		
		//interface percentage
		if(intVols[0] != 0)
			intPercentage = (sumArray(intVols) / siloVol) * 100;
		else
			intPercentage = 0;
		
		//tab 2 skim vol
		s = etSkimVol.getText().toString();
		if(rbTwoHandles.isChecked())
		{
			if(s.equals(""))
				skimVol = 0;
			else
				skimVol = Float.valueOf(s);
		}
		else
			skimVol = 0;
		
		
	}
	
	private int makeNewSemi()
	{
		initVariables();
		int rawRequired;
		
		if(rawFats[0] == 0)//1 raw silo
		{
			if(raw1Fat == 0)
				return 0; 
			if(intVols[0] == 0)
				rawRequired = (int)newSemi(siloVol, raw1Fat);
			else
				rawRequired = (int)newSemi(siloVol, raw1Fat, intFats, intVols);
		}
		else // 2 raw silos
		{
			if(intVols[0] == 0)
				rawRequired = (int)newSemi(siloVol, rawFats, raw1Vol);
			else
				rawRequired = (int)newSemi(siloVol, rawFats, raw1Vol, intFats, intVols);
		}
		
		return rawRequired;
	}
	
	private void resetVariables()
	{
		etR1Fat.setText("");
		etR1Vol.setText("");
		etR2Fat.setText("");
		etInt1Fat.setText("");
		etInt1Vol.setText("");
		etInt2Fat.setText("");
		etInt2Vol.setText("");
		etCustom.setText("");
		etSkimVol.setText("");
		
		siloVol = 0;
		intFats[0] = 0;
		intFats[1] = 0;
		raw1Fat = 0;
		raw1Vol = 0;
		rawFats[0] = 0;
		rawFats[1] = 0;
		intVols[0] = 0;
		intVols[1] = 0;
		intPercentage = 0;
		skimVol = 0;
	}
			

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bMakeSemi:
			int raw = makeNewSemi();
			boolean oneHandle = rbOneHandle.isChecked();
			boolean intPct = false;
			if(intPercentage > 10)
				intPct = true;
			
			int flowMeterReading = 0;
			String handleMsg = "";
			String intWarning = "";
			
			if(intPct)
				intWarning = "The amount of interface in this silo is > 10%. Is that correct?\n";
			
			if(oneHandle)
			{
				flowMeterReading = raw + (int)sumArray(intVols);
				handleMsg = "Turn the handle back to skim at: \n";
			}
			else
			{
				if(skimVol == 0)
					flowMeterReading = raw + (int)sumArray(intVols) - 1200;
				else
					flowMeterReading = raw + (int)sumArray(intVols) + (int)skimVol;
				
				handleMsg = "Turn the handles back to skim at: \n";
			}
			
			
			String rawText = Integer.toString(raw) + " litres \n\n";
			String totVol = Integer.toString(flowMeterReading) + " litres \n\n";
			String rawMsg = "The total raw milk required is: \n";
			Bundle bundle = new Bundle();
			bundle.putString("raw msg", rawMsg);
			bundle.putString("handle msg", handleMsg);
			bundle.putString("raw required", rawText);
			bundle.putString("total vol", totVol);
			bundle.putString("intWarning", intWarning);
			Intent i = new Intent(this, SemiResults.class);
			i.putExtras(bundle);
			resetVariables();
			startActivity(i);
			break;
		}
	}

}
