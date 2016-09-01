package com.example.protectradish0711.activity;

//��μ�ס�ղŽ�����ʾ�ĵڼ��ŵ�ͼƬ����ҪŪ����ViewPager����ʵ�֣�
import java.util.ArrayList;

import com.zx.protectradish0711.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends BaseActivity implements OnTouchListener,
		OnPageChangeListener {

	// �����������ؼ�����
	ViewPager vp;
	// ���View�ļ���
	ArrayList<View> views = new ArrayList<View>();
	// �������ֹ���������
	LayoutInflater li;
	// �����ؼ�����
	ImageView[] ivs = new ImageView[4];
	// �����ؼ�id����
	String[] strs = { "aback", "ahelp", "amonster", "awerpon" };
	String[] texts = { "first", "second", "third", "fourth" };
	MyAdapter my;
	TextView stv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_layout);
		stv = (TextView) findViewById(R.id.tv_help_abox);
		// 1.����LayoutInflater����
		li = LayoutInflater.from(this);
		// 2.��������
		setContent("ahelpi" + 4, texts[3]);
		for (int i = 0; i < 4; i++) {
			setContent("ahelpi" + (i + 1), texts[i]);
		}
		setContent("ahelpi" + 1, texts[0]);
		// 3.��ȡViewPager�ؼ�����
		vp = (ViewPager) findViewById(R.id.viewPager);
		// 4.��������������
		my = new MyAdapter();
		//5.�������ؼ��������������
		vp.setAdapter(my);
		// ���õ�ǰ����
		vp.setCurrentItem(1);
		// ����onPager����
		vp.setOnPageChangeListener(this);
		// ��ȡ�ؼ����󣬲������ؼ�
		for (int i = 0; i < ivs.length; i++) {
			String name = "iv_help_" + strs[i];
			int id = getResources().getIdentifier(name, "id", getPackageName());
			ivs[i] = (ImageView) findViewById(id);
			ivs[i].setOnTouchListener(this);
		}
	}

	public void setContent(String name, String text) {
		// ��ȡ�����ļ�תΪView
		View view = li.inflate(R.layout.help_viewpaper_item, null);
		// ��ȡ�ؼ�
		ImageView iv = (ImageView) view.findViewById(R.id.item_iv);
		TextView tv = (TextView) view.findViewById(R.id.item_it);
		// ���ÿؼ�����
		int id = getResources().getIdentifier(name, "drawable",
				getPackageName());
		iv.setImageResource(id);
		tv.setText(text);
		views.add(view);

	}

	/**
	 * ����ViewPager��������
	 * 
	 */
	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/**
		 * ���Ԫ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			System.out.println(views.get(position));
			// Ϊʲô�Ǳ����ڼ��ϳߴ磬�Ǳ���ôȷ���ģ���Ϊ�Ƴ�������һ���ߴ�Ƚϴ�ļ��ϡ����ԽǱ���ܴ��ڼ��ϳߴ�
			View v = position >= views.size() ? views.get(views.size() - 1)
					: views.get(position);
			container.addView(v);
			return v;
		}

		/**
		 * ȥ��Ԫ��
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			View v = position >= views.size() ? views.get(views.size() - 1)
					: views.get(position);
			// Ϊʲô������removeAt?
			container.removeView(v);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v.getId() == R.id.iv_help_aback) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				ivs[0].setAlpha(0.5f);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				finish();
			}
		} else if (v.getId() == R.id.iv_help_ahelp) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				views.clear();
				ivs[1].setBackgroundResource(R.drawable.ahelp2);
				ivs[2].setBackgroundResource(R.drawable.amonster1);
				ivs[3].setBackgroundResource(R.drawable.aweapon1);
				stv.setText("1/4");
				// 2.��������
				// ���û�����ͼƬ����ѭ����
				setContent("ahelpi" + 4, texts[3]);
				for (int i = 0; i < 4; i++) {
					setContent("ahelpi" + (i + 1), texts[i]);
				}
				setContent("ahelpi" + 1, texts[0]);
				// ˢ��������
				my.notifyDataSetChanged();
				//ˢ�º�Ҫ����ViewPager
				vp.setAdapter(my);
				vp.setCurrentItem(1);
			}
		} else if (v.getId() == R.id.iv_help_amonster) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				views.clear();
				ivs[1].setBackgroundResource(R.drawable.ahelp1);
				ivs[2].setBackgroundResource(R.drawable.amonster2);
				ivs[3].setBackgroundResource(R.drawable.aweapon1);
				stv.setText("1/5");
				// 2.��������
				setContent("e" + 5, "");
				for (int i = 0; i < 5; i++) {
					setContent("e" + i, "");
				}
				setContent("e" + 1, "");
				//ˢ��������
				my.notifyDataSetChanged();
				//ˢ�º�Ҫ����ViewPager
				vp.setAdapter(my);
				// �ǵ�����̧���ǰ��ʾ��ͼƬ
				vp.setCurrentItem(1);
			}
		} else if (v.getId() == R.id.iv_help_awerpon) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				views.clear();
				ivs[1].setBackgroundResource(R.drawable.ahelp1);
				ivs[2].setBackgroundResource(R.drawable.amonster1);
				ivs[3].setBackgroundResource(R.drawable.aweapon2);
				stv.setText("1/8");
				// 2.��������
				setContent("ahelpw" + 8, "");
				for (int i = 0; i < 8; i++) {
					setContent("ahelpw" + (i + 1), "");
				}
				setContent("ahelpw" + 1, "");
				//ˢ��������
				my.notifyDataSetChanged();
				//ˢ�º�Ҫ����ViewPager
				vp.setAdapter(my);
				//�ǵ�����̧���ǰ��ʾ��ͼƬ
				vp.setCurrentItem(1);
			}
		}
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		//TextView��������ͼƬ�ı仯���仯
		stv.setText(arg0 + "/" + (views.size() - 2));
		// �����ʾ�������һ�ţ��ͱ�ɵ�һ��
		if (arg0 == views.size() - 1) {
			vp.setCurrentItem(1, false);
		} else if (arg0 == 0) {
			vp.setCurrentItem(views.size() - 2, false);
		}
	}

}
