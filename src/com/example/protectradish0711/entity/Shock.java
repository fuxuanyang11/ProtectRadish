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
	// �ӵ�ͼƬ
	Bitmap[] bits2;
	ShockBullet sb;
	ArrayList<ShockBullet> bullets;
	int ADK = 1;
	// ��ըͼƬ
	Bitmap[] bits3;
	// ��ը����
	ArrayList<Bomb> bombs;
	int time;

	public Shock(Bitmap[] bits/* ����ͼƬ */, Bitmap[] bits2/* �ӵ�ͼƬ */, Bitmap[] bits3,
			float x, float y) {
		super(bits, x, y);
		this.bits2 = bits2;
		this.bits3 = bits3;
		matrix = new Matrix();
		// ���Խ�ͼƬƽ�Ƶ�ָ����λ�á�
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
		// ����ըЧ��
		if (bombs != null) {
			for (int i = 0; i < bombs.size(); i++) {
				bombs.get(i).draw(canvas);
			}
		}
	}

	@Override
	public void update(Monster monster) {
		// ˢ�±�ըЧ��
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
					// �½��ӵ�
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
			
			// ˢ���ӵ�
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
