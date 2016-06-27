package com.example.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.activity.ImageActivity;
import com.example.activity.SetActivity;
import com.example.adapter.Life_ImageAdapter;
import com.example.imageeveryday.R;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Lomo_fragment extends Fragment{
	private String lomo_url = "http://photo.poco.cn/like/index-upi-p-1-tpl_type-hot-channel_id-5.html#list";
	private String lomo_url1 = "http://photo.poco.cn/like/index-upi-p-";
	private String lomo_url2 = "-tpl_type-hot-channel_id-5.html#list";
	private int lomo_page_index = 1;
	private TextView nextPager;
	private TextView lastPager;
	private GridView gridview = null;
	private TextView pagerNum;

	private ImageView set_imageView;

	private List<String> url_list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.lomo_fragment, null);
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridview = (GridView)getActivity().findViewById(R.id.lomo_gridview);
		lastPager = (TextView)getActivity().findViewById(R.id.lomo_lastpage);
		nextPager = (TextView)getActivity().findViewById(R.id.lomo_nextpager);
		pagerNum = (TextView)getActivity().findViewById(R.id.lomo_pageindex);
		pagerNum.setText("1/100页");
		
		lastPager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lomo_page_index <= 1){
					lomo_page_index = 100;
					String newUrl = lomo_url1 + lomo_page_index + lomo_url2;
					loadImage(newUrl);
					pagerNum.setText(lomo_page_index + "/100页");
					//Toast.makeText(getActivity(), "已经是第一页了", 1).show();
				}else{

					lomo_page_index = lomo_page_index - 1;
					String newUrl = lomo_url1 + lomo_page_index + lomo_url2;
					loadImage(newUrl);
					pagerNum.setText(lomo_page_index + "/100页");
				}
			}
		});
		nextPager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lomo_page_index >=100){
					lomo_page_index = 1;
					String newUrl = lomo_url1 + lomo_page_index + lomo_url2;
					loadImage(newUrl);
					pagerNum.setText(lomo_page_index + "/100页");
					//Toast.makeText(getActivity(), "已经是最后一页了", 1).show();;
				}else{
					lomo_page_index = lomo_page_index + 1;

					String newUrl = lomo_url1 + lomo_page_index + lomo_url2;
					loadImage(newUrl);
					pagerNum.setText(lomo_page_index + "/100页");
				}
			}
		});
		loadImage(lomo_url);
	}



	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		//loadImage(life_url);
	}



	private void loadImage(String lomo_url3) {
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, List<String>>(){

			@Override
			protected List<String> doInBackground(String... params) {
				// TODO Auto-generated method stub
				Document doc;
				List<String> lists = new ArrayList<String>();
				try {
					doc = Jsoup.connect(params[0]).timeout(5000).get();
					Elements elements = doc.select("div.img-box");
					String result = "";
					for (Element ele:elements){
						String title = ele.select("a[href]").first().attr("href");
						//result = result + "title:" + title + "/n";
						lists.add(title);
						String image_url = ele.select("img[src]").first().attr("src");
						lists.add(image_url);
						//Log.i("info", image_url);
						//Log.i("info", title);
					}

					return lists;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return lists;
				}

			}
			/**
			 * 提交参数
			 * */
			protected void onPostExecute(java.util.List<String> result) {
				/**
				 * 调用方法，设置适配器，加载图片
				 * */
				lifeImageSetAdapter(getActivity().getApplicationContext(),result);
			};
		}.execute(lomo_url3);
	}

	private void lifeImageSetAdapter(Context context, List<String> result) {
		// TODO Auto-generated method stub
		gridview.setAdapter(new Life_ImageAdapter(getActivity(), result));
		url_list = result;
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if(url_list != null && !url_list.isEmpty()){
					Intent intent = new Intent(getActivity().getApplicationContext(),ImageActivity.class);
					intent.putExtra("images", url_list.get(position * 2));
					startActivity(intent);
				}
			}
		});
	}
}
