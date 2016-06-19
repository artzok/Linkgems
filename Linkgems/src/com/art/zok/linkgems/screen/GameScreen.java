package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.art.zok.linkgems.game.Board;
import com.art.zok.linkgems.game.Coord;
import com.art.zok.linkgems.game.Square.SquareType;
import com.art.zok.linkgems.util.Constants;
import com.art.zok.linkgems.util.Tools;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * The <code>GameScreen</code> class implements all of game logic of this game.
 * 
 * @author artzok 
 * @version 1.0 
 **/
public class GameScreen extends AbstractScreen {

    private TextureRegion _boardBackground;
    private TextureRegion[] _gemRegions;
    private TextureRegion _selector;
    
    private Board _board;
    
    // selected square position
    private Coord _selected;
    
    public GameScreen(Linkgems linkgems) {
        super(linkgems);
        _board = new Board();
        _board.generateBoard();
    }

    @Override
    public void show() {
    	// load board
        _assetManager.load("images/board.png", Texture.class);
        // load gems
        for(int i = 0; i < Constants.GEMSPATH.length; i++) {
        	_assetManager.load(Constants.GEMSPATH[i], Texture.class);
        }
        // load selector
        _assetManager.load("images/selector.png", Texture.class);
    }

    @Override
    public void hide() {
    	// unload board
        _assetManager.unload("images/board.png");
        // unload gems
        for(int i = 0; i < Constants.GEMSPATH.length; i++) {
        	_assetManager.unload(Constants.GEMSPATH[i]);
        }
        // unload selector
        _assetManager.unload("images/selector.png");
    }

    @Override
    public void assignAssets() {
    	// board
        Texture texture = _assetManager.get("images/board.png", Texture.class);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _boardBackground = new TextureRegion(texture);
        _boardBackground.flip(false, true);
        
        _gemRegions = new TextureRegion[ Constants.GEMSPATH.length];
        
        // gem regions
        for(int i = 0; i < Constants.GEMSPATH.length; i++) {
     	   texture = _assetManager.get(Constants.GEMSPATH[i]);
            _gemRegions[i] = Tools.textureToRegion(texture, true);
        }
        
        // selector
        texture = _assetManager.get("images/selector.png");
        _selector = new TextureRegion(texture);
        _selector.flip(false, true);
    }
    
    @Override
    public void update(float delta) {
        
    }

    @Override
    public void render() {
    	
    	// render board background
        _batch.draw(_boardBackground, 0, 0);
        
        // render gems squares
        for(int x = 0; x < 8; x++) {
        	for(int y = 0; y < 8; y++) {
        		SquareType type = _board.getSquare(x, y).getType();
        		if(type == SquareType.Empty) continue;
        		int index = type.ordinal() - 1;
        		_batch.draw(_gemRegions[index], Constants.BOARD_LEFT_UP_CORNER.getX() + x * Constants.GEMS_SIZE, 
        				Constants.BOARD_LEFT_UP_CORNER.getY() + y * Constants.GEMS_SIZE);
        	}
        }
        
        // render selected square's selector
        _batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        if(_selected != null) {
        	_batch.draw(_selector, Constants.BOARD_LEFT_UP_CORNER.getX() + _selected.getX() * Constants.GEMS_SIZE, 
    				Constants.BOARD_LEFT_UP_CORNER.getY() + _selected.getY() * Constants.GEMS_SIZE);
        } 
        
        // reset tin color
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void resume() {
    	super.resume();
    	_selected = null;
    }
    
    @Override
	public void dispose() {
	}
    
    @Override
    public boolean keyDown(int keycode) {
    	if(keycode == Input.Keys.ESCAPE) {
    		_parent.changeScreen(Constants.MENU_SCREEN);
    	}
    	return true;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	Vector3 mousePos = _parent.getMousePos();
    	if(mousePos.x < Constants.BOARD_LEFT_UP_CORNER.getX() || 
    		mousePos.y < Constants.BOARD_LEFT_UP_CORNER.getY() || 
    		mousePos.x > Constants.GEMS_SIZE * 8 + Constants.BOARD_LEFT_UP_CORNER.getX() ||
    		mousePos.y > Constants.GEMS_SIZE * 8 + Constants.BOARD_LEFT_UP_CORNER.getY()) {
    		return false;
    	}
    	
    	Coord hitSquare = new Coord();
    	float x = mousePos.x - Constants.BOARD_LEFT_UP_CORNER.getX();
    	float y	= mousePos.y - Constants.BOARD_LEFT_UP_CORNER.getY();
    	hitSquare.setCoord(x / Constants.GEMS_SIZE, y / Constants.GEMS_SIZE);
    	
    	if(_selected == null) {
    		_selected = hitSquare;
    	} else {
    		Coord target = hitSquare;
    		if(_board.matches(_selected, target) != null) {
    			_board.getSquare(_selected.x, _selected.y).setEmpty();
    			_board.getSquare(target.x, target.y).setEmpty();
    		} 
			_selected = null;
    	}
    	
    	return true;
    }
}













