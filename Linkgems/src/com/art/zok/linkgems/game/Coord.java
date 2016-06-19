package com.art.zok.linkgems.game;

public class Coord {
	public  int x;
	public  int y;
	
	public Coord() {
	}
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Coord copy() {
		return new Coord(x, y);
	}

	public void setCoord(float f, float g) {
		this.x = (int)f;
		this.y = (int)g;
	}
}
