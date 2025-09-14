package main.java.com.chocoEngine.test;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import main.java.com.chocoEngine.core.Camera;
import main.java.com.chocoEngine.core.ILogic;
import main.java.com.chocoEngine.core.MouseInput;
import main.java.com.chocoEngine.core.ObjectLoader;
import main.java.com.chocoEngine.core.RenderManager;
import main.java.com.chocoEngine.core.WindowManager;
import main.java.com.chocoEngine.core.entity.Entity;
import main.java.com.chocoEngine.core.entity.Model;
import main.java.com.chocoEngine.core.entity.Texture;
import main.java.com.chocoEngine.core.utils.Consts;

public class TestGame implements ILogic {
    
    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        

        Model model = loader.loadOBJModel("/models/bunny.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")));

        entity = new Entity(model, new Vector3f(0, 0, -5), new Vector3f(0, 0, 0),1);
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);

        if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
            cameraInc.z = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_S)){
            cameraInc.z = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_A)){
            cameraInc.x = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_D)){
            cameraInc.x = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_Z)){
            cameraInc.y = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_X)){
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        //can this be done more efficiently?
        camera.movePosition(cameraInc.x * Consts.CAMERA_STEP, cameraInc.y * Consts.CAMERA_STEP, cameraInc.z * Consts.CAMERA_STEP);

        if (mouseInput.isRightBtnPressed()){
            
            Vector2f rotVec = mouseInput.getDisplayVec();
            camera.moveRotation(rotVec.x * Consts.MOUSE_SENS, rotVec.y * Consts.MOUSE_SENS, 0);

        }

        entity.incRotation(0.05f, 0.5f, 0.0f);
    }

    @Override
    public void render() {
        if(window.isResize()){  //if window is resized, duhhhh
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.render(entity, camera);

    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }

}
