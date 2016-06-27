package com.example.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.baidu.ops.appunion.sdk.AppUnionSDK;
import com.example.adapter.ViewPagerAdapter;
import com.example.data.GetImagesService;
import com.example.imageeveryday.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageActivity extends Activity implements OnPageChangeListener, OnLongClickListener,OnClickListener,Serializable{
	private String images_sourse_url;
	/*一下三个对象是用来处理viewpager的*/
	private DisplayImageOptions options;	//加载图片的属性设置对象
	private List<View> views;		//viewpager的每个页面对象
	private TextView set_to_wallpaper;		//设置壁纸
	private TextView save_to_phone;		//保存到手机
	private TextView shel_to_qq;		//分享到qq
	private TextView shel_to_qq1;
	private TextView close_image;	//关闭
	private LinearLayout linearLayout;		//设置的线性布局
	private LinearLayout info_linerLayout;	//图片信息的线性布局
	private int view_index = 0;		//viewpager页面的位置
	private List<String> iamgeResultsUrl;
	private ViewPager vp;			//
	private TextView image_name, image_auther, image_infomation;

	//private ImageView[] iImageView;		//
	private ViewPagerAdapter adapter;		//适配器
	//private String image_url;	//	这个是图片集的url，根须网页的结构，这个变量是必须有的，因为网页需要解析两次，这个url作为介质找到图片集合的url
	private int[] image_result = new int[]{		//先把id找到，方便利用角标找到控件
			R.id.viewpager_01, R.id.viewpager_02,
			R.id.viewpager_03, R.id.viewpager_04,
			R.id.viewpager_05, R.id.viewpager_06,
			R.id.viewpager_07, R.id.viewpager_08,
			R.id.viewpager_09, R.id.viewpager_10,
			R.id.viewpager_11, R.id.viewpager_12,
			R.id.viewpager_13, R.id.viewpager_14,
			R.id.viewpager_15, R.id.viewpager_16,
			R.id.viewpager_17, R.id.viewpager_18,
			R.id.viewpager_19, R.id.viewpager_20,
	};


	private int[] viewPagerRedult = new int[] {
			R.layout.image_viewpager1, R.layout.image_viewpager2,
			R.layout.image_viewpager3, R.layout.image_viewpager4,
			R.layout.image_viewpager5, R.layout.image_viewpager6,
			R.layout.image_viewpager7, R.layout.image_viewpager8,
			R.layout.image_viewpager9, R.layout.image_viewpager10,
			R.layout.image_viewpager11, R.layout.image_viewpager12,
			R.layout.image_viewpager13, R.layout.image_viewpager14,
			R.layout.image_viewpager15, R.layout.image_viewpager16,
			R.layout.image_viewpager17, R.layout.image_viewpager18,
			R.layout.image_viewpager19, R.layout.image_viewpager20,
	};

	//初始化activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_look);
		//adjust_image = (TextView)this.findViewById(R.id.adjust_bitmap);
		image_auther = (TextView)this.findViewById(R.id.image_auther);
		image_name = (TextView)this.findViewById(R.id.image_name);
		image_infomation = (TextView)this.findViewById(R.id.image_show);
		info_linerLayout = (LinearLayout)this.findViewById(R.id.image_message);
	
		/*
		iImageView = new ImageView[20];
		for (int i = 0; i < 20; ++i){
			iImageView[i] = (ImageView)this.findViewById(image_result[i]);
		}*/
		//setImageClickLinsner();
		set_to_wallpaper = (TextView)this.findViewById(R.id.set_to_wallpaper);
		save_to_phone = (TextView)this.findViewById(R.id.save_to_phone);
		shel_to_qq = (TextView)this.findViewById(R.id.shel_to_qq);
		shel_to_qq1 = (TextView)this.findViewById(R.id.shel_to_qq1);
		linearLayout = (LinearLayout)this.findViewById(R.id.liner_set);
		close_image = (TextView)this.findViewById(R.id.close);
		close_image.setOnClickListener(this);
		set_to_wallpaper.setOnClickListener(this);
		save_to_phone.setOnClickListener(this);
		shel_to_qq.setOnClickListener(this);
		shel_to_qq1.setOnClickListener(this);
		//adjust_image.setOnClickListener(this);

		//vp.setOnClickListener(this);
		//vp.setOnLongClickListener(this);
		options = new DisplayImageOptions.Builder()  //设置land——image——插件的加载属性
		.showImageOnLoading(R.drawable.landing_image)  
		.showImageOnFail(R.drawable.landfail_image)
		.cacheInMemory(true)  
		.cacheOnDisk(true)  
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		Intent intent = getIntent();		//获得上一层activity传进来的activity
		images_sourse_url = intent.getStringExtra("images");
		//Toast.makeText(ImageActivity.this, "图片url：" + images_sourse_url, 1).show();
		getImages(images_sourse_url);	//到这里是正常的
		//initViewPager();
	}


	public String getImageQualityCode(){
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		// 使用getString方法获得value，注意第2个参数是value的默认值 
		String name =sharedPreferences.getString("quality_code", "1"); 
		return name;
	}

	//开始解析图片
	private void getImages(String images_sourse_url2) {		//获取图片集url线程
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, List<String>>(){

			@Override
			protected List<String> doInBackground(String... params) {
				// TODO Auto-generated method stub
				Document doc;		//网页解析对象，解析出介质url
				List<String> lists = new ArrayList<String>();
				try {
					doc = Jsoup.connect(params[0]).timeout(5000).get();
					Elements elements = doc.select("div.endpage-main");
					//			String result = "";
					for (Element ele:elements){
						try {
							String imagess_url = ele.select("a[href]").first().attr("href");
							lists.add(imagess_url);
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(ImageActivity.this, "解析介质url异常1：", 1).show();
						}
					}

					return lists;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Toast.makeText(ImageActivity.this, "解析介质url异常2：", 1).show();
					return null;
				}

			}
			/**
			 * 提交参数
			 * */
			protected void onPostExecute(List<String> result) {
				/**
				 * 调用方法，设置适配器，加载图片
				 * */
				if (result != null && !result.isEmpty() && (result.size() > 0)){
					getImagesss(result.get(0));		//去解析所有的高清图片
				}
				//Toast.makeText(ImageActivity.this, result.get(0) + "+title:" + result.get(1), 1).show();
			}

		}.execute(images_sourse_url2);
	}



	private void getImagesss(String every_image_url) {		//获取所有图片的线程
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, List<String>>(){

			@Override
			protected List<String> doInBackground(String... params) {
				// TODO Auto-generated method stub
				Document doc;
				List<String> lists = new ArrayList<String>();
				try {

					doc = Jsoup.connect(params[0]).timeout(5000).get();
					Elements elements = doc.select("div.act_detail_info");
					//	String result = "";

					for (Element ele:elements){
						String imagess_url = ele.select("script").html();

						//result = result + "title:" + title + "/n";
						lists.add(imagess_url);
						//String title = doc.select("a.white").attr("title");
						//String image_url = ele.select("img[src]").first().attr("src");
						//lists.add(title);
						//Log.i("info", image_url);
						//Log.i("info", title);
					}
					String name = doc.select("h1.mt10").text();		//获得名称
					String info = doc.select("div.content").text();	//获得介绍
					String auther = doc.select("a.font_color_default").first().text();
					lists.add(name);
					if (info != null && !info.isEmpty()){
						lists.add(info);
					}
					if (auther != null){
						lists.add(auther);
					}
					//String auther = doc.select("").text();
					//Toast.makeText(ImageActivity.this, "keyi", 1).show();
					//Log.i("name", name);
					//String info = doc.select("div.content font_color_default mt15 mb10 word_break").text();
					//Toast.makeText(ImageActivity.this, info, 1).show();
					return lists;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Toast.makeText(ImageActivity.this, "解析所有图片url异常", 1).show();
					return null;
				}

			}
			/**
			 * 提交参数
			 * */
			protected void onPostExecute(java.util.List<String> result) {
				/**
				 * 调用方法，设置适配器，加载图片
				 * */
				if (result != null && !result.isEmpty()){
					try{
						info_linerLayout.setVisibility(View.VISIBLE);
						if (result.size() > 1){
							image_name.append(result.get(1));
						}

						if (result.size() > 2){
							image_infomation.append(result.get(2));
						}else{
							image_infomation.append(result.get(1));
						}
						if (result.size() > 3){
							image_auther.append(result.get(3));
						}
						String substr = result.get(0).substring(504, 9952);
						/**
						 * 获得高清图片的url列表list
						 * */
						String code = getImageQualityCode();	//获取当前图片的清晰质量代码，1则普通，2则高清
						//Toast.makeText(ImageActivity.this, code, 1).show();;
						List<String> images_index_lists = GetImagesService.getImages(substr, code);		//开始解析图片url序列
						//images_index_lists.add(result.get(1));
						iamgeResultsUrl = images_index_lists;
						initViewPager(images_index_lists);
					}catch(Exception e){
						Toast.makeText(ImageActivity.this, "解析出错", 1);
						finish();
					}

				}
			}
		}.execute(every_image_url);
	};

	private void initViewPager(List<String> images_index_lists1) {
		// TODO Auto-generated method stub
		/*
		for (String s: images_index_lists1){
			System.out.println(s);
		}*/
		LayoutInflater inflater = LayoutInflater.from(this);
		//ImageView iView = (ImageView)this.findViewById(R.id.viewpager_01);

		if(!images_index_lists1.isEmpty()){
			views = new ArrayList<View>();
			for (int i = 0; i < images_index_lists1.size(); ++i){
				views.add(inflater.inflate(viewPagerRedult[i], null));
			}
			for (int i = 0; i < images_index_lists1.size(); ++i){
				//	ImageView iView = (ImageView)views.get(i).findViewById(image_result[i]);
			}
			adapter = new ViewPagerAdapter(views, this);
			vp = (ViewPager)this.findViewById(R.id.viewpager_id);
			vp.setAdapter(adapter);
			for (int i = 0; i < images_index_lists1.size(); ++i){
				ImageView iView = (ImageView)views.get(i).findViewById(image_result[i]);
				iView.setOnClickListener(this);
				iView.setOnLongClickListener(this);
				try {
					ImageLoader.getInstance().displayImage(images_index_lists1.get(i) , iView, options);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(ImageActivity.this, "加载图片异常：", 1).show();
				}
			}
			vp.setOnPageChangeListener(this);

		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

		linearLayout.setVisibility(View.GONE);
		info_linerLayout.setVisibility(View.GONE);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		//Log.i("info", "arg0" + arg0 + "float:" + arg1 + "arg2" + arg2);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		view_index = arg0;		//对当前的view页数位置进行定位
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		linearLayout.setVisibility(View.VISIBLE);
		return true;
	}

	public void SaveBitmap(Bitmap bmp, String name)  
	{  
		//Bitmap bitmap = Bitmap.createBitmap(800, 600, Config.ARGB_8888);    
		//Canvas canvas = new Canvas(bitmap);  
		//加载背景图片  
		//ImageView save_jpg = (ImageView)views.get(view_index).findViewById(image_result[view_index]);
		//Bitmap bmps = ;  
		//存储路径  
		name = name.substring(name.length()-18, name.length());
		File file = new File("/sdcard/Beauty/");  
		if(!file.exists())  
			file.mkdirs();  
		try {  
			FileOutputStream fileOutputStream = new FileOutputStream(file.getPath() + name);  
			//bmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);  
			fileOutputStream.close();  

			Toast.makeText(ImageActivity.this, "保存成功,图片放在SD卡根目录下", 1).show();
			MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", "");
		} catch (Exception e) {  
			//e.printStackTrace();  
			Toast.makeText(ImageActivity.this, "保存失败", 1).show();
		}  
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shel_to_qq1:
			
			break;
		case R.id.save_to_phone:

			DisplayImageOptions options1;	//保存图片设置
			options1 = new DisplayImageOptions.Builder()  
			.showImageOnLoading(R.drawable.result)  
			.showImageOnFail(R.drawable.ic_launcher)  
			.cacheInMemory(true)  
			.cacheOnDisk(true)  
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();


			//ImageView save_jpg = (ImageView)views.get(view_index).findViewById(image_result[view_index]);
			//save_jpg.setDrawingCacheEnabled(true);		
			Bitmap bitmap_save = ImageLoader.getInstance().loadImageSync(iamgeResultsUrl.get(view_index), options);
			SaveBitmap(bitmap_save, iamgeResultsUrl.get(view_index));
			break;
		case R.id.set_to_wallpaper:
			WallpaperManager wallpaperManager = WallpaperManager.getInstance(this); 
			DisplayImageOptions options2;	//保存图片设置
			options1 = new DisplayImageOptions.Builder()  
			.showImageOnLoading(R.drawable.result)  
			.showImageOnFail(R.drawable.ic_launcher)  
			.cacheInMemory(true)  
			.cacheOnDisk(true)  
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();


			//ImageView save_jpg = (ImageView)views.get(view_index).findViewById(image_result[view_index]);
			//save_jpg.setDrawingCacheEnabled(true);		
			Bitmap bitmap_wall = ImageLoader.getInstance().loadImageSync(iamgeResultsUrl.get(view_index), options);
			try {
				//wallpaperManager.suggestDesiredDimensions(1080, 1920);
				wallpaperManager.setBitmap(bitmap_wall);
				//wallpaperManager.suggestDesiredDimensions(1080, 1920);
				Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "设置失败，图片加载失败或者没有相应限权", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.shel_to_qq:
			AppUnionSDK.getInstance(ImageActivity.this).showAppList();
			break;
		case R.id.close:
			finish();
			break;
		default:
			if (info_linerLayout.getVisibility() != View.VISIBLE && linearLayout.getVisibility() != View.VISIBLE){
				info_linerLayout.setVisibility(View.VISIBLE);
			}else{
				info_linerLayout.setVisibility(View.GONE);
			}
			if (linearLayout.getVisibility() == View.VISIBLE){
				linearLayout.setVisibility(View.GONE);
			}
			break;
		}
	}
}
