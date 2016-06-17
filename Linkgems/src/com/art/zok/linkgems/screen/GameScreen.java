package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.art.zok.linkgems.util.Constants;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The <code>GameScreen</code> class implements all of game logic of this game.
 * 
 * @author artzok 
 * @version 1.0 
 **/
public class GameScreen extends AbstractScreen {

    private TextureRegion _board;
    
    public GameScreen(Linkgems linkgems) {
        super(linkgems);
    }

    @Override
    public void show() {
        _assetManager.load("images/board.png", Texture.class);
    }

    @Override
    public void hide() {
        _assetManager.unload("images/board.png");
    }

    @Override
    public void assignAssets() {
        Texture texture = _assetManager.get("images/board.png", Texture.class);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _board = new TextureRegion(texture);
        _board.flip(false, true);
    }
    
    @Override
    public void update(float delta) {
        
    }

    @Override
    public void render() {
        _batch.draw(_board, 0, 0);
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
}













