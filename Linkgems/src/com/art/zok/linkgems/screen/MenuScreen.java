package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.art.zok.linkgems.util.Constants;
import com.art.zok.linkgems.util.Tools;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends AbstractScreen {

    private TextureRegion _background;
    private TextureRegion _mainLogo;
    private TextureRegion _highLight;
    private TextureRegion[] _regionGems;
    private TextBounds[] _menuOptTxtBounds;
    
    private BitmapFont _menuFont;

    float _animTime;
    float _totalTime;
    
    int _currentOption;
    
    public MenuScreen(Linkgems linkgems) {
        super(linkgems);
        _animTime = 0.0f;
        _totalTime = 0.8f;
        _currentOption = -1;
        _regionGems = new TextureRegion[7];
    }

    @Override
    public void show() {
        _assetManager.load("images/mainMenuBackground.png", Texture.class);
        _assetManager.load("images/mainMenuLogo.png", Texture.class);
        _assetManager.load("images/menuHighlight.png", Texture.class);
        BitmapFontParameter parameter = new BitmapFontParameter();
        parameter.flip = true;
        parameter.magFilter = TextureFilter.Linear;
        parameter.minFilter = TextureFilter.Linear;
        _assetManager.load("fonts/menuFont.fnt", BitmapFont.class, parameter);
        
        for(int i = 0; i < Constants.gemsPath.length; i++) {
        	_assetManager.load(Constants.gemsPath[i], Texture.class);
        }
    }

    @Override
    public void hide() {
        _assetManager.unload("images/mainMenuBackground.png");
        _assetManager.unload("images/mainMenuLogo.png");
        _assetManager.unload("images/menuHighlight.png");
        _assetManager.unload("fonts/menuFont.fnt");
        for(int i = 0; i < Constants.gemsPath.length; i++) {
        	_assetManager.unload(Constants.gemsPath[i]);
        }
    }
    
    @Override
    public void assignAssets() {
    	// background
       Texture texture = _assetManager.get("images/mainMenuBackground.png");
       _background = Tools.textureToRegion(texture, true);

       // logo
       texture = _assetManager.get("images/mainMenuLogo.png");
       _mainLogo = Tools.textureToRegion(texture, true);
       
       // high light
       texture = _assetManager.get("images/menuHighlight.png");
       _highLight = Tools.textureToRegion(texture, true);
       
       // gems
       for(int i = 0; i < Constants.gemsPath.length; i++) {
    	   texture = _assetManager.get(Constants.gemsPath[i]);
           _regionGems[i] = Tools.textureToRegion(texture, true);
       }
       
       // menu font
       _menuFont = _assetManager.get("fonts/menuFont.fnt", BitmapFont.class);
       _menuFont.setScale(1.5f);
       
       // text bounds
       int length = Constants.MENU_OPTIONS.length;
       _menuOptTxtBounds = new TextBounds[length];
       for(int i = 0; i < length; i++) {
    	   _menuOptTxtBounds[i] = _menuFont.getBounds(Constants.MENU_OPTIONS[i], 
    			   new TextBounds());
       }
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
        
        // render gems and animation
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
        // 390
        for(int i = 0; i < Constants.MENU_OPTIONS.length; i++) {
        	// render shadow 
        	_menuFont.setColor(0.0f, 0.0f, 0.0f,0.5f);
        	_menuFont.draw(_batch, Constants.MENU_OPTIONS[i], 
        			(Constants.VIEWPORT_WIDTH - _menuOptTxtBounds[i].width) / 2.0f + 4, 
        			390.0f + i * 100 + 4);
        	
        	// render options
        	_menuFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        	_menuFont.draw(_batch, Constants.MENU_OPTIONS[i], 
        			(Constants.VIEWPORT_WIDTH - _menuOptTxtBounds[i].width) / 2.0f, 
        			390.0f + i * 100);
        	
        	//render high light if need
        	if(_currentOption != i) continue;
        	_batch.draw(_highLight, (Constants.VIEWPORT_WIDTH - _highLight.getRegionWidth()) / 2.0f, 
        			390.0f + i * 100, _highLight.getRegionWidth(),  100.0f);
        }
    }

    @Override
    public void resume() {
    	super.resume();
    	_currentOption = -1;
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	super.mouseMoved(screenX, screenY);
    	Vector3 mouse = _parent.getMousePos();
    	_currentOption = getOption(mouse);
    	return true;
    	
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	// 由于移动设备不会调用mouseMoved,因此需要再次获得选项
		_currentOption = getOption(_parent.getMousePos());
    	return true;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	switch (_currentOption) {
		case 0:
			_parent.changeScreen(Constants.GAME_SCREEN);
			break;
		case 1:
			_parent.changeScreen(Constants.TUTORIAL_SCREEN);
			break;
		case 2:
			Gdx.app.exit();
			break;
		default:
			break;
		}
    	return true;
    }
    
    public int getOption(Vector3 mousePos) {
    	if(mousePos == null || mousePos.y < 390.0f) return -1;
    	int option = (int)((mousePos.y - 390.0f) / 100.0f); 
    	return option;
    }
}
