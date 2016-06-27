package com.example.activity;

import com.example.imageeveryday.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SetActivity extends Activity implements OnClickListener{
	private TextView set_but_setting;
	private TextView set_but_toll;
	private TextView set_but_iamvider;
	private TextView set_but_return;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_layout);
		set_but_iamvider = (TextView)this.findViewById(R.id.set_but_iamvider);
		set_but_return = (TextView)this.findViewById(R.id.set_but_return);
		set_but_setting = (TextView)this.findViewById(R.id.set_but_setting);
		set_but_toll = (TextView)this.findViewById(R.id.set_but_toll);
		String code1 = getImageQualityCode();
		if (code1.equals("1")){
			set_but_setting.setText("����ͼƬ����:��ͨ");
		}else{
			set_but_setting.setText("����ͼƬ����:����");
		}
		set_but_iamvider.setOnClickListener(this);
		set_but_return.setOnClickListener(this);
		set_but_setting.setOnClickListener(this);
		set_but_toll.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set_but_iamvider:

			break;
		case R.id.set_but_return:
			finish();
			break;
		case R.id.set_but_setting:
			//Toast.makeText(SetActivity.this, getImageQualityCode(), 1).show();;
			String code = getImageQualityCode();
			if (code.equals("1")){
				saveImageQualityCode("2");
				set_but_setting.setText("����ͼƬ����:����");
			}else{
				saveImageQualityCode("1");
				set_but_setting.setText("����ͼƬ����:��ͨ");
			}
			break;
		case R.id.set_but_toll:
			Intent intent = new Intent(SetActivity.this,ExampleActivity.class);
			startActivity(intent);
			break;
		}
	}

	public void saveImageQualityCode(String quality_code){
		SharedPreferences mySharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("quality_code", quality_code); 
		//�ύ��ǰ���� 
		editor.commit(); 
	}

	public String getImageQualityCode(){
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
		String name =sharedPreferences.getString("quality_code", "1"); 
		return name;
	}
}
