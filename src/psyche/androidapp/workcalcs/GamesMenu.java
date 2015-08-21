package psyche.androidapp.workcalcs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesMenu extends ListActivity
{
	private String[] menuItems = {"ProfaniMan", "Trouser Snake", "Another Game"};
	private String[] menuOptions = {"ProfaniMan", "TrouserSplash", "TempScreen"};
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setListAdapter(new ArrayAdapter<String>(GamesMenu.this, android.R.layout.simple_list_item_1, menuItems));
	}

	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		
		String option = menuOptions[position];
		try
		{
			Class<?> optionClass = Class.forName("psyche.androidapp.workcalcs." + option);
			Intent i = new Intent(GamesMenu.this, optionClass);
			startActivity(i);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater menInf = getMenuInflater();
		menInf.inflate(R.menu.popup_gamesmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.gamesmenu_info:
			Intent i = new Intent("psyche.androidapp.workcalcs.GAMESINFO");
			startActivity(i);
			break;
		}
		return false;
	}
	
	
	
	

}
