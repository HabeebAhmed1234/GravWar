package com.cromiumapps.gravwar;

import com.cromiumapps.gravwar.Planet.PlanetType;

public class Move{	
	public float missilesToFireAmmount;
	public boolean isAiMove;
	public PlanetType fromPlanetType;
	public PlanetType toPlanetType;
	
	Move(float missilesToFireAmmount, Planet from, Planet to, boolean isAiMove)
	{
		fromPlanetType = from.getPlanetType();
		toPlanetType = to.getPlanetType();
		this.missilesToFireAmmount = missilesToFireAmmount;
		this.isAiMove = isAiMove;
	}
	
}

