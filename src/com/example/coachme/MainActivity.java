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
        myDb = new DBAdapter(this);
        openDB();
        filldb();
        myDb.getAllRows();
       // loadMylist();
        
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
		String procedure1 ="Any ball above the belt requires that the thumbs be brought closer together so that the fingers point up to the sky (photo a). This ensures that two hands are used to catch the ball. Any ball below the belt requires that the pinkies be brought together (fingers point down to the ground) (photo b).With the athletes facing you between the side-by-side cones, move through the receiving positions as a group. When you point up, the athletes should move their hands up, connecting their thumbs. When you point down, the athletes should flip their hands around so that the pinkies touch. The first part of this drill is simply to feel the hands turn over and move from �thumbs� to �pinkies.� The second part of the drill is to understand the concept of when to use each position�on a ball above the belt, use thumbs; on a ball below the belt, use pinkies";
		String procedure2 ="With the player standing at thier infield position the coach yes Set and the player gets in the ready position.  the coach then proceeds to  to hit short fungoes directly at the player who fields the ball and throws to first.  Continue the drill by hitting balls to the glove and backhand sides.  The goal of the players is to move their feet so they field the ball in front of thier bodies not allowing the players to reach for the ball with only their gloves.";
		String procedure3 ="the coach pulls the rug out form under the faller. to execute  this drill properly, the faller thorws his arms and head back when the rug is poulled out. ONe leg should bend underneath to cushion the fall which should leave the player in a sitting position at teh end of the fall teh top leg slightly flexed should remain four inches off the floor";
		
		myDb.insertRow("Beginner","Running"," " , " " , " " ,"www.br1.com");
		myDb.insertRow("Beginner","Catching",focus1 , procedure1 , "Fingers up thumbs down" ,"www.bc2.com");
		myDb.insertRow("Beginner","Batting"," " , " " , " " ,"www.bc3.com");
		myDb.insertRow("Beginner","Fielding"," " , " " , " " ,"www.bc4.com");
		myDb.insertRow("Beginner","Bunting"," " , " " , " " ,"www.bc5.com");
		myDb.insertRow("Beginner","Catching"," " , " " , " " ,"www.bc6.com");
		myDb.insertRow("Beginner","Batting"," " , " " , " " ,"www.ib7.com");		    	
			
		myDb.insertRow("Intermedite","BatTing"," " , " " , " " ,"www.ib1.com");		
		myDb.insertRow("Intermedite","Catching"," " , " " , " " ,"www.ib3.com");		    	
		myDb.insertRow("Intermedite","Running"," " , " " , " " ,"www.ib2.com");		    	
		myDb.insertRow("Intermedite","Fielding",focus2 , procedure2 , "infield drill" ,"www.ib4.com");		    	
		myDb.insertRow("Intermedite","Catching"," " , " " , " " ,"www.ib5.com");		    	
		myDb.insertRow("Intermedite","Batting"," " , " " , " " ,"www.ib6.com");		    	
			
		myDb.insertRow("Advanced"," Sliding",focus3 , procedure3 , "Rub Pull" ,"www.ar1.com");
		myDb.insertRow("Advanced"," Batting"," " , " " , " " ,"www.ar2.com");
		myDb.insertRow("Advanced"," Catching"," " , " " , " " ,"www.ar3.com");
		myDb.insertRow("Advanced"," Fielding"," " , " " , " " ,"www.ar4.com");
		myDb.insertRow("Advanced"," Batting"," " , " " , " " ,"www.ar5.com");
		  
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
