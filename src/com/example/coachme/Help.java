package com.example.coachme;

import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class Help extends Activity {
 
   @Override
public void onCreate(Bundle savedInstanceState){
   super.onCreate(savedInstanceState);
   setContentView(R.layout.help);
   
   }
   
/////When clicking user related field
	public  void displayAlert()
   {
    new AlertDialog.Builder(this).setMessage("You Need to be Logged in")  
          .setTitle("Non User")  
          .setCancelable(false)  
          
          .setPositiveButton("Log in",  
             new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialog, int whichButton){
           	  
           	  Intent mainIntent = new Intent().setClass(
           				Help.this, LoginActivity.class);
           				startActivity(mainIntent);
           	  
                 finish();
             }  
             }) 
          
          .setNegativeButton("No Thanks",  
             new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialog, int whichButton){
           	  dialog.cancel();
             }  
             }) 
          
           
          .show(); 
    
    
    
   }
	
	
/////HIDE SHOW MENU STUFF IN CONTENT PAGE
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		
		if(ParseUser.getCurrentUser()==null){
		    MenuItem   item1 = menu.findItem(R.id.action_create_account);
		    MenuItem   item2 = menu.findItem(R.id.action_forgot_password);
		    
		    item1.setVisible(true);
		    item2.setVisible(true);
		    invalidateOptionsMenu();} /// CALL to reinsert items,restart action bar with correct items 
		
		    else{ 
		    	MenuItem   item1 = menu.findItem(R.id.action_create_account);
			    MenuItem   item2 = menu.findItem(R.id.action_forgot_password);
			    
			    item1.setVisible(false);
			    item2.setVisible(false);
			    invalidateOptionsMenu();}
		
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
			if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Favorites.class));
		    	}
	    
	    return true;
	    
	    // 4, From overflow menu, goes to the Team Settings page
       case R.id.action_team:

       	if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Coach.class));
		    	}
   	return true;
	    
	    // 5, From overflow menu, goes to the Help page
   	case R.id.action_help:
	    startActivity(new Intent(this, Help.class));
	    return true;
		    	
		// 6, From overflow menu, goes to the About page
		case R.id.action_about:
		startActivity(new Intent(this, About.class));
		return true;

		case android.R.id.home:
	    // This ID represents the Home or Up button. In the case of this
		// activity, the Up button is shown. Use NavUtils to allow users
		// to navigate up one level in the application structure. For
		// more details, see the Navigation pattern on Android Design:
		//
		// http://developer.android.com/design/patterns/navigation.html#up-vs-back
		//
		NavUtils.navigateUpFromSameTask(this);
		return true;
	    
	    // 7, From overflow menu, Exits program
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
