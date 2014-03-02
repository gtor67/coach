package com.example.coachme;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class Content extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		String title = intent.getStringExtra(TrainingFilterActivity.EXTRA_MESSAGE);
		/*
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(title);
		
		setContentView(textView);
		*/
		TextView titleTV = (TextView) findViewById (R.id.textViewTitle);
		titleTV.setText(title);
		StringBuffer fileName = new StringBuffer ("p");
	    
		boolean done = false;
		int i = 0;
		while(!done)
		{
			char temp = title.charAt(i);
			if(Character.isDigit(temp))
				fileName.append(temp);
			else
				done = true;
			i++;	
		}
		
		ImageView image = (ImageView) findViewById (R.id.imageView1);
		//String imageLoc = "p1";
		int resourceId = this.getResources().getIdentifier(fileName.toString(), "drawable", "com.example.coachme");
		image.setImageResource(resourceId);
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

}
