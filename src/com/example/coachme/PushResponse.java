package com.example.coachme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class PushResponse extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_response);
		Intent intent = this.getIntent();
		String message = intent.toURI();
		TextView tv = (TextView)findViewById(R.id.pushedTextView);
		tv.setText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.push_response, menu);
		return true;
	}

}
