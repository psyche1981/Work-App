package psyche.androidapp.workcalcs;
//TODO refactor all classes into relevant packages
//TODO add this project to github
//TODO figure out the gitignore thing

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity
{
	private String menuItems[] = { "FPD Check", "Make Semi", "Make STD", "Adjust Silo", "Hall of Shame"};
	private String menuOptions[] = { "FPDCheck", "SemiDisclaimer", "STDDisclaimer", "Adjust", "ShameGallery"};
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, menuItems));
	}

	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		String option = menuOptions[position];
		
		try 
		{
			Class<?> c = Class.forName("psyche.androidapp.workcalcs." + option);
			Intent i = new Intent(Menu.this, c);
			startActivity(i);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public boolean onCreateOptionsMenu(android.view.Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater menInf = getMenuInflater();
		menInf.inflate(R.menu.popup_menu, menu);
		return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.popup_menu_about:
			Intent menuAbout = new Intent("psyche.androidapp.workcalcs.MENUABOUT");
			startActivity(menuAbout);
			break;
		case R.id.popup_menu_games:
			Intent gameMenu = new Intent("psyche.androidapp.workcalcs.GAMEMENU");
			startActivity(gameMenu);
			break;
		}
		return false;
	}
	
	
	
	
}
