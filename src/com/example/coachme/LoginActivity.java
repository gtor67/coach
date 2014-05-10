package com.example.coachme;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.*;
/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	/////////////////// PARSE TEST
	public boolean test;
	////////
	private boolean scalingComplete = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
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
            				LoginActivity.this, LoginActivity.class);
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

    	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	if (!scalingComplete) // only do this once
	{
		scaleContents(findViewById(R.id.logincontent), findViewById(R.id.login_form));
        scalingComplete = true;
	}
	     
		super.onWindowFocusChanged(hasFocus);
	}
	
	
/////HIDE SHOW MENU STUFF IN CONTENT PAGE
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		
		if(ParseUser.getCurrentUser()==null){
		    MenuItem   item1 = menu.findItem(R.id.action_create_account);
		    MenuItem   item2 = menu.findItem(R.id.action_forgot_password);
		    
		    item1.setVisible(true);
		    item2.setVisible(true);
		    invalidateOptionsMenu();} /// CALL to reinsert items,restart action bar with correct items 
		
		    else{ 
		    	MenuItem   item1 = menu.findItem(R.id.action_create_account);
			    MenuItem   item2 = menu.findItem(R.id.action_forgot_password);
			    
			    item1.setVisible(false);
			    item2.setVisible(false);
			    invalidateOptionsMenu();}
		
		return true;
	}

	
	public void createAccount(View view){
		Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

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
	    // 3, From overflow menu, goes to the messages page
	 	case R.id.action_push:
	 			if(ParseUser.getCurrentUser()==null){
	 				displayAlert();
	 			}  
	 			else{ 
	 				startActivity(new Intent(this, PushResponse.class));
	 				}
	 		return true;
				
	    // 4, From overflow menu, goes to the Favorites page
		case R.id.action_favorites:
			if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Favorites.class));
		    	}
	    
	    return true;
	    
	    // 5, From overflow menu, goes to the Team Settings page
        case R.id.action_team:

        	if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Coach.class));
		    	}
    	return true;
	    
	    // 6, From overflow menu, goes to the Help page
    	case R.id.action_help:
	    startActivity(new Intent(this, Help.class));
	    return true;
		    	
		// 7, From overflow menu, goes to the About page
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
	    
	    // 8, From overflow menu, Exits program
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

			    	Toast toast = Toast.makeText(LoginActivity.this, text, duration);
			    	toast.show();  
				} else {
				// Something went wrong. Look at the ParseException to see what's up.
					CharSequence text = "Email was not sent. Check email address you supplied.";
			    	int duration = Toast.LENGTH_SHORT;

			    	Toast toast = Toast.makeText(LoginActivity.this, text, duration);
			    	toast.show();  
				}
				}
				});
	}
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				ParseUser.logInInBackground(mEmail,mPassword, new LogInCallback() {
					  @Override
					public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					      // Hooray! The user is logged in.
					    	if(user.getBoolean("emailVerified")){
					    		
				////////////////////////FIX SUBCRIBE BUG
					    		
					    		
//					    		ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("players");
//					    		innerQuery.whereExists(user.getEmail());
//					    		ParseQuery<ParseObject> query = ParseQuery.getQuery("coaches");
//					    		query.whereMatchesQuery("player", innerQuery);
//					    		query.findInBackground(new FindCallback<ParseObject>() {
//					    		  public void done(List<ParseObject> playerList, ParseException e) {
//					    		    // list of players 
//					    			  if(playerList !=null){
//					    				  Log.d("add", "already part of team");
//										    //Register a channel to test push channels
//										      Context ctx = LoginActivity.this.getApplicationContext();
//										        PushService.subscribe(ctx, teamTitle, PushResponse.class);
//					    				  
//					    			  }
//					    		  }
//					    		});
					    		
					    		
					    		
					    		ParseQuery<ParseObject> query =ParseQuery.getQuery("coaches");
					    		query.orderByAscending("createdAt");
								query.findInBackground(new FindCallback<ParseObject>() {
									@Override
									public void done(List<ParseObject> coach, ParseException e) {
										if (coach == null) {
										} else {
											for(int i= 0; i < coach.size(); i++){
											final String teamTitle = coach.get(i).getString("name");
//											ParseInstallation pi = ParseInstallation.getCurrentInstallation();
//									        
//									        pi.saveEventually();
									       
											//check if player is already part of the team
											ParseRelation<ParseObject> relation = coach.get(i).getRelation("players");
											ParseQuery<ParseObject>playersearch = relation.getQuery();
											playersearch.whereEqualTo("email",ParseUser.getCurrentUser().get("email") );
											playersearch.getFirstInBackground(new GetCallback<ParseObject>() {
											public void done(ParseObject player, ParseException e) {
												    if (player == null) {
												      Log.d("add", "not a part of team yet");
												    } else {
												      Log.d("add", "already part of team");
												    //Register a channel to test push channels
												      Context ctx = LoginActivity.this.getApplicationContext();
												        PushService.subscribe(ctx, teamTitle, PushResponse.class);
												    }
												  }
												});
											
										}//end forloop
										}
									}
								});
//					    		
					    		
					    		
					  
				/////////////////////////END SUBSCRIBE BUG FIX
					    		
					    		
					    		
					    		
					    		
					    		
					    		
					    		test = true; 
					    	}
					    } else {
					      // Signup failed. Look at the ParseException to see what happened.
					    	 test = false;
					    	 Log.d("login fail", e.getMessage());
					    }
					  }
					});
				
				
				// Simulate network access.
				//Thread.sleep(2000);
				Thread.sleep(5000);
				
			} catch (InterruptedException e) {
				return false;
			}
/////// testing parse log in ///////////////////////////
		/**	
			ParseUser user = new ParseUser();
			user.setUsername("Jerry@jerry.com");
			user.setPassword("showmethemoney");
			user.setEmail("email@example.com");
			 
			// other fields can be set just like with ParseObject
			//user.put("phone", "650-253-0000");
			 
			user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      // Hooray! Let them use the app now.
			    } else {
			      // Sign up didn't succeed. Look at the ParseException
			      // to figure out what went wrong
			    }
			  }
			});
			*/
			
	////////////////end parse login test  ///////////////////////
			return test;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				
				///// bug fix, finish parent activity so you cant go back
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
//				Intent openMainActivity = new Intent("android.intent.action.MAINACTIVITY");
//				startActivity(openMainActivity);
				finish();
				
				
			} else {
				//Need to display right thing here
				/*
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
				*/
				if(( (ParseUser.getCurrentUser() != null) && (!ParseUser.getCurrentUser().getBoolean("emailVerified"))))
				{
					ParseUser.logOut(); 
					//finish(); 
					Toast.makeText(getApplicationContext(),"Check your email to verify your account.",Toast.LENGTH_LONG).show();
	        	}
				else
					Toast.makeText(getApplicationContext(),"Invalid email address or password.",Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
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