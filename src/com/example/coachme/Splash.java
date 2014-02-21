package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.*;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
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
					sleep(5000);
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
