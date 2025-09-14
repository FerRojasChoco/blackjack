package main.java.com.chocoEngine.core.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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

    //function for reading a text file
    /*public static List<String> readAllLines(String fileName){
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(fileName))))  {
            String line;

            while ((line = br.readLine()) != null){
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }*/

    //function for reading a text file
    public static List<String> readAllLines(String fileName){
        List<String> list = new ArrayList<>();

        // Use the class's own classloader to get the resource
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(fileName))))  {
            String line;

            while ((line = br.readLine()) != null){
                list.add(line);
            }
        } catch (Exception e) {
            // It's better to throw a custom exception or handle it more gracefully
            // than just printing the stack trace, but for this example, it works.
            e.printStackTrace();
        }

        return list;
    }
}
