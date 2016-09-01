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
		//��ȡ��Ļ���
				//1.��ȡ���ڹ�����
				WindowManager manager = getWindowManager();
				//2.��ȡ����Ĭ��ֵ
				Display display = manager.getDefaultDisplay();
				//3.��ȡDisplayMetrics����
				DisplayMetrics dm = new DisplayMetrics();
				//4.�����ݴ洢��DisplayMetrics������
				display.getMetrics(dm);
				//5.��ȡ��Ļ���
				SCREEN_WIDTH = dm.widthPixels;
				SCREEN_HEIGHT = dm.heightPixels;
		
		super.onCreate(savedInstanceState);
	}
}
