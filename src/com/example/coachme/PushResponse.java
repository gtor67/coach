package com.example.coachme;

import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PushResponse extends Activity {
	private boolean scalingComplete = false;
	private TextView messView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_response);
		//Intent intent = this.getIntent();
		/*
		JSONObject json;
		try {
			json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
			String message = json.getString("alert");
			//TextView tv = (TextView)findViewById(R.id.pushedTextView);
			//tv.setText(message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		messView = (TextView) findViewById(R.id.textViewMessages);
		final Set<String> set = PushService.getSubscriptions(PushResponse.this.getApplicationContext());
		Log.d("All Channels",set.toString());
		if(set.contains("halos"))
			Log.d("Halos?", "Yes");
		ParseQuery<ParseObject> query =ParseQuery.getQuery("coaches");
		query.orderByAscending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> coach, ParseException e) {
				if (coach == null) {
				} else {
					Log.d("PUSHRESPONSE", "Found coach table");
					for(int i= 0; i < coach.size(); i++){
					final String teamTitle = coach.get(i).getString("name");
//					ParseInstallation pi = ParseInstallation.getCurrentInstallation();
//			        
//			        pi.saveEventually();
			       
					//check if player is already part of the team
					Log.d("Does set have ", "" + teamTitle);
					if(set.contains(teamTitle)){
						ParseRelation<ParseObject> relation = coach.get(i).getRelation("Messages");
						ParseQuery<ParseObject> messageSearch = relation.getQuery();
						messageSearch.orderByDescending("createdAt");
						messageSearch.findInBackground(new FindCallback<ParseObject>() 
								{
							  		@Override
							  		public void done(List<ParseObject> messList, ParseException e) 
							  		{
							  			if(e == null)
							  			{
							  				Log.d("PUSHRESPONSE", "Found messages");
							  				String current = "";
							  				if(messList.isEmpty())
							  					Log.d("PUSHRESPONSE", "There are no messages.");
											for(ParseObject pMessage:messList)
											{
												Log.d("Message", "" + pMessage.get("Message"));
												
												current = messView.getText().toString();
												String addedText = "Team: " + pMessage.get("Team") + "\nMessage: " + pMessage.get("Message") + "\n\n";
												String newText = current + addedText;
												messView.setText(newText);
												//messView.append("" + "Team: " + pMessage.get("Team") + "\nMessage: " + pMessage.get("Message") + "\n\n");
											}
							  			}
							  		}
								});
						/*
						try {
							List<ParseObject> messList = messageSearch.find();
							Log.d("PUSHRESPONSE", "Found messages");
							String current = "";
							for(ParseObject pMessage:messList)
							{
								Log.d("Message", "" + pMessage.get("Message"));
								
								current = messView.getText().toString();
								String addedText = "Team: " + pMessage.get("Team") + "\nMessage: " + pMessage.get("Message") + "\n\n";
								String newText = current + addedText;
								messView.setText(newText);
								//messView.append("" + "Team: " + pMessage.get("Team") + "\nMessage: " + pMessage.get("Message") + "\n\n");
							}
							
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						
					}
			
					
				}//end forloop
				}
			}
		});


	}
		@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	if (!scalingComplete) // only do this once
	{
		scaleContents(findViewById(R.id.pushContent), findViewById(R.id.scrollView1));
        scalingComplete = true;
	}
	     
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.push_response, menu);
		return true;
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
