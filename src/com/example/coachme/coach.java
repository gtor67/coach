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

public class coach extends Activity {
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
//							teamcode.setText(object.getString("name"));
						}
					}
				});
			}
		});
	}

}