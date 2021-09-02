package deneme;


import deneme.Wave;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.alListener3f;
import org.lwjgl.openal.ALC;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
/**
 *
 * @author abdullatif
 */
public class AudioMaster {
    
    private static List<Integer> buffers=new ArrayList<Integer>();
    private static long device;
    private static long context;
    
    public static void init(){
        //initialization
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        System.out.println(defaultDeviceName);
        device = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
    }
    
    public static void setListenerData(float x, float y, float z){
        alListener3f(AL10.AL_POSITION,x,y,z);
        alListener3f(AL10.AL_VELOCITY,0,0,0);
    }
    public static void setListenerOrientation(float angle){
        float[] orientation = new float[6];
        orientation[0]=(float) cos(PI*angle/360);
        orientation[1]=(float) sin(PI*angle/360);
        orientation[2]=0;
        orientation[3]=0;
        orientation[4]=0;
        orientation[5]=-1;
        AL10.alListenerfv(AL10.AL_ORIENTATION,orientation);
    }
    
    public static int loadSound(String filename){
        int buffer=AL10.alGenBuffers();
        buffers.add(buffer);
        //WaveData waveFile=WaveData.create(file);
        
        File waveFile=new File(filename);
        try {
            Wave wave = new Wave(waveFile);
            AL10.alBufferData(buffer, wave.getFormat(), wave.getData(), wave.getSampleRate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //waveFile.dispose();
        return buffer;
    }
    
    public static void cleanUp(){
        for(int buffer:buffers){
            AL10.alDeleteBuffers(buffer);
        }
        ALC.destroy();
    }
    
}
