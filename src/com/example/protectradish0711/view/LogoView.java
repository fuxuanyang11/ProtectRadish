package com.example.protectradish0711.view;

import com.zx.protectradish0711.R;
import com.example.protectradish0711.activity.BaseActivity;
import com.example.protectradish0711.activity.LogoActivity;
import com.example.protectradish0711.activity.MenuActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class LogoView extends View implements Runnable {
	Bitmap logo;
	Paint paint;
	int alpha = 255;
	int temp = 30;
	LogoActivity la;

	public LogoView(Context context) {
		super(context);
		// ����ǿתΪcontext��
		la = (LogoActivity) context;
		paint = new Paint();
		logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		paint.setAlpha(255);
		// �����߳�
		Thread thread = new Thread(this);
		thread.start();
	}

	@SuppressLint("ShowToast")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);// ���ñ�����ɫ
		// Toast.makeText(
		// getContext(),
		// (BaseActivity.SCREEN_WIDTH / 2)
		// + "======", 0).show();
		// ��ͼƬ����������
		canvas.drawBitmap(logo, BaseActivity.SCREEN_WIDTH / 2 - logo.getWidth()
				/ 2, BaseActivity.SCREEN_HEIGHT / 2 - logo.getHeight() / 2,
				paint);
	}

	@Override
	public void run() {
		while (alpha > 0) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alpha += temp;
			if (alpha >= 255) {
				temp = -30;
				alpha = 255;
			} else if (alpha <= 0) {
				alpha = 0;
			}
			paint.setAlpha(alpha);
			postInvalidate();
		}
		// RunTimeException��

		Intent toMenuActivity = new Intent(la, MenuActivity.class);
		la.startActivity(toMenuActivity);
		// ������ǰ����
		la.finish();
	}

}
