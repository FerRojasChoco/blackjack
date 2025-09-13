package main.java.com.chocoEngine.core.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.lwjgl.system.MemoryUtil;

public class Utils {

    public static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }


    public static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static String loadResource(String fileName) throws Exception{

        InputStream in = Utils.class.getResourceAsStream(fileName);
        if (in == null){
            throw new FileNotFoundException("\nRESOURCE NOT FOUND: " + fileName + " at " + Utils.class.getResource(fileName));
        }


        String result;
        try(Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())){
                result = scanner.useDelimiter("\\A").next();
            }
        return result;
    }


}
