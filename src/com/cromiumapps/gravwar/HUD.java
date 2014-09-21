package com.cromiumapps.gravwar;

import java.util.ArrayList;

import org.andengine.entity.primitive.Line;

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
		if(move.from == null || move.to == null || move.from.isNeutral())return false;
		Log.d(TAG,"from id = "+move.from.getId()+" to id = "+move.to.getId());
		for(int i = 0 ; i< paths.size() ; i++)
		{
			Path path = paths.get(i);
			if((path.planetA.getId() == move.from.getId() && path.planetB.getId() == move.to.getId()) || 
			   (path.planetB.getId() == move.from.getId() && path.planetA.getId() == move.to.getId()))
			{
				Log.d(TAG,"path.planetA id = "+path.planetA.getId()+" path.planetB id = "+path.planetB.getId());
				if(move.isAiMove && move.from.isEnemy() || !move.isAiMove && move.from.isPlayerPlanet()){
					return true;
				}
			}
		}
		
		return false;
	}
}
