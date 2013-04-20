package com.example.hashsearch;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetListAdapter extends BaseAdapter {
	private Tweet[] data;
	Context context;
	
	TweetListAdapter (Tweet[] data_n, Context c){
		data = data_n;
		context = c;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int arg0) {
		return data[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		View v = arg1;
		if(v==null){
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.tweet_list_view, null);
		}
		Tweet tweet = (Tweet)data[arg0];
		((TextView)v.findViewById(R.id.tweet_handle)).setText("@"+tweet.getHandle());
		((TextView)v.findViewById(R.id.tweet_name)).setText(tweet.getName());
		((TextView)v.findViewById(R.id.tweet_text)).setText(tweet.getText());
		((TextView)v.findViewById(R.id.tweet_date)).setText(format.format(tweet.getCreated()));
		new DownloadImageTask((ImageView)v.findViewById(R.id.tweet_pic)).execute(tweet.getPic());
		return v;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
		ImageView bmImage;
		public DownloadImageTask(ImageView bmImage){
			this.bmImage=bmImage;
		}
		
		protected Bitmap doInBackground (String... urls){
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try{
				InputStream in = new URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			}catch(Exception e){
				e.printStackTrace();
			}
			return mIcon11;
		}
		
		protected void onPostExecute(Bitmap result){
			bmImage.setImageBitmap(result);
		}
	}
	
}
