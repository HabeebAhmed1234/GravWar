package com.cromiumapps.gravwar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GameAi {
	private GameManager gameManager;
	
	GameAi(GameManager gameManager)
	{
		this.gameManager = gameManager;	
	}
	
	public void update(float secondsElapsed){
		if(gameManager.getGameClock() % Constants.GAME_AI_MAKE_MOVE_INTERVAL == 0)executeOneMove();
	}
	
	private void executeOneMove()
	{
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
					if(planetA.getId() != planetB.getId() && gameManager.getHud().arePlanetsConnected(planetA, planetB))
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
            	Planet aToPlanet = gameManager.planetManager.getPlanetByID(a.toPlanetId);
            	Planet bToPlanet = gameManager.planetManager.getPlanetByID(b.toPlanetId);
            	Planet aFromPlanet = gameManager.planetManager.getPlanetByID(a.fromPlanetId);
            	Planet bFromPlanet = gameManager.planetManager.getPlanetByID(b.fromPlanetId);
            	
            	//the bigger a factor the better the move
            	//the weights should add up to 100
            	float aPositionFactor = getPositionFactor(aToPlanet, 50);
            	float aHealthFactor = getHealthFactor(aFromPlanet, aToPlanet,50);

            	float bPositionFactor = getPositionFactor(bToPlanet, 50);
            	float bHealthFactor = getHealthFactor(bFromPlanet, bToPlanet,50);
            	
            	float aFactor = aPositionFactor + aHealthFactor;
            	float bFactor = bPositionFactor + bHealthFactor;
            	
            	if(aFactor > bFactor){
            		return 1;
            	}else if (aFactor == bFactor){
            		return 0;
            	}else if (aFactor < bFactor){
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
	
	//minimum factor value is 0 and max factor value is weight
	private float getPositionFactor(Planet moveToPlanet, float weight){
		return moveToPlanet.getPosition().getY()/ gameManager.getGameCamera().getHeight() * weight;
	}
	
	private float getHealthFactor(Planet moveFromPlanet, Planet moveToPlanet, float weight){
		float diff = moveFromPlanet.getHealthInMissiles() - moveToPlanet.getHealthInMissiles();
		if(diff<0) return 0;
		return diff/Constants.PLANET_MAX_HEALTH * weight;
	}
	
	private float howManyMissilesShouldIFire(Planet planet)
	{
		float missilesToFire = Math.round((Constants.PERCENTAGE_OF_MISSILES_TO_BE_FIRED_BY_AI * planet.getHealthInMissiles()));
		if(missilesToFire == planet.getHealthInMissiles()) missilesToFire--;
		return missilesToFire;
	}
}