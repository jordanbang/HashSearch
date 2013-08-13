package com.jordanbang.hashsearch;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class Tweet {
	private String text;
	private Date date;
	private String twitter_handle;
	private String name;
	private String pic_url;
	private Bitmap bit;
	private TweetListAdapter ada;
	
	public String getText(){
		return text;
	}
	
	public String getHandle(){
		return twitter_handle;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPic(){
		return pic_url;
	}
	
	public Date getCreated(){
		return date;
	}
	
	public Bitmap getBitmap(){
		return bit;
	}
	
	public TweetListAdapter getAdapter(){
		return ada;
	}
	
	public void setText(String text_n){
		text = text_n;
	}
	
	public void setHandle(String handle){
		twitter_handle = handle;
	}
	
	public void setName(String name_n){
		name = name_n;
	}
	
	public void setPic(String pic){
		pic_url = pic;
	}
	
	public void setDateCreated(Date created){
		date = created;
	}
	
	public void setBitmap(Bitmap bit_n){
		bit = bit_n;
	}
	
	public void setAdapter(TweetListAdapter sada){
		ada = sada;
	}
	
	public void newTweet(String text_n, String handle, String name_n, String pic, Date created, TweetListAdapter sada){
		setText(text_n);
		setAdapter(sada);
		setHandle(handle);
		setName(name_n);
		pic = pic.replace("normal", "bigger");
		setPic(pic);
		setDateCreated(created);
		setBitmap(null);
	}
	
	public void loadImage(TweetListAdapter sada){
		ada = sada;
		if (pic_url !=null && !pic_url.equals("")){
			new ImageLoadTask().execute(pic_url);
		}
	}
	
	private class ImageLoadTask extends AsyncTask<String, String, Bitmap>{
		
		@Override
		protected Bitmap doInBackground(String... params) {
			try{
				URL url = new URL(params[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap b = BitmapFactory.decodeStream(input);
				return b;
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
		protected void onPostExecute(Bitmap ret){
			if(ret!=null){
				bit = ret;
				if(ada!=null){
					ada.notifyDataSetChanged();
				}
			}
		}
		
	}
	
}
