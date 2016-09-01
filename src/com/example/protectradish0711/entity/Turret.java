package com.example.protectradish0711.entity;

import java.util.ArrayList;

import com.example.protectradish0711.bomb.Bomb;
import com.example.protectradish0711.bullet.TurretBullet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Turret extends Weapon {
	int index = 0;
	// �������Ź�����ת
	Matrix matrix;
	// �����ӵ���ͼƬ
	Bitmap[] bits2;
	ArrayList<TurretBullet> bullets;
	// ������ը��ͼƬ
	Bitmap[] bits3;
	ArrayList<Bomb> bombs;
	// ������
	int ADK = 2;

	int time;

	public Turret(Bitmap[] bits/* ����ͼƬ */, Bitmap[] bits2/* �ӵ�ͼƬ */,
			Bitmap[] bits3, float x, float y) {
		super(bits, x, y);
		this.bits2 = bits2;
		this.bits3 = bits3;
		matrix = new Matrix();
		// ���Խ�ͼƬƽ�Ƶ�ָ����λ�á�
		matrix.setTranslate(x, y);// ������ƽ�Ʋ�����������ת���Ḳ�ǵ���
		// matrix.setRotate(degrees)//˳ʱ��Ϊ���������������ĵ�͹������ĵ���㣬���÷����Ǻ�������Ƕ�
		attack = 2;// ������Χ
		bullets = new ArrayList<TurretBullet>();
		bombs = new ArrayList<Bomb>();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits[index], matrix, null);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(canvas);
		}
		// ����ըЧ��
		if (bombs != null) {
			for (int i = 0; i < bombs.size(); i++) {
				bombs.get(i).draw(canvas);
			}
		}

	}

	@Override
	public void update(Monster monster/* Ҫ�����Ĺ��� */) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
			if (bullets.get(i).mon.isDead) {
				bullets.remove(i);
				i--;
			}
		}

		// ˢ�±�ըЧ��
		for (int j = 0; j < bombs.size(); j++) {
			bombs.get(j).update();
			if (bombs.get(j).index == 2) {
				bombs.remove(j);
			}
		}
		if (monster != null) {
			// ͼƬ���л�
			time++;
			index++;
			if (time < 300) {
				if (index == 3) {
					index = 0;
					// �������½��ӵ�
					TurretBullet tb = new TurretBullet(bits2, midX()
							- bits2[index].getWidth(), midY()
							- bits2[index].getHeight() / 2, monster);
					bullets.add(tb);
				}
			} else if (time>=300&&time<=900) {
				if (index==6) {
					index = 3;
					TurretBullet tb = new TurretBullet(bits2, midX()
							- bits2[index].getWidth(), midY()
							- bits2[index].getHeight() / 2, monster);
					bullets.add(tb);
				}
			}else {
				if (index==9) {
					index=6;
					TurretBullet tb = new TurretBullet(bits2, midX()
							- bits2[index].getWidth(), midY()
							- bits2[index].getHeight() / 2, monster);
					bullets.add(tb);
				}
			}

			// ���Ͻǣ�-degree�����ϣ�degree
			// ���½ǣ�-��180-degree�������½ǣ�180-degree
			float deltaX = Math.abs(midX() - monster.midX());
			float deltaY = Math.abs(midY() - monster.midY());
			double degree = 90;// ����Ĭ�ϽǶ�Ϊ90��
			if (deltaY != 0) {
				float tan = deltaX / deltaY;
				double r = Math.atan(tan);// ��������ֵ����ǵĻ���ֵ
				degree = Math.toDegrees(r);// ����Ƕ�
			}
			// ��ʱ��Ϊ����˳ʱ��Ϊ��
			// ���������������
			if (midX() > monster.midX()) {
				// ���Ϸ�
				if (midY() >= monster.midY()) {
					degree = -degree;
				} else {
					// ����
					degree = -(180 - degree);
				}
			} else {
				if (midY() < monster.midY()) {
					degree = 180 - degree;
				}
			}
			// ��set������һ����ת�ĽǶȡ�
			matrix.setTranslate(x, y);
			// ��ԭ�еĻ����Ͻ�����ת��
			matrix.postRotate((float) degree, midX(), midY());
		} else {

			// ������ﲻ�ڹ�����Χ������λ�ã�������ӵ�
			matrix.setTranslate(x, y);
			// bullets.clear();
			bombs.clear();
		}
	}

	@Override
	public void attack(Monster monster) {
		for (int i = 0; i < bullets.size(); i++) {
			boolean flag = bullets.get(i).getRectF()
					.intersect(monster.getRectF());
			if (monster.isDead == false && flag) {
				monster.hp -= ADK;
				Bomb bomb = new Bomb(monster.midX() - bits3[0].getWidth() / 2,
						monster.midY(), bits3, monster);
				bombs.add(bomb);

				if (monster.hp <= 0) {
					monster.isDead = true;
				}
				bullets.remove(i);
				// ʹ��forѭ�������Ƴ��ӵ���Ҫi--�������õ�������
				i--;
			} else {
				bombs.clear();
			}
		}

	}

}
