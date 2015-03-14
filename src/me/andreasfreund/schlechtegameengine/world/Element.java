package me.andreasfreund.schlechtegameengine.world;

import org.lwjgl.opengl.GL11;

import me.andreasfreund.schlechtegameengine.SchlechteGameEngine;
import me.andreasfreund.schlechtegameengine.display.Sprite;

public abstract class Element {
	private int x = 0, y = 0, layer = LAYER_DEFAULT, rotation = 0;
	private int displayX, displayY;
	private boolean animation;
	private float animationframe;
	private float animationspeed;

	public static final int LAYER_BACKGROUND = 5;
	public static final int LAYER_DEFAULT = 3;
	public static final int LAYER_FOREGROUND = 2;
	public static final int LAYER_OVERLAY = 0;
	
	private Sprite[] sprites;
	private World world;
	
	boolean inWorld;

	protected Element(Sprite[] sprites, World world) {
		this.setSprites(sprites);
		this.world = world;
	}

	protected void setSprites(Sprite[] sprites) {
		if (sprites.length != 4) {
			throw new IllegalArgumentException("No Valid Sprites Supplied");
		}
		for (Sprite sprite : sprites) {
			if (sprite == null) {
				throw new IllegalArgumentException("No Valid Sprite Supplied");
			}
		}
		this.sprites = sprites;
	}

	public void draw() {
		if(this.animation)
		{
			this.animationframe = this.animationframe % this.sprites[this.rotation].getFrameCount();
			this.sprites[this.rotation].bindFrame((int)(this.animationframe));
			this.animationframe += this.animationspeed;
		}
		else
		{
			this.sprites[this.rotation].bindFrame(0);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(this.displayX, this.displayY, this.getLayer() / 10f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(0.5f, 0.5f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public void setAnimationEnabled(boolean anim) {
		this.animation = anim;
	}
	
	public void setAnimationSpeed(float animationspeed)
	{
		this.animationspeed = animationspeed;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = (rotation % 4) + 4;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(inWorld)
			world.getCollisionmap().setOccupied(this.x, this.y, false);
		this.y = y;
		if(inWorld)
			world.getCollisionmap().setOccupied(this.x, this.y, true);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(inWorld)
			world.getCollisionmap().setOccupied(this.x, this.y, false);
		this.x = x;
		if(inWorld)
			world.getCollisionmap().setOccupied(this.x, this.y, true);
	}
	
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String toString(){
		return this.getClass().getName();
	}
	
	public void tick(SchlechteGameEngine engine){
		
	}
	
	public void updatePosition(){
		this.displayX = this.x;
		this.displayY = this.y;
	}

	public int getDisplayX() {
		return this.displayX;
	}

	public int getDisplayY() {
		return this.displayY;
	}
	
	public boolean getCollidable(){
		return false;
	}
}
