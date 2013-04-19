package com.example.hashsearch;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HashSearchMain extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPref.initSharedPref(this);
		if (zGetVar("Search").equalsIgnoreCase("")){
			zSetVar("Search","bieber".toLowerCase(Locale.CANADA));
		}
		this.setTitle("#"+zGetVar("Search"));
		setContentView(R.layout.activity_has_search_main);
		
		
//		try {
//			URL twitter = new URL("http://search.twitter.com/search.json?q=%23"+zGetVar("Search").toLowerCase());
//			URLConnection tc = twitter.openConnection();
//			BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
//			String line;
//			while ((line=in.readLine())!= null){
//				JSONArray ja = new JSONArray(line);
//				for(int i = 0; i <ja.length();i++){
//					JSONObject jo = (JSONObject) ja.get(i);
//					int x = 0;
//				}
//			}
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_has_search_main, menu);
		return true;
	}

	public String zGetVar(String idName)
	{
	        return SharedPref.getVar(idName, "");
	}

    public void zSetVar(String idName, String newValue)
	{
	        SharedPref.setVar(idName, newValue);
	}

}
