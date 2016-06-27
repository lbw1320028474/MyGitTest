package com.example.imageeveryday;

import java.io.Serializable;

import com.baidu.ops.appunion.sdk.AppUnionSDK;
import com.baidu.ops.appunion.sdk.banner.BaiduBanner;
import com.baidu.ops.appunion.sdk.banner.BannerType;
import com.example.activity.ImageActivity;
import com.example.activity.SetActivity;
import com.example.fragment.Dawu_fragment;
import com.example.fragment.Happy_fragment;
import com.example.fragment.Life_fragment;
import com.example.fragment.Lomo_fragment;
import com.example.fragment.Meishi_fragment;
import com.example.fragment.Secenery_fragment;
import com.example.fragment.Sexy_fragment;
import com.example.fragment.Shenghuo_fragment;
import com.example.fragment.Shentai_fragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,Serializable{




	private TextView soft_setting, soft_good, image_kinds;
	private LinearLayout image_kinds_layout;
	private Life_fragment life_fragment;		//����fragment����
	private Sexy_fragment sexy_fragment;
	private Secenery_fragment secenery_fragment;
	private Lomo_fragment lomo_fragment;
	private Shenghuo_fragment shenghuo_fragment;
	private Shentai_fragment shentai_fragment;
	private Dawu_fragment dawu_fragment;
	private Meishi_fragment meishi_fragment;
	private Happy_fragment happy_fragment;
	private TextView tv_life, tv_lomo, tv_secenery, tv_sexy, tv_shenhuo, tv_shentai, tv_dawu, tv_meishi,tv_happy;	//ѡ�
	private FragmentManager fm = null;
	private FragmentTransaction ft;		//fragment�Ĺ�����
	private int index;	//������λĿǰ������ʾ�Ǹ�fragment
	private LinearLayout content_layout;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
		return false;
	}



	public void saveImageQualityCode(String quality_code){
		SharedPreferences mySharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("quality_code", quality_code); 
		//�ύ��ǰ���� 
		editor.commit();
	}

	
	
	/*
	 * ��������
	 * */
	public void adnimationVisoble(){
		Animation scaleAnimation=new ScaleAnimation(1.0f, 1.0f,0.0f, 0.0f);

		scaleAnimation.setDuration(1000);//���ö�������ʱ��Ϊ3��

		scaleAnimation.setFillAfter(true);//���ö��������󱣳ֵ�ǰ��λ�ã��������ص�������ʼǰ��λ�ã�

		scaleAnimation.setRepeatCount(3);

		image_kinds_layout.startAnimation(scaleAnimation);
	}
	
	public void adnimationGone(){
		Animation scaleAnimation=new ScaleAnimation(0.0f, 0.0f,1.0f, 1.0f);

		scaleAnimation.setDuration(1000);//���ö�������ʱ��Ϊ3��

		scaleAnimation.setFillAfter(true);//���ö��������󱣳ֵ�ǰ��λ�ã��������ص�������ʼǰ��λ�ã�

		scaleAnimation.setRepeatCount(3);

		image_kinds_layout.startAnimation(scaleAnimation);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		/*
		 * ͼƬ���õ���layout
		 * */
		soft_setting = (TextView)this.findViewById(R.id.soft_setting);
		soft_good = (TextView)this.findViewById(R.id.soft_goods);
		image_kinds = (TextView)this.findViewById(R.id.image_kinds);
		image_kinds_layout = (LinearLayout)this.findViewById(R.id.image_kinds_layout);
		
		fm = getFragmentManager();	//��һ������ʱ��Ĭ��fragment

		/*
		 * ��������1����ʧ����
		 * */
		AnimationSet animationSet = new AnimationSet(true);

		//����һ��AlphaAnimation���󣬲�������ȫ��͸���ȣ�����ȫ�Ĳ�͸��

		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

		//���ö���ִ�е�ʱ��

		alphaAnimation.setDuration(1000);

		//��alphaAnimation������ӵ�AnimationSet����

		animationSet.addAnimation(alphaAnimation);

		/*
		 * ���ֶ���
		 * */
		AnimationSet animationSet1 = new AnimationSet(true);

		//����һ��AlphaAnimation���󣬲�������ȫ��͸���ȣ�����ȫ�Ĳ�͸��

		AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);

		//���ö���ִ�е�ʱ��

		alphaAnimation1.setDuration(1000);

		//��alphaAnimation������ӵ�AnimationSet����

		animationSet1.addAnimation(alphaAnimation1);


		AppUnionSDK.getInstance(MainActivity.this).initSdk();
		initAvd();
		content_layout = (LinearLayout)this.findViewById(R.id.content_linner);
		life_fragment = new Life_fragment();		//ʵ����fragment����
		sexy_fragment = new Sexy_fragment();
		secenery_fragment = new Secenery_fragment();
		lomo_fragment = new Lomo_fragment();
		shenghuo_fragment = new Shenghuo_fragment();
		shentai_fragment = new Shentai_fragment();
		dawu_fragment = new Dawu_fragment();
		meishi_fragment = new Meishi_fragment();
		happy_fragment = new Happy_fragment();

		tv_life = (TextView)this.findViewById(R.id.tv_life);	//�ҵ�����textview��ť
		tv_lomo = (TextView)this.findViewById(R.id.tv_lomo);
		tv_secenery = (TextView)this.findViewById(R.id.tv_secenery);
		tv_sexy = (TextView)this.findViewById(R.id.tv_sexy);
		tv_dawu = (TextView)this.findViewById(R.id.tv_dawu);
		tv_shenhuo = (TextView)this.findViewById(R.id.tv_shenghuo);
		tv_shentai = (TextView)this.findViewById(R.id.tv_sehngtai);
		tv_meishi = (TextView)this.findViewById(R.id.tv_meishi);
		tv_happy = (TextView)this.findViewById(R.id.tv_happy);
		
		tv_happy.setOnClickListener(this);
		soft_good.setOnClickListener(this);
		soft_setting.setOnClickListener(this);
		image_kinds.setOnClickListener(this);
		tv_life.setOnClickListener(this);		//ѡ����������¼�
		tv_lomo.setOnClickListener(this);
		tv_secenery.setOnClickListener(this);
		tv_sexy.setOnClickListener(this);
		tv_dawu.setOnClickListener(this);
		tv_shenhuo.setOnClickListener(this);
		tv_shentai.setOnClickListener(this);
		tv_meishi.setOnClickListener(this);

		tv_lomo.setBackgroundColor(Color.rgb(168, 232, 242));	//�ȰѰ�ť���������ٶ���͸����
		tv_secenery.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_life.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_sexy.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_shenhuo.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_shentai.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_meishi.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_dawu.setBackgroundColor(Color.rgb(168, 232, 242));
		tv_happy.setBackgroundColor(Color.rgb(168, 232, 242));

		tv_secenery.getBackground().setAlpha(0);
		tv_life.getBackground().setAlpha(0);
		tv_sexy.getBackground().setAlpha(0);
		tv_shenhuo.getBackground().setAlpha(0);
		tv_shentai.getBackground().setAlpha(0);
		tv_meishi.getBackground().setAlpha(0);
		tv_dawu.getBackground().setAlpha(0);
		tv_happy.getBackground().setAlpha(0);

		index = 8;
		ft = fm.beginTransaction();
		ft.replace(R.id.content_linner, happy_fragment);

		ft.commit();
	}

	//����ʼ��
	private void initAvd() {
		AppUnionSDK.getInstance(MainActivity.this).showInterstitialAd(MainActivity.this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.soft_goods:
			AppUnionSDK.getInstance(MainActivity.this).showAppList();
			break;
		case R.id.soft_setting:
			Intent intent1 = new Intent(MainActivity.this,SetActivity.class);
			startActivity(intent1);
			break;
		case R.id.image_kinds:
			if(image_kinds_layout.getVisibility() != View.VISIBLE){
				image_kinds_layout.setVisibility(View.VISIBLE);
			}else{
				image_kinds_layout.setVisibility(View.GONE);
			}
			break;
		case R.id.tv_secenery:
			image_kinds_layout.setVisibility(View.GONE);
			try {
				tv_secenery.setBackgroundColor(Color.rgb(168, 232, 242));
				tv_life.getBackground().setAlpha(0);
				tv_lomo.getBackground().setAlpha(0);
				tv_sexy.getBackground().setAlpha(0);
				tv_shenhuo.getBackground().setAlpha(0);
				tv_shentai.getBackground().setAlpha(0);
				tv_meishi.getBackground().setAlpha(0);
				tv_dawu.getBackground().setAlpha(0);
				tv_happy.getBackground().setAlpha(0);
				//FragmentManager fm1 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
				//FragmentTransaction ft1 = fm1.beginTransaction();
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
				ft.addToBackStack("secenery_fragment");
				ft.replace(R.id.content_linner, secenery_fragment).commit();
				break;
			} catch (Exception e) {
				// TODO: handle exception
				break;
			}

		case R.id.tv_life:
			image_kinds_layout.setVisibility(View.GONE);
			try {
				tv_life.setBackgroundColor(Color.rgb(168, 232, 242));
				tv_secenery.getBackground().setAlpha(0);
				tv_lomo.getBackground().setAlpha(0);
				tv_sexy.getBackground().setAlpha(0);
				tv_shenhuo.getBackground().setAlpha(0);
				tv_shentai.getBackground().setAlpha(0);
				tv_meishi.getBackground().setAlpha(0);
				tv_dawu.getBackground().setAlpha(0);
				tv_happy.getBackground().setAlpha(0);
				//FragmentManager fm2 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
				//FragmentTransaction ft2 = fm2.beginTransaction();
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
				ft.addToBackStack("life_fragment");
				ft.replace(R.id.content_linner, life_fragment).commit();
				break;
			} catch (Exception e) {
				// TODO: handle exception
				break;
			}

		case R.id.tv_lomo:
			image_kinds_layout.setVisibility(View.GONE);
			try {
				tv_lomo.setBackgroundColor(Color.rgb(168, 232, 242));
				tv_secenery.getBackground().setAlpha(0);
				tv_life.getBackground().setAlpha(0);
				tv_sexy.getBackground().setAlpha(0);
				tv_shenhuo.getBackground().setAlpha(0);
				tv_shentai.getBackground().setAlpha(0);
				tv_meishi.getBackground().setAlpha(0);
				tv_dawu.getBackground().setAlpha(0);
				tv_happy.getBackground().setAlpha(0);
				//FragmentManager fm3 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
				//FragmentTransaction ft3 = fm3.beginTransaction();
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
				ft.addToBackStack("lomo_fragment");
				ft.replace(R.id.content_linner, lomo_fragment).commit();
				break;
			} catch (Exception e) {
				// TODO: handle exception
				break;
			}

		case R.id.tv_sexy:
			image_kinds_layout.setVisibility(View.GONE);
			try {
				tv_sexy.setBackgroundColor(Color.rgb(168, 232, 242));
				tv_secenery.getBackground().setAlpha(0);
				tv_life.getBackground().setAlpha(0);
				tv_lomo.getBackground().setAlpha(0);
				tv_shenhuo.getBackground().setAlpha(0);
				tv_shentai.getBackground().setAlpha(0);
				tv_meishi.getBackground().setAlpha(0);
				tv_dawu.getBackground().setAlpha(0);
				tv_happy.getBackground().setAlpha(0);
				//FragmentManager fm4 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
				//FragmentTransaction ft4 = fm4.beginTransaction();
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
				ft.addToBackStack("sexy_fragment");
				ft.replace(R.id.content_linner, sexy_fragment).commit();
				break;
			} catch (Exception e) {
				// TODO: handle exception
				break;
			}
		case R.id.tv_meishi:
			image_kinds_layout.setVisibility(View.GONE);
			tv_meishi.setBackgroundColor(Color.rgb(168, 232, 242));
			tv_secenery.getBackground().setAlpha(0);
			tv_life.getBackground().setAlpha(0);
			tv_lomo.getBackground().setAlpha(0);
			tv_shenhuo.getBackground().setAlpha(0);
			tv_shentai.getBackground().setAlpha(0);
			tv_sexy.getBackground().setAlpha(0);
			tv_dawu.getBackground().setAlpha(0);
			tv_happy.getBackground().setAlpha(0);
			//FragmentManager fm5 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
			//FragmentTransaction ft5 = fm5.beginTransaction();
			ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
			ft.addToBackStack("meishi_fragment");
			ft.replace(R.id.content_linner, meishi_fragment).commit();
			break;
		case R.id.tv_sehngtai:
			image_kinds_layout.setVisibility(View.GONE);
			tv_shentai.setBackgroundColor(Color.rgb(168, 232, 242));
			tv_secenery.getBackground().setAlpha(0);
			tv_life.getBackground().setAlpha(0);
			tv_lomo.getBackground().setAlpha(0);
			tv_shenhuo.getBackground().setAlpha(0);
			tv_meishi.getBackground().setAlpha(0);
			tv_sexy.getBackground().setAlpha(0);
			tv_dawu.getBackground().setAlpha(0);
			tv_happy.getBackground().setAlpha(0);
			//FragmentManager fm6 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
			//FragmentTransaction ft6 = fm6.beginTransaction();
			ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
			ft.addToBackStack("shentai_fragment");
			ft.replace(R.id.content_linner, shentai_fragment).commit();
			break;
		case R.id.tv_shenghuo:
			image_kinds_layout.setVisibility(View.GONE);
			tv_shenhuo.setBackgroundColor(Color.rgb(168, 232, 242));
			tv_secenery.getBackground().setAlpha(0);
			tv_life.getBackground().setAlpha(0);
			tv_lomo.getBackground().setAlpha(0);
			tv_shentai.getBackground().setAlpha(0);
			tv_meishi.getBackground().setAlpha(0);
			tv_sexy.getBackground().setAlpha(0);
			tv_dawu.getBackground().setAlpha(0);
			tv_happy.getBackground().setAlpha(0);
			//FragmentManager fm7 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
			//FragmentTransaction ft7 = fm7.beginTransaction();
			ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
			ft.addToBackStack("shenghuo_fragment");
			ft.replace(R.id.content_linner, shenghuo_fragment).commit();
			break;
		case R.id.tv_dawu:
			image_kinds_layout.setVisibility(View.GONE);
			tv_dawu.setBackgroundColor(Color.rgb(168, 232, 242));
			tv_secenery.getBackground().setAlpha(0);
			tv_life.getBackground().setAlpha(0);
			tv_lomo.getBackground().setAlpha(0);
			tv_shentai.getBackground().setAlpha(0);
			tv_meishi.getBackground().setAlpha(0);
			tv_sexy.getBackground().setAlpha(0);
			tv_shenhuo.getBackground().setAlpha(0);
			tv_happy.getBackground().setAlpha(0);
			//FragmentManager fm8 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
			//FragmentTransaction ft8 = fm8.beginTransaction();
			ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
			ft.addToBackStack("dawu_fragment");
			ft.replace(R.id.content_linner, dawu_fragment).commit();
			break;
		case R.id.tv_happy:
			image_kinds_layout.setVisibility(View.GONE);
			tv_happy.setBackgroundColor(Color.rgb(168, 232, 242));
			tv_secenery.getBackground().setAlpha(0);
			tv_life.getBackground().setAlpha(0);
			tv_lomo.getBackground().setAlpha(0);
			tv_shentai.getBackground().setAlpha(0);
			tv_meishi.getBackground().setAlpha(0);
			tv_sexy.getBackground().setAlpha(0);
			tv_shenhuo.getBackground().setAlpha(0);
			tv_dawu.getBackground().setAlpha(0);
			//FragmentManager fm8 = getFragmentManager();	//��һ������ʱ��Ĭ��fragment
			//FragmentTransaction ft8 = fm8.beginTransaction();
			ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
			ft.addToBackStack("happy_fragment");
			ft.replace(R.id.content_linner, happy_fragment).commit();
			break;
		default:
			break;
		}
	}

}
