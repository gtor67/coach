package com.example.coachme;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Favorites extends Activity {
 
   ListView listViewFavs ;
   private List<String> corresID = new ArrayList<String>();
   private int rowNum = 2; //Represents the row number in beginner table of selected fav row
   private String parseID = ""; //Represents ParseOBject ID of selected row
   public final static String EXTRA_ROWNUM = "com.example.coach.RowNum";
   
   @Override
public void onCreate(Bundle savedInstanceState){
   super.onCreate(savedInstanceState);
   setContentView(R.layout.favorites);
   initList();

   listViewFavs.setOnItemClickListener(new OnItemClickListener() {
   	  @Override
   	  public void onItemClick(AdapterView<?> parent, View view,
   	    int position, long id) {
   		  			Intent intent = new Intent(Favorites.this,Content.class);
   		  			
		  	        //String selRoutine = TrainingFilterActivity.advancedList[position]; //Get text of item
		  	        //String selRoutine = (String)parent.getAdapter().getItem(position);
   		  			parseID = Favorites.this.corresID.get(+position);
   		  			Log.d("ItemClick:Row Parse ID", parseID);
   		  			
   		  			Favorites.this.getRowNumandLaunch();
   		  		Log.d("Click:Real Row Num", "" + rowNum);
		  	        //intent.putExtra(EXTRA_ROWNUM, "" + rowNum + "d");
   		  			
		  	       // intent.putExtra("Origin", "Favorites");
		  	        
		  	        
		  	      //Log.d("FINAL TITLE","my title"+selRoutine);
	            	  //startActivity(intent);
   		  		/*
		  	          Toast.makeText(getApplicationContext(),
		  	            "Click Advance ListItem Number " + position, Toast.LENGTH_LONG)
		  	            .show();
		  	            */
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
           				Favorites.this, LoginActivity.class);
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
		getMenuInflater().inflate(R.menu.favorites, menu);
		
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
				
	    
	    // 3, From overflow menu, goes to the Team Settings page
       case R.id.action_team:

       	if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Coach.class));
		    	}
   	return true;
	    
	    // 4, From overflow menu, goes to the Help page
   	case R.id.action_help:
	    startActivity(new Intent(this, Help.class));
	    return true;
		    	
		// 5, From overflow menu, goes to the About page
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
	    
	    // 6, From overflow menu, Exits program
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
   
   public void initList()
   {
	 Log.d("INITLIST:","You are inside method");
	listViewFavs = (ListView) findViewById(R.id.listViewFavs);
	//now try to see if i can see what user has
	// first we will create a query on the Book object
	//ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User");
	ParseRelation<ParseObject> relation = ParseUser.getCurrentUser().getRelation("Favs"); 
	ParseQuery<ParseObject> query2 = relation.getQuery();
	query2.findInBackground(new FindCallback<ParseObject>() {
		  @Override
		public void done(List<ParseObject> favs, ParseException e) {
		    if (e == null) {
		    	int tableSize= favs.size();
		    	//Need to make list of strings out of list of ParseOBjects
		    	List<String> appList = new ArrayList<String>();
		       
		    	for(int i= 0; i < tableSize; i++){
		    		
		    		
		    		//Cursor exercise = myDb.getRow(Beginner1.get(i).getString("Title"));
		    		Log.d("Routine:", favs.get(i).getString("Title"));
		    		appList.add((i + 1) + "." + favs.get(i).getString("Title"));
		    		corresID.add(favs.get(i).getObjectId());
		    		Log.d("Object ID into list", favs.get(i).getObjectId());
		    ////////////TO REFRESH WITH PARSE		
		    		favs.get(i).fetchIfNeededInBackground(new GetCallback<ParseObject>() {
		    			  @Override
						public void done(ParseObject object, ParseException e) {
		    			    if (e == null) {
		    			      // Success!
		    			    } else {
		    			      // Failure!
		    			    }
		    			  }
		    			});
		    /////////////END REFRESH		
		    		
		    		
		    		
		    		
		    		}//for loop
		    //Need to now add list of strings to ListView
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Favorites.this,
		    		android.R.layout.simple_list_item_1, appList);
		    //maybe replace simple list_item_1?
		    listViewFavs.setAdapter(adapter);
		    Log.d("Empty?", "Error: " + corresID.isEmpty());
			   TextView tv = (TextView)findViewById(R.id.empty_textview);
			   LinearLayout ly = (LinearLayout) findViewById(R.id.favorites);
			   if(!(corresID.isEmpty()))
			   {
				   tv.setText("");
				   ly.setBackgroundColor(Color.WHITE);
			   }
			   else
				   {
				   		tv.setText(R.string.no_favorites);
				   		ly.setBackgroundColor(Color.DKGRAY);
				   	
				   }
		    } else {
		    	Log.d("ERROR", "Error: " + e.getMessage());
		      // something went wrong
		    		}
		  	}
			});
	
	
   }
   public void getRowNumandLaunch()
   {
	   //Need to find the row index by using selected item's parseID
	   int row;
	   ParseQuery<ParseObject> query = ParseQuery.getQuery("Beginner");
		query.orderByAscending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
		  @Override
		public void done(List<ParseObject> beginner1, ParseException e) {
		    if (e == null) {
		    	
		    	//Note: Local DB is 1 index ahead, but doesn't matter here since listPos starts at 0
		    	int tableSize = beginner1.size();
		  
		    	for(int i= 0; i < tableSize; i++)
		    	{
		    		if( (beginner1.get(i).getObjectId()).equals(parseID))
		    				{
		    					Favorites.this.rowNum = i;
		    					Log.d("GETROWNUM", "" + Favorites.this.rowNum);
		    					break;
		    				}
		    	}
		    	//Need to adjust rowNum due to local being ahead by 1
		    	Favorites.this.rowNum += 1;
		    	Log.d("GETROWNUM + 1", "" + Favorites.this.rowNum);
				Intent intent = new Intent(Favorites.this,Content.class);
				intent.putExtra(EXTRA_ROWNUM, "" + Favorites.this.rowNum + "d");
				intent.putExtra("Origin", "Favorites");
				startActivity(intent);

		    } else {
		    	Log.d("ERROR", "Error: " + e.getMessage());
		      // something went wrong
		    		}
		  	}
			});

   }
}