package com.art.zok.linkgems.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tools {
	public static TextureRegion textureToRegion(Texture texture, boolean flip) {
		 texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	     TextureRegion  region = new TextureRegion(texture);
	     region.flip(false, flip);
	     return region;
	}
}
