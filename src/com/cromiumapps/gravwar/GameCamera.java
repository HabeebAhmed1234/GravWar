package com.cromiumapps.gravwar;

import org.andengine.engine.camera.Camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;



public class GameCamera extends Camera {
	public static final int FPS = 30; 
	
	private static float desiredWidth = 480;
	private static float desiredHeight = 800;
	
	private Activity context;
	
	public GameCamera(Activity context) {
		super(0, 0, desiredWidth, desiredHeight);
		this.context = context;
		setScreenSize();
	}

	@SuppressLint("NewApi") private void setScreenSize()
	{
		int width = 0;
		int height = 0;
		
		Display display = context.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1){
		    //Do something for API 17 only (4.2)
			display.getRealSize(size);
			width = size.x;
			height = size.y;
		}
		else if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2){
		    // Do something for API 13 and above , but below API 17 (API 17 will trigger the above block
		    //getSize()
			display.getSize(size);
			width = size.x;
			height = size.y;
		} else{
		    // do something for phones running an SDK before Android 3.2 (API 13)
		    //getWidth(), getHeight()
			width=display.getWidth();
			height = display.getHeight();
		}
		
		desiredWidth= width;
		desiredHeight = height;
	}
}