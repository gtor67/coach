package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import com.parse.*;
public class Splash extends Activity
{

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
					
					sleep(2000);
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

	
}
