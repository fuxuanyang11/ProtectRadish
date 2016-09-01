package com.example.protectradish0711.entity;

import java.util.ArrayList;

import com.example.protectradish0711.bomb.Bomb;
import com.example.protectradish0711.bullet.ShockBullet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Shock extends Weapon {
	int index;
	Matrix matrix;
	// 子弹图片
	Bitmap[] bits2;
	ShockBullet sb;
	ArrayList<ShockBullet> bullets;
	int ADK = 1;
	// 爆炸图片
	Bitmap[] bits3;
	// 爆炸集合
	ArrayList<Bomb> bombs;
	int time;

	public Shock(Bitmap[] bits/* 炮塔图片 */, Bitmap[] bits2/* 子弹图片 */, Bitmap[] bits3,
			float x, float y) {
		super(bits, x, y);
		this.bits2 = bits2;
		this.bits3 = bits3;
		matrix = new Matrix();
		// 可以将图片平移到指定的位置。
		matrix.setTranslate(x, y);
		attack = 2.5f;
		bullets = new ArrayList<ShockBullet>();
		bombs = new ArrayList<Bomb>();
	}

	@Override
	public void draw(Canvas canvas) {
		// System.out.println(index+"---------------------");
		canvas.drawBitmap(bits[index], matrix, null);
		if (sb != null) {
			sb.draw(canvas);
		}
		// 画爆炸效果
		if (bombs != null) {
			for (int i = 0; i < bombs.size(); i++) {
				bombs.get(i).draw(canvas);
			}
		}
	}

	@Override
	public void update(Monster monster) {
		// 刷新爆炸效果
		if (sb!=null) {
			for (int j = 0; j < bombs.size(); j++) {
				bombs.get(j).update();
				
				if (bombs.get(j).index == 2||sb.mon.isDead) {
					bombs.remove(j);
				}
			}
		}
	
		if (monster != null && !monster.isDead) {
			time++;
			index++;
			if (time<=300) {
				if (index == 4) {
					index = 0;
					// 新建子弹
					sb = new ShockBullet(bits2, midX() - bits2[0].getWidth() / 2,
							midY(), monster);
					bullets.add(sb);
				}
			}else if (time>300&&time<=600) {
				if (index == 8) {
					index = 4;
					sb = new ShockBullet(bits2, midX() - bits2[0].getWidth() / 2,
							midY(), monster);
					bullets.add(sb);
				}
			}else {
				if (index == 12) {
					index = 8;
					sb = new ShockBullet(bits2, midX() - bits2[0].getWidth() / 2,
							midY(), monster);
					bullets.add(sb);
				}
			}
			
			// 刷新子弹
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update();
			}
		} else {
			matrix.setTranslate(x, y);
			sb = null;
		}
	}

	@Override
	public void attack(Monster monster) {
		if (sb!=null) {
//			boolean flag = sb.getRectF().intersect(monster.getRectF());
			if (sb.mon.isDead == false ) {
				monster.hp -= ADK;
				Bomb bomb = new Bomb(monster.midX() - bits3[0].getWidth() / 2,
						monster.midY(), bits3,sb.mon);
				bombs.add(bomb);
				}else {
				bombs.clear();
			}
		}
		

	}

}
