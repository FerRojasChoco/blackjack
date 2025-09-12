package com.chocoEngine;

import com.chocoEngine.core.EngineManager;
import com.chocoEngine.core.WindowManager;
import com.chocoEngine.core.utils.Consts;

public class Launcher{

    private static WindowManager window;
    private static EngineManager engine;
    public static void main(String[] args){
        window = new WindowManager(Consts.TITLE, 1600, 900, false); 
        engine = new EngineManager();  
        
        try{
            engine.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow(){
        return window;
    }
}