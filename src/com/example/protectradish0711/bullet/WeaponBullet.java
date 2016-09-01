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
	 * �ӵ����ĵ�ĺ�����
	 */
	public float midX() {
		return x + bits[0].getWidth() / 2;
	}

	/**
	 * �ӵ����ĵ��������
	 */
	public float midY() {
		return y + bits[0].getHeight() / 2;
	}

	/**
	 * �����ӵ�
	 */
	public abstract void draw(Canvas canvas);

	/**
	 * �����ӵ�
	 */
	public abstract void update();
}
