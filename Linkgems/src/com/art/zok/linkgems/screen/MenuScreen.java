package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.art.zok.linkgems.util.Constants;
import com.art.zok.linkgems.util.Tools;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuScreen extends AbstractScreen {

    private TextureRegion _background;
    private TextureRegion _mainLogo;
    private TextureRegion[] _regionGems;
    float _animTime;
    float _totalTime;
    public MenuScreen(Linkgems linkgems) {
        super(linkgems);
        _animTime = 0.0f;
        _totalTime = 3.0f;
        _regionGems = new TextureRegion[7];
    }

    @Override
    public void show() {
        _assetManager.load("images/mainMenuBackground.png", Texture.class);
        _assetManager.load("images/mainMenuLogo.png", Texture.class);
        for(int i = 0; i < Constants.gemsPath.length; i++) {
        	_assetManager.load(Constants.gemsPath[i], Texture.class);
        }
    }

    @Override
    public void hide() {
        _assetManager.unload("images/mainMenuBackground.png");
        _assetManager.unload("images/mainMenuLogo.png");
        
        for(int i = 0; i < Constants.gemsPath.length; i++) {
        	_assetManager.unload(Constants.gemsPath[i]);
        }
    }
    
    @Override
    public void assignAssets() {
       Texture texture = _assetManager.get("images/mainMenuBackground.png");
       _background = Tools.textureToRegion(texture, true);

       texture = _assetManager.get("images/mainMenuLogo.png");
       _mainLogo = Tools.textureToRegion(texture, true);
       
       for(int i = 0; i < Constants.gemsPath.length; i++) {
    	   texture = _assetManager.get(Constants.gemsPath[i]);
           _regionGems[i] = Tools.textureToRegion(texture, true);
       }
    }
    
    @Override
    public void update(float delta) {
    	if(_animTime < _totalTime)
    		_animTime += delta;
    }
    
    @Override
    public void render() {
    	
    	// render background
        _batch.draw(_background, 0, 0);
        
        // render alpha animation for main logo
        _batch.setColor(1.0f, 1.0f, 1.0f, _animTime / _totalTime);
        _batch.draw(_mainLogo, 0, 0);
        _batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        // render gems 
        for(int i = 0; i < 7; i++) {
        	_batch.draw(_regionGems[i], (Constants.VIEWPORT_WIDTH - 78 * 7) / 2 + i * 78, 258);
        }
    }

    @Override
    public void dispose() {
        
    }
}
