package com.cromiumapps.gravwar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;

public class GameAi {
	private GameManager gameManager;
	
	GameAi(GameManager gameManager)
	{
		this.gameManager = gameManager;	
		//computeMoves();
	}
	
	public void update(float secondsElapsed){
		//if(gameManager.getGameClock() % Constants.GAME_AI_MOVE_COMPUTE_UPDATE_INTERVAL == 0)computeMoves();
		if(gameManager.getGameClock() % Constants.GAME_AI_MAKE_MOVE_INTERVAL == 0)executeOneMove();
	}
	
	private void executeOneMove()
	{

		//Move move  = movesList.get(0);
		//gameManager.makeAMove(move);
		//if(movesList.size()>0)movesList.remove(0);
		Move move = getMove();
		if (move != null)
			gameManager.makeAMove(move);
	}
	
	private Move getMove(){
		ArrayList<Move> movesList = new ArrayList<Move>();
		ArrayList <Planet> allPlanets = gameManager.planetManager.getAllPlanets();
		
		for(int i = 0 ; i < allPlanets.size() ; i++)
		{
			for(int x = 0 ; x < allPlanets.size() ; x++)
			{
				try{
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
				}catch (InvalidMoveException e){
					e.printWhat();
				}
			}	
		}
		
		return getBestMove(movesList);
	}
	
	private Move getBestMove(ArrayList<Move> moves){
		Move [] movesArray = moves.toArray(new Move[moves.size()]);
		Arrays.sort (movesArray, new Comparator<Move>() {
            @Override
            public int compare(Move a, Move b) {
            	float ay = gameManager.planetManager.getPlanetByID(a.toPlanetId).getPosition().getY();
            	float by = gameManager.planetManager.getPlanetByID(b.toPlanetId).getPosition().getY();
            	if(ay > by){
            		return 1;
            	}else if (by == ay){
            		return 0;
            	}else if (ay < by){
            		return -1;
            	}
            	return 0;
            }
        });
		 
		if(movesArray.length > 0){
			return movesArray[0];
		}else{
			return null;
		}
	}
	
	/*private void computeMoves()
	{
		ArrayList <Planet> allPlanets = gameManager.planetManager.getAllPlanets();
		
		for(int i = 0 ; i < allPlanets.size() ; i++)
		{
			for(int x = 0 ; x < allPlanets.size() ; x++)
			{
				try{
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
				}catch (InvalidMoveException e){
					e.printWhat();
				}
			}	
		}
	}*/
	
	private float howManyMissilesShouldIFire(Planet planet)
	{
		float missilesToFire = Math.round((Constants.PERCENTAGE_OF_MISSILES_TO_BE_FIRED_BY_AI * planet.getHealthInMissiles()));
		if(missilesToFire == planet.getHealthInMissiles()) missilesToFire--;
		return missilesToFire;
	}
}