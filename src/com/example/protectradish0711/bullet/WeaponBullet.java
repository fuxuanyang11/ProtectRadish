package com.example.protectradish0711.bullet;

import com.example.protectradish0711.entity.Monster;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class WeaponBullet {

	int speed;
	float x, y;
	Bitmap[] bits;
	public Monster mon;

	public WeaponBullet(Bitmap[] bits, float x, float y, Monster mon) {
		this.bits = bits;
		this.x = x;
		this.y = y;
		this.mon = mon;
	}

	/**
	 * 子弹中心点的横坐标
	 */
	public float midX() {
		return x + bits[0].getWidth() / 2;
	}

	/**
	 * 子弹中心点的纵坐标
	 */
	public float midY() {
		return y + bits[0].getHeight() / 2;
	}

	/**
	 * 绘制子弹
	 */
	public abstract void draw(Canvas canvas);

	/**
	 * 更新子弹
	 */
	public abstract void update();
}
