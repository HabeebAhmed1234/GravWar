package com.cromiumapps.gravwar;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;
import android.util.Log;

public class GameTextureManager {
	public static final String TAG = "GameTextureManager"; 
	private BitmapTextureAtlas BitmapTextureAtlas;
	private SimpleBaseGameActivity context;
	
	public static final int ENEMY_PLANET_TEXTURE_INDEX = 0;
	public static final int NEUTRAL_PLANET_TEXTURE_INDEX = 1;
	public static final int PLAYER_PLANET_TEXTURE_INDEX = 2;
	public static final int PLANET_SELECTOR_TEXTURE_INDEX = 3;
	
	public ITiledTextureRegion tiledPlanetTexture; 
	public ITiledTextureRegion missileTexture;
	
	GameTextureManager(SimpleBaseGameActivity context)
	{
		this.context = context;
		loadTextures();
	}
	
	private void loadTextures()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas BitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 1024);
		
		Log.d(TAG, "loading planet texture");
		ITiledTextureRegion TiledPlanetTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(BitmapTextureAtlas, context, "Planet.png", 0, 0, 2, 2);
		Log.d(TAG, "loading Missile texture");
		ITiledTextureRegion MissileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(BitmapTextureAtlas, context, "Missile.png", 460, 460,1,1);
		Log.d(TAG, "BitmapTextureAtlas.load");
		BitmapTextureAtlas.load(); 
		
		tiledPlanetTexture = TiledPlanetTexture;
		missileTexture = MissileTextureRegion;
	}
}
