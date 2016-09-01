package com.example.protectradish0711.activity;

//如何记住刚才界面显示的第几张的图片？？要弄三个ViewPager才能实现！
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

	// 声明适配器控件对象
	ViewPager vp;
	// 存放View的集合
	ArrayList<View> views = new ArrayList<View>();
	// 声明布局过滤器对象
	LayoutInflater li;
	// 创建控件数组
	ImageView[] ivs = new ImageView[4];
	// 创建控件id数组
	String[] strs = { "aback", "ahelp", "amonster", "awerpon" };
	String[] texts = { "first", "second", "third", "fourth" };
	MyAdapter my;
	TextView stv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_layout);
		stv = (TextView) findViewById(R.id.tv_help_abox);
		// 1.创建LayoutInflater对象
		li = LayoutInflater.from(this);
		// 2.设置内容
		setContent("ahelpi" + 4, texts[3]);
		for (int i = 0; i < 4; i++) {
			setContent("ahelpi" + (i + 1), texts[i]);
		}
		setContent("ahelpi" + 1, texts[0]);
		// 3.获取ViewPager控件对象
		vp = (ViewPager) findViewById(R.id.viewPager);
		// 4.创建适配器对象
		my = new MyAdapter();
		//5.适配器控件对象关联适配器
		vp.setAdapter(my);
		// 设置当前界面
		vp.setCurrentItem(1);
		// 设置onPager监听
		vp.setOnPageChangeListener(this);
		// 获取控件对象，并关联控件
		for (int i = 0; i < ivs.length; i++) {
			String name = "iv_help_" + strs[i];
			int id = getResources().getIdentifier(name, "id", getPackageName());
			ivs[i] = (ImageView) findViewById(id);
			ivs[i].setOnTouchListener(this);
		}
	}

	public void setContent(String name, String text) {
		// 获取布局文件转为View
		View view = li.inflate(R.layout.help_viewpaper_item, null);
		// 获取控件
		ImageView iv = (ImageView) view.findViewById(R.id.item_iv);
		TextView tv = (TextView) view.findViewById(R.id.item_it);
		// 设置控件内容
		int id = getResources().getIdentifier(name, "drawable",
				getPackageName());
		iv.setImageResource(id);
		tv.setText(text);
		views.add(view);

	}

	/**
	 * 配置ViewPager的适配器
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
		 * 添加元素
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			System.out.println(views.get(position));
			// 为什么角标会大于集合尺寸，角标怎么确定的？因为移除的是另一个尺寸比较大的集合。所以角标可能大于集合尺寸
			View v = position >= views.size() ? views.get(views.size() - 1)
					: views.get(position);
			container.addView(v);
			return v;
		}

		/**
		 * 去除元素
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			View v = position >= views.size() ? views.get(views.size() - 1)
					: views.get(position);
			// 为什么不能用removeAt?
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
				// 2.设置内容
				// 设置滑动的图片可以循环。
				setContent("ahelpi" + 4, texts[3]);
				for (int i = 0; i < 4; i++) {
					setContent("ahelpi" + (i + 1), texts[i]);
				}
				setContent("ahelpi" + 1, texts[0]);
				// 刷新适配器
				my.notifyDataSetChanged();
				//刷新后还要关联ViewPager
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
				// 2.设置内容
				setContent("e" + 5, "");
				for (int i = 0; i < 5; i++) {
					setContent("e" + i, "");
				}
				setContent("e" + 1, "");
				//刷新适配器
				my.notifyDataSetChanged();
				//刷新后还要关联ViewPager
				vp.setAdapter(my);
				// 记得设置抬起后当前显示的图片
				vp.setCurrentItem(1);
			}
		} else if (v.getId() == R.id.iv_help_awerpon) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				views.clear();
				ivs[1].setBackgroundResource(R.drawable.ahelp1);
				ivs[2].setBackgroundResource(R.drawable.amonster1);
				ivs[3].setBackgroundResource(R.drawable.aweapon2);
				stv.setText("1/8");
				// 2.设置内容
				setContent("ahelpw" + 8, "");
				for (int i = 0; i < 8; i++) {
					setContent("ahelpw" + (i + 1), "");
				}
				setContent("ahelpw" + 1, "");
				//刷新适配器
				my.notifyDataSetChanged();
				//刷新后还要关联ViewPager
				vp.setAdapter(my);
				//记得设置抬起后当前显示的图片
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
		//TextView的文字随图片的变化而变化
		stv.setText(arg0 + "/" + (views.size() - 2));
		// 如果显示的是最后一张，就变成第一张
		if (arg0 == views.size() - 1) {
			vp.setCurrentItem(1, false);
		} else if (arg0 == 0) {
			vp.setCurrentItem(views.size() - 2, false);
		}
	}

}
