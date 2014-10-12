package com.cromiumapps.gravwar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import cromiumapps.gravwar.R;

public class MainMenuActivity extends Activity implements OnClickListener{
	LinearLayout difficultysettingsframe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		initViews();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		return true;
	}
	
	private void initViews()
	{
		//get views
		TextView titleText = (TextView)findViewById(R.id.gametitletext);
	    TextView quickGameText = (TextView)findViewById(R.id.quickgamebuttontext);
	    TextView settingsText = (TextView)findViewById(R.id.settingsbuttontext);
	    TextView easyText = (TextView)findViewById(R.id.easybuttontext);
	    TextView mediumText = (TextView)findViewById(R.id.normalbuttontext);
	    TextView hardText = (TextView)findViewById(R.id.hardbuttontext);
	    
	    LinearLayout quickGameButton = (LinearLayout)findViewById(R.id.quickgamebutton);
	    LinearLayout settingsButton = (LinearLayout)findViewById(R.id.settingsbutton);
	    LinearLayout easyButton = (LinearLayout)findViewById(R.id.easybutton);
	    LinearLayout normalButton = (LinearLayout)findViewById(R.id.normalbutton);
	    LinearLayout hardButton = (LinearLayout)findViewById(R.id.hardbutton);
	    
	    difficultysettingsframe = (LinearLayout) findViewById(R.id.difficultysettingsframe);
	    
	    //click listeners
	    quickGameButton.setOnClickListener(this);
	    settingsButton.setOnClickListener(this);
	    easyButton.setOnClickListener(this);
	    normalButton.setOnClickListener(this);
	    hardButton.setOnClickListener(this);
	    
	    //fonts
	    titleText.setTypeface(Typeface.createFromAsset(getAssets(),"fnt/heavy_data.ttf"));
	    quickGameText.setTypeface(Typeface.createFromAsset(getAssets(),"fnt/heavy_data.ttf"));
	    settingsText.setTypeface(Typeface.createFromAsset(getAssets(),"fnt/heavy_data.ttf"));
	    easyText.setTypeface(Typeface.createFromAsset(getAssets(),"fnt/heavy_data.ttf"));
	    mediumText.setTypeface(Typeface.createFromAsset(getAssets(),"fnt/heavy_data.ttf"));
	    hardText.setTypeface(Typeface.createFromAsset(getAssets(),"fnt/heavy_data.ttf"));
	    
	    //animations
	    titleText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
	    quickGameButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
	    settingsButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.quickgamebutton)
		{
			Utilities.startMainActivity(this);
		}
		
		if(v.getId() == R.id.settingsbutton){
			if(difficultysettingsframe.getVisibility() == LinearLayout.VISIBLE){
				difficultysettingsframe.setVisibility(LinearLayout.GONE);
			}else{
				difficultysettingsframe.setVisibility(LinearLayout.VISIBLE);
			}
		}
	}
}
