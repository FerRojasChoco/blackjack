package com.chocoEngine.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class WindowManager {
    public static final float FOV = (float) Math.toRadians(60);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000f;

    private final String title;
    
    private int width, height;

    private long window;

    private boolean resize, vSync;

    private final Matrix4f projectionMatrix;

    public WindowManager(String title, int width, int height, boolean vSync){
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix = new Matrix4f();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit())
            throw new IllegalStateException("unable to init glfw");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);  //initially the window won't be visible
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE); //it can be resizable

        //these are recommended versions defined 
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);

        //tells openGL which profile to use, it can be any
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        //any functions deprecated in the current version of openGL will also be deprecated in the code
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        boolean maximised = false;
        if (width == 0 || height == 0){
            width = height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximised = true;
        }

        //create a window
        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
            throw new RuntimeException("failed to create a window");

        // takes window as parameters and outputs params as window, w and h
        // so if the window gets resized, we can apply the new values to the window
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height)-> {
            this.width = width;
            this.height = height;
            this.setResize(true);
        });

        //detect if the user has pressed ESC in order to close the window
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action , mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                GLFW.glfwSetWindowShouldClose(window, true);
        });

        //maximizes or centers the window
        if(maximised)
            GLFW.glfwMaximizeWindow(window);
        else{
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(
                window,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(window);

        if(vSync)
            GLFW.glfwSwapInterval(1);

        
        //finally show the window
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    //tells open gl to start rendering all the things we placed in the "queue"
    public void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();  
    }

    public void cleanup(){
        GLFW.glfwDestroyWindow(window);
    }

    public void setClearColor(float r, float g, float b, float alpha){
        GL11.glClearColor(r, g, b, alpha);
    }

    /*
     * GETTERS AND SETTERS
     */

    public boolean isKeyPressed(int keycode){
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        GLFW.glfwSetWindowTitle(window, title);
    }

    public boolean isResize(){
        return resize;
    }

    public boolean isvSync(){
        return vSync;
    }


    public void setResize(boolean resize){
        this.resize = resize;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public long getWindow(){
        return window;
    }

    public Matrix4f geMatrix4f(){
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix(){
        float aspectRatio = width/height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public Matrix4f updateProjetionMatrix4f(Matrix4f matrix, int width, int height){
        float aspectRatio = width/height;
        return matrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    
}
