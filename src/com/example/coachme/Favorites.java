package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Favorites extends Activity {
 
   public void onCreate(Bundle savedInstanceState){
   super.onCreate(savedInstanceState);
   setContentView(R.layout.favorites);
   
   }
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.favorites, menu);
	return true;
	}

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle item selection
	switch (item.getItemId()) {
    
	// From overflow menu, goes to the Create an Account page
	case R.id.action_create_account:
	startActivity(new Intent(this, CreateAccount.class));
	return true;

	// From overflow menu, goes to the Settings page
 	case R.id.action_settings:
    startActivity(new Intent(this, Settings.class));
    return true;
    
    // From overflow menu, goes to the About page
 	case R.id.action_about:
    startActivity(new Intent(this, About.class));
    return true;
	default:
	return super.onOptionsItemSelected(item);
	}
  }
}