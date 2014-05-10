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
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	private boolean scalingComplete = false;
	private String messToSend;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coach);
		addListenerOnButton();

}
	@Override
		public void onWindowFocusChanged(boolean hasFocus) {
		if (!scalingComplete) // only do this once
		{
			scaleContents(findViewById(R.id.coachcontent), findViewById(R.id.coachframe));
	        scalingComplete = true;
		}
		     
			super.onWindowFocusChanged(hasFocus);
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
		
		return true;
	}

	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
	    
	    // 3, From overflow menu, goes to the About page
	 	case R.id.action_push:
	 			if(ParseUser.getCurrentUser()==null){
	 				displayAlert();
	 			}  
	 			else{ 
	 				startActivity(new Intent(this, PushResponse.class));
	 				}
	 		return true;
				
	    // 4, From overflow menu, goes to the Favorites page
		case R.id.action_favorites:
			if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Favorites.class));
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
	public void sendMessage(View view)
	{
		//Currently set so that all messages sent to halos team
		
		EditText et = (EditText)findViewById(R.id.sendMessEditText); 
	    messToSend = et.getText().toString();
		ParseQuery<ParseObject> query =ParseQuery.getQuery("coaches");
		query.whereEqualTo("email",ParseUser.getCurrentUser().getEmail());
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(final ParseObject object, ParseException e) {
				if (object == null) {
					Log.d("SENDMESSAGE", "User does not have a team.");
				} else {
					//Save to table
					//ParseQuery<ParseObject> query = ParseQuery.getQuery("coaches");
					ParseObject message = new ParseObject("PushMessages");
					message.put("Team", object.get("name"));
					message.put("Email", ParseUser.getCurrentUser().getEmail());
					message.put("Message", messToSend);
					try {
						message.save();
						object.getRelation("Messages").add(message);
						object.saveInBackground();
						/*
						message.refreshInBackground(new RefreshCallback()
						{

							@Override
							public void done(ParseObject arg0, ParseException arg1) {
								
								object.getRelation("Messages").add(arg0);
								object.saveInBackground();
							}
							
						}
								);*/
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//message.saveInBackground(); 
					//Now need to add relation to team table? 
	
					//Team found(found team where user is coach)
					ParsePush push = new ParsePush();
					push.setChannel(object.getString("name"));
					push.setMessage(messToSend);
					push.sendInBackground();
				}
			}
		});
		/*below used for hard coding
	    push.setChannel("halos");
	    push.setMessage(message);
	    push.sendInBackground();
	    */
	}
	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		request = (Button) findViewById(R.id.buttonrequestcoach);
		teamcode = (TextView) findViewById(R.id.teamcode);
		named = (EditText)findViewById(R.id.coachCode); 
		team = (EditText)findViewById(R.id.teamCode); //this is actually the code entered by user
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
							//coachdb.saveInBackground();
							try {
								coachdb.save();
								int duration = Toast.LENGTH_LONG;
								CharSequence text = "You have created the team. Please give the code below to team members.";
							    Toast toast = Toast.makeText(Coach.this, text, duration);
								toast.show();
								
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Log.d("after save","team created ");
							// to get the coach team id for coach to give to
							// players in order to add
							ParseQuery<ParseObject>query2 = ParseQuery.getQuery("coaches");
							query2.whereEqualTo("email", me.get("email"));
							query2.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									// if query does not find the object make one
									// if null, that means save didn't work
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
							//this is the case when team already exists
							Log.d("score", "found team");
							//teamcode.setText(object.getObjectId());
							int duration = Toast.LENGTH_LONG;
							CharSequence text = "You already have a team.";
						    Toast toast = Toast.makeText(Coach.this, text, duration);
							toast.show();
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
							int duration = Toast.LENGTH_LONG;
							CharSequence text = "Incorrect code.";
						    Toast toast = Toast.makeText(Coach.this, text, duration);
							toast.show();
						} else {
							Log.d("team create", "already joined team");
							//So this else probably means that team exists? So then here I will also set up push stuff
							String teamTitle = object.getString("name");
							//Manually join halos team
							ParseInstallation pi = ParseInstallation.getCurrentInstallation();
					        
					        //Register a channel to test push channels
					        Context ctx = Coach.this.getApplicationContext();
					        PushService.subscribe(ctx, teamTitle, PushResponse.class);
					        
					        pi.saveEventually();
					       
							
							
							
							//check if player is already part of the team
							ParseRelation<ParseObject> relation = object.getRelation("players");
							ParseQuery<ParseObject>playersearch = relation.getQuery();
							playersearch.whereEqualTo("email",me.get("email") );
							playersearch.getFirstInBackground(new GetCallback<ParseObject>() {
							public void done(ParseObject object, ParseException e) {
									int duration = Toast.LENGTH_SHORT;
								    if (object == null) {
								      Log.d("add", "not a part of team yet");
								      CharSequence text = "You have joined the team.";
									    Toast toast = Toast.makeText(Coach.this, text, duration);
										toast.show();
								    } else {
								      Log.d("add", "already part of team");
								  	
								    CharSequence text = "You already belong to this team.";
								    Toast toast = Toast.makeText(Coach.this, text, duration);
									toast.show();
								    }
								  }
								});
							//So below means that not part of team?
							relation.add(me);
							object.saveInBackground();
							
							/* Commented out since moved to upper part for customization
							//Manually join halos team
							ParseInstallation pi = ParseInstallation.getCurrentInstallation();
					        
					        //Register a channel to test push channels
					        Context ctx = Coach.this.getApplicationContext();
					        PushService.subscribe(ctx, "halos", PushResponse.class);
					        
					        pi.saveEventually();
					        */
//							teamcode.setText(object.getString("name"));
						}
					}
				});
			}
		});
	}
  	// Scales the contents of the given view so that it completely fills the
	// given
	// container on one axis (that is, we're scaling isotropically).
	private void scaleContents(View rootView, View container) {
	
		
		
		// Compute the scaling ratio
		float xScale = (float) container.getWidth() / rootView.getWidth();
		float yScale = (float) container.getHeight() / rootView.getHeight();
		float scale = Math.min(xScale, yScale);
      Log.d("xscale"," "+ xScale);
      Log.d("yscale", " " +yScale);
      Log.d("scale", " "+scale);

		// Scale our contents
		scaleViewAndChildren(rootView, scale);
	}

	// Scale the given view, its contents, and all of its children by the given
	// factor.
	public  void scaleViewAndChildren(View root, float scale) {
		// Retrieve the view's layout information
		ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float x= metrics.xdpi;
		float y= metrics.ydpi;
		 Log.d("xdensity"," "+ x);
	      Log.d("ydensity", " " +y);

		// Scale the view itself
		if (layoutParams.width != ViewGroup.LayoutParams.FILL_PARENT
				&& layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
			layoutParams.width *= scale;
		}
		if (layoutParams.height != ViewGroup.LayoutParams.FILL_PARENT
				&& layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
			layoutParams.height *= scale;
		}

		// If this view has margins, scale those too
		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
			marginParams.leftMargin *= scale;
			marginParams.rightMargin *= scale;
			marginParams.topMargin *= scale;
			marginParams.bottomMargin *= scale;
		}

		// Set the layout information back into the view
		root.setLayoutParams(layoutParams);

		// Scale the view's padding
		root.setPadding((int) (root.getPaddingLeft() * scale),
				(int) (root.getPaddingTop() * scale),
				(int) (root.getPaddingRight() * scale),
				(int) (root.getPaddingBottom() * scale));

		// If the root view is a TextView, scale the size of its text
		if (root instanceof TextView) {
			if(x>400){
			TextView textView = (TextView) root;
			textView.setTextSize((float) (textView.getTextSize() * scale*.4));
			}else{
				TextView textView = (TextView) root;
				textView.setTextSize((float) (textView.getTextSize() * scale));
			}
		}

		// If the root view is a ViewGroup, scale all of its children
		// recursively
		if (root instanceof ViewGroup) {
			ViewGroup groupView = (ViewGroup) root;
			for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
				scaleViewAndChildren(groupView.getChildAt(cnt), scale);
		}
	}
	public static String getDensity(Context context) {
	    String r;
	    DisplayMetrics metrics = new DisplayMetrics();

	    if (!(context instanceof Activity)) {
	        r = "hdpi";
	    } else {
	        Activity activity = (Activity) context;
	        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

	        if (metrics.densityDpi <= DisplayMetrics.DENSITY_LOW) {
	            r = "ldpi";
	        } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_MEDIUM) {
	            r = "mdpi";
	        } else {
	            r = "hdpi";
	        }
	    }

	    return r;
	}
}