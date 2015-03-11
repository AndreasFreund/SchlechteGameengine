package me.andreasfreund.schlechtegameengine.test;

import me.andreasfreund.schlechtegameengine.Generator;
import me.andreasfreund.schlechtegameengine.SchlechteGameEngine;
import me.andreasfreund.schlechtegameengine.world.*;
import me.andreasfreund.schlechtesgame.*;

public class TestWorld implements Generator {
	private SchlechteGameEngine engine;
	
	public static void main(String[] args) {
		TestWorld testworld = new TestWorld();
		testworld.engine = new SchlechteGameEngine(testworld,
				"Fenstertitel");
		testworld.engine.getWindow().getContext();
		testworld.engine.getWindow().setScale(32);
		testworld.engine.start();
	}

	@Override
	public void generateWorld(World world, SchlechteGameEngine engine) {
		for (int x = -3; x < 4; x++) {
			for (int y = -4; y < 4; y++) {
				Wall wall = new Wall();
				wall.setX(x);
				wall.setY(y);
				wall.setLayer(Element.LAYER_BACKGROUND);
				world.addElement(wall);
			}
		}
		for (int x = -9; x < 10; x++) {
			Grass wall = new Grass();
			wall.setX(x);
			wall.setY(-5);
			wall.setLayer(Element.LAYER_BACKGROUND);
			world.addElement(wall);
		}
		Player p = new Player();
		engine.getCamera().setCameraLockedTo(p);
		world.addElement(p);
		world.addElement(new Ghost());
	}
}
