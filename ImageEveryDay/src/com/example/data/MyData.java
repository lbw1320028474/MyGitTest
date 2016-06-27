package com.example.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MyData extends Activity{
	public static int width = (new Activity().getWindowManager().getDefaultDisplay().getWidth())/3 - 5;


	/**
	 *得到图片质量的参数 
	 */
	public String getImageQualityCode(){
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		// 使用getString方法获得value，注意第2个参数是value的默认值 
		String name =sharedPreferences.getString("quality_code", "1"); 
		return name;
	}

	/*
	 * 保存图片质量的参数
	 * */
	public void saveImageQualityCode(String quality_code){
		SharedPreferences mySharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("quality_code", quality_code); 
		//提交当前数据 
		editor.commit(); 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
}
