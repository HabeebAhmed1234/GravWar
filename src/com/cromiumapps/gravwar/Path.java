package com.cromiumapps.gravwar;

import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Path {
	private Line pathLine;
	public Planet planetA;
	public Planet planetB;
	
	Path (Planet planeta,Planet planetb, VertexBufferObjectManager vertexBufferObjectManager)
	{
		pathLine = new Line (planeta.getPosition().getX() ,
				planeta.getPosition().getY() ,
				planetb.getPosition().getX() ,
				planetb.getPosition().getY() ,
				vertexBufferObjectManager);

		pathLine.setColor(128,128,128);
		
		this.planetA = planeta;
		this.planetB = planetb;
	}
	
	public Line getLine ()
	{
		return this.pathLine;
	}
	
}
