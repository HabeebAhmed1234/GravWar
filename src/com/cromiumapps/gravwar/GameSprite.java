package com.cromiumapps.gravwar;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

public class GameSprite extends AnimatedSprite  {
	//private boolean isCoordinateCenterInCenter = false;
	
	public GameSprite(float x, float y, ITiledTextureRegion  texture,VertexBufferObjectManager vertexBufferObjectManager,boolean isCoordinateCenterInCenter) {
		super(x, y, texture,vertexBufferObjectManager);
	}
	
	public void setAngle(Angle a)
	{
		setRotation(a.get());
	}
	
	public Angle getAngle()
	{
		return new Angle(this.getRotation());
	}
	
	@Override
	public void setRotation(float radians)
	{
		while(radians>(Math.PI*2)){radians-=(Math.PI*2);}
		while(radians<(0)){radians+=(Math.PI*2);}
		this.mRotation = MathUtils.radToDeg((float) ((radians-Math.PI/2)*-1));
	}
	
	@Override 
	public float getRotation()
	{
		float radians = (float) ((MathUtils.degToRad((float)this.mRotation)*-1)+Math.PI/2);
		while(radians>(Math.PI*2)){radians-=(Math.PI*2);}
		while(radians<0){radians+=(Math.PI*2);}
		return radians;
	} 
}
