package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.art.zok.linkgems.util.Constants;
import com.art.zok.linkgems.util.Tools;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class MenuScreen extends AbstractScreen {

    private TextureRegion _background;
    private TextureRegion _mainLogo;
    private TextureRegion[] _regionGems;
    private TextBounds _startTxtBounds;
    private TextBounds _tutorialTxtBounds;
    private TextBounds _exitTxtBounds;
    private BitmapFont _menuFont;
    float _animTime;
    float _totalTime;
    public MenuScreen(Linkgems linkgems) {
        super(linkgems);
        _animTime = 0.0f;
        _totalTime = 0.8f;
        _regionGems = new TextureRegion[7];
    }

    @Override
    public void show() {
        _assetManager.load("images/mainMenuBackground.png", Texture.class);
        _assetManager.load("images/mainMenuLogo.png", Texture.class);
        _assetManager.load("fonts/menuFont.fnt", BitmapFont.class);
        for(int i = 0; i < Constants.gemsPath.length; i++) {
        	_assetManager.load(Constants.gemsPath[i], Texture.class);
        }
    }

    @Override
    public void hide() {
        _assetManager.unload("images/mainMenuBackground.png");
        _assetManager.unload("images/mainMenuLogo.png");
        _assetManager.unload("fonts/menuFont.fnt");
        for(int i = 0; i < Constants.gemsPath.length; i++) {
        	_assetManager.unload(Constants.gemsPath[i]);
        }
    }
    
    @Override
    public void assignAssets() {
    	
    	// ±³¾°
       Texture texture = _assetManager.get("images/mainMenuBackground.png");
       _background = Tools.textureToRegion(texture, true);

       // logo
       texture = _assetManager.get("images/mainMenuLogo.png");
       _mainLogo = Tools.textureToRegion(texture, true);
       
       // gems
       for(int i = 0; i < Constants.gemsPath.length; i++) {
    	   texture = _assetManager.get(Constants.gemsPath[i]);
           _regionGems[i] = Tools.textureToRegion(texture, true);
       }
       
       // menu font
       _menuFont = _assetManager.get("fonts/menuFont.fnt", BitmapFont.class);
       
       // text bounds
       _startTxtBounds = _menuFont.getBounds(Constants.MENU_START, new TextBounds());
       _tutorialTxtBounds = _menuFont.getBounds(Constants.MENU_TUTORIAL, new TextBounds());
       _exitTxtBounds = _menuFont.getBounds(Constants.MENU_EXIT, new TextBounds());
    }
    
    @Override
    public void update(float delta) {
    	if(_animTime < _totalTime * 2)
    		_animTime += delta;
    }
    
    @Override
    public void render() {
    	
    	// render background
        _batch.draw(_background, 0, 0);
        
        float percent = _animTime / _totalTime;
        
        // render alpha animation for main logo
        if(percent > 1.0f) percent = 1.0f;
        _batch.setColor(1.0f, 1.0f, 1.0f, percent);
        _batch.draw(_mainLogo, 0, 0);
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        // render gems 
        for(int i = 0; i < 7; i++) {
        	float componentTime = _animTime - i * _totalTime / 7.0f;
        	if(componentTime < 0) 
        		continue;
        	else {
        		if(componentTime > _totalTime) {
        			_batch.draw(_regionGems[i], (Constants.VIEWPORT_WIDTH - 78 * 7) / 2 + i * 78, 278);
        		} else {
        			percent = componentTime / _totalTime;
        			_batch.draw(_regionGems[i], (Constants.VIEWPORT_WIDTH - 78 * 7) / 2 + i * 78, 
        					-600 * ((percent - 1) * (percent - 1) * (percent - 1) + 1) + 600 + 278);
        		}
        	}
        }
        
        // render menu
        
    }

    @Override
    public void dispose() {
        
    }
}
