package com.example.protectradish0711.activity;

import com.example.protectradish0711.view.GameView;

import android.os.Bundle;

public class GameActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameView gv = new GameView(this);
		setContentView(gv);
	}
}
