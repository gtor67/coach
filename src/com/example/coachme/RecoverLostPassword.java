package com.example.coachme;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RecoverLostPassword extends Activity {
 
   @Override
public void onCreate(Bundle savedInstanceState){
   super.onCreate(savedInstanceState);
   setContentView(R.layout.recover_lost_password);
   
   }
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recover_lost_password, menu);
		return true;
	}
	public void resetPassword(View view){
		EditText emailET = (EditText)findViewById(R.id.editTextrecoverEmail);
		String email = emailET.getText().toString();
		
		ParseUser.requestPasswordResetInBackground(email,
                new RequestPasswordResetCallback() {
				@Override
				public void done(ParseException e) {
				if (e == null) {
				// An email was successfully sent with reset instructions.
					CharSequence text = "Check your inbox for instructions.";
			    	int duration = Toast.LENGTH_SHORT;

			    	Toast toast = Toast.makeText(RecoverLostPassword.this, text, duration);
			    	toast.show();  
				} else {
				// Something went wrong. Look at the ParseException to see what's up.
					CharSequence text = "Email was not sent. Check email address you supplied.";
			    	int duration = Toast.LENGTH_SHORT;

			    	Toast toast = Toast.makeText(RecoverLostPassword.this, text, duration);
			    	toast.show();  
				}
				}
				});
	}
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle item selection
	switch (item.getItemId()) {
    
	// 1, From overflow menu, goes to the Create an Account page
	case R.id.action_create_account:
    startActivity(new Intent(this, CreateAccount.class));
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
	default:
	return super.onOptionsItemSelected(item);
	}
  }
}