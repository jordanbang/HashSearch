package com.example.hashsearch;

import android.app.Activity;
import android.content.SharedPreferences;

public class SharedPref {
	public static SharedPreferences settings;
	public static SharedPreferences.Editor editor;
	
	public static void initSharedPref(Activity current){
		settings = current.getSharedPreferences("hashsearch",0);
		editor = settings.edit();
	}
	
	public static String getVar(String VarName, String DefValue)
	{
		return settings.getString(VarName, DefValue);
	}

	public static void setVar(String VarName, String nValue)
	{
	    editor.putString(VarName, nValue);
	    editor.commit();    
	}
}
