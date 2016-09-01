package com.example.protectradish0711.entity;

import java.util.ArrayList;

import com.example.protectradish0711.activity.BaseActivity;
import com.example.protectradish0711.activity.GameActivity;
import com.example.protectradish0711.bomb.Bomb;
import com.example.protectradish0711.bullet.LeafBullet;
import com.example.protectradish0711.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Leaf extends Weapon {
	int index;
	Bitmap[] bits2;// ����ͼƬ
	Matrix matrix;
	Bitmap[] bits3;// �ӵ�ͼƬ

	Bitmap[] bits4;// ��ըͼƬ
	ArrayList<Bomb> bombs;
	ArrayList<LeafBullet> leafBullets;
	// ������
	int ATK = 3;
	int time;

	public Leaf(Bitmap[] bits, Bitmap[] bits2, Bitmap[] bits3, Bitmap[] bits4,
			float x, float y) {
		super(bits, x + GameView.x / 2, y);
		// �ǵø�ֵ������
		this.bits2 = bits2;
		this.bits3 = bits3;
		this.bits4 = bits4;
		matrix = new Matrix();
		// ���Խ�ͼƬƽ�Ƶ�ָ����λ�á�
		matrix.setTranslate(x + GameView.x / 2 - bits[0].getWidth() / 2, y);
		attack = 3;
		leafBullets = new ArrayList<LeafBullet>();
		bombs = new ArrayList<Bomb>();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bits2[index], x - bits[1].getWidth() / 2, y + GameView.y
				/ 4, null);
		canvas.drawBitmap(bits[index], matrix, null);
		// ��ԭ�еĻ����Ͻ�����ת��
		matrix.postRotate(30, x, y + bits[0].getHeight() / 2);
		// ���ӵ�
		for (int i = 0; i < leafBullets.size(); i++) {
			leafBullets.get(i).draw(canvas);
		}
		// ����ը
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).draw(canvas);
		}
	}

	@Override
	public void update(Monster monster) {
		// ���±�ը
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).update();
			if (bombs.get(i).index == 2) {
				bombs.remove(i);
			}
		}

		if (monster != null) {
			time++;
			if (time == 9) {
				time = 0;
				//�½��ӵ�
				LeafBullet lb = new LeafBullet(bits3, x - GameView.x / 2, y
						+ bits3[0].getHeight() / 2, monster);
				leafBullets.add(lb);
			}
			
				for (int i = 0; i < leafBullets.size(); i++) {
					leafBullets.get(i).update();
			}
			
		} else {
			// matrix.setTranslate(x + GameView.x / 2 - bits[0].getWidth() / 2,
			// y);
			leafBullets.clear();
		}

	}

	@Override
	public void attack(Monster monster) {
		for (int i = 0; i < leafBullets.size(); i++) {
			boolean flag = leafBullets.get(i).getRectF()
					.intersect(monster.getRectF());
			if (flag && !monster.isDead) {
				monster.hp -= ATK;
				Bomb bomb = new Bomb(monster.midX() - bits4[0].getWidth(),
						monster.midY() - bits4[0].getHeight(), bits4,monster);
				bombs.add(bomb);

			} else {
				bombs.clear();
			}
			if (monster.hp <= 0) {
				monster.isDead = true;
			}
			if (leafBullets.get(i).midX() > BaseActivity.SCREEN_WIDTH
					|| leafBullets.get(i).midX() < 0
					|| leafBullets.get(i).midY() < 0
					|| leafBullets.get(i).midY() > GameActivity.SCREEN_HEIGHT) {
				leafBullets.remove(i);
			}
		}
	}

}
