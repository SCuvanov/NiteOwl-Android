package com.owl.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Event")
public class Event extends ParseObject {
	// methods to interface with an Event Object stored in Parse DB
	
	public Event(){
		
	}

	public String getTitle() {
		return getString("title");
	}

	public void setTitle(String title) {
		put("title", title);
	}

	public String getDesc() {
		return getString("desc");
	}

	public void setDesc(String desc) {
		put("desc", desc);
	}

	public String getTime() {
		return getString("time");
	}

	public void setTime(String time) {
		put("time", time);
	}

	public String getDate() {
		return getString("date");
	}

	public void setDate(String date) {
		put("date", date);
	}

	public ParseUser getUser() {
		return getParseUser("user");
	}

	public void setUser(ParseUser value) {
		put("user", value);
	}

	public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }
 
    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

	public static ParseQuery<Event> getQuery() {
		return ParseQuery.getQuery(Event.class);
	}

}
