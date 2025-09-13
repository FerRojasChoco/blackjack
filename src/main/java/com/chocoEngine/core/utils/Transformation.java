package main.java.com.chocoEngine.core.utils;

import org.joml.Matrix4f;

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
}
