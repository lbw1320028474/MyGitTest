package com.example.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MyData extends Activity{
	public static int width = (new Activity().getWindowManager().getDefaultDisplay().getWidth())/3 - 5;


	/**
	 *�õ�ͼƬ�����Ĳ��� 
	 */
	public String getImageQualityCode(){
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
		String name =sharedPreferences.getString("quality_code", "1"); 
		return name;
	}

	/*
	 * ����ͼƬ�����Ĳ���
	 * */
	public void saveImageQualityCode(String quality_code){
		SharedPreferences mySharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("quality_code", quality_code); 
		//�ύ��ǰ���� 
		editor.commit(); 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
}
