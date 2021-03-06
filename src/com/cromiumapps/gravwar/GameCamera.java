package com.cromiumapps.gravwar;

import org.andengine.engine.camera.Camera;



public class GameCamera extends Camera {
	public static final int FPS = 30; 
	public static float GAME_SCALING_FACTOR = 1;
	
	public static float desiredWidth = 450;
	public static float desiredHeight = 800;
	
	public GameCamera() {
		super(0, 0, desiredWidth, desiredHeight);
		//setScreenSize();
	}
	
	public float getScreenHeight(){
		return this.getHeight();
	}

	/*@SuppressLint("NewApi")
	private void setScreenSize()
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
		GAME_SCALING_FACTOR = (desiredWidth + desiredHeight) / (width + height);
		desiredWidth= width;
		desiredHeight = height;
	}*/
}