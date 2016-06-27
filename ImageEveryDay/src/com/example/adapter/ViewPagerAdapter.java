package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{
	private List<View> views;
	private Context context;



	public ViewPagerAdapter(List<View> views, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.views = views;
	}



	@Override
	public void destroyItem(View container, int position, Object arg2) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(views.get(position));
	}



	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}



	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}



	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}



	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}



	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}
}
