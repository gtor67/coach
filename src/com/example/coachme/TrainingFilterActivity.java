package com.example.coachme;

import android.os.Bundle;
import android.app.Activity;
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
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TrainingFilterActivity extends Activity {
	TabHost tabH;
	Spinner exerciseFilter;
	ListView listViewBeginner ;
	ListView listViewIntermediate;
	ListView listViewAdvance;
	
	/////ARRAY for SPINNER FILTER
	String[] exrcises = {
            "catching",
            "bunting",
            "field",
            "pitching"
    };
	
	//////ARRAY for LIST VIEW BEGINNER ///////
	
	String[] beginnerList = new String[] { "Beginner 1", 
            "Beginner 2",
            "Beginner 3",
            "Beginner 4", 
            "Beginner 5",
            "Beginner 6",
            "Beginner 7",
            "Beginner 8", 
            "Beginner 9",
            "Beginner 10",
            "Beginner 11" 
           };
	
//////ARRAY for LIST VIEW INTERMEDIATE ///////
	
	String[] intermediateList = new String[] { "Intermediate 1", 
			"Intermediate 2",
	        "Intermediate 3",
	        "Intermediate 4", 
	        "Intermediate 5",
	        "Intermediate 6",
	        "Intermediate 7", 
	        "Intermediate 8",
	        "Intermediate 9",
	        "Intermediate 10", 
	        "Intermediate 11" 
         };
	
//////ARRAY for LIST VIEW BEGINNER ///////
	
	String[] advanceList = new String[] { "Advance 1", 
          "Advance 2",
          "Advance 3",
          "Advance 4", 
          "Advance 5",
          "Advance 6",
          "Advance 7", 
          "Advance 8",
          "Advance 9",
          "Advance 10", 
          "Advance 11"
         };
	//Testing to see if I can pass list item text to next screen
	public final static String EXTRA_MESSAGE = "com.example.coach.RName";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_filter);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//get intent
		Intent intent = getIntent();
		setContentView(R.layout.activity_training_filter);
		
		///////////////////TABS////////////////////////
		//// youtube vids from user "TheNewBoston " android vids 84-87?
		tabH = (TabHost) findViewById (R.id.tabhost);
		tabH.setup();
		
		 TabSpec spec1Beginner=tabH.newTabSpec("TAB 1");
	        spec1Beginner.setContent(R.id.tab1);
	        spec1Beginner.setIndicator("Beginner");//text on tab
	        tabH.addTab(spec1Beginner);
	        //don't need to make new tabspec object?
	        TabSpec spec2Intermediate=tabH.newTabSpec("TAB 2");
	        spec2Intermediate.setIndicator("Intermediate"); //text on tab
	        spec2Intermediate.setContent(R.id.tab2);
	        tabH.addTab(spec2Intermediate);
	      
	        TabSpec spec3Advance=tabH.newTabSpec("TAB 3");
	        spec3Advance.setContent(R.id.tab3);
	        spec3Advance.setIndicator("Advance"); //text on tab
	        tabH.addTab(spec3Advance);
	     
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
	                        int position = exerciseFilter.getSelectedItemPosition();
	                        Toast.makeText(getApplicationContext(),"You have selected "+exrcises[+position],Toast.LENGTH_LONG).show();
	                        // TODO Auto-generated method stub
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
	        
	        ArrayAdapter<String> adapterBeginnerList = new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, android.R.id.text1, beginnerList);
	      
	      
	              // Assign adapter to ListView
	              listViewBeginner.setAdapter(adapterBeginnerList); 
	              
	              // ListView Item Click Listener
	              listViewBeginner.setOnItemClickListener(new OnItemClickListener() {
	            	  @Override
	            	  public void onItemClick(AdapterView<?> parent, View view,
	            	    int position, long id) {
	            		Intent intent = new Intent(TrainingFilterActivity.this,Content.class);
	  	            	startActivity(intent);
	            	    Toast.makeText(getApplicationContext(),
	            	      "Click Beginner ListItem Number " + position, Toast.LENGTH_LONG)
	            	      .show();
	            	  }
	            	}); 
	        
	        //////////////END LIST VIEW BEGINNER  ////////////////////////
	              
	        ////////////////LIST VIEW INTERMEDIATE  /////////////////////
	  	        
	  	     listViewIntermediate = (ListView) findViewById(R.id.listView2Intermediate);
	  	        
	  	     ArrayAdapter<String> adapterIntermediateList = new ArrayAdapter<String>(this,
	  	                android.R.layout.simple_list_item_1, android.R.id.text1, intermediateList);
	  	      
	  	      
	  	              // Assign adapter to ListView
	  	        	listViewIntermediate.setAdapter(adapterIntermediateList); 
	  	              
	  	              // ListView Item Click Listener
	  	            listViewIntermediate.setOnItemClickListener(new OnItemClickListener() {
	  	            	@Override
	  	            	public void onItemClick(AdapterView<?> parent, View view,
	  	            	  int position, long id) {
	  	             	  Intent intent = new Intent(TrainingFilterActivity.this,Content.class);
	  	            	  startActivity(intent);
	  	            	  Toast.makeText(getApplicationContext(),
	  	            	    "Click Intermediate ListItem Number " + position, Toast.LENGTH_LONG)
	  	            	    .show();
	  	   
	  	            	  }
	  	            	}); 
	  	        
	  	        //////////////END LIST VIEW INTERMEDIATE  ////////////////////////
	      
	  	            
	  	     ////////////////LIST VIEW ADVANCE  /////////////////////
		  	        
	  		  listViewAdvance = (ListView) findViewById(R.id.listView3Advance);
	  		  	        
	  		  ArrayAdapter<String> adapterAdvanceList = new ArrayAdapter<String>(this,
	  		  	         android.R.layout.simple_list_item_1, android.R.id.text1, advanceList);
	  		  	      
	  		  	      
	  		  	              // Assign adapter to ListView
	  		  	    listViewAdvance.setAdapter(adapterAdvanceList); 
	  		  	              
	  		  	              // ListView Item Click Listener
	  		  	    listViewAdvance.setOnItemClickListener(new OnItemClickListener() {
	  		  	        @Override
	  		  	        public void onItemClick(AdapterView<?> parent, View view,
	  		  	          int position, long id) {
	  		  	        Intent intent = new Intent(TrainingFilterActivity.this,Content.class);
	  		  	        //String selRoutine = TrainingFilterActivity.advancedList[position]; //Get text of item
	  		  	        String selRoutine = (String)parent.getAdapter().getItem(position);
	  		  	        intent.putExtra(EXTRA_MESSAGE, selRoutine);
	  	            	startActivity(intent);
	  		  	          Toast.makeText(getApplicationContext(),
	  		  	            "Click Advance ListItem Number " + position, Toast.LENGTH_LONG)
	  		  	            .show();
	  		  	          }
	  		  	        }); 
	  		  	        
	  		  	        //////////////END LIST VIEW ADVANCE  ////////////////////////
	  		      
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

}
