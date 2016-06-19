package com.art.zok.linkgems.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.art.zok.linkgems.game.Square.SquareType;

public class Board {
	private Random _random;
	private Square[][] _squares = new Square[8][8];

	public Board() {
		_random = new Random();
	}
	
	public void generateBoard() {
		List<Coord> listPos = new ArrayList<>(8 * 8);
		
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				listPos.add(new Coord(x, y));
				_squares[x][y] = new Square(x, y);
 			} 
		}
		
		while(!listPos.isEmpty()) {
			int size = listPos.size();
			Coord r1 = listPos.remove(_random.nextInt(size));
			size = listPos.size();
			Coord r2 = listPos.remove(_random.nextInt(size));
			int index = _random.nextInt(7) + 1;
			_squares[r1.x][r1.y].setType(SquareType.values()[index]);
			_squares[r2.x][r2.y].setType(SquareType.values()[index]);
		}
	}
	
	public Square getSquare(int x, int y) {
		return _squares[x][y];
	}
	
	public List<Coord> matches(Coord src, Coord dest) {
		if((src.x == dest.x && src.y == dest.y) ||
			_squares[src.x][src.y].getType() != _squares[dest.x][dest.y].getType())
			return null;
		return matchTwoBlock(src, dest);
	}
	
	
	/*1折连接*/
	private boolean matchBlock(Coord src, Coord dest) {
		if(src.x != dest.x && src.y != dest.y) 
			return false;
		
		int min, max;
		
		/*		Vertical	*/
		if(src.x == dest.x) {
			min = Math.min(src.y, dest.y);
			max = Math.max(src.y, dest.y);
			for(int t = min + 1; t < max; t++) {
				if(!_squares[src.x][t].isEmpty()) 
					return false;
			}
		}
		/*		Horizontal	*/ 
		else {
			min = Math.min(src.x, dest.x);
			max = Math.max(src.x, dest.x);
			for(int t = min + 1; t < max; t++) {
				if(!_squares[t][src.y].isEmpty()) 
					return false;
			}
		}
		return true;
	}
	
	
	/*1折连接*/
	private Coord matchOneBlock(Coord src, Coord dest) {
		// if direct link 
		if(matchBlock(src, dest))
			return new Coord(-1, -1);
		
		Coord csd = new Coord(src.x, dest.y);
		Coord cds = new Coord(dest.x, src.y);
		
		if(_squares[csd.x][csd.y].isEmpty() && matchBlock(src, csd) && matchBlock(csd, dest))
			return csd;
		
		if(_squares[cds.x][cds.y].isEmpty() && matchBlock(src, cds) && matchBlock(cds, dest))
			return cds;
		
		return null;
	}
	
	
	/*1折连接*/
	private List<Coord> matchTwoBlock(Coord src, Coord dest) {
		
		Coord temp, find;
		LinkedList<Coord> list = new LinkedList<>();
		
		if((temp = matchOneBlock(src, dest)) != null) {
			if(temp.x == -1 && temp.y == -1)
				return list;			// direct link
			else {
				list.add(temp);			// one block link
				return list;
			} 
		}
		
		/* left to right*/
		temp = new Coord(0, 0);
		for(int x = src.x + 1; x < _squares[0].length; x++) {
			temp.setCoord(x, src.y);
			if(!_squares[temp.x][temp.y].isEmpty()) break;
			if((find = matchOneBlock(temp, dest)) != null) {
				list.add(temp);
				list.add(find);
				return list;
			}
		}
		/*	right to left	*/
		if(src.x != 0)
		for(int x = src.x - 1; x >= 0; x--) {
			temp.setCoord(x, src.y);
			if(!_squares[temp.x][temp.y].isEmpty()) break;
			if((find = matchOneBlock(temp, dest)) != null) {
				list.add(temp);
				list.add(find);
				return list;
			}
		}
		/*	top to bottom	*/
		for(int y = src.y + 1; y < _squares.length; y++) {
			temp.setCoord(src.x, y);
			if(!_squares[temp.x][temp.y].isEmpty()) break;
			if((find = matchOneBlock(temp, dest)) != null) {
				list.add(temp);
				list.add(find);
				return list;
			}
		}
		
		/*	bottom to top	*/
		if(src.y != 0)
		for(int y = src.y - 1; y >= 0; y--) {
			temp.setCoord(src.x, y);
			if(!_squares[temp.x][temp.y].isEmpty()) break;
			if((find = matchOneBlock(temp, dest)) != null) {
				list.add(temp);
				list.add(find);
				return list;
			}
		}
		
		return null;
	}
}















