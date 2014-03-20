package com.example.coachme;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TrainingFilterActivity extends Activity implements OnTabChangeListener {
	TabHost tabH;
	Spinner exerciseFilter;
	ListView listViewBeginner ;
	String type ="All";
	String level="Beginner";
	private DBAdapter myDb;
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
        listViewBeginner.setOnItemClickListener(new OnItemClickListener() {
      	  @Override
      	  public void onItemClick(AdapterView<?> parent, View view,
      	    int position, long id) {
      		  Intent intent = new Intent(TrainingFilterActivity.this,Content.class);
		  	        //String selRoutine = TrainingFilterActivity.advancedList[position]; //Get text of item
		  	        String selRoutine = (String)parent.getAdapter().getItem(position);
		  	        intent.putExtra(EXTRA_MESSAGE, selRoutine);
		  	        //intent.putExtra("routine",position);
		  	        Log.d("Position","my postion"+position);
		  	      Log.d("FINAL TITLE","my title"+selRoutine);
	            	startActivity(intent);
		  	          Toast.makeText(getApplicationContext(),
		  	            "Click Advance ListItem Number " + position, Toast.LENGTH_LONG)
		  	            .show();
      	  }
      	}); 
        
	        
	   // this.loadMylist("level","All");
	    tabH.setOnTabChangedListener(this);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.training_filter, menu);
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
        		loadMylist("Beginner",type);
        		break;
        case 1:
        		loadMylist("Intermedite",type);
        		break;
        case 2:
        		loadMylist("Advanced",type);
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

}
