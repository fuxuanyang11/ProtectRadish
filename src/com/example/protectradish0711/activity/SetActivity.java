package com.example.protectradish0711.activity;

import com.zx.protectradish0711.R;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SetActivity extends BaseActivity implements OnTouchListener {
	ImageView[] ivs = new ImageView[5];
	String[] strs = { "setback", "set1item", "set1about", "sound", "music" };
	boolean sound;
	boolean music;
	LinearLayout background;
	RelativeLayout part2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_layout);
		background = (LinearLayout) findViewById(R.id.set_background);
		part2 = (RelativeLayout) findViewById(R.id.set_part2);
		for (int i = 0; i < ivs.length; i++) {
			String name = "iv_set_" + strs[i];
			int id = getResources().getIdentifier(name, "id", getPackageName());
			ivs[i] = (ImageView) findViewById(id);
			ivs[i].setOnTouchListener(this);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// ���ؼ�
		if (v.getId() == R.id.iv_set_setback) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				ivs[0].setBackgroundResource(R.drawable.setback1);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				ivs[0].setBackgroundResource(R.drawable.setback2);
				finish();
			}
			// ѡ��
		} else if (v.getId() == R.id.iv_set_set1item) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// ���ѡ��ͽ�ѡ��ͼƬ�л�����ɫ���������л���ǳɫ
				ivs[1].setBackgroundResource(R.drawable.setitem2);
				ivs[2].setBackgroundResource(R.drawable.set1about1);
				background.setBackgroundResource(R.drawable.setbg);
				part2.setVisibility(View.VISIBLE);
			}
			// ������
		} else if (v.getId() == R.id.iv_set_set1about) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// �������������������ͼƬ�л�����ɫ��ѡ��ͼƬ�л���ǳɫ
				ivs[2].setBackgroundResource(R.drawable.set1about2);
				ivs[1].setBackgroundResource(R.drawable.set1item1);

				background.setBackgroundResource(R.drawable.aboutbg);
				part2.setVisibility(View.INVISIBLE);
			}
			// ��Ч
		} else if (v.getId() == R.id.iv_set_sound) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				sound = !sound;
				if (sound) {
					ivs[3].setBackgroundResource(R.drawable.sound2);
				} else {
					ivs[3].setBackgroundResource(R.drawable.sound1);
				}
			}
			// ��������
		} else if (v.getId() == R.id.iv_set_music) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// �ж�ǰ�ȷ�ת
				music = !music;
				if (music) {
					ivs[4].setBackgroundResource(R.drawable.music2);
				} else {
					ivs[4].setBackgroundResource(R.drawable.music1);
				}
			}
		}

		return true;
	}
}
