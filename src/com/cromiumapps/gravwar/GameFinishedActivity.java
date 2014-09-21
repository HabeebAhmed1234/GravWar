package com.cromiumapps.gravwar;

import cromiumapps.gravwar.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameFinishedActivity extends Activity implements OnClickListener {
	private int gameOutCome;
	private float gameTime = 0;
	
	private TextView outComeText;
	private TextView timeText;
	private Button restartButton;
	private Button mainMenuButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_finished);
		
		Intent intent = getIntent();
		if(intent!=null)
		{
			Bundle extras = intent.getExtras();
			if(extras!=null)
			{
				gameOutCome = (Integer) extras.get(Constants.GAME_OUTCOME_EXTRA_KEY);
				gameTime = (Float) extras.get(Constants.GAME_TIME_ELAPSED_EXTRA_KEY);
			}
		}
		
		initViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game_finished, menu);
		return true;
	}
	
	private void initViews()
	{
		outComeText = (TextView)findViewById(R.id.gameoutcometext);
		if(gameOutCome == Constants.GAME_WON) outComeText.setText(Constants.GAME_WON_TEXT);
		if(gameOutCome == Constants.GAME_LOST) outComeText.setText(Constants.GAME_LOST_TEXT);
		timeText = (TextView)findViewById(R.id.timetext);
		timeText.setText("time: "+gameTime);
		restartButton = (Button)findViewById(R.id.restartbutton);
		restartButton.setOnClickListener(this);
		mainMenuButton = (Button)findViewById(R.id.mainmenubutton);
		mainMenuButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.restartbutton)
		{
			Utilities.startMainActivity(this);
		}
		
		if(v.getId() == R.id.mainmenubutton)
		{
			Utilities.startMainMenuActivity(this);
		}
	}
}
