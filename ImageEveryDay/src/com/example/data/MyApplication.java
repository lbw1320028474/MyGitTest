package com.example.data;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//����Ĭ�ϵ�ImageLoader���ò���  
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration  
                .createDefault(this);  
         
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);  
	}
}
