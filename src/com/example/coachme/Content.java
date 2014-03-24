package com.example.coachme;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		// Show the Up button in the action bar.
		setupActionBar();
		// for the View Video Button
		addListenerOnButton();
		
		Intent intent = getIntent();
		String title = intent.getStringExtra(TrainingFilterActivity.EXTRA_MESSAGE);
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
		TextView proTV = (TextView) findViewById (R.id.TextView02);
		proTV.setText(exersice.getString(4));
		
		ImageView image = (ImageView) findViewById (R.id.imageView1);
		int resourceId = this.getResources().getIdentifier(fileName.toString(), "drawable", "com.example.coachme");
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
		switch (item.getItemId()) {
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
		}
		return super.onOptionsItemSelected(item);
		
		
	}
	 
	public void addListenerOnButton() {
 
		button = (Button) findViewById(R.id.viewVideobutton);
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				//Uri address = Uri.parse("https://www.youtube.com/watch?v=_UTxTzRzpVg");
				Uri address = Uri.parse(link);
				Intent browserSurf = new Intent(Intent.ACTION_VIEW, address);
			    startActivity(browserSurf);
 				
				/*
				Intent intent = new Intent(Content.this, Video.class);
		    	startActivity(intent);
		    	*/
			}
 
		});
		
	}


}

