package com.example.adapter;

import java.util.List;

import com.example.imageeveryday.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Life_ImageAdapter extends BaseAdapter{

	private Context mContext;
	private List<String> life_url;
	private DisplayImageOptions options;

	public Life_ImageAdapter(Context c, List<String> life_url) {
		mContext = c;
		this.life_url = life_url;
		options = new DisplayImageOptions.Builder()  
		.showImageOnLoading(R.drawable.result)  
		.showImageOnFail(R.drawable.ic_launcher)  
		.cacheInMemory(true)  
		.cacheOnDisk(true)  
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	public int getCount() {
		return 18;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		WindowManager manager = (WindowManager)parent.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		ImageView imageView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(width/3-5, width/3-5));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		try {
			ImageLoader.getInstance().displayImage(life_url.get(position * 2 + 1) , imageView, options);
		} catch (Exception e) {
			// TODO: handle exception
			imageView.setImageResource(R.drawable.landfail_image);
		}

		// imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}
}
