package com.example.protectradish0711.bullet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.protectradish0711.entity.Monster;

public class LeafBullet extends WeaponBullet {
	int index;
	float fx, fy;
	RectF rF;

	public LeafBullet(Bitmap[] bits, float x, float y, Monster mon) {
		super(bits, x, y, mon);
		speed = 30;
//		float midX = mon.midX();
//		float midY = mon.midY();
		//改成用武器的中心点。
		float ox = mon.midX() - midX();
		float oy = mon.midY() - midY();
		fx = (float) (ox / Math.sqrt((ox * ox) + (oy * oy)));
		fy = (float) (oy / Math.sqrt((ox * ox) + (oy * oy)));
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[index], x, y, null);
	}

	@Override
	public void update() {
		index++;
		if (index == 3) {
			index = 0;
		}
		y += speed * fy;
		x += speed * fx;
	}
	
	/**
	 * 叶子子弹的矩形框
	 */
	public RectF getRectF() {
		rF = new RectF(x, y, x+bits[index].getWidth(), y+bits[index].getHeight());
		return rF;
	}

}
