package com.example.coachme;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class Video extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		// Show the Up button in the action bar.
		setupActionBar();
		
		YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.videoView1);
		  youTubeView.initialize("AIzaSyBXqEZwiMA9SVkJ_qqOXwTnnopEGDJ4IBc", this); //long string is developer key 
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
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,YouTubePlayer player, boolean wasRestored) {
		// Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(Content.VIDEO_MESSAGE);  
	    Log.d ("VIDKEY",message);
		if (!wasRestored) player.cueVideo(message); // your video ID to play
    }
    @Override
    public void onInitializationFailure(Provider arg0,  YouTubeInitializationResult arg1){
    }
}
