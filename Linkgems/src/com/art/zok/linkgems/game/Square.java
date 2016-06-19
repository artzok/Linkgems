package com.art.zok.linkgems.game;

public class Square {
	
	public enum SquareType {
			Empty,
			Red,
			Orange,
			Yellow,
			Green,
			Bule,
			Purple,
			White
	};
	
	private Coord _coord;
	private SquareType _type;
	
	public Square(int x, int y) {
		_coord = new Coord(x, y);
		_type = SquareType.Empty;
	}
	
	public Square(Coord pos) {
		this(pos.getX(), pos.getY());
	}

	public Coord getCoord() {
		return _coord;
	}
	
	public void setType(SquareType type) {
		this._type = type;
	}
	
	public SquareType getType() {
		return _type;
	}
	
	public boolean isEmpty() {
		return _type == SquareType.Empty;
	}
	public void setEmpty() {
		_type = SquareType.Empty;
	}
}
