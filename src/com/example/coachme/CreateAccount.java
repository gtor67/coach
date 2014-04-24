package com.example.coachme;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class CreateAccount extends Activity {
	private EditText emailView;
	private View focusView;
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
		// Handle item selection
		switch (item.getItemId()) {

		// 1, From overflow menu, goes to Recover Lost Password page
    	case R.id.action_forgot_password:
	    startActivity(new Intent(this, RecoverLostPassword.class));
	    return true;
	    
	    // 2, From overflow menu, goes to the Favorites page
	 	case R.id.action_favorites:
	 	startActivity(new Intent(this, Favorites.class));
	 	return true;
	 	
	    // 3, From overflow menu, goes to the Team Settings page
        case R.id.action_team:
    	startActivity(new Intent(this, Coach.class));
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
			default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void createAccount(View view) 
	{
		emailView = (EditText) findViewById(R.id.emailEditText);
		EditText passView = (EditText) findViewById(R.id.passEditText);
		String email = emailView.getText().toString();
		String pass = passView.getText().toString();
		//Need to check if form inputs are okay
		boolean cancel = false;
		focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(pass)) {
			passView.setError(getString(R.string.error_field_required));
			focusView = passView;
			cancel = true;
		} else if (pass.length() < 4) {
			passView.setError(getString(R.string.error_invalid_password));
			focusView = passView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			emailView.setError(getString(R.string.error_field_required));
			focusView = emailView;
			cancel = true;
		} else if (!email.contains("@")) {
			emailView.setError(getString(R.string.error_invalid_email));
			focusView = emailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		
		else
		{
			//End of form check
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
						ParseUser.logOut();
				    	finish();
						Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
				    	startActivity(intent);
					    } else {
					      // Sign up didn't succeed. Look at the ParseException
					      // to figure out what went wrong
					    	//Log.d("Creation Failed", e.getMessage() + e.getCode());
					    	if(e.getCode() == 202)
					    	{
					    		emailView.setError(getString(R.string.error_email_taken));
					    		focusView = emailView;
					    		focusView.requestFocus();
					    	}
					    	else
					    		Toast.makeText(getApplicationContext(),"Failed to create account.",Toast.LENGTH_LONG).show();
					    }
				}
			});
		
		}//end of else condition[whether valid or invalid form]
	}

}
