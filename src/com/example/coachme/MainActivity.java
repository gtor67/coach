package com.example.coachme;

import java.util.List;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        myDb = new DBAdapter(this);
        openDB();
     
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
        filldb();// makes it only run once
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstTime", true);
        editor.commit();
        }
        
        //String title = "Fingers up thumbs down";
        //Cursor exercise = myDb.getRow(title);
        //String word = exercise.getString(1);
        check();
        myDb.getAllRows();
       // loadMylist();
        
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
        	Log.d("User logged in ", ""+currentUser.getUsername());
        	View lOut =findViewById(R.id.buttonLogout);
        	lOut.setVisibility(View.VISIBLE);
        	View lIn =findViewById(R.id.button1Login);
        	lIn.setVisibility(View.INVISIBLE);
        	View crAcc =findViewById(R.id.createAccButton);
        	crAcc.setVisibility(View.INVISIBLE);
        	
        	View settings = findViewById(R.id.settingsButton);
        	settings.setVisibility(View.VISIBLE);
        	
        } else {
        	Log.d("User  is logged out ", ""+currentUser);
        	View lOut = findViewById(R.id.buttonLogout);
        	lOut.setVisibility(View.INVISIBLE);
        	View lIn = findViewById(R.id.button1Login);
        	lIn.setVisibility(View.VISIBLE);
        	View crAcc = findViewById(R.id.createAccButton);
        	crAcc.setVisibility(View.VISIBLE);
        	
        	View settings = findViewById(R.id.settingsButton);
        	settings.setVisibility(View.INVISIBLE);
        	
        }
    }
    
    private void loadMylist() {
  		// TODO Auto-generated method stub
  		List<String> list = myDb.listdata("Beginner","All");
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
		String procedure1 ="Any ball above the belt requires that the thumbs be brought closer together so that the fingers point up to the sky (photo a). This ensures that two hands are used to catch the ball. Any ball below the belt requires that the pinkies be brought together (fingers point down to the ground) (photo b).With the athletes facing you between the side-by-side cones, move through the receiving positions as a group. When you point up, the athletes should move their hands up, connecting their thumbs. When you point down, the athletes should flip their hands around so that the pinkies touch. The first part of this drill is simply to feel the hands turn over and move from ���thumbs��� to ���pinkies.��� The second part of the drill is to understand the concept of when to use each position���on a ball above the belt, use thumbs; on a ball below the belt, use pinkies";
		String procedure2 ="With the player standing at thier infield position the coach yells Set and the player gets in the ready position.  the coach then proceeds to  to hit short fungoes directly at the player who fields the ball and throws to first.  Continue the drill by hitting balls to the glove and backhand sides.  The goal of the players is to move their feet so they field the ball in front of thier bodies not allowing the players to reach for the ball with only their gloves.";
		String procedure3 ="the coach pulls the rug out form under the faller. to execute  this drill properly, the faller thorws his arms and head back when the rug is poulled out. ONe leg should bend underneath to cushion the fall which should leave the player in a sitting position at teh end of the fall teh top leg slightly flexed should remain four inches off the floor";
		String focus4= "Working on hand eye corrdination";
		String procedure4 ="This one is essay just instruct the child to throw the ball up into the air and have then catch the ball using the basket catch( the glove in front of the body and facing palm up)";
		String focus5= "Practice catching a ball thrown by someone else";
		String procedure5 ="Position the child in different position and toss him the ball underhanded making him reach to either side to catch the ball";
		String focus6= "this is used to teach the mechanics and the timing required to be a successfull bunter";
		String procedure6 ="This drill does not use a drill. This drill requires a person to pretent to be a pitcher.  this person will pretend to throw a pitch going throu the whole motion a real pitcher users  the player will then work on the motion of getting ready to bunt while timing the imaginary pitch.";
		String focus7 = "Work on bunting the ball to a certain location";
		String procedure7 ="This drill requiera bit of a setup of four cones and having the bunter try to bunt the ball in between two of the four logs when insturctred view picture to see the setup of the cones";
		String focus8= "This is used to help futher develop hand-eye coordination, quick reaction and the value of knocking down the ball";
		String procedure8 ="The coach will instruct the player to get in position at whatever base they are and start hitting or simulating a player hitting grounders.  The player then has to stop the balls however they can if they are unable to catch the ball they should try to bat the ball down with their glove";
		String focus9= "Drill used to develop reaction to pitches in different locations in the strike zone.";
		String procedure9 ="This drill requires that the coach be in a position where he can pitch two balls at a time." +
				"the coach will instruct the batter which ball he is to swing at while the balls have been pitched. (Ususally high or low but can be inside outside)";
		String focus10= "This is used to emphsis the importance of hitting the ball to the ground as these types of hits are harder to deffined";
		String procedure10 ="To run this drill the coach will instruct the player to hit a ground ball only giving the player praise when he hits the ball on the ground.  If the player hits a popup then he is reminded to swing down instead of up which cased the popup";
		myDb.insertRow("Beginner","Running"," " , " " , "asdflj" ,"www.br1.com");
		myDb.insertRow("Beginner","Catching",focus1 , procedure1 , "Fingers up thumbs down" ,"http://youtu.be/Hu-qaaVLmNo");
		myDb.insertRow("Beginner","Batting"," " , " " , "Swing Down" ,"www.bc3.com");
		myDb.insertRow("Beginner","Fielding"," " , " " , "66 " ,"www.bc4.com");
		myDb.insertRow("Beginner","Bunting",focus6 , procedure6 , "Sacrifice bunting " ,"http://youtu.be/-l7Hi8IBtNg");
		myDb.insertRow("Beginner","Catching",focus4 , procedure4 , "Self Toss " ,"http://youtu.be/S7lhOwxFoqo");
		myDb.insertRow("Beginner","Catching",focus5 , procedure5 , "Play Toss " ,"http://youtu.be/uLuPbyGGXOk");		    	
			
		myDb.insertRow("Intermediate","Buntung",focus7 , procedure7 , " Aim Game" ,"http://youtu.be/r0BYBlkgRsg");		
		myDb.insertRow("Intermediate","Catching"," " , " " , "777 " ,"www.ib3.com");		    	
		myDb.insertRow("Intermediate","Running"," " , " " , "888 " ,"www.ib2.com");		    	
		myDb.insertRow("Intermediate","Fielding",focus2 , procedure2 , "infield drill" ,"http://youtu.be/7BD-zfEqyF4");		    	
		myDb.insertRow("Intermediate","Catching"," " , " " , "999 " ,"www.ib5.com");		    	
		myDb.insertRow("Intermediate","Batting"," " , " " , "9999 " ,"www.ib6.com");	
		myDb.insertRow("Intermediate","Batting"," " , " " , "99999 " ,"www.ib6.com");
			
		myDb.insertRow("Advanced"," Sliding",focus3 , procedure3 , "Rub Pull" ,"http://youtu.be/8kiiOXW41kI");
		myDb.insertRow("Advanced"," Batting",focus9 , procedure9 , "Hi Low Hitting" ,"http://youtu.be/FaHo2XqNSWU");
		myDb.insertRow("Advanced"," Catching"," " , " " , "456 " ,"www.ar3.com");
		myDb.insertRow("Advanced"," Fielding",focus8 , procedure8 , "Pepper " ,"http://youtu.be/U2lqU2ESp9Q");
		myDb.insertRow("Advanced"," Batting",focus10 , procedure10 , "Swing Low " ,"http://youtu.be/VMHWBi1efJQ");
		myDb.insertRow("Advanced"," Batting"," " , " " , "1012 " ,"www.ar5.com");
		myDb.insertRow("Advanced"," Batting"," " , " " , "1013 " ,"www.ar5.com");
		  
	}
	 public void check() {
			
		 	//myDb.insertRow("Beginner","Running"," " , " " , " " ,"www.br1.com");
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Beginner");
			query.orderByAscending("createdAt");
			query.findInBackground(new FindCallback<ParseObject>() {
			  public void done(List<ParseObject> Beginner1, ParseException e) {
			    if (e == null) {
			    	int tableSize= Beginner1.size();
			    	//tableSize = 4; //we only have 4 rows to update
			    	//Cursor c = myDb.getAllRows();
			    	boolean test = true;
			    	//String title = "Fingers up thumbs down";
			       
			    	for(int i= 0; i < tableSize; i++){
			    		int c=i+1;
			    		 Cursor exercise = myDb.getRow(c);
			    		//Cursor exercise = myDb.getRow(Beginner1.get(i).getString("Title"));
			    		test=true;
			    		Log.d("TEST mydb","variable I " +i);
			    		Log.d("TEST mydb","" +exercise.getString(5));
			    		Log.d("TEST parse","" +Beginner1.get(i).getString("Title"));
			    		
			    ////////////TO REFRESH WITH PARSE		
			    		Beginner1.get(i).fetchIfNeededInBackground(new GetCallback<ParseObject>() {
			    			  public void done(ParseObject object, ParseException e) {
			    			    if (e == null) {
			    			      // Success!
			    			    } else {
			    			      // Failure!
			    			    }
			    			  }
			    			});
			    /////////////END REFRESH		
			    		
			    		
			    		if( Beginner1.get(i).getString("Level").equals(exercise.getString(1))==false){
			    		test=false;	
			    		}
			    		else if( Beginner1.get(i).getString("Type").equals(exercise.getString(2))==false){
				    		test=false;	
				    		}
			    		else if( Beginner1.get(i).getString("Focus").equals(exercise.getString(3))==false){
				    		test=false;	
				    		}
			    		else if( Beginner1.get(i).getString("Procedure").equals(exercise.getString(4))==false){
				    		test=false;	
				    		}
			    		else if( Beginner1.get(i).getString("Title").equals(exercise.getString(5))==false){
				    		test=false;	
				    		}
			    		else if( Beginner1.get(i).getString("VideoURL").equals(exercise.getString(6))==false){
				    		test=false;	
				    		}
			    		
			    		if(test==false)
			    		{
			    			Log.d("TEST update","update row " + i );
			    			Log.d("TEST update","update row c" + c );
			    			myDb.updateRow(c, Beginner1.get(i).getString("Level"), Beginner1.get(i).getString("Type"), Beginner1.get(i).getString("Focus"), Beginner1.get(i).getString("Procedure"), Beginner1.get(i).getString("Title"), Beginner1.get(i).getString("VideoURL"));
			    		}
			    		Log.d("TEST LOOP","" + i );
			    		Log.d("TEST V","" + test);
			    		Log.d("TEST end mydb","" +exercise.getString(5));
			    		Log.d("TEST end parse","" +Beginner1.get(i).getString("Title"));
			    		
			    		}//for loop
			    		
			    		//Log.d("TEST PARSE",  " " +row.getString("Type"));
			    		
			    	
//			    	ParseObject row=	Beginner1.get(0);
//			    	Log.d("TEST PARSE",  " " +row.getString("Type"));

			      
			    } else {
			    	Log.d("ERROR", "Error: " + e.getMessage());
			      // something went wrong
			    		}
			  	}
				});

			}//end check
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    	    @Override
    	    public boolean onOptionsItemSelected(MenuItem item) {
    	    	// Handle item selection
    	    	switch (item.getItemId()) {
    	    	
    	    	/**
    	    	// From overflow menu, goes to Sign-in page
    	    	case R.id.action_login:
    	    	startActivity(new Intent(this,LoginActivity.class));
    	    	return true;
    	    	*/
    	    	
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
    	    	startActivity(new Intent(this, Favorites.class));
    	    	return true;
        	    
        	    // 4, From overflow menu, goes to the Help page
    	    	case R.id.action_help:
        	    startActivity(new Intent(this, Help.class));
        	    return true;
    	    	
        	    // 5, From overflow menu, goes to the About page
    	    	case R.id.action_about:
        	    startActivity(new Intent(this, About.class));
        	    return true;
    	    	default:
    	    	return super.onOptionsItemSelected(item);
    	    	}
    	    	}
    
   /** Called when the user clicks the Baseball button */
   public void chooseBaseball(View view) {
   
   // Baseball Button Sound
   final MediaPlayer baseballSound = MediaPlayer.create(MainActivity.this, R.raw.baseballsound);
   
   Intent intent = new Intent(this, TrainingFilterActivity.class);
   startActivity(intent);
   baseballSound.start();
   }

    public void chooseLogin(View view) {
    	Intent intent = new Intent(this, LoginActivity.class);
    	startActivity(intent);
    }
    
    public void createAccount(View view){
    	Intent intent = new Intent(this, CreateAccount.class);
    	startActivity(intent);
    }
    
    public void logOut(View view){
    	ParseUser.logOut();
    	finish();
    	startActivity(getIntent());
    	
    }
    
    public void chooseSettings(View view){
    	Intent intent = new Intent(this, AccSettings.class);
    	startActivity(intent);
    }
}
