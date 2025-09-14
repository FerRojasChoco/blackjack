package main.java.com.chocoEngine.core;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import main.java.com.chocoEngine.test.Launcher;

public class MouseInput {

    private final Vector2d prevPos, currentPos;
    private final Vector2f displayVec;

    private boolean inWindow = false;
    private boolean leftBtnPressed = false;
    private boolean rightBtnPressed = false;

    public MouseInput(){
        prevPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displayVec = new Vector2f();
    }

    public void init(){
        
        //Mouse/cursor position callbacks
        GLFW.glfwSetCursorPosCallback(Launcher.getWindow().getWindow(), (window, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        GLFW.glfwSetCursorEnterCallback(Launcher.getWindow().getWindow(), (window, entered) -> {
            inWindow = entered;
        });

        GLFW.glfwSetMouseButtonCallback(Launcher.getWindow().getWindow(), (window, button, action, mods) -> {
            leftBtnPressed = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            rightBtnPressed = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        });
    }

    public void input(){
        displayVec.x = 0;
        displayVec.y = 0;
        
        if(prevPos.x > 0 && prevPos.y > 0 && inWindow){
            double x = currentPos.x - prevPos.x;
            double y = currentPos.y - prevPos.y;

            boolean rotateX = x != 0;
            boolean rotateY = y != 0;

            if(rotateX){
                displayVec.y = (float) x;
            }
            if(rotateY){
                displayVec.x = (float) y;
            }

        }

        //for comparing in the following input
        prevPos.x = currentPos.x;
        prevPos.y = currentPos.y;
    }


    public Vector2f getDisplayVec() {
        return displayVec;
    }

    //SETTERS section
    
    public boolean isLeftBtnPressed(){
        return leftBtnPressed;
    }
    
    public boolean isRightBtnPressed(){
        return rightBtnPressed;
    }
}
