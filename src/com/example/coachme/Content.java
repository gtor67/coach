package com.example.coachme;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

public class Content extends Activity {
	
	// for the View Video Button
	Button button;
	
	private DBAdapter myDb;
	private String link;
	private int rowNum;
	public final static String VIDEO_MESSAGE = "com.example.coach.VideoURL";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		// Show the Up button in the action bar.
		setupActionBar();
		// for the View Video Button
		viewVideo();
		
		Intent intent = getIntent();
		Log.d("Content Page","Made it into content.");
		String title = "";
		String origin = intent.getStringExtra("Origin");
		Log.d("ORIGIN", origin);
		if(origin.equals("Favorites"))
			title = intent.getStringExtra(Favorites.EXTRA_ROWNUM);
		else
			title = intent.getStringExtra(TrainingFilterActivity.EXTRA_MESSAGE);
		Log.d("test",title);
		myDb = new DBAdapter(this);
	    myDb.open();
		/*
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(title);
		
		setContentView(textView);
		*/
		
		//Obtain title only
/*		
		Pattern stopWords = Pattern.compile("\\b(?:Beginner|Intermedite|Advanced|Catching|Bunting|Fielding|Pitching|Sliding|Running|Batting)\\b\\s*");
		Matcher matcher = stopWords.matcher(title);
		String clean = matcher.replaceAll("");
		Log.d("Is this the title?", clean);
		
	*/	
		StringBuffer fileName = new StringBuffer ("p");
	    StringBuffer id = new StringBuffer ("");
		boolean done = false;
		int i = 0;
		while(!done)
		{
			char temp = title.charAt(i);
			if(Character.isDigit(temp))
				{
					fileName.append(temp);
					id.append(temp);
				}
			else
				done = true;
			i++;	
		}
		String idS = id.toString();
		long idL = Long.valueOf(idS).longValue();
		rowNum = Integer.parseInt(idS);
		//Cursor curs = myDb.getRow(idL);
		//Cursor exersice = myDb.getRow(clean);
		Cursor exersice = myDb.getRow(idL);
		Log.d("exersice 0", exersice.getString(0));
		Log.d("exersice 1", exersice.getString(1));
		Log.d("exersice 2", exersice.getString(2));
		Log.d("exersice 3", exersice.getString(3));
		Log.d("exersice 4", exersice.getString(4));
		Log.d("exersice 5", exersice.getString(5));
		
	
		//fileName.append(exersice.getString(0));
		Log.d("pic id",""+fileName);
		
		
		TextView titleTV = (TextView) findViewById (R.id.textViewTitle);
		titleTV.setText(exersice.getString(5));
		
		TextView focusTV = (TextView) findViewById (R.id.TextView01);
		focusTV.setText(exersice.getString(3));
		focusTV.setMovementMethod(new ScrollingMovementMethod());
		TextView proTV = (TextView) findViewById (R.id.TextView02);
		proTV.setMovementMethod(new ScrollingMovementMethod());
		proTV.setText(exersice.getString(4));
		
		ImageView image = (ImageView) findViewById (R.id.imageView1);
		int resourceId = this.getResources().getIdentifier(fileName.toString(), "drawable", "com.example.coachme");
		Log.d("resourcce id",""+resourceId);
		image.setImageResource(resourceId);
		
		link = exersice.getString(6);
		
		
		
		//String imageLoc = "p1";
		//int resourceId = this.getResources().getIdentifier(fileName.toString(), "drawable", "com.example.coachme");
		//image.setImageResource(resourceId);
		//String imageLoc = "android.resource://your.pack.name" + "";
		//Uri myUri = Uri.parse(imageLoc);
		//image.setImageURI("" + "");
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
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
				
	    // From overflow menu, goes to the Favorites page
		case R.id.action_favorites:
	    startActivity(new Intent(this, Favorites.class));
	    return true;
			    
	    // From overflow menu, goes to the Settings page
		case R.id.action_settings:
		startActivity(new Intent(this, Settings.class));
	    return true;
		    	
		// From overflow menu, goes to the About page
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
		default:
		return super.onOptionsItemSelected(item);
		}
	}
	 
	public void viewVideo() {
 
		button = (Button) findViewById(R.id.viewVideobutton);
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				//Uri address = Uri.parse("https://www.youtube.com/watch?v=_UTxTzRzpVg");
				/*
				Uri address = Uri.parse(link);
				Intent browserSurf = new Intent(Intent.ACTION_VIEW, address);
			    startActivity(browserSurf);
 				*/
				//		Pattern stopWords = Pattern.compile("\\b(?:Beginner|Intermedite|Advanced|Catching|Bunting|Fielding|Pitching|Sliding|Running|Batting)\\b\\s*");

//				Pattern stopWord = Pattern.compile("\\b(?:https://www.youtube.com/watch?v=|.|//|wwwyoutubecomwatch)\\b\\s*");
//				Matcher matcher = stopWord.matcher(link);
				String videoKey = link.substring(32, link.length());
				
				Log.d("Is this the title?", videoKey);
				
			
				Intent intent = new Intent(Content.this, Video.class);
				intent.putExtra(VIDEO_MESSAGE, videoKey);
		    	startActivity(intent);
		    	
			}
 
		});
		
	}
	public void addToFavorites(View view){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Beginner");
		query.orderByAscending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
		  public void done(List<ParseObject> beginner1, ParseException e) {
		    if (e == null) {
		    	
		    	//Note: Local DB is 1 index ahead
		    	int parseRowNum = rowNum - 1;
		    	ParseObject favRow = beginner1.get(parseRowNum);
		    	if(beginner1.contains(favRow))
		    		ParseUser.getCurrentUser().getRelation("Favs").remove(favRow);
		    	else
		    		ParseUser.getCurrentUser().getRelation("Favs").add(favRow);
		    	ParseUser.getCurrentUser().saveInBackground();
		    	

		    } else {
		    	Log.d("ERROR", "Error: " + e.getMessage());
		      // something went wrong
		    		}
		  	}
			});
	    }	

	//assumes that the user table has the relation column    
	private void testingRelation2(){
	    	
	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Beginner");
	    	query.getInBackground("LM3gQPNPm8", new GetCallback<ParseObject>() {
	    		  public void done(ParseObject object, ParseException e) {
	    		    if (e == null) {
	    		    	ParseObject currentUser = ParseUser.getCurrentUser();
	    		    	ParseRelation<ParseObject> relation = currentUser.getRelation("Favs");
	    		    	relation.add(object);
	    		    	currentUser.saveInBackground();
	    		    } else {
	    		      // something went wrong
	    		    }
	    		  }
	    		});
	    	
	    	//now try to see if i can see what user has
	    	// first we will create a query on the Book object
	    	//ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User");
	    	ParseRelation<ParseObject> relation = ParseUser.getCurrentUser().getRelation("Favs"); 
	    	ParseQuery<ParseObject> query2 = relation.getQuery();
	    	query2.findInBackground(new FindCallback<ParseObject>() {
				  public void done(List<ParseObject> favs, ParseException e) {
				    if (e == null) {
				    	int tableSize= favs.size();
				    	
				       
				    	for(int i= 0; i < tableSize; i++){
				    		
				    		
				    		//Cursor exercise = myDb.getRow(Beginner1.get(i).getString("Title"));
				    		Log.d("Routine:", favs.get(i).getString("Title"));
				
				    		
				    ////////////TO REFRESH WITH PARSE		
				    		favs.get(i).fetchIfNeededInBackground(new GetCallback<ParseObject>() {
				    			  public void done(ParseObject object, ParseException e) {
				    			    if (e == null) {
				    			      // Success!
				    			    } else {
				    			      // Failure!
				    			    }
				    			  }
				    			});
				    /////////////END REFRESH		
				    		
				    		
				    		
				    		
				    		}//for loop

				    } else {
				    	Log.d("ERROR", "Error: " + e.getMessage());
				      // something went wrong
				    		}
				  	}
					});
	    }
	
    private void testingRelation(){
    	
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Beginner");
    	query.getInBackground("gpMBEObMvm", new GetCallback<ParseObject>() {
    		  public void done(ParseObject object, ParseException e) {
    		    if (e == null) {
    		    	ParseObject currentUser = ParseUser.getCurrentUser();
    		    	ParseRelation<ParseObject> relation = object.getRelation("Users");
    		    	relation.add(currentUser);
    		    	object.saveInBackground();
    		    } else {
    		      // something went wrong
    		    }
    		  }
    		});
    	
    	//now try to see if i can see what user has
    	// first we will create a query on the Book object
    	ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Beginner");
    	 
    	// now we will query the authors relation to see if the author object we have 
    	// is contained therein
    	query2.whereEqualTo("Users", ParseUser.getCurrentUser());
    	query2.findInBackground(new FindCallback<ParseObject>() {
			  public void done(List<ParseObject> favs, ParseException e) {
			    if (e == null) {
			    	int tableSize= favs.size();
			    	
			       
			    	for(int i= 0; i < tableSize; i++){
			    		int c=i+1;
			    		
			    		//Cursor exercise = myDb.getRow(Beginner1.get(i).getString("Title"));
			    		Log.d("Routine:", favs.get(i).getString("Title"));
			
			    		
			    ////////////TO REFRESH WITH PARSE		
			    		favs.get(i).fetchIfNeededInBackground(new GetCallback<ParseObject>() {
			    			  public void done(ParseObject object, ParseException e) {
			    			    if (e == null) {
			    			      // Success!
			    			    } else {
			    			      // Failure!
			    			    }
			    			  }
			    			});
			    /////////////END REFRESH		
			    		
			    		
			    		
			    		
			    		}//for loop

			    } else {
			    	Log.d("ERROR", "Error: " + e.getMessage());
			      // something went wrong
			    		}
			  	}
				});
    }
}

