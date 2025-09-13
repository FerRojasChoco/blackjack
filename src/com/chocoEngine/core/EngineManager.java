package com.chocoEngine.core;
import com.chocoEngine.core.utils.Consts;
import com.chocoEngine.test.Launcher;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

/*
 * RELEVANT NOTES: the comments with "og" in them represent the original version of the code
 * after several trials, the original of certain parts of the code seem to be provoking an infinite
 * loop (guess) or some other sort of trouble because of how the logic worked, so instead of following
 * the tutorial's way to define functions like stop and start, I decided to implement a more "hardheaded"
 * way, but it seemed logical to me, and the most importan part, it works.
 * But if you think it can be improved...
 * let me know... LET ME KNOWWWW LET ME KNOWWW UGWUGAUUAHEHAUSAHUEHUHTUAHS - end comment ^^
 */

public class EngineManager {
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private WindowManager window;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;

    private void init() throws Exception{
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        window = Launcher.getWindow();
        gameLogic = Launcher.getGame();

        window.init();
        gameLogic.init();
    }

    public void start() throws Exception{
        init();
        if(!isRunning)
            run(true);
        /*og start
        if(isRunning)
            return;
        run();*/
    }
/*og run: public void run(){this.isRunning=truel ... }*/
    public void run(boolean run){
        this.isRunning = run;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;     //tells whether or not we start to render the screen

        while(isRunning){
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double)NANOSECOND;
            frameCounter += passedTime;

            input();
            
            while(unprocessedTime > frameTime){
                render = true;
                unprocessedTime -= frameTime;

                if(window.windowShouldClose())
                    stop();

                if(frameCounter >= NANOSECOND){
                    setFps(frames);
                    window.setTitle(Consts.TITLE + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render){
                update();
                render();
                frames++;

            }
        }
        cleanup();
    }

    private void stop(){
        if(isRunning)
            run(false);
        /* og stop
        if(!isRunning)
            return;
        isRunning = false;*/
    }

    private void input(){
        gameLogic.input();
    }

    private void render(){
        gameLogic.render();
        window.update();
    }

    private void update(){
        gameLogic.update();
    }

    private void cleanup(){
        window.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    //GETTERS & SETTERS SECTION
    public static int getFps(){
        return fps;
    }

    public static void setFps(int fps){
        EngineManager.fps = fps;
    }
}
