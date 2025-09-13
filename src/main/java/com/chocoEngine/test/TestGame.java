package main.java.com.chocoEngine.test;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import main.java.com.chocoEngine.core.ILogic;
import main.java.com.chocoEngine.core.ObjectLoader;
import main.java.com.chocoEngine.core.RenderManager;
import main.java.com.chocoEngine.core.WindowManager;
import main.java.com.chocoEngine.core.entity.Entity;
import main.java.com.chocoEngine.core.entity.Model;
import main.java.com.chocoEngine.core.entity.Texture;

public class TestGame implements ILogic {

    private int direction = 0;
    private float color = 0;
    
    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;

    public TestGame(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
                        -0.5f, 0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f,
                        0.5f, -0.5f, 0.0f,
                        0.5f, 0.5f, 0.0f
        };

        int[] indices = {
            0, 1, 2, 
            0, 2, 3
        };

        float[] textureCoords = {
            0,0,
            0,1,
            1,1,
            1,0
        };

        Model model = loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")));

        entity = new Entity(model, new Vector3f(1, 0, 0), new Vector3f(0, 0, 0),1);
    }

    @Override
    public void input() {
        if(window.isKeyPressed(GLFW.GLFW_KEY_UP))
            direction = 1;
        else if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN))
            direction = -1;
        else
            direction = 0;
    }

    @Override
    public void update() {
        color += direction * 0.001f;
        if (color > 1)
            color = 1.0f;
        else if (color <= 0)
            color = 0.0f;

        if(entity.getPos().x < -1.5f){
            entity.getPos().x = 1.5f;
        }

        entity.getPos().x -= 0.01f; // move the entity from the right corner part of the screen to the left
    }

    @Override
    public void render() {
        if(window.isResize()){  //if window is resized, duhhhh
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(color, color, color, 0.0f);
        renderer.render(entity);

    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }

}
