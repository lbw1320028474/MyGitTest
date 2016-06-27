package com.example.activity;

import com.example.imageeveryday.MainActivity;
import com.example.imageeveryday.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomActivity extends Activity{

	private TextView tip_tTextView;
	private ImageView timeIv;
	private int index = 0;
	private int[] timeCode = new int[]{R.drawable.five, R.drawable.fore, R.drawable.three,
			R.drawable.two, R.drawable.one};
	private Chronometer chronometer;
	private TextView tv_tiaoguo;

	private String tip1 = "据说，男人每天看美女10分钟，可以长寿";
	private String tip2 = "如果自动设置壁纸后显示效果不佳，可以先把图片下载后再在相册中手动设置";
	private String tip3 = "在网络状况不佳时可以在设置中把图片质量设置为普通";
	private String tip4 = "如果感觉浏览图片角度不佳时，可以开启手机的自动旋转，然后旋转手机，可以调整最佳浏览方向";




	private void goHome(){
		Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		setContentView(R.layout.welcome_layout);
		timeIv = (ImageView)this.findViewById(R.id.time);
		tv_tiaoguo = (TextView)this.findViewById(R.id.but_tiaoguo);

		chronometer = (Chronometer) this.findViewById(R.id.chronometer);
		chronometer.start();

		tip_tTextView = (TextView)this.findViewById(R.id.welcome_tip);
		String tip_code = getImageQualityCode();
		if (tip_code.equals("1")){
			tip_tTextView.setText(tip1);
			saveImageQualityCode("2");
		}else if(tip_code.equals("2")){
			tip_tTextView.setText(tip2);
			saveImageQualityCode("3");
		}else if(tip_code.equals("3")){
			tip_tTextView.setText(tip3);
			saveImageQualityCode("4");
		}else if(tip_code.equals("4")){
			tip_tTextView.setText(tip4);
			saveImageQualityCode("1");
		}
		
		tv_tiaoguo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chronometer.stop();
				goHome();
			}
		});

		chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {

			@Override
			public void onChronometerTick(Chronometer chronometer) {
				// TODO Auto-generated method stub
				if (index == 5){
					chronometer.stop();
					goHome();
				}else{

					timeIv.setImageResource(timeCode[index]);
					index = index + 1;
				}
			}
		});





		//saveImageQualityCode("2");
		//init();

	}

	public void saveImageQualityCode(String tip_code){
		SharedPreferences mySharedPreferences= getSharedPreferences("tip", 
				Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("tip", tip_code); 
		//提交当前数据 
		editor.commit(); 
	}

	public String getImageQualityCode(){
		SharedPreferences sharedPreferences= getSharedPreferences("tip", 
				Activity.MODE_PRIVATE); 
		// 使用getString方法获得value，注意第2个参数是value的默认值 
		String name =sharedPreferences.getString("tip", "1"); 
		return name;
	}
}
