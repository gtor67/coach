package com.example.coachme;

import java.util.List;

import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class TrainingFilterActivity extends Activity implements OnTabChangeListener {
	TabHost tabH;
	Spinner exerciseFilter;
	ListView listViewBeginner ;
	String type ="All";
	String level="Beginner";
	private DBAdapter myDb;
		private boolean scalingComplete = false;
	/////ARRAY for SPINNER FILTER
	String[] exrcises = {
			"All",
            "Catching",
            "Bunting",
            "Fielding",
            "Pitching",
            "Sliding",
            "Running",
            "Batting"
    };
	

	//Testing to see if I can pass list item text to next screen
	public final static String EXTRA_MESSAGE = "com.example.coach.RName";
	public final static int routine=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_filter);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//get intent
		Intent intent = getIntent();
		setContentView(R.layout.activity_training_filter);
		
		//open database
		 myDb = new DBAdapter(this);
	     myDb.open();
		///////////////////TABS////////////////////////
		//// youtube vids from user "TheNewBoston " android vids 84-87?
		tabH = (TabHost) findViewById (R.id.tabhost);
		tabH.setup();
		
		 TabSpec spec1Beginner=tabH.newTabSpec("TAB 1");
	        spec1Beginner.setContent(R.id.listView1Beginner);
	        spec1Beginner.setIndicator("Beginner");//text on tab
	        tabH.addTab(spec1Beginner);
	        //don't need to make new tabspec object?
	        TabSpec spec2Intermediate=tabH.newTabSpec("TAB 2");
	        spec2Intermediate.setIndicator("Intermediate"); //text on tab
	        spec2Intermediate.setContent(R.id.listView1Beginner);
	        tabH.addTab(spec2Intermediate);
	      
	        TabSpec spec3Advance=tabH.newTabSpec("TAB 3");
	        spec3Advance.setContent(R.id.listView1Beginner);
	        spec3Advance.setIndicator("Advance"); //text on tab
	        tabH.addTab(spec3Advance);
	        
	        tabH.setCurrentTab(1);
	        tabH.setCurrentTab(0);
	
	     ///////// END TAB CODE  //////////////////////////////////   
	        
	        ///////////SPINNER FILTER/////////////
	        ///// http://www.learn2crack.com/2013/12/android-spinner-dropdown-example.html   //////
	        exerciseFilter = (Spinner) findViewById(R.id.spinner1Filter);  

	        ArrayAdapter<String> adapterTabs = new ArrayAdapter<String>(
	                this, android.R.layout.simple_spinner_item, exrcises);
	        exerciseFilter.setAdapter(adapterTabs);
	        exerciseFilter.setOnItemSelectedListener(
	                new AdapterView.OnItemSelectedListener() {
	                    @Override
	                    public void onItemSelected(AdapterView<?> arg0, View arg1,
	                            int arg2, long arg3) {
	                    	 myDb.open();
	                        int position = exerciseFilter.getSelectedItemPosition();
	                        Toast.makeText(getApplicationContext(),"You have selected "+exrcises[+position],Toast.LENGTH_LONG).show();
	                        // TODO Auto-generated method stub
//	                        Log.d("***Selected Tab", "Im currently in tab with index::" + tabH.getCurrentTab());
//	            	        Log.d("level",level);
//	            	        Log.d("type",type);
	                        type=exrcises[+position];
//	                        Log.d("type",type);
	                        loadMylist(level,type);
	                    }
	                    @Override
	                    public void onNothingSelected(AdapterView<?> arg0) {
	                        // TODO Auto-generated method stub
	                    }
	                }
	            );
	        /////////////END SPINNER FILTER CODE  /////////////////
	        
	        ////////////////LIST VIEW BEGINNER  /////////////////////
	        ////// http://www.vogella.com/tutorials/AndroidListView/article.html /////
        listViewBeginner = (ListView) findViewById(R.id.listView1Beginner);
        Log.d("type","got this far");
        listViewBeginner.setOnItemClickListener(new OnItemClickListener() {
      	  @Override
      	  public void onItemClick(AdapterView<?> parent, View view,
      	    int position, long id) {
      		  Intent intent = new Intent(TrainingFilterActivity.this,Content.class);
		  	        //String selRoutine = TrainingFilterActivity.advancedList[position]; //Get text of item
		  	        String selRoutine = (String)parent.getAdapter().getItem(position);
		  	        intent.putExtra(EXTRA_MESSAGE, selRoutine);
		  	        intent.putExtra("Origin", "TrainingFilter");
		  	        //intent.putExtra("routine",position);
		  	        Log.d("Position","my postion"+position);
		  	        Log.d("FINAL TITLE","my title"+selRoutine);
	            	startActivity(intent);
	            	/*
		  	          Toast.makeText(getApplicationContext(),
		  	            "Click Advance ListItem Number " + position, Toast.LENGTH_LONG)
		  	            .show(); */
      	  }
      	}); 
        
	        
	   // this.loadMylist("level","All");
	    tabH.setOnTabChangedListener(this);

	}

		@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	if (!scalingComplete) // only do this once
	{
	scaleContents(findViewById(R.id.tabhost), findViewById(R.id.experienceContainer));
        scalingComplete = true;
	}
	     
		super.onWindowFocusChanged(hasFocus);
	}

	
//////////////////////////////////////////////////////////

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
            				TrainingFilterActivity.this, LoginActivity.class);
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
		getMenuInflater().inflate(R.menu.training_filter, menu);
		
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


	@Override
	public void onTabChanged(String tabId)
	{
		// TODO Auto-generated method stub
		//Need to reopen database since it closes everytime the myDb.list is called
		myDb.open();
		int selTab = tabH.getCurrentTab();
		//replaced beginnerList, intermediateList, advancedList with mydb.___
        switch (selTab)
        {
        case 0:
        		level="Beginner";
        		loadMylist(level,type);
        		break;
        case 1:
        		level="Intermediate";
        		loadMylist(level,type);
        		break;
        case 2:
        		level= "Advanced";
        		loadMylist(level,type);
        		break;
        default:
        		break;
        	
        }
        
	}
	 public void loadMylist(String level, String type) {
		  // TODO Auto-generated method stub
		  List<String> list = myDb.listdata(level,type);
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		    android.R.layout.simple_list_item_1, list);
		  	listViewBeginner.setAdapter(adapter);
		  
		 }
	
	@Override
    protected void onDestroy() {
     super.onDestroy(); 
     myDb.close();
    }
    // Scales the contents of the given view so that it completely fills the
	// given
	// container on one axis (that is, we're scaling isotropically).
	private void scaleContents(View rootView, View container) {
		// Compute the scaling ratio
		float xScale = (float) container.getWidth() / rootView.getWidth();
		float yScale = (float) container.getHeight() / rootView.getHeight();
		float scale = Math.min(xScale, yScale);

		// Scale our contents
		scaleViewAndChildren(rootView, scale);
	}

	// Scale the given view, its contents, and all of its children by the given
	// factor.
	public static void scaleViewAndChildren(View root, float scale) {
		// Retrieve the view's layout information
		ViewGroup.LayoutParams layoutParams = root.getLayoutParams();

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
			TextView textView = (TextView) root;
			textView.setTextSize(textView.getTextSize() * (scale));
		}

		// If the root view is a ViewGroup, scale all of its children
		// recursively
		if (root instanceof ViewGroup) {
			ViewGroup groupView = (ViewGroup) root;
			for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
				scaleViewAndChildren(groupView.getChildAt(cnt), scale);
		}
	}

}
