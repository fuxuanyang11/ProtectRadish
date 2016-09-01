package com.example.protectradish0711.bullet;


import com.example.protectradish0711.entity.Monster;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class TurretBullet extends WeaponBullet{
	int index;
	// 子弹矩形框
	RectF rF;
	Paint paint;
	
	public TurretBullet(Bitmap[] bits, float x, float y,Monster mon) {
		super(bits, x, y,mon);
		speed = 30;
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		
	}

	@Override
	public void draw(Canvas canvas) {
		System.out.println(index+"2222222222222222");
		canvas.drawBitmap(bits[index], x+bits[index].getWidth()/2, y, null);
//		canvas.drawRect(rF,paint);
	}

	@Override
	public void update() {
		index++;
		if (index==9) {
			index = 0;
		}
		float ox = mon.midX()-midX();
		float oy = mon.midY()-midY();
		
		x += speed*ox /Math.sqrt(ox*ox+oy*oy);
		y += speed*oy /Math.sqrt(ox*ox+oy*oy);

	}
	
	/**
	 * 子弹矩形框
	 * @return
	 */
	public RectF getRectF() {
		 rF = new RectF(x, y, x + bits[index].getWidth(), y
				+ bits[index].getHeight());
		return rF;
	}
	
}
