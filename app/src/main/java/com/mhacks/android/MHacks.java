package com.mhacks.android;

import android.app.Application;

import com.bugsnag.android.Bugsnag;
import com.mhacks.android.data_old.model.Announcement;
import com.mhacks.android.data_old.model.Award;
import com.mhacks.android.data_old.model.CountdownItem;
import com.mhacks.android.data_old.model.Event;
import com.mhacks.android.data_old.model.EventType;
import com.mhacks.android.data_old.model.Location;
import com.mhacks.android.data_old.model.Map;
import com.mhacks.android.data_old.model.Sponsor;
import com.mhacks.android.data_old.model.SponsorTier;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import org.mhacks.android.R;

/**
 * Created by Omkar Moghe on 11/15/2014.
 */
public class MHacks extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Announcement.class);
        ParseObject.registerSubclass(Award.class);
        ParseObject.registerSubclass(CountdownItem.class);
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(EventType.class);
        ParseObject.registerSubclass(Location.class);
        ParseObject.registerSubclass(Sponsor.class);
        ParseObject.registerSubclass(SponsorTier.class);
        ParseObject.registerSubclass(Map.class);


        // enabling local data store causes weird 'ParseObject not found for update' error
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this,
                         getString(R.string.parse_application_id),
                         getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

        Bugsnag.init(this, getString(R.string.bugsnag_key));
    }

}
