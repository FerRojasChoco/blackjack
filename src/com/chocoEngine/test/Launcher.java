package com.chocoEngine.test;

import com.chocoEngine.core.EngineManager;
import com.chocoEngine.core.WindowManager;
import com.chocoEngine.core.utils.Consts;

public class Launcher{

    private static WindowManager window;
    private static TestGame game;
    public static void main(String[] args){
        window = new WindowManager(Consts.TITLE, 1600, 900, false); 
        game = new TestGame();
        EngineManager engine = new EngineManager();  
        
        try{
            engine.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //GETTERS AND SETTERS SECTION
    public static WindowManager getWindow(){
        return window;
    }

    public static TestGame getGame(){
        return game;
    }
}