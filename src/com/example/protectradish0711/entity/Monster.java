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

	public float hp;// 血量
	public float totalHP;
	public int speed ;// 怪物移动的速度
	float x, y;// 怪物的坐标
	Bitmap[][] bits;// 所有怪物的图片
	int level;// 决定怪物是哪只
	int index;// 怪物的角标
	// 将怪物的运动的状态放到一个数组
	String[] path = { "下", "右", "上", "右", "下", "右", "上" };
	int pathIndex = 0;// 当前怪物的状态
	public boolean isDead = false;// 判断怪物是否死亡
	// 怪物的矩形框
	RectF  rF;
	//血条的矩形框
	RectF blood;
	public Paint paint;

	public Monster(Bitmap[][] bits, float x, float y) {
		this.bits = bits;
		this.x = x;
		this.y = y;
		Random random = new Random();
		level = random.nextInt(5);// 包括0，不包括5.
		paint = new Paint();
//		paint.setStyle(Style.STROKE);
		paint.setColor(Color.GREEN);
		hp = 100/(level+1);
		totalHP = 100/(level+1);
		blood = new RectF(x, y, x+bits[level][index].getWidth(),y+3);
		speed = 5*(level+1);
		
	}

	/**
	 * 移动 6个拐角点，一个终点，进行编号。判断遇到哪个拐角点，就改变方向。
	 * (4,1),(4,4)(3,4)(3,7)(4,7)(4,10)(1,10)
	 */
	public void update(Point[] points) {
		// 当前怪物的状态
		if (path[pathIndex].equals("下")) {
			y += speed;
			if (y > points[pathIndex].x/* 格子的行 */* (GameView.y)) {
				if (pathIndex != points.length - 1) {
					pathIndex++;
					y = points[pathIndex].x*(GameView.y);
				} else {
					isDead = true;
				}
			}
		}
		if (path[pathIndex].equals("右")) {
			x += speed;
			if (x > points[pathIndex].y/* 格子的列 */* (GameView.x)) {
				if (pathIndex != points.length - 1) {
					pathIndex++;
					x =  points[pathIndex].y/* 格子的列 */* (GameView.x);
				} else {
					isDead = true;
				}
			}
		}
		if (path[pathIndex].equals("上")) {
			y -= speed;
			// y应该小于，大于的话是一直大于的。
			if (y <= points[pathIndex].x/* 格子的列 */* (GameView.y)) {
				if (pathIndex != points.length - 1) {
					pathIndex++;
					y = points[pathIndex].x/* 格子的列 */* (GameView.y);
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
	 * 怪物中心点的横坐标
	 */
	public float midX() {
		return x + bits[level][0].getWidth() / 2;
	}

	/**
	 * 怪物中心点的纵坐标
	 */
	public float midY() {
		return y + bits[level][0].getHeight() / 2;
	}

	/**
	 * 绘制怪物
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[level][index], x, y, null);
		//记得将血量声明为float，不然int值每次都变成0
		canvas.drawRect(x, y, x+bits[level][index].getWidth()*(hp/totalHP),y+3, paint);
		//血条打一下就不见了。
//		blood.set(x, y, x+bits[level][index].getWidth()*(hp/totalHP),y+3);
//		blood.set(x, y, x+bits[level][index].getWidth(),y+3);
//		canvas.drawRect(blood, paint);
	}
	
	/**
	 * 怪物的矩形框
	 */
	public RectF getRectF() {
		rF = new RectF(x, y, x + bits[level][index].getWidth(), y
				+ bits[level][index].getHeight());
		return rF;
	}
	
}
