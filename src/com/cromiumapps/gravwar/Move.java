package com.cromiumapps.gravwar;

import android.util.Log;

import com.cromiumapps.gravwar.Planet.PlanetType;

public class Move{	
	public float missilesToFireAmmount;
	public boolean isAiMove;
	public PlanetType fromPlanetType;
	public PlanetType toPlanetType;
	public float fromPlanetId;
	public float toPlanetId;
	
	Move(float missilesToFireAmmount, Planet from, Planet to, boolean isAiMove) throws InvalidMoveException
	{
		if(from.getId() == to.getId()) throw new InvalidMoveException("fromid and toid are identical");
		if(missilesToFireAmmount <= 0) throw new InvalidMoveException("missiles to fire are <= 0");
		fromPlanetType = from.getPlanetType();
		toPlanetType = to.getPlanetType();
		this.missilesToFireAmmount = missilesToFireAmmount;
		this.isAiMove = isAiMove;
		this.fromPlanetId = from.getId();
		this.toPlanetId = to.getId();
	}
	
}

class InvalidMoveException extends Exception{
	private String what;
	public static String TAG = "InvalidMoveException";
	
	InvalidMoveException(String what){
		this.what = what;
	}
	
	public void printWhat(){
		Log.d(TAG,what);
	}
	
}

