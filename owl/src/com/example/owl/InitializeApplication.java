package com.example.owl;
import android.app.Application;

import com.example.owl.model.Event;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

public class InitializeApplication extends Application {

		static final String TAG = "MyApp";

		@Override
		public void onCreate() {
			super.onCreate();

			ParseObject.registerSubclass(Event.class);
			Parse.initialize(this, "84A4VkKcDQCNDZWeCtIx62TtkoGufWUWlgEBNcR6", "NRg7OnjI3yqsGapBJ4ZczfkQEkCWd4Dqc9ufDpnV");
	        ParseFacebookUtils.initialize(getString(R.string.app_id));

	}

}
