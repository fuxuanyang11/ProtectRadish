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

	// ��������surface�Ķ���
	SurfaceHolder sh;
	Bitmap bg;// ����ͼƬ
	Bitmap map;// �켣ͼƬ
	// �и�����ͼƬ������
	Bitmap[][] twoBitmaps = new Bitmap[3][2];
	String[] strs = { "turreti", "shocki", "leafi" };
	Bitmap[] bitmaps = new Bitmap[3];
	// �������ڵľ��ο������
	RectF[] buttons = new RectF[3];

	// ����ͼƬ�Ķ�ά���������
	Bitmap[][] nineBitmaps = new Bitmap[3][9];
	String[] str2 = { "turret", "leafb", "turretb" };
	Bitmap[] bitmaps2 = new Bitmap[3];
	// 12��ͼƬ�Ķ�ά����
	Bitmap[][] twelveBitmaps = new Bitmap[2][12];
	String[] str3 = { "shock", "shockbullet" };
	Bitmap[] bitmaps3 = new Bitmap[2];
	// ����ͼƬ�Ķ�ά����
	Bitmap[][] threeBitmaps = new Bitmap[2][3];
	String[] str4 = { "leftb", "leaff" };
	Bitmap[] bitmaps4 = new Bitmap[2];
	// ���ù���ͼƬ�Ķ�ά����
	Bitmap[][] monsterBitmaps = new Bitmap[5][2];
	String[] str5 = { "e0", "e1", "e2", "e3", "e4" };
	Bitmap[] bitmaps5 = new Bitmap[5];
	// ��ű�ըͼƬ�Ķ�ά����
	Bitmap[][] bomBitmaps = new Bitmap[3][2];
	String[] str6 = { "turretbz", "shockbz", "leafbz" };
	Bitmap[] bitmaps6 = new Bitmap[3];
	// ��Ź���ļ���
	ArrayList<Monster> monsters = new ArrayList<Monster>();
	// �ܷ���������Ķ�ά����,Ĭ��false���Է�����
	boolean[][] notEmpty = new boolean[7][12];
	// ��Ź���������
	Point[] points = new Point[7];

	float bx;
	float by;

	// ������
	int time;// �������ˢ�µĴ���
	// ���������ļ���
	ArrayList<Weapon> weapons;

	// ������������
	Canvas canvas;
	Paint paint;
	GameActivity ga;

	// �жϰ���ʱ����ѡ���ĸ�����
	int select = 0;// ƿ��1��ˮ��2��Ҷ��3

	int n;// ����
	public static float x/* ���ӿ� */, y/* ���Ӹ� */;
	public static double diagonal;

	public GameView(Context context) {
		super(context);
		ga = (GameActivity) context;
		// ��������surface�Ķ���
		sh = getHolder();
		// �ǵõ��ó�ʼ��ͼƬ�ķ���
		intiBitmap();
		// ��ʼ�����ο򡢵�
		init();
		// �ص�������
		sh.addCallback(this);
		// �½���������
		weapons = new ArrayList<Weapon>();
		// TODO �Ƿ�հ�
		for (int i = 0; i < notEmpty.length; i++) {
			for (int j = 0; j < notEmpty[i].length; j++) {
				// ·��λ��
				if ((((j == 1 || j == 10) && (i >= 1 && i <= 4))
						|| (i == 3 && (j >= 4 && j < 8)) || (i == 4 && ((j >= 2 && j <= 4) || (j >= 7 && j <= 9))))
						// ����λ��
						|| ((i == 6) && (j >= 5 && j <= 7))) {
					notEmpty[i][j] = true;
				}
			}
		}

	}

	/**
	 * ֻҪ��������������ͻᴥ���˷���������Ҫ������
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();// ����������ʱ��x���ꡣ�в������������λ�õ�
		float touchY = event.getY();// ����������ʱ��y����

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (int i = 0; i < buttons.length; i++) {
				boolean flag = buttons[i].contains(touchX, touchY);// ����������ھ��ο��ڣ�����һ��booleanֵ
				if (flag) {
					select = i + 1;
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (select != 0) {
				// �����������м�������
				int bx = (int) (touchX / x);
				int by = (int) (touchY / y);
				if (!notEmpty[by][bx]) {
					switch (select) {
					case 1:
						Turret turret = new Turret(nineBitmaps[0],
								nineBitmaps[2], bomBitmaps[0], bx * x, by * y);

						// ����������ӵ����ϡ�
						weapons.add(turret);

						select = 0;
						notEmpty[by][bx] = true;
						break;
					case 2:
						Shock shock = new Shock(twelveBitmaps[0],
								twelveBitmaps[1],bomBitmaps[1], bx * x, by * y);
						// ����������ӵ����ϡ�
						weapons.add(shock);
						notEmpty[by][bx] = true;
						select = 0;
						break;
					case 3:
						Leaf leaf = new Leaf(threeBitmaps[0], threeBitmaps[1],
								nineBitmaps[1],bomBitmaps[2], bx * x, by * y);

						// ����������ӵ����ϡ�
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
		return true/* ����ֵҪ�ĳ�true������Ϊtrue���޷��ж���һ��������ֻ�ܼ���һ������ */;
	}

	/**
	 * ʵ��Callback�ӿڵķ���
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
	 * TODO ��������ķ���
	 */
	public void attackMonster() {
		for (int i = 0; i < weapons.size(); i++) {
			for (int j = 0; j < monsters.size(); j++) {
				weapons.get(i).attack(monsters.get(j));
			}

		}
	}

	/**
	 * TODO ��������
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
					// �ǵ��ҵ�һ��Ҫbreak��
					break;
				}
			}
			// ˢ������
			weapon.update(mon);
		}
	}

	/**
	 * TODO ���¹���
	 */
	public void updateMonster() {
		time++;
		if (time % 30 == 0) {
			// �½�����
			Monster monster = new Monster(monsterBitmaps, x, y);
			monsters.add(monster);

		}
		if (time % 300 == 0) {
			// ÿ10��һֻboss
			Monster monster = new Monster(monsterBitmaps, x, y);
			monster.hp = 10 * monster.hp;
			monster.totalHP = monster.hp;
			monster.paint.setColor(Color.RED);
			monsters.add(monster);
		}
		if (time % 3000 == 0) {
			// ÿ20��һֻboss
			Monster monster = new Monster(monsterBitmaps, x, y);
			monster.hp = 2 * monster.hp;
			monster.totalHP = monster.hp;
			monster.paint.setColor(Color.BLUE);
			monsters.add(monster);
		}
		if (time % 3 == 0) {
			// ˢ���Ѵ��ڵĹ������update
			for (Monster monster1 : monsters) {
				monster1.update(points);

			}
		}

	}

	/**
	 * ��ʼ����������
	 */
	public void init() {
		// ��ʼ�����ο�
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new RectF((5 + i) * x, 6 * y, (6 + i) * x, 7 * y);
		}
		/*
		 * ��ʼ���� (4,1),(4,4)(3,4)(3,7)(4,7)(4,10)(1,10)
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
	 * TODO ��ʼ��ͼƬ
	 */
	public void intiBitmap() {
		// ��ʼ������
		paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		// ����ͼƬ��Դ
		bg = BitmapFactory
				.decodeResource(getResources(), R.drawable.background);
		map = BitmapFactory.decodeResource(getResources(), R.drawable.map);

		// ����,����7��ֱ��
		y = 1.0f * GameActivity.SCREEN_HEIGHT / 7;
		// ����Ļһ�п������ж��ٸ�С�����εĽ���ֵ��
		n = Math.round(GameActivity.SCREEN_WIDTH / y);
		// ��������εĿ�
		x = 1.0f * GameActivity.SCREEN_WIDTH / n;
		diagonal = Math.sqrt(x * x + y * y);

		// ����ͼƬ
		decodeBitmap(strs, bitmaps);
		decodeBitmap(str2, bitmaps2);
		decodeBitmap(str3, bitmaps3);
		decodeBitmap(str4, bitmaps4);
		decodeBitmap(str5, bitmaps5);
		decodeBitmap(str6, bitmaps6);

		// �����и�ͼƬ�ķ���

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

		// ����ͼƬ,bgͼƬ���Ϊ��Ļ���
		bg = Bitmap.createScaledBitmap(bg, GameActivity.SCREEN_WIDTH,
				GameActivity.SCREEN_HEIGHT, true);

		// ���þ�������ͼƬmap
		Matrix matrix = new Matrix();
		float sx = 1.0f * GameActivity.SCREEN_WIDTH / map.getWidth();
		matrix.setScale(sx, sx);
		map = Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(),
				matrix, true);

	}

	/**
	 * ����ͼƬ
	 */
	public void decodeBitmap(String[] strs, Bitmap[] bitmap) {
		// ����ͼƬ
		for (int i = 0; i < bitmap.length; i++) {
			String name = strs[i];
			int id = getResources().getIdentifier(name, "drawable",
					ga.getPackageName());
			bitmap[i] = BitmapFactory.decodeResource(getResources(), id);
		}
	}

	/**
	 * TODO �и�ͼƬ
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
	 * TODO ����ͼƬ
	 */
	public void draw() {

		// ��ȡ����
		canvas = sh.lockCanvas();
		if (canvas != null) {
			// ������ͼ�͹켣ͼ
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
			// ������
			for (int i = 0; i < 3; i++) {
				canvas.drawBitmap(twoBitmaps[i][0], (5 + i) * x, 6 * y, null);
			}

			// ������
			// ��for-eachָ������������������Ƴ�����һ��
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
			// ������
			// turret.draw(canvas);
			for (Weapon weapon : weapons) {
				weapon.draw(canvas);
			}

			// ���ظ�������
			sh.unlockCanvasAndPost(canvas);
		}
	}

	boolean isRun = true;

	/**
	 * TODO ���߳�
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
