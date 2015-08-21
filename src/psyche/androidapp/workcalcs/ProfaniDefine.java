package psyche.androidapp.workcalcs;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProfaniDefine extends Activity implements OnClickListener
{
	private static TextView wordBox, definitionBox;
	private static String word, definition;
	private static Button bBack;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.profani_definition);
		initActivity();
		receiveBundle();
		setTextViews();
	}
	
	private void initActivity()
	{
		wordBox = (TextView)findViewById(R.id.tvProfDefWord);
		definitionBox = (TextView)findViewById(R.id.tvProfDefDefinition);
		bBack = (Button)findViewById(R.id.bProfDefBack);
		bBack.setOnClickListener(this);
		word = "";
		definition = "";
	}
	
	private void receiveBundle()
	{
		Bundle extras = getIntent().getExtras();
		word = extras.getString("word");
		definition = extras.getString("definition");
	}
	
	private void setTextViews()
	{
		wordBox.setText(word);
		definitionBox.setText(definition);
	}

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bProfDefBack:
			finish();
			break;
		}
	}

}
