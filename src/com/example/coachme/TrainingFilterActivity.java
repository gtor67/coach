package com.example.coachme;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
	
	/////ARRAY for SPINNER FILTER
	String[] exrcises = {
            "catching",
            "bunting",
            "field",
            "pitching"
    };
	
	
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
		
		tabH = (TabHost) findViewById (R.id.tabhost);
		tabH.setup();
		
		 TabSpec spec1Beginner=tabH.newTabSpec("TAB 1");
	        spec1Beginner.setContent(R.id.tab1);
	        spec1Beginner.setIndicator("Beginner");
	        tabH.addTab(spec1Beginner);
	      
	        TabSpec spec2Intermediate=tabH.newTabSpec("TAB 2");
	        spec2Intermediate.setIndicator("Intermediate");
	        spec2Intermediate.setContent(R.id.tab2);
	        tabH.addTab(spec2Intermediate);
	      
	        TabSpec spec3Advance=tabH.newTabSpec("TAB 3");
	        spec3Advance.setContent(R.id.tab3);
	        spec3Advance.setIndicator("Advance"); 
	        tabH.addTab(spec3Advance);
	     
	     ///////// END TAB CODE  //////////////////////////////////   
	        
	        ///////////SPINNER FILTER/////////////
	        exerciseFilter = (Spinner) findViewById(R.id.spinner1Filter);  


	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	                this, android.R.layout.simple_spinner_item, exrcises);
	        exerciseFilter.setAdapter(adapter);
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
