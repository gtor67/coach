package com.example.coachme;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	DBAdapter myDb;
	ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
        filldb();
        loadMylist();
    }
    private void loadMylist() {
  		// TODO Auto-generated method stub
  		List<String> list = myDb.listdata();
  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
  				android.R.layout.simple_list_item_1, list);
  				
  		lv = (ListView) findViewById(R.id.listdb);
  		lv.setAdapter(adapter);
  		
  	}
	private void filldb() {
		// TODO Auto-generated method stub

			
			myDb.insertRow("Beginner","Running", "www.br1.com");
			myDb.insertRow("Beginner","Catching","www.bc2.com");
			myDb.insertRow("Beginner","Batting","www.bc3.com");
			myDb.insertRow("Beginner","Fielding","www.bc4.com");
			myDb.insertRow("Beginner","bunting","www.bc5.com");
			myDb.insertRow("Beginner","better Catching","www.bc6.com");
			myDb.insertRow("Beginner","improve Bating","www.ib7.com");		    	
			
			myDb.insertRow("intermedite","Bating","www.ib1.com");		
			myDb.insertRow("intermedite","Catching","www.ib3.com");		    	
			myDb.insertRow("intermedite","Running","www.ib2.com");		    	
			myDb.insertRow("intermedite","Fielding","www.ib4.com");		    	
			myDb.insertRow("intermedite","better Catching","www.ib5.com");		    	
			myDb.insertRow("intermedite","improve batting","www.ib6.com");		    	
			
			myDb.insertRow("Advanted"," Running","www.ar1.com");
			myDb.insertRow("Advanted"," batting","www.ar2.com");
			myDb.insertRow("Advanted"," catching","www.ar3.com");
			myDb.insertRow("Advanted"," fielding","www.ar4.com");
			myDb.insertRow("Advanted"," batting","www.ar5.com");
		   
	}
	@Override
    protected void onDestroy() {
     super.onDestroy(); 
     closeDB();
    }

    private void openDB() {
     myDb = new DBAdapter(this);
     myDb.open();
    }
    private void closeDB() {
     myDb.close();
    }
    
    /*
    private String getData(){
    	Cursor cursor = myDb.getAllRows();
    	 String[] fromFieldNames = new String[] 
    	    {DBAdapter.KEY_LEVEL, DBAdapter.KEY_TYPE, DBAdapter.KEY_URL};
    	 int[] toViewIDs = new int[]
    	   {R.id.level, R.id.type, R.id.url};
    	 @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,  // Context
    	    	      R.layout.itemlayout, // Row layout template
    	    	      cursor,     // cursor (set of DB records to map)
    	    	      fromFieldNames,   // DB Column names
    	    	      toViewIDs    // View IDs to put information in
    	    	      );
    	 adapter.notifyDataSetChanged();
    	 lv.setAdapter(adapter);
	return null;
   }
   */
    
    
    
    
//    @SuppressWarnings("deprecation")
//	private void populateList() {
//    	  Cursor cursor = myDb.getAllRows();
//    	  
//    	  // Allow activity to manage lifetime of the cursor.
//    	  // DEPRECATED! Runs on the UI thread, OK for small/short queries.
//    	  startManagingCursor(cursor);
//    	  
//    	  // Setup mapping from cursor to view fields:
//    	  String[] fromFieldNames = new String[] 
//    	    {DBAdapter.KEY_LEVEL, DBAdapter.KEY_TYPE, DBAdapter.KEY_URL};
//    	  int[] toViewIDs = new int[]
//    	    {R.id.level,     R.id.type,           R.id.url};
//    	  
//    	  // Create adapter to may columns of the DB onto elemesnt in the UI.
//    	  SimpleCursorAdapter myCursorAdapter = 
//    	    new SimpleCursorAdapter(
//    	      this,  // Context
//    	      R.layout.itemlayout, // Row layout template
//    	      cursor,     // cursor (set of DB records to map)
//    	      fromFieldNames,   // DB Column names
//    	      toViewIDs    // View IDs to put information in
//    	      );
//    	  
//    	  // Set the adapter for the list view
//    	  ListView myList = (ListView) findViewById(R.id.listdb);
//    	  myList.setAdapter(myCursorAdapter);
//    	 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Baseball button */
    public void chooseBaseball(View view) {
    	Intent intent = new Intent(this, TrainingFilterActivity.class);
    	startActivity(intent);
    }
    
    public void chooseLogin(View view) {
    	Intent intent = new Intent(this, LoginActivity.class);
    	startActivity(intent);
    }
    
    public void createAccount(View view){
    	Intent intent = new Intent(this, CreateAccount.class);
    	startActivity(intent);
    }
}
