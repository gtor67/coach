package com.example.coachme;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Application;

public class parseInit extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, "eT6q3q5rgzDUgTAjRuTPAzyHSzvo7sbilbu9jqvU", "4TrzrKp78gLmGH0IcEB2pE4BtyIDz8siPrGszV6i");		
		PushService.setDefaultPushCallback(this, PushResponse.class);
		ParseAnalytics.trackAppOpened(null);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		
	}

}
