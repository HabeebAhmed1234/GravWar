package com.cromiumapps.gravwar;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.cromiumapps.gravwar.GameManager.GameOutcomeListener;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener, GameOutcomeListener {
	private GameManager gameManager;
	private GameCamera gameCamera;
	
	@Override
	public void onCreate(Bundle pSavedInstanceState)
	{
		super.onCreate(pSavedInstanceState);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		gameCamera = new GameCamera(this);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), gameCamera);
	}
	
	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new org.andengine.engine.LimitedFPSEngine(pEngineOptions, gameCamera.FPS);
	}

	@Override
	public void onCreateResources() {
	}
	
	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
				
		//all the things required for the gameManager to run
		GameOutcomeListener gameOutcomeListener = this;
		VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager(); 
		GameScene gameScene = new GameScene(this); 
		GameCamera camera = gameCamera; 
		GameResourceManager.loadAllResources(this);
		
		gameManager = new GameManager(gameOutcomeListener,
									  vertexBufferObjectManager, 
									  gameScene, 
									  camera);
		
		return gameManager.getGameScene();
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		Log.d("GravWar", "MainActivty: touch event occured");
		gameManager.onTouchAnywhere(pSceneTouchEvent);
		return false;
	}

	@Override
	public void onGameOutComeListener(int gameOutCome, float timeElapsed) {
		  Utilities.startGameFinishedActivity(this, gameOutCome, timeElapsed);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Utilities.startMainMenuActivity(this);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}