package com.example.coachme;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class CreateAccount extends Activity {
	private EditText emailView;
	private View focusView;
		private boolean scalingComplete = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		// Show the Up button in the action bar.
		setupActionBar();
	}
		@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	if (!scalingComplete) // only do this once
	{
		scaleContents(findViewById(R.id.createcontent), findViewById(R.id.createcontainer));
        scalingComplete = true;
	}
	     
		super.onWindowFocusChanged(hasFocus);
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

/////When clicking user related field
	public  void displayAlert()
    {
     new AlertDialog.Builder(this).setMessage("You Need to be Logged in")  
           .setTitle("Non User")  
           .setCancelable(false)  
           
           .setPositiveButton("Log in",  
              new DialogInterface.OnClickListener() {  
              public void onClick(DialogInterface dialog, int whichButton){
            	  
            	  Intent mainIntent = new Intent().setClass(
            				CreateAccount.this, LoginActivity.class);
            				startActivity(mainIntent);
            	  
                  finish();
              }  
              }) 
           
           .setNegativeButton("No Thanks",  
              new DialogInterface.OnClickListener() {  
              public void onClick(DialogInterface dialog, int whichButton){
            	  dialog.cancel();
              }  
              }) 
           
            
           .show(); 
     
     
     
    }
	
	
/////HIDE SHOW MENU STUFF IN CONTENT PAGE
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		
		if(ParseUser.getCurrentUser()==null){
//		    MenuItem   item1 = menu.findItem(R.id.action_create_account);
		    MenuItem   item2 = menu.findItem(R.id.action_forgot_password);
		    
//		    item1.setVisible(true);
		    item2.setVisible(true);
		    invalidateOptionsMenu();} /// CALL to reinsert items,restart action bar with correct items 
		
		    else{ 
//		    	MenuItem   item1 = menu.findItem(R.id.action_create_account);
			    MenuItem   item2 = menu.findItem(R.id.action_forgot_password);
			    
//			    item1.setVisible(false);
			    item2.setVisible(false);
			    invalidateOptionsMenu();}
		
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
				
	    // 2, From overflow menu, goes to the messages page
	 	case R.id.action_push:
	 			if(ParseUser.getCurrentUser()==null){
	 				displayAlert();
	 			}  
	 			else{ 
	 				startActivity(new Intent(this, PushResponse.class));
	 				}
	 		return true;
	 	// 2, From overflow menu, goes to the Favorites page	
		case R.id.action_favorites:
			if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Favorites.class));
		    	}
	    
	    return true;
	    
	    // 4, From overflow menu, goes to the Team Settings page
        case R.id.action_team:

        	if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Coach.class));
		    	}
    	return true;
	    
	    // 5, From overflow menu, goes to the Help page
    	case R.id.action_help:
	    startActivity(new Intent(this, Help.class));
	    return true;
		    	
		// 6, From overflow menu, goes to the About page
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
	    
	    // 7, From overflow menu, Exits program
    	case R.id.action_exit:
    	this.finish();
    	Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
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
	// Scales the contents of the given view so that it completely fills the
	// given
	// container on one axis (that is, we're scaling isotropically).
	private void scaleContents(View rootView, View container) {
	
		
		
		// Compute the scaling ratio
		float xScale = (float) container.getWidth() / rootView.getWidth();
		float yScale = (float) container.getHeight() / rootView.getHeight();
		float scale = Math.min(xScale, yScale);
      Log.d("xscale"," "+ xScale);
      Log.d("yscale", " " +yScale);
      Log.d("scale", " "+scale);

		// Scale our contents
		scaleViewAndChildren(rootView, scale);
	}

	// Scale the given view, its contents, and all of its children by the given
	// factor.
	public  void scaleViewAndChildren(View root, float scale) {
		// Retrieve the view's layout information
		ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float x= metrics.xdpi;
		float y= metrics.ydpi;
		 Log.d("xdensity"," "+ x);
	      Log.d("ydensity", " " +y);

		// Scale the view itself
		if (layoutParams.width != ViewGroup.LayoutParams.FILL_PARENT
				&& layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
			layoutParams.width *= scale;
		}
		if (layoutParams.height != ViewGroup.LayoutParams.FILL_PARENT
				&& layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
			layoutParams.height *= scale;
		}

		// If this view has margins, scale those too
		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
			marginParams.leftMargin *= scale;
			marginParams.rightMargin *= scale;
			marginParams.topMargin *= scale;
			marginParams.bottomMargin *= scale;
		}

		// Set the layout information back into the view
		root.setLayoutParams(layoutParams);

		// Scale the view's padding
		root.setPadding((int) (root.getPaddingLeft() * scale),
				(int) (root.getPaddingTop() * scale),
				(int) (root.getPaddingRight() * scale),
				(int) (root.getPaddingBottom() * scale));

		// If the root view is a TextView, scale the size of its text
		if (root instanceof TextView) {
			if(x>400){
			TextView textView = (TextView) root;
			textView.setTextSize((float) (textView.getTextSize() * scale*.4));
			}
			else if(x<300){
				TextView textView = (TextView) root;
				textView.setTextSize((float) (textView.getTextSize() * scale*1.5));
			}else{
				TextView textView = (TextView) root;
				textView.setTextSize((float) (textView.getTextSize() * scale));
			}
		}

		// If the root view is a ViewGroup, scale all of its children
		// recursively
		if (root instanceof ViewGroup) {
			ViewGroup groupView = (ViewGroup) root;
			for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
				scaleViewAndChildren(groupView.getChildAt(cnt), scale);
		}
	}
	public static String getDensity(Context context) {
	    String r;
	    DisplayMetrics metrics = new DisplayMetrics();

	    if (!(context instanceof Activity)) {
	        r = "hdpi";
	    } else {
	        Activity activity = (Activity) context;
	        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

	        if (metrics.densityDpi <= DisplayMetrics.DENSITY_LOW) {
	            r = "ldpi";
	        } else if (metrics.densityDpi <= DisplayMetrics.DENSITY_MEDIUM) {
	            r = "mdpi";
	        } else {
	            r = "hdpi";
	        }
	    }

	    return r;
	}
}