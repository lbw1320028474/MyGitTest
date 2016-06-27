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
	/*һ��������������������viewpager��*/
	private DisplayImageOptions options;	//����ͼƬ���������ö���
	private List<View> views;		//viewpager��ÿ��ҳ�����
	private TextView set_to_wallpaper;		//���ñ�ֽ
	private TextView save_to_phone;		//���浽�ֻ�
	private TextView shel_to_qq;		//����qq
	private TextView shel_to_qq1;
	private TextView close_image;	//�ر�
	private LinearLayout linearLayout;		//���õ����Բ���
	private LinearLayout info_linerLayout;	//ͼƬ��Ϣ�����Բ���
	private int view_index = 0;		//viewpagerҳ���λ��
	private List<String> iamgeResultsUrl;
	private ViewPager vp;			//
	private TextView image_name, image_auther, image_infomation;

	//private ImageView[] iImageView;		//
	private ViewPagerAdapter adapter;		//������
	//private String image_url;	//	�����ͼƬ����url��������ҳ�Ľṹ����������Ǳ����еģ���Ϊ��ҳ��Ҫ�������Σ����url��Ϊ�����ҵ�ͼƬ���ϵ�url
	private int[] image_result = new int[]{		//�Ȱ�id�ҵ����������ýǱ��ҵ��ؼ�
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

	//��ʼ��activity
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
		options = new DisplayImageOptions.Builder()  //����land����image��������ļ�������
		.showImageOnLoading(R.drawable.landing_image)  
		.showImageOnFail(R.drawable.landfail_image)
		.cacheInMemory(true)  
		.cacheOnDisk(true)  
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		Intent intent = getIntent();		//�����һ��activity��������activity
		images_sourse_url = intent.getStringExtra("images");
		//Toast.makeText(ImageActivity.this, "ͼƬurl��" + images_sourse_url, 1).show();
		getImages(images_sourse_url);	//��������������
		//initViewPager();
	}


	public String getImageQualityCode(){
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
		String name =sharedPreferences.getString("quality_code", "1"); 
		return name;
	}

	//��ʼ����ͼƬ
	private void getImages(String images_sourse_url2) {		//��ȡͼƬ��url�߳�
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, List<String>>(){

			@Override
			protected List<String> doInBackground(String... params) {
				// TODO Auto-generated method stub
				Document doc;		//��ҳ�������󣬽���������url
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
							Toast.makeText(ImageActivity.this, "��������url�쳣1��", 1).show();
						}
					}

					return lists;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Toast.makeText(ImageActivity.this, "��������url�쳣2��", 1).show();
					return null;
				}

			}
			/**
			 * �ύ����
			 * */
			protected void onPostExecute(List<String> result) {
				/**
				 * ���÷���������������������ͼƬ
				 * */
				if (result != null && !result.isEmpty() && (result.size() > 0)){
					getImagesss(result.get(0));		//ȥ�������еĸ���ͼƬ
				}
				//Toast.makeText(ImageActivity.this, result.get(0) + "+title:" + result.get(1), 1).show();
			}

		}.execute(images_sourse_url2);
	}



	private void getImagesss(String every_image_url) {		//��ȡ����ͼƬ���߳�
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
					String name = doc.select("h1.mt10").text();		//�������
					String info = doc.select("div.content").text();	//��ý���
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
					Toast.makeText(ImageActivity.this, "��������ͼƬurl�쳣", 1).show();
					return null;
				}

			}
			/**
			 * �ύ����
			 * */
			protected void onPostExecute(java.util.List<String> result) {
				/**
				 * ���÷���������������������ͼƬ
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
						 * ��ø���ͼƬ��url�б�list
						 * */
						String code = getImageQualityCode();	//��ȡ��ǰͼƬ�������������룬1����ͨ��2�����
						//Toast.makeText(ImageActivity.this, code, 1).show();;
						List<String> images_index_lists = GetImagesService.getImages(substr, code);		//��ʼ����ͼƬurl����
						//images_index_lists.add(result.get(1));
						iamgeResultsUrl = images_index_lists;
						initViewPager(images_index_lists);
					}catch(Exception e){
						Toast.makeText(ImageActivity.this, "��������", 1);
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
					Toast.makeText(ImageActivity.this, "����ͼƬ�쳣��", 1).show();
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
		view_index = arg0;		//�Ե�ǰ��viewҳ��λ�ý��ж�λ
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
		//���ر���ͼƬ  
		//ImageView save_jpg = (ImageView)views.get(view_index).findViewById(image_result[view_index]);
		//Bitmap bmps = ;  
		//�洢·��  
		name = name.substring(name.length()-18, name.length());
		File file = new File("/sdcard/Beauty/");  
		if(!file.exists())  
			file.mkdirs();  
		try {  
			FileOutputStream fileOutputStream = new FileOutputStream(file.getPath() + name);  
			//bmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);  
			fileOutputStream.close();  

			Toast.makeText(ImageActivity.this, "����ɹ�,ͼƬ����SD����Ŀ¼��", 1).show();
			MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", "");
		} catch (Exception e) {  
			//e.printStackTrace();  
			Toast.makeText(ImageActivity.this, "����ʧ��", 1).show();
		}  
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shel_to_qq1:
			
			break;
		case R.id.save_to_phone:

			DisplayImageOptions options1;	//����ͼƬ����
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
			DisplayImageOptions options2;	//����ͼƬ����
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
				Toast.makeText(this, "���óɹ�", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "����ʧ�ܣ�ͼƬ����ʧ�ܻ���û����Ӧ��Ȩ", Toast.LENGTH_SHORT).show();
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
