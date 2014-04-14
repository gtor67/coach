package com.example.coachme;

import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.SaveCallback;

public class AccSettings extends Activity {
	private ProgressBar spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acc_settings);
		
		TextView email = (TextView)findViewById(R.id.textViewEmail);
		
		ParseUser user = ParseUser.getCurrentUser();
		String emailAdd = user.getEmail();
		Log.d("Email Address", emailAdd);
		email.setText(emailAdd);
		
		spinner = (ProgressBar)findViewById(R.id.progressBar1);
		spinner.setVisibility(View.GONE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acc_settings, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle item selection
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
    	default:
    	return super.onOptionsItemSelected(item);
    	}
    }
	
	  public void updatePass(View view){
		  	spinner.setVisibility(View.VISIBLE);
		  	ParseUser user = ParseUser.getCurrentUser();
		  	
			
			EditText newPassET = (EditText) findViewById(R.id.editTextNewPass);
			
			String newPass = newPassET.getText().toString();
			user.setPassword(newPass);
			//user.saveInBackground();
			
			user.saveInBackground(new SaveCallback() {
				  @Override
				public void done(ParseException e) {
				    if(e == null)
				    {
				    	spinner.setVisibility(View.GONE);
				    	CharSequence text = "Password updated!";
				    	int duration = Toast.LENGTH_SHORT;

				    	Toast toast = Toast.makeText(AccSettings.this, text, duration);
				    	toast.show();
				    	
				    }
				    else
				    {
				    	spinner.setVisibility(View.GONE);
				    	CharSequence text = "Error";
				    	int duration = Toast.LENGTH_SHORT;

				    	Toast toast = Toast.makeText(AccSettings.this, text, duration);
				    	toast.show();
				    }
				  }
				});
			
	    }
	  
	  public void updateEmail(View view){
		  	spinner.setVisibility(View.VISIBLE);
		  	ParseUser user = ParseUser.getCurrentUser();
		  	
			
			EditText newEmailET = (EditText) findViewById(R.id.editTextSettingEmail);
			
			String newEmail = newEmailET.getText().toString();
			user.setEmail(newEmail);
			//user.saveInBackground();
			user.saveInBackground(new SaveCallback() {
				  @Override
				public void done(ParseException e) {
				    if(e == null)
				    {
				    	spinner.setVisibility(View.GONE);
				    	CharSequence text = "Email updated!";
				    	int duration = Toast.LENGTH_SHORT;

				    	Toast toast = Toast.makeText(AccSettings.this, text, duration);
				    	toast.show();
				    }
				    else
				    {
				    	spinner.setVisibility(View.GONE);
				    	CharSequence text = "Error";
				    	int duration = Toast.LENGTH_SHORT;

				    	Toast toast = Toast.makeText(AccSettings.this, text, duration);
				    	toast.show();    	
				    }
				  }
				});
			
	    }

}
