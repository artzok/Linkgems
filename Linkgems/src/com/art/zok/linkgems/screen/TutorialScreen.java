package com.art.zok.linkgems.screen;

import com.art.zok.linkgems.Linkgems;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TutorialScreen extends AbstractScreen {

    private TextureRegion _tutorial;
    
    public TutorialScreen(Linkgems linkgems) {
        super(linkgems);
    }

    @Override
    public void show() {
        _assetManager.load("images/tutorial.png", Texture.class);
    }

    @Override
    public void hide() {
        _assetManager.unload("images/tutorial.png");
    }

    @Override
    public void assignAssets() {
        Texture texture = _assetManager.get("images/tutorial.png", Texture.class);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _tutorial = new TextureRegion(texture);
        _tutorial.flip(false, true);
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render() {
        _batch.draw(_tutorial, 0, 0);
    }

    @Override
    public void dispose() {
    }
}
