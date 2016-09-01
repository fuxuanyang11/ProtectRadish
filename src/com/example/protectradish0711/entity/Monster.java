package com.example.protectradish0711.entity;

import java.util.Random;

import com.example.protectradish0711.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Monster {

	public float hp;// Ѫ��
	public float totalHP;
	public int speed ;// �����ƶ����ٶ�
	float x, y;// ���������
	Bitmap[][] bits;// ���й����ͼƬ
	int level;// ������������ֻ
	int index;// ����ĽǱ�
	// ��������˶���״̬�ŵ�һ������
	String[] path = { "��", "��", "��", "��", "��", "��", "��" };
	int pathIndex = 0;// ��ǰ�����״̬
	public boolean isDead = false;// �жϹ����Ƿ�����
	// ����ľ��ο�
	RectF  rF;
	//Ѫ���ľ��ο�
	RectF blood;
	public Paint paint;

	public Monster(Bitmap[][] bits, float x, float y) {
		this.bits = bits;
		this.x = x;
		this.y = y;
		Random random = new Random();
		level = random.nextInt(5);// ����0��������5.
		paint = new Paint();
//		paint.setStyle(Style.STROKE);
		paint.setColor(Color.GREEN);
		hp = 100/(level+1);
		totalHP = 100/(level+1);
		blood = new RectF(x, y, x+bits[level][index].getWidth(),y+3);
		speed = 5*(level+1);
		
	}

	/**
	 * �ƶ� 6���սǵ㣬һ���յ㣬���б�š��ж������ĸ��սǵ㣬�͸ı䷽��
	 * (4,1),(4,4)(3,4)(3,7)(4,7)(4,10)(1,10)
	 */
	public void update(Point[] points) {
		// ��ǰ�����״̬
		if (path[pathIndex].equals("��")) {
			y += speed;
			if (y > points[pathIndex].x/* ���ӵ��� */* (GameView.y)) {
				if (pathIndex != points.length - 1) {
					pathIndex++;
					y = points[pathIndex].x*(GameView.y);
				} else {
					isDead = true;
				}
			}
		}
		if (path[pathIndex].equals("��")) {
			x += speed;
			if (x > points[pathIndex].y/* ���ӵ��� */* (GameView.x)) {
				if (pathIndex != points.length - 1) {
					pathIndex++;
					x =  points[pathIndex].y/* ���ӵ��� */* (GameView.x);
				} else {
					isDead = true;
				}
			}
		}
		if (path[pathIndex].equals("��")) {
			y -= speed;
			// yӦ��С�ڣ����ڵĻ���һֱ���ڵġ�
			if (y <= points[pathIndex].x/* ���ӵ��� */* (GameView.y)) {
				if (pathIndex != points.length - 1) {
					pathIndex++;
					y = points[pathIndex].x/* ���ӵ��� */* (GameView.y);
				} else {
					isDead = true;
				}
			}
		}
		index++;
		if (index == 2) {
			index = 0;
		}
	}

	/**
	 * �������ĵ�ĺ�����
	 */
	public float midX() {
		return x + bits[level][0].getWidth() / 2;
	}

	/**
	 * �������ĵ��������
	 */
	public float midY() {
		return y + bits[level][0].getHeight() / 2;
	}

	/**
	 * ���ƹ���
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[level][index], x, y, null);
		//�ǵý�Ѫ������Ϊfloat����Ȼintֵÿ�ζ����0
		canvas.drawRect(x, y, x+bits[level][index].getWidth()*(hp/totalHP),y+3, paint);
		//Ѫ����һ�¾Ͳ����ˡ�
//		blood.set(x, y, x+bits[level][index].getWidth()*(hp/totalHP),y+3);
//		blood.set(x, y, x+bits[level][index].getWidth(),y+3);
//		canvas.drawRect(blood, paint);
	}
	
	/**
	 * ����ľ��ο�
	 */
	public RectF getRectF() {
		rF = new RectF(x, y, x + bits[level][index].getWidth(), y
				+ bits[level][index].getHeight());
		return rF;
	}
	
}
