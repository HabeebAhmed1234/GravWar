package com.cromiumapps.gravwar;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;

public class GameAi {
	private GameManager gameManager;
	ArrayList<Move> movesList = new ArrayList<Move>();
	
	
	GameAi(GameManager gameManager)
	{
		this.gameManager = gameManager;	
		computeMoves();
	}
	
	public void update(float secondsElapsed){
		if(gameManager.getGameClock() % Constants.GAME_AI_MOVE_COMPUTE_UPDATE_INTERVAL == 0)computeMoves();
		if(gameManager.getGameClock() % Constants.GAME_AI_MAKE_MOVE_INTERVAL == 0)executeOneMove();
	}
	
	private void executeOneMove()
	{
		Move move  = movesList.get(0);
		gameManager.makeAMove(move);
		if(movesList.size()>0)movesList.remove(0);
	}
	
	private void computeMoves()
	{
		ArrayList <Planet> allPlanets = gameManager.planetManager.getAllPlanets();
		
		for(int i = 0 ; i < allPlanets.size() ; i++)
		{
			for(int x = 0 ; x < allPlanets.size() ; x++)
			{
				Planet planetA = allPlanets.get(i);
				Planet planetB = allPlanets.get(x);
				if(planetA.getId() != planetB.getId())
				{
					if(planetA.isEnemy() && planetB.isPlayerPlanet())
					{
						movesList.add(new Move(howManyMissilesShouldIFire(planetA), planetA, planetB,true));
					}
					
					if(planetB.isEnemy() && planetA.isPlayerPlanet())
					{
						movesList.add(new Move(howManyMissilesShouldIFire(planetB), planetB, planetA,true));
					}
					
					if(planetA.isEnemy() && planetB.isNeutral())
					{
						movesList.add(new Move(howManyMissilesShouldIFire(planetA), planetA, planetB,true));
					}
					
					if(planetA.isEnemy() && planetB.isEnemy())
					{
						if(planetA.getPosition().getY()>planetB.getPosition().getY()){
							movesList.add(new Move(howManyMissilesShouldIFire(planetA), planetA, planetB,true)); 
						}else{
							movesList.add(new Move(howManyMissilesShouldIFire(planetB), planetB, planetA,true)); 
						}
					}
				}
			}	
		}
	}
	
	private float howManyMissilesShouldIFire(Planet planet)
	{
		return Math.round((Constants.PERCENTAGE_OF_MISSILES_TO_BE_FIRED_BY_AI * planet.getHealthInMissiles()));
	}
}