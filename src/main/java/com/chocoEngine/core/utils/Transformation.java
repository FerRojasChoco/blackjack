package main.java.com.chocoEngine.core.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import main.java.com.chocoEngine.core.Camera;
import main.java.com.chocoEngine.core.entity.Entity;

public class Transformation {

    public static Matrix4f createTransformationMatrix(Entity entity){
        
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(entity.getPos()).
        rotateX((float)Math.toRadians(entity.getRotation().x)).
        rotateX((float)Math.toRadians(entity.getRotation().y)).
        rotateX((float)Math.toRadians(entity.getRotation().z)).
        scale(entity.getScale());

        return matrix;
    }

    public static Matrix4f getViewMatrix(Camera camera){
        
        Vector3f pos = camera.getPosition();
        Vector3f rot = camera.getRotation();

        Matrix4f matrix = new Matrix4f();

        matrix.identity();
        //here I learned about method chaining, basically this is the same as writing in three separate lines matrix.blabla 
        matrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1,0,0))
            .rotate((float) Math.toRadians(rot.y), new Vector3f(0, 1, 0))
            .rotate((float) Math.toRadians(rot.z), new Vector3f(0, 0 ,1));

        //negative bc we are moving all the world's objects in the opp direction in which we want to go
        matrix.translate(-pos.x, -pos.y, -pos.z);
        return matrix;
    }
}
