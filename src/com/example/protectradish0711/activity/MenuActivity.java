package com.example.protectradish0711.activity;


import com.zx.protectradish0711.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MenuActivity extends BaseActivity implements OnTouchListener {
	TextView[] tvs = new TextView[3];
	// �洢�ؼ�id������
	String[] strs = { "menu", "set", "about" };
	// �洢ͼƬid������
	String[] strs1 = { "menu1", "set1", "about1" };
	String[] strs2 = { "menu2", "set2", "about2" };
	@SuppressWarnings("rawtypes")
	Class[] cls={GameActivity.class,SetActivity.class,HelpActivity.class};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout2);
	
		// ������
		for (int i = 0; i < tvs.length; i++) {
			String name = "tv_menu_" + strs[i];
			int id = getResources().getIdentifier(name, "id", getPackageName());
			tvs[i] = (TextView) findViewById(id);
			tvs[i].setOnTouchListener(this);
		}
	}

	/**
	 * �����¼�
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		for (int i = 0; i < tvs.length; i++) {
			String name = "tv_menu_" + strs[i];
			String drawableId1 = strs1[i];
			String drawableId2 = strs2[i];
			// ��ȡ�ؼ�id
			int id = getResources().getIdentifier(name, "id", getPackageName());	

			// �жϴ����ؼ�����״̬
			if (v.getId() == id) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// ��ȡͼƬid
					int id1 = getResources().getIdentifier(drawableId1,
							"drawable", getPackageName());
					tvs[i].setBackgroundResource(id1);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// ��ȡͼƬid
					int id2 = getResources().getIdentifier(drawableId2,
							"drawable", getPackageName());
					tvs[i].setBackgroundResource(id2);
					Intent intent = new Intent(this, cls[i]);
					startActivity(intent);
				}
			}
		}

		return true;
	}
}
