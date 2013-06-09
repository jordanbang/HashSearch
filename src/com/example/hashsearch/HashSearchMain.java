package com.example.hashsearch;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

public class HashSearchMain extends Activity {
	Tweet[] tweets;
	File cacheDir;
	TweetListAdapter tweetAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPref.initSharedPref(this);
		setContentView(R.layout.activity_has_search_main);
		
		RefreshContents();	
	}
	
	//parses the JSON array of results into Twitter Objects
	public void JSONParse(JSONArray results) throws JSONException, ParseException{
		tweets = new Tweet[results.length()];
		tweetAdapter = new TweetListAdapter(tweets,this);
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZ");
		for(int x = 0; x< results.length(); x++){
			JSONObject current = results.getJSONObject(x);
			tweets[x] = new Tweet();
			tweets[x].newTweet(current.getString("text"), 
					current.getString("from_user"), 
					current.getString("from_user_name"), 
					current.getString("profile_image_url"), 
					format.parse(current.getString("created_at")),
					tweetAdapter);
			tweets[x].loadImage(tweetAdapter);
		}
	}
	
	public void RefreshContents(){
		if (zGetVar("Search").equalsIgnoreCase("")){
			zSetVar("Search","bieber".toLowerCase(Locale.CANADA));
		}
		this.setTitle("");
		this.setTitle("Searching #"+zGetVar("Search").replace("#", ""));
		GetTweets back = new GetTweets("", "", null);
		JSONObject j=null;
		try {
			j = back.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		JSONArray ja=null;
		try {
			ja = j.getJSONArray("results");
			JSONParse(ja);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setTitle("#"+zGetVar("Search").replace("#", ""));
		ListView list = (ListView) findViewById(R.id_main.listView);
		list.setAdapter(tweetAdapter);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_has_search_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		int tm = item.getItemId();
		switch(tm){
		case R.id.menu_newhash:
			getNewHashTag();
			break;
		case R.id.menu_refresh:
			RefreshContents();
			break;
		}
		return true;
	}
	
	public void getNewHashTag(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Enter New Hashtag to Search");
		final EditText input = new EditText(this);
		input.setText("#");
		input.setSelection(input.getText().length());
		alert.setView(input);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String val= input.getText().toString();
				val = val.substring(1);
				zSetVar("Search", input.getText().toString());
				RefreshContents();
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {	
			}
		});
		alert.show();
	}
	

	public String zGetVar(String idName)
	{
	        return SharedPref.getVar(idName, "");
	}

    public void zSetVar(String idName, String newValue)
	{
	        SharedPref.setVar(idName, newValue);
	}
       
    public class GetTweets extends AsyncTask<String, String, JSONObject>{
    	List<NameValuePair> postparams = new ArrayList<NameValuePair>();
    	String URL = null;
    	String method = null;
    	InputStream is = null;
    	
    	public GetTweets(String url, String method, List<NameValuePair> params){
    		URL = url;
    		postparams = params;
    		this.method = method;
    	}
    	
    	
		@Override
		protected JSONObject doInBackground(String... params) {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String url = "http://search.twitter.com/search.json?q=%23"+zGetVar("Search")+"&result_type=mixed";
			HttpGet httpeg = new HttpGet(url);
			HttpResponse httpResponse;
			String json=null;
			try {
				httpResponse = httpClient.execute(httpeg);
				HttpEntity httpentity = httpResponse.getEntity();
				is = httpentity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null){
					sb.append(line+"\n");
				}
				is.close();
				json = sb.toString();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JSONObject jobj=null;
			try {
				jobj= new JSONObject(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jobj;
		}
		
    }

}
