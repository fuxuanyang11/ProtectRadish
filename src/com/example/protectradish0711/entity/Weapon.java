package com.example.protectradish0711.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Weapon {
	
	Bitmap[] bits;//������ͼƬ
	float x,y;//����������
	public float attack ;//������Χ
	public Weapon(Bitmap[] bits,float x,float y) {
		this.bits = bits;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * �������ĵ�ĺ�����
	 */
	public float midX() {
		return x+bits[0].getWidth()/2;
	}
	/**
	 * �������ĵ��������
	 */
	public float midY() {
		return y+bits[0].getHeight()/2;
	}
	/**
	 * ���������ķ���
	 * @param canvas
	 */
	public abstract void draw(Canvas canvas);
	
	/**
	 * ����,������ͼƬ���л�
	 */
	public abstract void update(Monster monster);
	

	
	/**
	 * ��������
	 */
	public abstract void attack(Monster monster);
}