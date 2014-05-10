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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Favorites extends Activity {
 
   ListView listViewFavs ;
   private List<String> corresID = new ArrayList<String>();
   private int rowNum = 2; //Represents the row number in beginner table of selected fav row
   private String parseID = ""; //Represents ParseOBject ID of selected row
   public final static String EXTRA_ROWNUM = "com.example.coach.RowNum";
   	private boolean scalingComplete = false;
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

   	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	if (!scalingComplete) // only do this once
	{
		scaleContents(findViewById(R.id.favContent), findViewById(R.id.favFrame));
        scalingComplete = true;
	}
	     
		super.onWindowFocusChanged(hasFocus);
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
		
		return true;
	}

	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

	    // 1, From overflow menu, goes to the messages page
	 	case R.id.action_push:
	 			if(ParseUser.getCurrentUser()==null){
	 				displayAlert();
	 			}  
	 			else{ 
	 				startActivity(new Intent(this, PushResponse.class));
	 				}
	 		return true;
				
	    
	    // 2, From overflow menu, goes to the Team Settings page
       case R.id.action_team:

       	if(ParseUser.getCurrentUser()==null){
				displayAlert();
		   }  
		
		    else{ 
		    	startActivity(new Intent(this, Coach.class));
		    	}
   	return true;
	    
	    // 3, From overflow menu, goes to the Help page
   	case R.id.action_help:
	    startActivity(new Intent(this, Help.class));
	    return true;
		    	
		// 4, From overflow menu, goes to the About page
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
	    
	    // 5, From overflow menu, Exits program
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
			   RelativeLayout ly = (RelativeLayout) findViewById(R.id.favContent);
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
			textView.setTextSize(textView.getTextSize() * scale);
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