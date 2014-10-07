package com.cromiumapps.gravwar;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;
import android.util.Log;

public class GameResourceManager {
	public static final String TAG = "GameTextureManager"; 
	
	public static final int ENEMY_PLANET_TEXTURE_INDEX = 0;
	public static final int NEUTRAL_PLANET_TEXTURE_INDEX = 1;
	public static final int PLAYER_PLANET_TEXTURE_INDEX = 2;
	public static final int PLANET_SELECTOR_TEXTURE_INDEX = 3;
	public static final String FONT_FILE_PATH = "fnt/heavy_data.ttf";
	public static final String PLANET_IMAGE_FILE_PATH = "Planet.png";
	public static final String MISSILE_PLAYER_IMAGE_FILE_PATH = "Missile_Player.png";
	public static final String MISSILE_ENEMY_IMAGE_FILE_PATH = "Missile_Enemy.png";
	
	public static ITiledTextureRegion tiledPlanetTexture; 
	public static ITiledTextureRegion missileTexturePlayer;
	public static ITiledTextureRegion missileTextureEnemy;
	
	public static Font font; 
	
	public static void loadAllResources(SimpleBaseGameActivity context){
		GameResourceManager.loadTextures(context);
		GameResourceManager.loadFonts(context);
	}
	
	public static ITiledTextureRegion getMissileTexture(boolean isPlayerMissile){
		if(isPlayerMissile) return missileTexturePlayer;
		return missileTextureEnemy;
	}
	
	private static void loadTextures(SimpleBaseGameActivity context)
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas BitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 1024);
		
		Log.d(TAG, "loading planet texture");
		ITiledTextureRegion TiledPlanetTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(BitmapTextureAtlas, context, PLANET_IMAGE_FILE_PATH, 0, 0, 2, 2);
		Log.d(TAG, "loading Missile Player texture");
		ITiledTextureRegion MissilePlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(BitmapTextureAtlas, context, MISSILE_PLAYER_IMAGE_FILE_PATH, 0, 920,1,1);
		Log.d(TAG, "loading Missile Player texture");
		ITiledTextureRegion MissileEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(BitmapTextureAtlas, context, MISSILE_ENEMY_IMAGE_FILE_PATH, 33, 920,1,1);
		Log.d(TAG, "BitmapTextureAtlas.load");
		BitmapTextureAtlas.load(); 
		
		tiledPlanetTexture = TiledPlanetTexture;
		missileTexturePlayer = MissilePlayerTextureRegion;
		missileTextureEnemy = MissileEnemyTextureRegion;
	}
	
	private static void loadFonts(SimpleBaseGameActivity context){
		font = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, context.getAssets(),
			    FONT_FILE_PATH, 18, true, android.graphics.Color.WHITE);
	    font.load();
	}
}
