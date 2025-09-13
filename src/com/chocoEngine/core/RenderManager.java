package com.chocoEngine.core;

import org.lwjgl.opengl.GL11;

import com.chocoEngine.test.Launcher;

/*class for actually render objects in the screen */

public class RenderManager {
    
    private final WindowManager window;

    public RenderManager(){
        window = Launcher.getWindow();
    }

    public void init() throws Exception{

    }

    public void render(){

    }

    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup(){
        
    }
}
