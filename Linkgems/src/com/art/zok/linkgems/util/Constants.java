package com.art.zok.linkgems.util;


public class Constants {
	// Orthographic camera's viewport size and aspect ratio
    public static final int VIEWPORT_WIDTH = 1280;
    public static final int VIEWPORT_HEIGHT = 720;
    public static final float ASPECT_RATIO = 1.7777f;
   
    // All screens key
    public static final String MENU_SCREEN = "menuScreen";
    public static final String TUTORIAL_SCREEN = "tutorialScreen";
    public static final String GAME_SCREEN = "gameScreen";
    
    // loading text
    public static final String LOADING = "Loading...";
    
    // gems path
    public static final String[] gemsPath = { "images/gemBlue.png",
    	"images/gemGreen.png", "images/gemOrange.png", "images/gemPurple.png",
    	"images/gemRed.png", "images/gemWhite.png", "images/gemYellow.png" };
    
    // menu string
    public static final String MENU_START = "Start";
    public static final String MENU_TUTORIAL = "Tutorial";
    public static final String MENU_EXIT = "Exit";
    public static final String[] MENU_OPTIONS = {MENU_START, MENU_TUTORIAL, MENU_EXIT};
}
