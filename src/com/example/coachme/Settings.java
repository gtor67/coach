package com.example.coachme;
//https://parse.com/tutorials/android-push-notifications
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends Activity {
	private Button request;
	private Button register;
	int counter;
	TextView display;
	
	private EditText named ;
	TextView teamcode;
 
   @Override
public void onCreate(Bundle savedInstanceState){
   super.onCreate(savedInstanceState);
   setContentView(R.layout.coach);
   addListenerOnButton();

   
   }
   
	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		display = (TextView) findViewById(R.id.teamcode);
		request = (Button) findViewById(R.id.buttonrequestcoach);
		teamcode = (TextView) findViewById(R.id.teamcode);
		   final ParseUser me = ParseUser.getCurrentUser();
		//name = (EditText) findViewById(R.id.coachCode);
		request.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter++;
				display.setText(" " + counter);
				
				
				Log.d("test email", "email"+ me.get("name"));
				//query to check of user has already created a team
				ParseQuery<ParseObject> query = ParseQuery.getQuery("coaches");
				query.whereEqualTo("email", me.get("email"));
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.d("score", "dint find team");
			        	ParseObject coachdb = new ParseObject("coaches");
						coachdb.put("email", me.get("email"));
						coachdb.put("name","sfgf" );
						coachdb.saveInBackground();
						Log.d("after save", "team created ");
						teamcode.setText("tema codes is");
				    } else {
				      Log.d("score", "found team");
				      Log.d("score", "" + object);
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
				// TODO Auto-generated method stub
				counter--;
				display.setText(" " + counter);
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("coaches");
				query.whereEqualTo("team", me.get("damn dogs"));
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
						Log.d("score", "Retrieved "  + " scores");
			//			Log.d("score", "Retrieved " + object.get("coach") + " scores");
						ParseRelation<ParseObject> relation = object.getRelation("players");
						relation.add(me);
						object.saveInBackground();

				    } else {
				      Log.d("score", "already joined team");
				      Log.d("score", "" + object);
				      teamcode.setText(object.getString("name"));
				    }
				  
				  }
				});
				
				
//				ParseQuery<ParseObject> query = ParseQuery.getQuery("coaches");
//				query.whereEqualTo("name", "angeles");
//				query.findInBackground(new FindCallback<ParseObject>() {
//					@Override
//					public void done(List<ParseObject> scoreList,
//							ParseException e) {
//					
//						
//						if (e == null) {
//							Log.d("score", "Retrieved " + scoreList.size() + " scores");
//							Log.d("score", "Retrieved " + scoreList.get(0).get("coach") + " scores");
//						
//							ParseRelation<ParseObject> relation = scoreList.get(0).getRelation("players");
//							relation.add(me);
//							scoreList.get(0).saveInBackground();
//
//						} else {
//							Log.d("score", "Error: " + e.getMessage());
//						}
//					}
//				});

			}
		});
	}

@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
	
	// From overflow menu, goes to the About page
	case R.id.action_about:
    startActivity(new Intent(this, About.class));
    return true;
	default:
	return super.onOptionsItemSelected(item);
	}
  }
}