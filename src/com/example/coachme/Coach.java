package com.example.coachme;

//https://parse.com/tutorials/android-push-notifications
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Coach extends Activity {
	Button request;
	Button register;
	EditText named;
	TextView teamcode;
	EditText team;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coach);
		addListenerOnButton();

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
            				Coach.this, LoginActivity.class);
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
		getMenuInflater().inflate(R.menu.coach, menu);
		
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
	    	    
	    // 4, From overflow menu, goes to the Help page
    	case R.id.action_help:
	    startActivity(new Intent(this, Help.class));
	    return true;
		    	
		// 5, From overflow menu, goes to the About page
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
	public void sendMessage(View view)
	{
		//Currently set so that all messages sent to halos team
		ParsePush push = new ParsePush();
		EditText et = (EditText)findViewById(R.id.sendMessEditText); 
	    String message = et.getText().toString();
	    push.setChannel("halos");
	    push.setMessage(message);
	    push.sendInBackground();
	}
	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		request = (Button) findViewById(R.id.buttonrequestcoach);
		teamcode = (TextView) findViewById(R.id.teamcode);
		named = (EditText)findViewById(R.id.coachCode); 
		team = (EditText)findViewById(R.id.teamCode); 
		final ParseUser me = ParseUser.getCurrentUser();
		// name = (EditText) findViewById(R.id.coachCode);
		request.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(me == null)
				{
					Intent intent = new Intent(Coach.this,LoginActivity.class);
					startActivity(intent);
					return;
				}
				// query to check of user has already created a team
				ParseQuery<ParseObject> query = ParseQuery.getQuery("coaches");
				query.whereEqualTo("email", me.get("email"));
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						// if query does not find the object make one
						if (object == null) {
							Log.d("score", "dint find team");
							ParseObject coachdb =new ParseObject("coaches");
							coachdb.put("email", me.get("email"));
							coachdb.put("name", named.getText().toString());
							coachdb.saveInBackground();
							Log.d("after save","team created ");
							// to get the coach team id for coach to give to
							// players in order to add
							ParseQuery<ParseObject>query2 = ParseQuery.getQuery("coaches");
							query2.whereEqualTo("email", me.get("email"));
							query2.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									// if query does not find the object make one
									if (object == null) {
										Log.d("score2", "dint find team");
									} else {
										// query found a object already exists
										Log.d("score2", "found team");
										teamcode.setText(object.getObjectId());
									}
// SeS2Gszpf7
								}
							});

						} else {
							// query found a object already exists
							Log.d("score", "found team");
							teamcode.setText(object.getObjectId());
						}
					}
				});

			}
		});

		register = (Button) findViewById(R.id.buttoncoachcode);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(me == null)
				{
					Intent intent = new Intent(Coach.this,LoginActivity.class);
					startActivity(intent);
					return;
				}
				// TODO Auto-generated method stub
				//search for team based on id
				ParseQuery<ParseObject> query =ParseQuery.getQuery("coaches");
				
				query.whereEqualTo("objectId", team.getText().toString());
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("score", "sorry team code not correct");
						} else {
							Log.d("team create", "already joined team");
							//check if player is already part of the team
							ParseRelation<ParseObject> relation = object.getRelation("players");
							ParseQuery<ParseObject>playersearch = relation.getQuery();
							playersearch.whereEqualTo("email",me.get("email") );
							playersearch.getFirstInBackground(new GetCallback<ParseObject>() {
							public void done(ParseObject object, ParseException e) {
								    if (object == null) {
								      Log.d("add", "not a part of team yet");
								    } else {
								      Log.d("add", "already part of team");
								    }
								  }
								});
							relation.add(me);
							object.saveInBackground();
							//Manually join halos team
							ParseInstallation pi = ParseInstallation.getCurrentInstallation();
					        
					        //Register a channel to test push channels
					        Context ctx = Coach.this.getApplicationContext();
					        PushService.subscribe(ctx, "halos", PushResponse.class);
					        
					        pi.saveEventually();
//							teamcode.setText(object.getString("name"));
						}
					}
				});
			}
		});
	}

}