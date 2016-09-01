package com.example.protectradish0711.bullet;

import com.example.protectradish0711.entity.Monster;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

public class ShockBullet extends WeaponBullet {
	int index;
	Matrix matrix;
	//攻击力
	int ADK = 1;
	RectF rF;

	public ShockBullet(Bitmap[] bits, float x, float y, Monster mon) {
		super(bits, x, y, mon);
		speed = 10;
		matrix = new Matrix();
		matrix.setTranslate(x, y);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[index], matrix, null);
	}
	
	/**
	 * 刷新子弹
	 */
	@Override
	public void update() {
		index++;
		if (index == 9) {
			index = 0;
		}
		float ox = Math.abs(mon.midX() - x);
		float oy = Math.abs(mon.midY() - y);

		double degree = 0;
		if (ox != 0) {
			double atan = Math.atan(oy / ox);
			degree = Math.toDegrees(atan);
		}
		// System.out.println(degree);
		if (mon.midX() - x < 0) {// 左
			if (mon.midY() - y < 0) {
				degree = 90 + degree;
			} else {
				degree = 90 - degree;
			}
		} else {
			if (mon.midY() - y < 0) {
				degree = -(90 + degree);
			} 	else {
				degree = -(90 - degree);
			}
		}
		//怪物和水晶的直线距离。
		float c = (float) Math.sqrt(ox * ox + oy * oy);
		
		//重置旋转的角度
		matrix.setTranslate(x, y);
		//拉伸
		matrix.postScale(1, c / bits[0].getHeight(),
				x + bits[0].getWidth() / 2, y);
		matrix.postRotate((float) degree, x + bits[0].getWidth() / 2, y);
		mon.hp-=ADK;
		if (mon.hp<=0) {
			mon.isDead = true;
		}
		
	}
	
	public RectF getRectF() {
		 rF = new RectF(x, y, x + bits[index].getWidth(), y
					+ bits[index].getHeight());
			return rF;
	}

}
