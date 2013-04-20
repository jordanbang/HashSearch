package com.example.hashsearch;

import java.util.Date;

public class Tweet {
	private String text;
	private Date date;
	private String twitter_handle;
	private String name;
	private String pic_url;
	
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
	
	public void newTweet(String text_n, String handle, String name_n, String pic, Date created){
		setText(text_n);
		setHandle(handle);
		setName(name_n);
		pic = pic.replace("normal", "bigger");
		setPic(pic);
		setDateCreated(created);
	}
	
}
