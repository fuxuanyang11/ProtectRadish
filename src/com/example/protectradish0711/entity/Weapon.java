package com.example.protectradish0711.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Weapon {
	
	Bitmap[] bits;//炮塔的图片
	float x,y;//炮塔的坐标
	public float attack ;//攻击范围
	public Weapon(Bitmap[] bits,float x,float y) {
		this.bits = bits;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 炮塔中心点的横坐标
	 */
	public float midX() {
		return x+bits[0].getWidth()/2;
	}
	/**
	 * 炮塔中心点的纵坐标
	 */
	public float midY() {
		return y+bits[0].getHeight()/2;
	}
	/**
	 * 绘制炮塔的方法
	 * @param canvas
	 */
	public abstract void draw(Canvas canvas);
	
	/**
	 * 更新,即炮塔图片的切换
	 */
	public abstract void update(Monster monster);
	

	
	/**
	 * 攻击怪物
	 */
	public abstract void attack(Monster monster);
}