package com.example.protectradish0711.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class BaseActivity extends Activity{
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//获取屏幕宽高
				//1.获取窗口管理者
				WindowManager manager = getWindowManager();
				//2.获取窗口默认值
				Display display = manager.getDefaultDisplay();
				//3.获取DisplayMetrics对象
				DisplayMetrics dm = new DisplayMetrics();
				//4.将数据存储到DisplayMetrics对象中
				display.getMetrics(dm);
				//5.获取屏幕宽高
				SCREEN_WIDTH = dm.widthPixels;
				SCREEN_HEIGHT = dm.heightPixels;
		
		super.onCreate(savedInstanceState);
	}
}
