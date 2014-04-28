package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class About extends Activity {

   @Override
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
 	 
   // 4, From overflow menu, goes to the Team Settings page
   case R.id.action_team:
   startActivity(new Intent(this, Coach.class));
   return true;
    
   // 5, From overflow menu, goes to the Help page
   case R.id.action_help:
   startActivity(new Intent(this, Help.class));
   return true;
    
   // 6, From overflow menu, Exits program
   case R.id.action_exit:
   this.finish();
   Intent intent = new Intent(Intent.ACTION_MAIN);
   intent.addCategory(Intent.CATEGORY_HOME);
   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   startActivity(intent);
   default:
   return super.onOptionsItemSelected(item);
   }
}
}