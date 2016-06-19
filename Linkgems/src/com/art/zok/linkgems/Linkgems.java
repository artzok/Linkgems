package com.art.zok.linkgems;

import static com.art.zok.linkgems.util.Constants.ASPECT_RATIO;
import static com.art.zok.linkgems.util.Constants.GAME_SCREEN;
import static com.art.zok.linkgems.util.Constants.MENU_SCREEN;
import static com.art.zok.linkgems.util.Constants.TUTORIAL_SCREEN;
import static com.art.zok.linkgems.util.Constants.VIEWPORT_HEIGHT;
import static com.art.zok.linkgems.util.Constants.VIEWPORT_WIDTH;

import java.util.HashMap;

import com.art.zok.linkgems.screen.AbstractScreen;
import com.art.zok.linkgems.screen.GameScreen;
import com.art.zok.linkgems.screen.MenuScreen;
import com.art.zok.linkgems.screen.TutorialScreen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
public class Linkgems extends Game {

    private SpriteBatch _batch;
    private BitmapFont _fontLoading;
    private AssetManager _assetManager;
    
    private HashMap<String, AbstractScreen> _screens;
    
    private Vector3 _mousePos;
    private TextureRegion _cursor;
    private OrthographicCamera _camera;
    private Rectangle _glviewport;
    
    @Override
    public void create() {
        _batch = new SpriteBatch();
        _assetManager = new AssetManager();
        
        // load "Loading" font
        BitmapFontParameter parameter = new BitmapFontParameter();
        parameter.flip = true;
        parameter.minFilter = TextureFilter.Linear;
        parameter.magFilter = TextureFilter.Linear;
        _assetManager.load("fonts/loadingFont.fnt", BitmapFont.class, parameter);
        
        // load mouse texture 
        _assetManager.load("images/handCursor.png", Texture.class);
        
        _assetManager.finishLoading();
        
        // get "loading" font
        _fontLoading = _assetManager.get("fonts/loadingFont.fnt", BitmapFont.class);
        _fontLoading.setScale(1.5f);
        
        // get cursor texture
        _cursor = new TextureRegion(
                _assetManager.get("images/handCursor.png", Texture.class));
        _cursor.flip(false, true);
        
        // create mouse position 
        _mousePos = new Vector3();
        
        // catch mouse for desktop platform
        Gdx.input.setCursorCatched(true);
        
        // create camera
        _camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        _camera.setToOrtho(true, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        _camera.update();
        
        // create "gl viewport"
        _glviewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), 
                Gdx.graphics.getHeight());
        
        // create list of screen
        _screens = new HashMap<String, AbstractScreen>();
        _screens.put(MENU_SCREEN, new MenuScreen(this));
        _screens.put(TUTORIAL_SCREEN, new TutorialScreen(this));
        _screens.put(GAME_SCREEN, new GameScreen(this));
        
        // set default screen
        changeScreen(MENU_SCREEN);
    }
    
    @Override
    public void resize(int width, int height) {
    	// calculate new viewport
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);
        
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height / (float)VIEWPORT_HEIGHT;
            crop.x = (width - VIEWPORT_WIDTH * scale) / 2.0f;
        } else if(aspectRatio < ASPECT_RATIO) {
            scale = (float)width / (float)VIEWPORT_WIDTH;
            crop.y = (height - VIEWPORT_HEIGHT * scale) / 2.0f;
        } else {
            scale = (float)width/(float)VIEWPORT_WIDTH;
        }

        float w = (float)VIEWPORT_WIDTH * scale;
        float h = (float)VIEWPORT_HEIGHT * scale;
        _glviewport.set(crop.x, crop.y, w, h);
        super.resize(width, height);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        Gdx.gl.glViewport((int)_glviewport.x, (int)_glviewport.y, 
                (int)_glviewport.width, (int)_glviewport.height);
        
        _batch.setProjectionMatrix(_camera.combined);
        
        _batch.begin();
        
        // render current screen
        super.render();
        
        // render mouse
        if(Gdx.app.getType() == ApplicationType.Desktop) {
            _mousePos.x = Gdx.input.getX();
            _mousePos.y = Gdx.input.getY();
            _camera.unproject(_mousePos, _glviewport.x, _glviewport.y, 
                    _glviewport.width, _glviewport.height);
            _batch.draw(_cursor, _mousePos.x, _mousePos.y);
        }
        _batch.end();
    }
    
    public boolean changeScreen(String key) {
        AbstractScreen nextScreen = _screens.get(key);
        Screen curScreen  = getScreen();
        
        if(nextScreen == null || nextScreen == curScreen) 
            return false;
        
        // set current screen to pause
        if(curScreen != null)
            curScreen.pause();
        
        // disable input
        Gdx.input.setInputProcessor(null);
        
        // change screen
        setScreen(nextScreen);

        // enable input
        Gdx.input.setInputProcessor(nextScreen);
        
        // reset some state of current screen
        nextScreen.resume();
        
        return true;
    }
    
    @Override
    public void dispose() {
        _batch.dispose();
        _assetManager.dispose();
    }
    
    public BitmapFont getLoadingFont() {
        return _fontLoading;
    }
    
    public SpriteBatch getSpriteBatch() {
        return _batch;
    }
    
    public AssetManager getAssetManager() {
        return _assetManager;
    }
    
    public Vector3 getMousePos() {
    	return _mousePos;
    }
}