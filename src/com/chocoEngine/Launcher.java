package com.chocoEngine;

import com.chocoEngine.core.WindowManager;

public class Launcher{
    public static void main(String[] args){
        WindowManager window = new WindowManager("CHOCO ENGINE", 1600, 900, false);   
        
        window.init();

        while(!window.windowShouldClose()){
            window.update();
        }

        window.cleanup();
    }
}