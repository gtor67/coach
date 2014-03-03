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
        //myDb = new DBAdapter(this);
        //openDB();
        //filldb();
        //loadMylist();
        //this.deleteDatabase("Mydb");
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
		String focus1="This drill teaches the receiving position of the hands. It also provides an opportunity to teach athletes to use two hands when catching a ball";
		String focus2="To stress proper fielding technique for ground balls hit directly at the fielder, to his glove, and to his backhand side";
		String focus3="To learn to perform a controlled fall for a bent-leg slide";
		String procedure1 ="Any ball above the belt requires that the thumbs be brought closer together so that the fingers point up to the sky (photo a). This ensures that two hands are used to catch the ball. Any ball below the belt requires that the pinkies be brought together (fingers point down to the ground) (photo b).With the athletes facing you between the side-by-side cones, move through the receiving positions as a group. When you point up, the athletes should move their hands up, connecting their thumbs. When you point down, the athletes should flip their hands around so that the pinkies touch. The first part of this drill is simply to feel the hands turn over and move from �thumbs� to �pinkies.� The second part of the drill is to understand the concept of when to use each position�on a ball above the belt, use thumbs; on a ball below the belt, use pinkies";
		String procedure2 ="With the player standing at thier infield position the coach yes Set and the player gets in the ready position.  the coach then proceeds to  to hit short fungoes directly at the player who fields the ball and throws to first.  Continue the drill by hitting balls to the glove and backhand sides.  The goal of the players is to move their feet so they field the ball in front of thier bodies not allowing the players to reach for the ball with only their gloves.";
		String procedure3 ="the coach pulls the rug out form under the faller. to execute  this drill properly, the faller thorws his arms and head back when the rug is poulled out. ONe leg should bend underneath to cushion the fall which should leave the player in a sitting position at teh end of the fall teh top leg slightly flexed should remain four inches off the floor";
		
		myDb.insertRow("Beginner","Running"," " , " " , " " ,"www.br1.com");
		myDb.insertRow("Beginner","Catching",focus1 , procedure1 , "Fingers up thumbs down" ,"www.bc2.com");
		myDb.insertRow("Beginner","Batting"," " , " " , " " ,"www.bc3.com");
		myDb.insertRow("Beginner","Fielding"," " , " " , " " ,"www.bc4.com");
		myDb.insertRow("Beginner","bunting"," " , " " , " " ,"www.bc5.com");
		myDb.insertRow("Beginner","better Catching"," " , " " , " " ,"www.bc6.com");
		myDb.insertRow("Beginner","improve Bating"," " , " " , " " ,"www.ib7.com");		    	
			
		myDb.insertRow("intermedite","Bating"," " , " " , " " ,"www.ib1.com");		
		myDb.insertRow("intermedite","Catching"," " , " " , " " ,"www.ib3.com");		    	
		myDb.insertRow("intermedite","Running"," " , " " , " " ,"www.ib2.com");		    	
		myDb.insertRow("intermedite","Fielding",focus2 , procedure2 , "infield drill" ,"www.ib4.com");		    	
		myDb.insertRow("intermedite","better Catching"," " , " " , " " ,"www.ib5.com");		    	
		myDb.insertRow("intermedite","improve batting"," " , " " , " " ,"www.ib6.com");		    	
			
		myDb.insertRow("Advanted"," slideing",focus3 , procedure3 , "Rub Pull" ,"www.ar1.com");
		myDb.insertRow("Advanted"," batting"," " , " " , " " ,"www.ar2.com");
		myDb.insertRow("Advanted"," catching"," " , " " , " " ,"www.ar3.com");
		myDb.insertRow("Advanted"," fielding"," " , " " , " " ,"www.ar4.com");
		myDb.insertRow("Advanted"," batting"," " , " " , " " ,"www.ar5.com");
		  
	}
	@Override
    protected void onDestroy() {
     super.onDestroy(); 
     closeDB();
    }

    private void openDB() {
     //myDb = new DBAdapter(this);
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
