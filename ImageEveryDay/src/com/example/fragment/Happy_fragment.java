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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Happy_fragment extends Fragment{
	private String happy_url = "http://www.duzhebao.com/tupian/1.htm";
	private String happy_url1 = "http://www.duzhebao.com/tupian/";
	private String happy_url2 = ".htm";
	private int happy_page_index = 1;
	private TextView nextPager;
	private TextView lastPager;
	private GridView gridview = null;
	private List<String> url;
	private TextView pagerNum;
	private ImageView set_imageView;
	private int width;
	private TextView happy01, happy02,happy03,happy04,happy05;
	private ImageView happy_image01, happy_image02, happy_image03,happy_image04,happy_image05;
	private DisplayImageOptions options;	//加载图片的属性设置对象
	private List<String> url_list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.happy_fragment, null);
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		options = new DisplayImageOptions.Builder()  //设置land——image——插件的加载属性
		.showImageOnLoading(R.drawable.landing_image)  
		.showImageOnFail(R.drawable.landfail_image)
		.cacheInMemory(true)  
		.cacheOnDisk(true)  
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		lastPager = (TextView)getActivity().findViewById(R.id.happy_lastpage);
		nextPager = (TextView)getActivity().findViewById(R.id.happy_nextpager);
		pagerNum = (TextView)getActivity().findViewById(R.id.happy_pageindex);

		pagerNum.setText("1/386页");

		happy01 = (TextView)getActivity().findViewById(R.id.happy_01);
		happy02 = (TextView)getActivity().findViewById(R.id.happy_02);
		happy03 = (TextView)getActivity().findViewById(R.id.happy_03);
		happy04 = (TextView)getActivity().findViewById(R.id.happy_04);
		happy05 = (TextView)getActivity().findViewById(R.id.happy_05);
		happy_image01 = (ImageView)getActivity().findViewById(R.id.happy_image1);
		happy_image02 = (ImageView)getActivity().findViewById(R.id.happy_image2);
		happy_image03 = (ImageView)getActivity().findViewById(R.id.happy_image3);
		happy_image04 = (ImageView)getActivity().findViewById(R.id.happy_image4);
		happy_image05 = (ImageView)getActivity().findViewById(R.id.happy_image5);


		lastPager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (happy_page_index <= 1){
					happy_page_index = 386;
					String newUrl = happy_url1 + happy_page_index + happy_url2;
					loadImage(newUrl);
					//Toast.makeText(getActivity(), "已经是第一页了", 1).show();
					pagerNum.setText(happy_page_index + "/386页");
				}else{

					happy_page_index = happy_page_index - 1;

					String newUrl = happy_url1 + happy_page_index + happy_url2;
					loadImage(newUrl);
					pagerNum.setText(happy_page_index + "/386页");
				}
			}
		});
		nextPager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (happy_page_index >=386){
					happy_page_index = 1;
					String newUrl = happy_url1 + happy_page_index + happy_url2;
					loadImage(newUrl);
					pagerNum.setText(happy_page_index + "/386页");
					//Toast.makeText(getActivity(), "已经是最后一页了", 1).show();;
				}else{
					happy_page_index = happy_page_index + 1;

					String newUrl = happy_url1 + happy_page_index + happy_url2;
					/**
					 * 搞笑图片加载区域
					 * */
					loadImage(newUrl);
					pagerNum.setText(happy_page_index + "/386页");
				}
			}
		});
		try {
			loadImage(happy_url);
			//loadImage(happy_url);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}




	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();


	}



	private void loadImage(String happy_url3) {	//查找图片的介质url
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, List<String>>(){

			@Override
			protected List<String> doInBackground(String... params) {
				// TODO Auto-generated method stub
				Document doc;
				List<String> lists = new ArrayList<String>();
				try {
					doc = Jsoup.connect(params[0]).timeout(5000).get();
					Elements elements = doc.select("div.hd");
					//String result = "";
					for (Element ele:elements){
						String title = ele.html();
						//result = result + "title:" + title + "/n";
						lists.add(title);

					}


					Elements elements1 = doc.select("div.module");
					//String result = "";
					Element ele = elements1.get(0);
					Elements eless = ele.select("li");
					for (Element e:eless){
						String imageurl = e.select("img[src]").attr("src");
						//result = result + "title:" + title + "/n";
						lists.add(imageurl);
					}
					lists.add(elements.size() + "");
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
			@SuppressWarnings("unchecked")
			protected void onPostExecute(java.util.List<String> result) {
				/**
				 * 调用方法，设置适配器，加载图片
				 * */

				try {
					int x, y;
					int rate;
					happy01.setText(result.get(0));
					happy02.setText(result.get(1));
					happy03.setText(result.get(2));
					happy04.setText(result.get(3));
					happy05.setText(result.get(4));

					WindowManager manager = (WindowManager)getActivity().getApplication().getSystemService(Context.WINDOW_SERVICE);
					Display display = manager.getDefaultDisplay();
					width = display.getWidth();
					/*
					int height;
					Bitmap happy_image1 = ImageLoader.getInstance().loadImageSync(result.get(5), options);

					x = happy_image1.getWidth();
					y = happy_image1.getHeight();
					rate = y/x;
					//happy_image01.setScaleY(width * rate);
					Bitmap happy_image2 = ImageLoader.getInstance().loadImageSync(result.get(6), options);

					x = happy_image2.getWidth();
					y = happy_image2.getHeight();
					rate = y/x;
					//happy_image02.setScaleY(width * rate);
					Bitmap happy_image3 = ImageLoader.getInstance().loadImageSync(result.get(7), options);

					x = happy_image3.getWidth();
					y = happy_image3.getHeight();
					rate = y/x;
					//happy_image03.setScaleY(width * rate);
					Bitmap happy_image4 = ImageLoader.getInstance().loadImageSync(result.get(8), options);
					x = happy_image4.getWidth();
					y = happy_image4.getHeight();
					rate = y/x;
					//happy_image04.setScaleY(width * rate);
					Bitmap happy_image5 = ImageLoader.getInstance().loadImageSync(result.get(9), options);

					x = happy_image5.getWidth();
					y = happy_image5.getHeight();
					rate = y/x;
					 */
					//happy_image05.setScaleY(width * rate);
					//happy_image01.setImageBitmap(happy_image1);
					//happy_image02.setImageBitmap(happy_image2);
					//happy_image03.setImageBitmap(happy_image3);
					//happy_image04.setImageBitmap(happy_image4);
					//happy_image05.setImageBitmap(happy_image5);

					url = result;

					new AsyncTask<List<String>, Void, Bitmap[]>(){

						@Override
						protected Bitmap[] doInBackground(List<String>... params) {
							
							
							// TODO Auto-generated method stub
							DisplayImageOptions options1;
							options1 = new DisplayImageOptions.Builder()  //设置land——image——插件的加载属性
							.showImageOnLoading(R.drawable.landing_image)  
							.showImageOnFail(R.drawable.landfail_image)
							.cacheInMemory(true)  
							.cacheOnDisk(true)  
							.bitmapConfig(Bitmap.Config.RGB_565)
							.build();
							Bitmap[] datelist = new Bitmap[10];
							int x, y;
							
							
							Bitmap image1 = ImageLoader.getInstance().loadImageSync(params[0].get(5), options1);
							Bitmap image2 = ImageLoader.getInstance().loadImageSync(params[0].get(6), options1);
							Bitmap image3 = ImageLoader.getInstance().loadImageSync(params[0].get(7), options1);
							Bitmap image4 = ImageLoader.getInstance().loadImageSync(params[0].get(8), options1);
							Bitmap image5 = ImageLoader.getInstance().loadImageSync(params[0].get(9), options1);
							
							Bitmap[] bitmaps = new Bitmap[5];
							while(true){
								if (image1.getWidth() > 0)
								{
									x = image1.getWidth();
									y = image1.getHeight();
									
									Matrix matrix = new Matrix(); 
									matrix.postScale((width/x),(width * y / x)/y); //长和宽放大缩小的比例 
									bitmaps[0] = Bitmap.createBitmap(image1,0,0,image1.getWidth(),image1.getHeight(),matrix,true); 
									break;
								}
							}
							while(true){
								if (image2.getWidth() > 0)
								{
									x = image2.getWidth();
									y = image2.getHeight();
									Matrix matrix = new Matrix(); 
									matrix.postScale((width/x),(width * y / x)/y); //长和宽放大缩小的比例 
									bitmaps[1] = Bitmap.createBitmap(image2,0,0,image2.getWidth(),image2.getHeight(),matrix,true);
									break;
								}
							}
							while(true){
								if (image3.getWidth() > 0)
								{
									x = image3.getWidth();
									y = image3.getHeight();
									Matrix matrix = new Matrix(); 
									matrix.postScale((width/x),(width * y / x)/y); //长和宽放大缩小的比例 
									bitmaps[2] = Bitmap.createBitmap(image3,0,0,image3.getWidth(),image3.getHeight(),matrix,true);
									break;
								}
							}
							while(true){
								if (image4.getWidth() > 0)
								{
									x = image4.getWidth();
									y = image4.getHeight();
									Matrix matrix = new Matrix(); 
									matrix.postScale((width/x),(width * y / x)/y); //长和宽放大缩小的比例 
									bitmaps[3] = Bitmap.createBitmap(image4,0,0,image4.getWidth(),image4.getHeight(),matrix,true);
									break;
								}
							}
							while(true){
								if (image5.getWidth() > 0)
								{
									x = image5.getWidth();
									y = image5.getHeight();
									Matrix matrix = new Matrix(); 
									matrix.postScale((width/x),(width * y / x)/y); //长和宽放大缩小的比例 
									bitmaps[4] = Bitmap.createBitmap(image5,0,0,image5.getWidth(),image5.getHeight(),matrix,true);
									break;
								}
							}
							//return null;
							return bitmaps;
						};
						protected void onPostExecute(Bitmap[] result) {
							happy_image01.setImageBitmap(result[0]);
							happy_image02.setImageBitmap(result[1]);
							happy_image03.setImageBitmap(result[2]);
							happy_image04.setImageBitmap(result[3]);
							happy_image05.setImageBitmap(result[4]);
							//Toast.makeText(getActivity().getApplicationContext(), result[3], 1).show();
							/*
							 * String s = "";
							for (int i =0; i < 10; ++i){
								s = s + result[i] + " + ";
							}
							
							//Toast.makeText(getActivity().getApplicationContext(), s, 1).show();
							
							
							LayoutParams params1 = happy_image01.getLayoutParams();  ;  
						    params1.height=width * (result[1]/result[0]);  
						    params1.width =width;  
						    happy_image01.setLayoutParams(params1);
						    happy_image01.postInvalidate();
						    
						    LayoutParams params2 = happy_image02.getLayoutParams();  
						    params2.height=width * (result[3]/result[2]);  
						    params2.width =width;  
						    happy_image02.setLayoutParams(params2);

						    LayoutParams params3 = happy_image03.getLayoutParams();  
						    params3.height=width * (result[5]/result[4]);  
						    params3.width =width;  
						    happy_image03.setLayoutParams(params3);

						    LayoutParams params4 = happy_image04.getLayoutParams();  
						    params4.height=width * (result[7]/result[6]);  
						    params4.width =width;  
						    happy_image04.setLayoutParams(params4);

						    LayoutParams params5 = happy_image05.getLayoutParams();  
						    params5.height=width * (result[9]/result[8]);  
						    params5.width =width;  
						    happy_image05.setLayoutParams(params5);
						    
						    ImageLoader.getInstance().displayImage(url.get(5) , happy_image01, options);
							ImageLoader.getInstance().displayImage(url.get(6) , happy_image02, options);
							ImageLoader.getInstance().displayImage(url.get(7) , happy_image03, options);
							ImageLoader.getInstance().displayImage(url.get(8) , happy_image04, options);
							ImageLoader.getInstance().displayImage(url.get(9) , happy_image05, options);
						    
						   */
						};
					}.execute(result);
					//happy_image01.setDrawingCacheEnabled(true);
					//happy_image02.setDrawingCacheEnabled(true);
					//happy_image03.setDrawingCacheEnabled(true);
					//happy_image04.setDrawingCacheEnabled(true);
					//happy_image05.setDrawingCacheEnabled(true);
					//Bitmap image1 = ImageLoader.getInstance().loadImageSync(result.get(5), options);
					//Bitmap image2 = ImageLoader.getInstance().loadImageSync(result.get(6), options);;
					//Bitmap image3 = ImageLoader.getInstance().loadImageSync(result.get(7), options);;
					//Bitmap image4 = ImageLoader.getInstance().loadImageSync(result.get(8), options);;
					//Bitmap image5 = ImageLoader.getInstance().loadImageSync(result.get(9), options);;
					//happy_image01.setLayoutParams(new LayoutParams(width, width * (image1.getHeight()/image1.getWidth())));
					/*
					LayoutParams params1 = happy_image01.getLayoutParams();  
				    params1.height=width * (image1.getHeight()/image1.getWidth());  
				    params1.width =width;  
				    happy_image01.setLayoutParams(params1);

				    LayoutParams params2 = happy_image02.getLayoutParams();  
				    params2.height=width * (image2.getHeight()/image2.getWidth());  
				    params2.width =width;  
				    happy_image03.setLayoutParams(params2);

				    LayoutParams params3 = happy_image03.getLayoutParams();  
				    params3.height=width * (image3.getHeight()/image3.getWidth());  
				    params3.width =width;  
				    happy_image04.setLayoutParams(params3);

				    LayoutParams params4 = happy_image04.getLayoutParams();  
				    params4.height=width * (image4.getHeight()/image4.getWidth());  
				    params4.width =width;  
				    happy_image04.setLayoutParams(params4);

				    LayoutParams params5 = happy_image05.getLayoutParams();  
				    params5.height=width * (image5.getHeight()/image5.getWidth());  
				    params5.width =width;  
				    happy_image05.setLayoutParams(params5);
					 */
					//Toast.makeText(getActivity().getApplicationContext(), image1.getWidth() + " + " + image1.getHeight(), 1).show();
				} catch (Exception e) {
					// TODO: handle exception
				}

			};
		}.execute(happy_url3);
		
	}
}
