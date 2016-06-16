package com.art.zok.linkgems;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Linkgems";
		cfg.width = 853;
		cfg.height = 480;
		
		new LwjglApplication(new Linkgems(), cfg);
	}
}
