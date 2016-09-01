package com.example.protectradish0711.activity;

import com.example.protectradish0711.view.LogoView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LogoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏信息栏
		Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LogoView lv = new LogoView(this);
		setContentView(lv);
	}
	
}
