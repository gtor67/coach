package com.example.coachme;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class CreateAccount extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.create_account, menu);
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
	
	public void createAccount(View view) 
	{
		EditText emailView = (EditText) findViewById(R.id.emailEditText);
		EditText passView = (EditText) findViewById(R.id.passEditText);
		String email = emailView.getText().toString();
		String pass = passView.getText().toString();
		
		ParseUser user = new ParseUser();
		user.setUsername(email);
		user.setPassword(pass);
		user.setEmail(email);
		 
		// other fields can be set just like with ParseObject
		//user.put("phone", "650-253-0000");
		user.signUpInBackground(new SignUpCallback() 
		{
			@Override
			public void done(com.parse.ParseException e) 
			{
				if (e == null) {
				      // Hooray! Let them use the app now.
					Intent intent = new Intent(CreateAccount.this, MainActivity.class);
			    	startActivity(intent);
				    } else {
				      // Sign up didn't succeed. Look at the ParseException
				      // to figure out what went wrong
				    }
			}
		});
		
		
	}

}
