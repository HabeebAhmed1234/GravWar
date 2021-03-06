package com.cromiumapps.gravwar;

public class Constants {
	//game outcome constants
	public static enum GAME_OUTCOME {WIN, LOSE};
	public static final String GAME_OUTCOME_EXTRA_KEY = "gameoutcomeextrakey";
	public static final String GAME_TIME_ELAPSED_EXTRA_KEY = "gametimeelapsedextrakey";
	
	//update intervals
	public static final float PLANET_REGENERATION_INTERVAL = 100;
	public static final float COLLISION_CHECK_INTERVAL = 5;
	public static final float MISSILE_SWARM_UPDATE_INTERVAL = 1;
	public static final float PLANET_UPDATE_INTERVAL = 1;
	public static final float GAME_OUTCOME_CHECK_INTERVAL = 100;
	
	public static final float GAME_AI_UPDATE_INTERVAL = 1;
	public static final float GAME_AI_MAKE_MOVE_INTERVAL_HARD =20; 
	public static final float GAME_AI_MOVE_COMPUTE_UPDATE_INTERVAL_MEDIUM = 20;
	public static final float GAME_AI_MAKE_MOVE_INTERVAL_MEDIUM = 40;
	public static final float GAME_AI_MOVE_COMPUTE_UPDATE_INTERVAL_EASY = 40; 
	public static final float GAME_AI_MAKE_MOVE_INTERVAL_EASY =80;
	
	//planet constants
	public static final float PLANET_HEALTH_IN_MISSILES_TO_DIAMETER_RATIO = 5;
	public static final float PLANET_MISSILES_PER_SELECTION = 2;
	public static final float PLANET_HEALTH_PER_MISSILE = 1;
	public static final float PLANET_MISSILE_REGENERATION_AMMOUNT = 1;
	public static final float PLANET_MAX_HEALTH = 15;
	public static final float MINIMUM_PLANET_DIAMETER_IN_MISSILES = 7;
	
	//missile constants
	public static final float MISSILE_PLANET_TO_MISSILE_GAP = 40;
	public static final float MISSILE_STARTING_MISSILE_SPEED = 1;
	public static final float MISSILE_SPREAD_INDEX = 0.3f;
	
	//animation constants
	public static final float PLANET_SELECTOR_ROTATION_SPEED = 0.05f;
	public static final float MISSILE_ROTATION_SPEED = 0.08f; 
	
	//Ai constants
	public static final float PERCENTAGE_OF_MISSILES_TO_BE_FIRED_BY_AI = 0.75f;
	
}
