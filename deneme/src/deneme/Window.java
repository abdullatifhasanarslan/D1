/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme;

import static deneme.Deneme.xPos;
import static deneme.Deneme.yPos;
import static deneme.Mic.RECORD_TIME;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;



/**
 *
 * @author abdullatif
 */
public class Window extends Thread {
    private int width,height;
    private String title;
    private long glfwWindow;
    
    private static Window window = null;
    private static float volume=1f;
    
    Window(){
        this.width=500;
        this.height=500;
        this.title="deneme";
        if(Window.window==null){
            Window.window=this;
            start();
        }
        
        
    }
    

    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getWindowX(){        
        IntBuffer posX = BufferUtils.createIntBuffer(1);
        IntBuffer posY = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowPos(glfwWindow, posX, posY);
        return posX.get(0);
    }
    
    public int getWindowY(){        
        IntBuffer posX = BufferUtils.createIntBuffer(1);
        IntBuffer posY = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowPos(glfwWindow, posX, posY);
        return posY.get(0);
    }
    
    public double getCursorPosX(){
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(glfwWindow, posX, posY);
        return posX.get(0);
    }
    
    
    public double getCursorPosY(){
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(glfwWindow, posX, posY);
        return posY.get(0);
    }
    
    public void drawTriangle(int x, int y){
        GL11.glPushMatrix();
        GL11.glTranslatef(width/2, height/2, 0.0f);
        GL11.glColor3i(255,0,0);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2i(x, y);
        GL11.glVertex2i(x+10, y+10);
        GL11.glVertex2i(x-10, y+10);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    public void drawListener(){
        int x=(int)(Deneme.xPos*10);
        int y=(int)(Deneme.yPos*10);
        GL11.glPushMatrix();
        GL11.glTranslatef(width/2, height/2, 0.0f);
        GL11.glTranslatef(x, y, 0.0f);
        GL11.glRotatef(Deneme.angle, 0, 0, 1);
        GL11.glColor3i(255,0,0);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2i(0, 0);
        GL11.glVertex2i(10, 10);
        GL11.glVertex2i(-10, 10);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public void run(){
        

        if(!GLFW.glfwInit()){
           System.out.println("Could not create window");
           return;
        }
        
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE,GLFW.GLFW_FALSE);
        glfwWindow= GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow==NULL){
            System.out.println("Error while creating GLFW window");
        }

        GLFW.glfwMakeContextCurrent(glfwWindow);
        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(glfwWindow);

        GL.createCapabilities();
        
        
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	GL11.glOrtho(0.0, width, height, 0, 1, -1);
	glMatrixMode(GL_MODELVIEW);

       
        //keyboard------------------------------
        glfwSetKeyCallback(glfwWindow, (glfwWindow, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_W && action == GLFW.GLFW_PRESS){
                Deneme.yPos-=cos(PI*Deneme.angle/180);
                Deneme.xPos+=sin(PI*Deneme.angle/180);
                AudioMaster.setListenerData( Deneme.xPos, Deneme.yPos,2);
            } else if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS){
                Deneme.yPos+=cos(PI*Deneme.angle/180);
                Deneme.xPos-=sin(PI*Deneme.angle/180);
                AudioMaster.setListenerData( Deneme.xPos, Deneme.yPos,2);
            } else if (key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_PRESS){
                Deneme.angle-=30;
                AudioMaster.setListenerOrientation( Deneme.angle);
            } else if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS){
                Deneme.angle+=30;
                AudioMaster.setListenerOrientation( Deneme.angle);
            } else if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS){
                Deneme.ALIVE.compareAndSet(true, false);
            } else{

            }
        });
        
        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            GLFW.glfwPollEvents();

            GL11.glClearColor((float)getCursorPosX()/255, 1.0f, 1.0f, 1.0f);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            drawListener();
            Iterator<Integer> iter = Moderator.sources.keySet().iterator();
            while(iter.hasNext()){
                int id=iter.next();
                Source source = Moderator.sources.get(id);
                drawTriangle((int)source.posX,(int)source.posY);
            }
            GLFW.glfwSwapBuffers(glfwWindow);
            
        }
    }
}
