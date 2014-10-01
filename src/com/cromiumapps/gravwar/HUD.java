package com.cromiumapps.gravwar;

import java.util.ArrayList;

import org.andengine.entity.primitive.Line;

import com.cromiumapps.gravwar.Planet.PlanetType;

import android.util.Log;



public class HUD {
	public static final String TAG = "HUD";
	ArrayList <Path> paths = new ArrayList<Path>();
	GameScene gameScene;
	
	HUD(ArrayList <Path> paths, GameScene gameScene)
	{
		this.gameScene = gameScene;
		this.paths = paths;
		Log.d("GravWar","HUD: HUD initialized with "+this.paths.size() +" paths");
		renderPaths();
	}
	
	public void renderPaths()
	{
		Log.d("GravWar","HUD: rendering paths");
		for(int i =0 ;i<paths.size();i++)
		{
			Log.d("GravWar","HUD: adding new path to scene");
			gameScene.attachChild(paths.get(i).getLine());
		}
	}
	
	public boolean isMovePermissible(Move move, PlanetManager planetManager)
	{
		if(planetManager.getPlanetByID(move.fromPlanetId).isNeutral())return false;
		if(!planetManager.getPlanetByID(move.fromPlanetId).isEnemy() && move.isAiMove) return false;
		for(int i = 0 ; i< paths.size() ; i++)
		{
			Path path = paths.get(i);
			if((path.planetA.getId() == move.fromPlanetId && path.planetB.getId() == move.toPlanetId) || 
			   (path.planetB.getId() == move.fromPlanetId && path.planetA.getId() == move.toPlanetId))
			{
				Log.d(TAG,"path.planetA id = "+path.planetA.getId()+" path.planetB id = "+path.planetB.getId());
				if(move.isAiMove && move.fromPlanetType == PlanetType.PLANET_TYPE_ENEMY || !move.isAiMove && move.fromPlanetType == PlanetType.PLANET_TYPE_PLAYER){
					return true;
				}
			}
		}
		
		return false;
	}
}
