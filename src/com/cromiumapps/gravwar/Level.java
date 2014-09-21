package com.cromiumapps.gravwar;

import java.util.ArrayList;

public class Level {
	ArrayList<Planet> planets;
	ArrayList<Path> paths;
	
	Level (ArrayList<Planet> planets,ArrayList<Path> paths)
	{
		this.planets = planets;
		this.paths = paths;
	}
	
	ArrayList<Planet> getPlanets()
	{
		return planets;
	}
	
	ArrayList<Path> getPaths()
	{
		return paths;
	}
	
}
