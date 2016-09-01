package com.example.protectradish0711.view;

import java.util.ArrayList;

import com.zx.protectradish0711.R;
import com.example.protectradish0711.activity.GameActivity;
import com.example.protectradish0711.entity.Leaf;
import com.example.protectradish0711.entity.Monster;
import com.example.protectradish0711.entity.Shock;
import com.example.protectradish0711.entity.Turret;
import com.example.protectradish0711.entity.Weapon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback, Runnable {

	// 声明管理surface的对象
	SurfaceHolder sh;
	Bitmap bg;// 背景图片
	Bitmap map;// 轨迹图片
	// 切割两张图片的数组
	Bitmap[][] twoBitmaps = new Bitmap[3][2];
	String[] strs = { "turreti", "shocki", "leafi" };
	Bitmap[] bitmaps = new Bitmap[3];
	// 道具所在的矩形框的数组
	RectF[] buttons = new RectF[3];

	// 九张图片的二维数组的数组
	Bitmap[][] nineBitmaps = new Bitmap[3][9];
	String[] str2 = { "turret", "leafb", "turretb" };
	Bitmap[] bitmaps2 = new Bitmap[3];
	// 12张图片的二维数组
	Bitmap[][] twelveBitmaps = new Bitmap[2][12];
	String[] str3 = { "shock", "shockbullet" };
	Bitmap[] bitmaps3 = new Bitmap[2];
	// 三张图片的二维数组
	Bitmap[][] threeBitmaps = new Bitmap[2][3];
	String[] str4 = { "leftb", "leaff" };
	Bitmap[] bitmaps4 = new Bitmap[2];
	// 放置怪物图片的二维数组
	Bitmap[][] monsterBitmaps = new Bitmap[5][2];
	String[] str5 = { "e0", "e1", "e2", "e3", "e4" };
	Bitmap[] bitmaps5 = new Bitmap[5];
	// 存放爆炸图片的二维数组
	Bitmap[][] bomBitmaps = new Bitmap[3][2];
	String[] str6 = { "turretbz", "shockbz", "leafbz" };
	Bitmap[] bitmaps6 = new Bitmap[3];
	// 存放怪物的集合
	ArrayList<Monster> monsters = new ArrayList<Monster>();
	// 能否放置炮塔的二维数组,默认false可以放炮塔
	boolean[][] notEmpty = new boolean[7][12];
	// 存放拐弯点的数组
	Point[] points = new Point[7];

	float bx;
	float by;

	// 计数器
	int time;// 计算怪物刷新的次数
	// 声明炮塔的集合
	ArrayList<Weapon> weapons;

	// 声明画布对象
	Canvas canvas;
	Paint paint;
	GameActivity ga;

	// 判断按下时，在选择哪个道具
	int select = 0;// 瓶子1，水晶2，叶子3

	int n;// 列数
	public static float x/* 格子宽 */, y/* 格子高 */;
	public static double diagonal;

	public GameView(Context context) {
		super(context);
		ga = (GameActivity) context;
		// 创建管理surface的对象
		sh = getHolder();
		// 记得调用初始化图片的方法
		intiBitmap();
		// 初始化矩形框、点
		init();
		// 回调函数。
		sh.addCallback(this);
		// 新建炮塔集合
		weapons = new ArrayList<Weapon>();
		// TODO 是否空白
		for (int i = 0; i < notEmpty.length; i++) {
			for (int j = 0; j < notEmpty[i].length; j++) {
				// 路径位置
				if ((((j == 1 || j == 10) && (i >= 1 && i <= 4))
						|| (i == 3 && (j >= 4 && j < 8)) || (i == 4 && ((j >= 2 && j <= 4) || (j >= 7 && j <= 9))))
						// 道具位置
						|| ((i == 6) && (j >= 5 && j <= 7))) {
					notEmpty[i][j] = true;
				}
			}
		}

	}

	/**
	 * 只要触摸到整个界面就会触发此方法。不需要监听。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();// 触摸到界面时的x坐标。有参数的是有相对位置的
		float touchY = event.getY();// 触摸到界面时的y坐标

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (int i = 0; i < buttons.length; i++) {
				boolean flag = buttons[i].contains(touchX, touchY);// 触摸点如果在矩形框内，返回一个boolean值
				if (flag) {
					select = i + 1;
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (select != 0) {
				// 算出该坐标个有几个格子
				int bx = (int) (touchX / x);
				int by = (int) (touchY / y);
				if (!notEmpty[by][bx]) {
					switch (select) {
					case 1:
						Turret turret = new Turret(nineBitmaps[0],
								nineBitmaps[2], bomBitmaps[0], bx * x, by * y);

						// 将该炮塔添加到集合。
						weapons.add(turret);

						select = 0;
						notEmpty[by][bx] = true;
						break;
					case 2:
						Shock shock = new Shock(twelveBitmaps[0],
								twelveBitmaps[1],bomBitmaps[1], bx * x, by * y);
						// 将该炮塔添加到集合。
						weapons.add(shock);
						notEmpty[by][bx] = true;
						select = 0;
						break;
					case 3:
						Leaf leaf = new Leaf(threeBitmaps[0], threeBitmaps[1],
								nineBitmaps[1],bomBitmaps[2], bx * x, by * y);

						// 将该炮塔添加到集合。
						weapons.add(leaf);
						notEmpty[by][bx] = true;
						select = 0;
						break;
					default:
						break;
					}
				}
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float touchX2 = event.getX();
			float touchY2 = event.getY();
			int row = (int) (touchX2 / x);
			int det = (int) (touchY2 / y);
			bx = row * x;
			by = det * y;

		}
		return true/* 返回值要改成true，若不为true，无法判断下一个动作，只能监听一个动作 */;
	}

	/**
	 * 实现Callback接口的方法
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRun = false;
	}

	/**
	 * TODO 攻击怪物的方法
	 */
	public void attackMonster() {
		for (int i = 0; i < weapons.size(); i++) {
			for (int j = 0; j < monsters.size(); j++) {
				weapons.get(i).attack(monsters.get(j));
			}

		}
	}

	/**
	 * TODO 更新炮塔
	 */
	public void updateWeapon() {

		for (int i = 0; i < weapons.size(); i++) {
			Weapon weapon = weapons.get(i);
			Monster mon = null;
			for (int j = 0; j < monsters.size(); j++) {
				Monster monster = monsters.get(j);
				double distance = Math.sqrt((weapon.midX() - monster.midX())
						* (weapon.midX() - monster.midX())
						+ (weapon.midY() - monster.midY())
						* (weapon.midY() - monster.midY()));
				if (distance < weapon.attack * diagonal) {
					mon = monster;
					// 记得找到一个要break。
					break;
				}
			}
			// 刷新炮塔
			weapon.update(mon);
		}
	}

	/**
	 * TODO 更新怪物
	 */
	public void updateMonster() {
		time++;
		if (time % 30 == 0) {
			// 新建怪物
			Monster monster = new Monster(monsterBitmaps, x, y);
			monsters.add(monster);

		}
		if (time % 300 == 0) {
			// 每10秒一只boss
			Monster monster = new Monster(monsterBitmaps, x, y);
			monster.hp = 10 * monster.hp;
			monster.totalHP = monster.hp;
			monster.paint.setColor(Color.RED);
			monsters.add(monster);
		}
		if (time % 3000 == 0) {
			// 每20秒一只boss
			Monster monster = new Monster(monsterBitmaps, x, y);
			monster.hp = 2 * monster.hp;
			monster.totalHP = monster.hp;
			monster.paint.setColor(Color.BLUE);
			monsters.add(monster);
		}
		if (time % 3 == 0) {
			// 刷新已存在的怪物，调用update
			for (Monster monster1 : monsters) {
				monster1.update(points);

			}
		}

	}

	/**
	 * 初始化（其他）
	 */
	public void init() {
		// 初始化矩形框
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new RectF((5 + i) * x, 6 * y, (6 + i) * x, 7 * y);
		}
		/*
		 * 初始化点 (4,1),(4,4)(3,4)(3,7)(4,7)(4,10)(1,10)
		 */
		points[0] = new Point(4, 1);
		points[1] = new Point(4, 4);
		points[2] = new Point(3, 4);
		points[3] = new Point(3, 7);
		points[4] = new Point(4, 7);
		points[5] = new Point(4, 10);
		points[6] = new Point(1, 10);
	}

	/**
	 * TODO 初始化图片
	 */
	public void intiBitmap() {
		// 初始化画笔
		paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		// 解析图片资源
		bg = BitmapFactory
				.decodeResource(getResources(), R.drawable.background);
		map = BitmapFactory.decodeResource(getResources(), R.drawable.map);

		// 画线,横向7条直线
		y = 1.0f * GameActivity.SCREEN_HEIGHT / 7;
		// 求屏幕一行可以排列多少个小正方形的近似值。
		n = Math.round(GameActivity.SCREEN_WIDTH / y);
		// 求出正方形的宽。
		x = 1.0f * GameActivity.SCREEN_WIDTH / n;
		diagonal = Math.sqrt(x * x + y * y);

		// 解析图片
		decodeBitmap(strs, bitmaps);
		decodeBitmap(str2, bitmaps2);
		decodeBitmap(str3, bitmaps3);
		decodeBitmap(str4, bitmaps4);
		decodeBitmap(str5, bitmaps5);
		decodeBitmap(str6, bitmaps6);

		// 调用切割图片的方法

		cut(bitmaps[0], twoBitmaps[0], x / (bitmaps[0].getWidth() / 2), y
				/ bitmaps[0].getHeight());
		cut(bitmaps[1], twoBitmaps[1], x / (bitmaps[1].getWidth() / 2), y
				/ bitmaps[1].getHeight());
		cut(bitmaps[2], twoBitmaps[2], x / (bitmaps[2].getWidth() / 2), y
				/ bitmaps[2].getHeight());
		cut(bitmaps2[0], nineBitmaps[0], x / (bitmaps2[0].getWidth() / 9), y
				/ bitmaps2[0].getHeight());
		cut(bitmaps2[1], nineBitmaps[1], x / (bitmaps2[1].getWidth() / 9), y
				/ bitmaps2[0].getHeight());
		cut(bitmaps2[2], nineBitmaps[2], 0.8f, 0.8f);
		cut(bitmaps3[0], twelveBitmaps[0], x / (bitmaps3[0].getWidth() / 12), y
				/ bitmaps3[0].getHeight());
		cut(bitmaps3[1], twelveBitmaps[1], 1, 1);
		cut(bitmaps4[0], threeBitmaps[0], x / (bitmaps4[0].getWidth() / 3) * 4
				/ 5, x / (bitmaps4[0].getWidth() / 3) * 4 / 5);
		cut(bitmaps4[1], threeBitmaps[1], x / (bitmaps4[1].getWidth() / 3) * 2
				/ 3, x / (bitmaps4[1].getWidth() / 3) * 2 / 3);
		cut(bitmaps5[0], monsterBitmaps[0], x / (bitmaps5[0].getWidth() / 2), y
				/ bitmaps5[0].getHeight());
		cut(bitmaps5[1], monsterBitmaps[1], x / (bitmaps5[1].getWidth() / 2), y
				/ bitmaps5[1].getHeight());
		cut(bitmaps5[2], monsterBitmaps[2], x / (bitmaps5[2].getWidth() / 2), y
				/ bitmaps5[2].getHeight());
		cut(bitmaps5[3], monsterBitmaps[3], x / (bitmaps5[3].getWidth() / 2), y
				/ bitmaps5[3].getHeight());
		cut(bitmaps5[4], monsterBitmaps[4], x / (bitmaps5[4].getWidth() / 2), y
				/ bitmaps5[4].getHeight());
		cut(bitmaps6[0], bomBitmaps[0], 1, 1);
		cut(bitmaps6[1], bomBitmaps[1], 1, 1);
		cut(bitmaps6[2], bomBitmaps[2], 1, 1);

		// 伸缩图片,bg图片宽高为屏幕宽高
		bg = Bitmap.createScaledBitmap(bg, GameActivity.SCREEN_WIDTH,
				GameActivity.SCREEN_HEIGHT, true);

		// 利用矩阵拉伸图片map
		Matrix matrix = new Matrix();
		float sx = 1.0f * GameActivity.SCREEN_WIDTH / map.getWidth();
		matrix.setScale(sx, sx);
		map = Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(),
				matrix, true);

	}

	/**
	 * 解析图片
	 */
	public void decodeBitmap(String[] strs, Bitmap[] bitmap) {
		// 解析图片
		for (int i = 0; i < bitmap.length; i++) {
			String name = strs[i];
			int id = getResources().getIdentifier(name, "drawable",
					ga.getPackageName());
			bitmap[i] = BitmapFactory.decodeResource(getResources(), id);
		}
	}

	/**
	 * TODO 切割图片
	 * 
	 * @param scr
	 * @param bits
	 * @param f
	 * @param f1
	 */
	public void cut(Bitmap scr, Bitmap[] bits, float f, float f1) {
		for (int i = 0; i < bits.length; i++) {
			bits[i] = Bitmap.createBitmap(scr, (scr.getWidth() / bits.length)
					* i, 0, scr.getWidth() / bits.length, scr.getHeight());
			// f = 1.0f * x / bits[i].getWidth();
			// f1 = 1.0f * y / bits[i].getHeight();
			Matrix m = new Matrix();
			m.setScale(f, f1);
			bits[i] = Bitmap.createBitmap(bits[i], 0, 0, bits[i].getWidth(),
					bits[i].getHeight(), m, true);
		}
	}

	/**
	 * TODO 绘制图片
	 */
	public void draw() {

		// 获取画布
		canvas = sh.lockCanvas();
		if (canvas != null) {
			// 画背景图和轨迹图
			canvas.drawBitmap(bg, 0, 0, null);
			canvas.drawBitmap(map, 0,
					GameActivity.SCREEN_HEIGHT - map.getHeight(), null);

			for (int i = 0; i < 6; i++) {
				canvas.drawLine(0, y * (i + 1), GameActivity.SCREEN_WIDTH, y
						* (i + 1), paint);
			}

			for (int i = 0; i < n - 1; i++) {
				canvas.drawLine(x * (i + 1), 0, x * (i + 1),
						GameActivity.SCREEN_HEIGHT, paint);
			}
			// 画道具
			for (int i = 0; i < 3; i++) {
				canvas.drawBitmap(twoBitmaps[i][0], (5 + i) * x, 6 * y, null);
			}

			// 画怪物
			// 用for-each指定怪物的数量，不能移除其中一个
			// for (Monster monster : monsters) {
			// monster.draw(canvas);
			// if (monster.isDead) {
			// // monsters.remove(monster);
			// }
			// }
			for (int i = 0; i < monsters.size(); i++) {
				monsters.get(i).draw(canvas);
				if (monsters.get(i).isDead) {
					monsters.remove(monsters.get(i));
				}
			}
			// canvas.drawBitmap(n, bx, by, null);
			// 画武器
			// turret.draw(canvas);
			for (Weapon weapon : weapons) {
				weapon.draw(canvas);
			}

			// 返回给主程序
			sh.unlockCanvasAndPost(canvas);
		}
	}

	boolean isRun = true;

	/**
	 * TODO 子线程
	 */
	@Override
	public void run() {
		while (isRun) {
			try {
				Thread.sleep(50);
				draw();
				updateWeapon();
				updateMonster();
				attackMonster();
				// updateBullet();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
