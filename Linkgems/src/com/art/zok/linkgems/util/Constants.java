package com.art.zok.linkgems.util;

import com.art.zok.linkgems.game.Coord;

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
    
    /* gems path order must be: Red, Orange, Yellow, Green, Blue, Purple, White */
    public static final String[] GEMSPATH = { "images/gemRed.png", "images/gemOrange.png", "images/gemYellow.png",
    		"images/gemGreen.png","images/gemBlue.png", "images/gemPurple.png","images/gemWhite.png",  };
  
    public static final int GEMSLENGTH = GEMSPATH.length;
    
    // board background left up corner
    public static final Coord BOARD_LEFT_UP_CORNER = new Coord(572, 68);
    
    // gems size
    public static final int GEMS_SIZE = 76;
    
    // menu gems horizontal position
    public static final int MENU_GEMS_HORI_POS = 278;
    
    // menu options start of y axis
    public static final int MENU_OPTS_START_OF_Y = 390;
    
    // The gap between options and another of menu
    public static final int MENU_OPTS_GAP =100; 
    
    // menu string
    public static final String MENU_START = "Start";
    public static final String MENU_TUTORIAL = "Tutorial";
    public static final String MENU_EXIT = "Exit";
    public static final String[] MENU_OPTIONS = {MENU_START, MENU_TUTORIAL, MENU_EXIT};
}
