package com.example.protectradish0711.bomb;

import com.example.protectradish0711.entity.Monster;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bomb {
	float x, y;
	Bitmap[] bits;
	public int index;
	Monster monster;

	public Bomb(float x, float y, Bitmap[] bits, Monster monster) {
		super();
		this.x = x;
		this.y = y;
		this.bits = bits;
		this.monster = monster;
	}

	/**
	 * Ë¢ÐÂ
	 */
	public void update() {
		index++;
		if (index == 2) {
			index = 0;
		}
	}

	/**
	 * »æÍ¼
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[index], monster.midX() - bits[index].getWidth()/2,
				monster.midY() - bits[index].getHeight()/2, null);
	}
}
