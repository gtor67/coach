package com.example.coachme;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import com.parse.*;
public class Splash extends Activity
{
	String level;
	String type;
	String focus;
	String procedure;
	String title;
	String URL;

	///Array of strings corresponds to rows in table. they are Unique keys
	String[] BeginnerRows ={"74JzuMovnV", "qgIW87yP9F"};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					
					SoundPool sp = new SoundPool(1,3,0);
					int id = sp.load(Splash.this,R.raw.splashsound,1);
				
					sp.play(id, 0.5f, 0.5f, 0, 0, 1.0f);
					MediaPlayer mPlayer = MediaPlayer.create(Splash.this, R.raw.splashsound);
					
					//originally 1000, but for debugging set it to lower
					sleep(100);
					mPlayer.start();
					sp.release();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent openMainActivity = new Intent("android.intent.action.MAINACTIVITY");
					startActivity(openMainActivity);
				}
			}
		};
		timer.start();
		
		
		////////////PARSE.COM TESTING ////////////
		Parse.initialize(this, "eT6q3q5rgzDUgTAjRuTPAzyHSzvo7sbilbu9jqvU", "4TrzrKp78gLmGH0IcEB2pE4BtyIDz8siPrGszV6i");		
		
		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		


	}
	
	
	
	public void getBeginner1(int row) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Beginner");
		query.findInBackground(new FindCallback<ParseObject>() {
		  public void done(List<ParseObject> Beginner1, ParseException e) {
		    if (e == null) {
//			level= Beginner1.getString("Level");// column names
//			type= Beginner1.getString("Type");
//			focus= Beginner1.getString("Focus");
//			procedure= Beginner1.getString("Procedure");
//			title= Beginner1.getString("Title");
//			URL= Beginner1.getString("VideoURL");

		      
		    } else {
		      // something went wrong
		    		}
		  	}
			});

		}//end getBeginner1

	
}
