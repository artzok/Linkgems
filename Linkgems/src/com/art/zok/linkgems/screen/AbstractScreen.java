package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.art.zok.linkgems.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * <p>This class done assets loading logic and handle same input.</p>
 * 
 * @author artzok 
 * @version 1.0 
 **/
public abstract class AbstractScreen implements Screen, InputProcessor {
    protected Linkgems _parent; 
    protected SpriteBatch _batch;
    protected BitmapFont _fontLoading;
    protected AssetManager _assetManager;
    private TextBounds _loadingTxtBounds;
    
    private enum State {Loading, Done};
    
    private State _state;
    
    public AbstractScreen(Linkgems linkgems) {
        _state = State.Loading;
        this._parent = linkgems;
        _batch = _parent.getSpriteBatch();
        _fontLoading = _parent.getLoadingFont();
        _assetManager = _parent.getAssetManager();
        _loadingTxtBounds = _fontLoading.getBounds(Constants.LOADING);
    }
    
    @Override
    public void render(float delta) {
        if(_state == State.Loading) {
            if(_assetManager.update()) {
                assignAssets();
                _state = State.Done;
            }
            renderLoadingTxt();
        } else {
            update(delta);
            render();
        }
    }
    
    protected void renderLoadingTxt() {
        _fontLoading.draw(_batch, Constants.LOADING,
                (Constants.VIEWPORT_WIDTH - _loadingTxtBounds.width) / 2, 
                (Constants.VIEWPORT_HEIGHT - _loadingTxtBounds.height) / 2);
    }
    
    
    /**
     * reset some state of current screen
     */
    @Override
    public void resume() {
        _state = State.Loading; 
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenX = MathUtils.clamp(screenX, 0, Gdx.graphics.getWidth());
        screenY = MathUtils.clamp(screenY, 0, Gdx.graphics.getHeight());
        Gdx.input.setCursorPosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) { return false; }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {   return false;  }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    public boolean keyTyped(char character) { return false; }
    public boolean scrolled(int amount) { return false; }
    public boolean keyUp(int keycode) { return false; }
    public void resize(int width, int height) {}
    public void pause() {}
    
    /**
     * load assets
     */
    @Override
    public abstract void show();
    /**
     * unload assets
     */
    public abstract void hide();
    /**
     * initialize member with assets
     */
    public abstract void assignAssets();
    /**
     * update game world
     */
    public abstract void update(float delta);
    /**
     * render game model
     */
    public abstract void render();
    /**
     * maybe not use
     */
    public abstract void dispose();
}
