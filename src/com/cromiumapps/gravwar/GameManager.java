package com.cromiumapps.gravwar;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import android.view.MotionEvent;

public class GameManager {
	interface GameOutcomeListener
	{
		public void onGameOutComeListener(int gameOutCom, float timeElapsed);
	}
	
	private VertexBufferObjectManager vertexBufferObjectManager;
	
	//andengine objects
	private GameCamera gameCamera;
	private GameTextureManager gameTextTextureManager;
	private GameAi gameAi;
	private CollisionManager collisionManager;
	private GameOutcomeListener gameOutcomeListener;
	private HUD hud;
	
	private float gameClock = 0;
	//managers
	public PlanetManager planetManager;
	public MissileSwarmManager missileSwarmManager;
	public GameScene gameScene;
	
	public float numMissilesReadyToFire = 0;
	
	GameManager(GameOutcomeListener gameOutcomeListener, VertexBufferObjectManager vertexBufferObjectManager, GameScene gameScene, GameCamera gameCamera, GameTextureManager gameTextTextureManager)
	{
		this.gameOutcomeListener = gameOutcomeListener;
		this.vertexBufferObjectManager =  vertexBufferObjectManager;
		this.gameScene = gameScene;
		this.gameCamera = gameCamera;
		this.gameTextTextureManager = gameTextTextureManager;
		this.missileSwarmManager = new MissileSwarmManager(gameScene, vertexBufferObjectManager, gameTextTextureManager);
		this.collisionManager = new CollisionManager(this);
		
		generateLevel();
		
		//buildWalls();
		
		createGameLoop();
		this.gameAi = new GameAi(this);
	}
	
	public GameScene getGameScene()
	{
		return this.gameScene;
	}
	
	private void createGameLoop()
	{
		gameScene.registerUpdateHandler(new IUpdateHandler() {                    
            public void reset() {        
            }             
            public void onUpdate(float pSecondsElapsed) {
                //HERE IS THE GAME LOOP
            	gameClock+=1;
            	updateGame(pSecondsElapsed);
            }
        });
	}
	
	public void updateGame(float SecondsElapsed)
	{
		if(gameClock % Constants.PLANET_REGENERATION_INTERVAL == 0) 	planetManager.generatePlanetHealths();
		if(gameClock % Constants.COLLISION_CHECK_INTERVAL == 0) 		collisionManager.update();
		if(gameClock % Constants.MISSILE_SWARM_UPDATE_INTERVAL == 0) 	missileSwarmManager.update();
		if(gameClock % Constants.PLANET_UPDATE_INTERVAL == 0) planetManager.update();
		if(gameClock % Constants.GAME_AI_UPDATE_INTERVAL == 0) gameAi.update(SecondsElapsed);
		if(gameClock % Constants.GAME_OUTCOME_CHECK_INTERVAL == 0) checkGameOutCome();
	}
	
	private void buildWalls()
	{
		final Rectangle ground = new Rectangle(0, gameCamera.getHeight() - 2, gameCamera.getWidth(), 2, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, gameCamera.getWidth(), 2, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 2, gameCamera.getHeight(), vertexBufferObjectManager);
		final Rectangle right = new Rectangle(gameCamera.getWidth() - 2, 0, 2, gameCamera.getHeight(), vertexBufferObjectManager);
		this.gameScene.attachChild(ground);
		this.gameScene.attachChild(roof);
		this.gameScene.attachChild(left);
		this.gameScene.attachChild(right);
	}
	
	public void generateLevel()
	{
		LevelGenerator levelGenerator = new LevelGenerator(gameCamera.getWidth(),gameCamera.getHeight(), this.vertexBufferObjectManager, this.gameScene, this, gameTextTextureManager);
		Level level = levelGenerator.generateLevel();
		this.hud = new HUD(level.getPaths(),gameScene);
		planetManager = new PlanetManager(level.getPlanets(),vertexBufferObjectManager, this.gameScene, this, this.gameTextTextureManager);
	}
	
	public void resetGame()
	{
		
	}
	
	public float getGameClock(){
		return this.gameClock;
	}
	
	public void checkGameOutCome()
	{
		if(planetManager.isAllEnemies())
		{
			gameOutcomeListener.onGameOutComeListener(Constants.GAME_LOST, gameClock);
		}
		
		if(planetManager.isAllPlayers())
		{
			gameOutcomeListener.onGameOutComeListener(Constants.GAME_WON, gameClock);
		} 
	}
	
	public void onTouchAnywhere(TouchEvent event)
	{
		Log.d("GravWar", "GameManager: touch event occured");
		planetManager.deSelectAllPlanets();
	}
	
	public void onTouchPlanet(TouchEvent event, float touchedPlanetID)
	{
		Log.d("GravWar", "GameManager: touch event on a planet of id +" + touchedPlanetID);
		Planet prevSelectedPlanet = planetManager.getSelectedPlanet();
		Planet touchedPlanet = planetManager.getPlanetByID(touchedPlanetID);
		makeAMove(new Move(numMissilesReadyToFire,prevSelectedPlanet,touchedPlanet,false));
		planetManager.selectPlanetByID(touchedPlanetID);
	}
	
	public void makeAMove(Move move)
	{
		if(move.missilesToFireAmmount>0)
		{
			
			if(this.hud.isMovePermissible(move,planetManager) && move.from!=null && !(move.from.getId()==move.to.getId()))
			{
				Log.d("GravWar", "GameManager: firing missile swarm from planet id " + move.from.getId()+" to planet id"+move.to.getId());
				this.missileSwarmManager.addMissileSwarm(move.from,move.from.getDiameter()/2,move.from.getPosition(), move.to.getPosition(), move.missilesToFireAmmount);
				move.from.damageHealth(move.missilesToFireAmmount);
			}
		}
	}
}
