package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class About extends Activity {
 
   public void onCreate(Bundle savedInstanceState){
   super.onCreate(savedInstanceState);
   setContentView(R.layout.about);
   
   }
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle item selection
	switch (item.getItemId()) {
	
	// 1, From overflow menu, goes to the Create an Account page
	case R.id.action_create_account:
    startActivity(new Intent(this, CreateAccount.class));
    return true;
    
     // 2, From overflow menu, goes to Recover Lost Password page
 	 case R.id.action_forgot_password:
     startActivity(new Intent(this, RecoverLostPassword.class));
     return true;
    
     // 3, From overflow menu, goes to the Favorites page
     case R.id.action_favorites:
 	 startActivity(new Intent(this, Favorites.class));
 	 return true;
    
    // 4, From overflow menu, goes to the Help page
	case R.id.action_help:
    startActivity(new Intent(this, Help.class));
    return true;
	default:
	return super.onOptionsItemSelected(item);
	}
  }
}