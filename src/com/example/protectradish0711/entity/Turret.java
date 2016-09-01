package com.example.protectradish0711.entity;

import java.util.ArrayList;

import com.example.protectradish0711.bomb.Bomb;
import com.example.protectradish0711.bullet.TurretBullet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Turret extends Weapon {
	int index = 0;
	// 炮塔随着怪物旋转
	Matrix matrix;
	// 声明子弹的图片
	Bitmap[] bits2;
	ArrayList<TurretBullet> bullets;
	// 声明爆炸的图片
	Bitmap[] bits3;
	ArrayList<Bomb> bombs;
	// 攻击力
	int ADK = 2;

	int time;

	public Turret(Bitmap[] bits/* 炮塔图片 */, Bitmap[] bits2/* 子弹图片 */,
			Bitmap[] bits3, float x, float y) {
		super(bits, x, y);
		this.bits2 = bits2;
		this.bits3 = bits3;
		matrix = new Matrix();
		// 可以将图片平移到指定的位置。
		matrix.setTranslate(x, y);// 设置了平移不能再设置旋转，会覆盖掉。
		// matrix.setRotate(degrees)//顺时针为正。以炮塔的中心点和怪物中心点计算，利用反三角函数计算角度
		attack = 2;// 攻击范围
		bullets = new ArrayList<TurretBullet>();
		bombs = new ArrayList<Bomb>();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[index], matrix, null);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(canvas);
		}
		// 画爆炸效果
		if (bombs != null) {
			for (int i = 0; i < bombs.size(); i++) {
				bombs.get(i).draw(canvas);
			}
		}

	}

	@Override
	public void update(Monster monster/* 要攻击的怪物 */) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
			if (bullets.get(i).mon.isDead) {
				bullets.remove(i);
				i--;
			}
		}

		// 刷新爆炸效果
		for (int j = 0; j < bombs.size(); j++) {
			bombs.get(j).update();
			if (bombs.get(j).index == 2) {
				bombs.remove(j);
			}
		}
		if (monster != null) {
			// 图片的切换
			time++;
			index++;
			if (time < 300) {
				if (index == 3) {
					index = 0;
					// 在这里新建子弹
					TurretBullet tb = new TurretBullet(bits2, midX()
							- bits2[index].getWidth(), midY()
							- bits2[index].getHeight() / 2, monster);
					bullets.add(tb);
				}
			} else if (time>=300&&time<=900) {
				if (index==6) {
					index = 3;
					TurretBullet tb = new TurretBullet(bits2, midX()
							- bits2[index].getWidth(), midY()
							- bits2[index].getHeight() / 2, monster);
					bullets.add(tb);
				}
			}else {
				if (index==9) {
					index=6;
					TurretBullet tb = new TurretBullet(bits2, midX()
							- bits2[index].getWidth(), midY()
							- bits2[index].getHeight() / 2, monster);
					bullets.add(tb);
				}
			}

			// 左上角：-degree，右上：degree
			// 左下角：-（180-degree），右下角：180-degree
			float deltaX = Math.abs(midX() - monster.midX());
			float deltaY = Math.abs(midY() - monster.midY());
			double degree = 90;// 设置默认角度为90度
			if (deltaY != 0) {
				float tan = deltaX / deltaY;
				double r = Math.atan(tan);// 根据正切值求出角的弧度值
				degree = Math.toDegrees(r);// 求出角度
			}
			// 逆时针为正，顺时间为负
			// 怪物在炮塔的左边
			if (midX() > monster.midX()) {
				// 在上方
				if (midY() >= monster.midY()) {
					degree = -degree;
				} else {
					// 左下
					degree = -(180 - degree);
				}
			} else {
				if (midY() < monster.midY()) {
					degree = 180 - degree;
				}
			}
			// 用set重置上一次旋转的角度。
			matrix.setTranslate(x, y);
			// 在原有的基础上进行旋转。
			matrix.postRotate((float) degree, midX(), midY());
		} else {

			// 如果怪物不在攻击范围，重置位置，并清除子弹
			matrix.setTranslate(x, y);
			// bullets.clear();
			bombs.clear();
		}
	}

	@Override
	public void attack(Monster monster) {
		for (int i = 0; i < bullets.size(); i++) {
			boolean flag = bullets.get(i).getRectF()
					.intersect(monster.getRectF());
			if (monster.isDead == false && flag) {
				monster.hp -= ADK;
				Bomb bomb = new Bomb(monster.midX() - bits3[0].getWidth() / 2,
						monster.midY(), bits3, monster);
				bombs.add(bomb);

				if (monster.hp <= 0) {
					monster.isDead = true;
				}
				bullets.remove(i);
				// 使用for循环遍历移除子弹后要i--，或者用迭代器。
				i--;
			} else {
				bombs.clear();
			}
		}

	}

}
