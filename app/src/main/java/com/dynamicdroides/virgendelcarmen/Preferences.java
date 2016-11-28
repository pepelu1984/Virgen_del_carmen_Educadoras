package com.dynamicdroides.virgendelcarmen;

import android.app.Activity;
import android.content.SharedPreferences;

public class Preferences 
{
	
	private static final String PREFERENCES_NAME = "com.dynamicdroides.virgendelcarmen.Preferences";
	public static final String PREF_USERNAME = "com.dynamicdroides.virgendelcarmen.Preferences.username";
	public static final String PREF_PASSWORD = "com.dynamicdroides.virgendelcarmen.Preferences.password";
	public static final String PREF_FULLNAME = "com.dynamicdroides.virgendelcarmen.Preferences.fullname";
	public static final String PREF_VIEWMODE = "com.dynamicdroides.virgendelcarmen.Preferences.viewMode";
	
	Activity activity;

	public Preferences(Activity activity) 
	{
		this.activity = activity;
	}

	public void put(String key, String value)
	{
		SharedPreferences settings = activity.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		if (value == null)
			editor.remove(key);
		else
			editor.putString(key, value);
		editor.commit();
	}
	
	public String get(String key)
	{
		SharedPreferences settings = activity.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		return settings.getString(key, null);
	}
	
}
