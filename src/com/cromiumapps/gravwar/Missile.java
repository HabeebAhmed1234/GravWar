package com.cromiumapps.gravwar;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.opengl.GLES20;
import android.util.Log;

public class Missile {
	public static final String TAG = "Missile";
	
	private Angle m_currentAngle = new Angle(0);
	private Position m_position = new Position(0,0);
	private Position m_originPosition = new Position(0,0);
	private Position m_destinationPosition = new Position(0,0);
	private GameSprite missileSprite;
	private final Planet fromPlanet;
	private float m_id = 0;
	private float v_x = 3;
	private float v_y = 3;
	
	Missile(float [] vxvy, float id, Planet fromPlanet, Position origin, Position destination, VertexBufferObjectManager vertexBufferObjectManager, GameTextureManager textureManager)
	{ 
		m_id = id;
		this.fromPlanet = fromPlanet;
		this.v_x = vxvy[0];    
		this.v_y = vxvy[1];
		m_position = new Position(origin.getX(),origin.getY());
		m_originPosition = new Position(origin.getX(),origin.getY());
		m_destinationPosition = new Position(destination.getX(),destination.getY());
		missileSprite = new GameSprite(m_position.getX(),m_position.getY(),textureManager.missileTexture,vertexBufferObjectManager,true);
		missileSprite.setUserData(m_id);
		m_currentAngle = Utilities.getVectorAngleFromComponents(v_x, v_y);
		missileSprite.setAngle(m_currentAngle);
		
		Log.d("MissileSystem","added missile sprite of id = "+m_id);
	}
	
	public Planet getSourcePlanet()
	{
		return this.fromPlanet;
	}
	
	public PlanetType sourcePlanetType()
	{
		return fromPlanet.getPlanetType();
	}
	 
	public float getId() 
	{ 
		return this.m_id;
	}
	
	public GameSprite getSprite()
	{
		return missileSprite;
	}
	
	public Position getPosition()
	{
		return m_position;
	} 
	
	public float getRadius()
	{
		return missileSprite.getHeight()/2;
	}
	
	public void update()
	{
		updateVelocity();
		updatePosition();
	}
	
	private void updateVelocity()
	{
		this.m_currentAngle = missileSprite.getAngle();
		float rotationDifference = Utilities.getVectorAngleFromPoints(m_position,m_destinationPosition).subtract(m_currentAngle.get());
		 
		if(rotationDifference > 0) 
		{
			if(Math.abs(rotationDifference)<Math.PI)
			{
				m_currentAngle.add(Constants.MISSILE_ROTATION_SPEED);
			}
			else if(Math.abs(rotationDifference) >Math.PI && Math.abs(rotationDifference)<(2*Math.PI)){
				m_currentAngle.subtract(Constants.MISSILE_ROTATION_SPEED);
			}
		}	
		
		if(rotationDifference < 0) 
		{
			if(Math.abs(rotationDifference)<Math.PI)
			{
				m_currentAngle.subtract(Constants.MISSILE_ROTATION_SPEED);
			}
			else if(Math.abs(rotationDifference) >Math.PI && Math.abs(rotationDifference)<(2*Math.PI)){
				m_currentAngle.add(Constants.MISSILE_ROTATION_SPEED);
			}
		}	
		
		missileSprite.setAngle(m_currentAngle);
		
		float [] vxvy = Utilities.getMissileVectorFromAngle(m_currentAngle);
		v_x = vxvy[0];
		v_y = vxvy[1];
		
		Log.d(TAG,"id = "+this.m_id+" m_currentAngle = " + m_currentAngle.get());
	}
	
	private void updatePosition()  
	{
		m_position.changeX(v_x);
		m_position.changeY(v_y);
		missileSprite.setPosition(m_position.getX(), m_position.getY());
	}
	
	public void explode()
	{
		missileSprite.detachSelf();
	}
	
	public void dock()
	{
		missileSprite.detachSelf();
	}
}