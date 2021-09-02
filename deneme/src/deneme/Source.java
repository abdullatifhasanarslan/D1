package deneme;

import util.Packet;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.*;


/**
 *
 * @author abdullatif
 */
public class Source {
    public float posX,posY,posZ;
    public float velX,velY,velZ;
    public int id;
    public int[] buffers;
    public int buffer_count;
    
    public Source(){
        try {
            this.id=alGenSources();
            this.buffer_count=4;
            this.buffers=new int[buffer_count];
            AL10.alGenBuffers(buffers);
            for(int i=0;i<buffer_count;i++){
                AL10.alBufferData(buffers[i], AL10.AL_FORMAT_MONO16, ByteBuffer.allocate(Mic2.buffsize), 44100);
            }
            AL10.alSourceQueueBuffers(this.id, buffers);
            AL10.alSourcePlay(this.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(Packet packet){
        int[] processed = new int[1];
        setPosition(packet.posX,packet.posY,packet.posZ);
        AL10.alGetSourcei(this.id, AL10.AL_BUFFERS_PROCESSED,processed);
        
        while(processed[0]>0){
            AL10.alDeleteBuffers(AL10.alSourceUnqueueBuffers(this.id));
            int new_buffer=AL10.alGenBuffers();
            AL10.alBufferData(new_buffer, AL10.AL_FORMAT_MONO16, ByteBuffer.wrap(packet.data), 44100);
            AL10.alSourceQueueBuffers(this.id, new_buffer);
            processed[0]--;
        }
    }
    
    public Source(float x, float y, float z){
        this.id=alGenSources();
        this.posX=x;this.posY=y;this.posZ=z;
    }
    
    
    public void play(int buffer){
        stop();
        alSourcei(this.id,AL_BUFFER,buffer);
        alSourcePlay(this.id);
        
    }
    
    public void play(byte[] buffer){
        ByteBuffer byte_buffer = ByteBuffer.wrap(buffer);
        stop();
        //AL10.alBufferData(this.id,AL10.AL_FORMAT_MONO16,byte_buffer,44100);
        
        alSourcePlay(this.id);
    }
    
    public void delete(){
        stop();
        alDeleteSources(this.id);
    }
    
    public void pause(){
        AL10.alSourcePause(id);
    }
    
    public void continuePlaying(){
        AL10.alSourcePlay(id);
    }
    
    public void stop(){
        AL10.alSourceStop(id);
    }
    
    public void setVelocity(float x, float y, float z){
        AL10.alSource3f(id,AL10.AL_VELOCITY,x,y,z);
    }
    
    public void setLooping(boolean loop){
        AL10.alSourcei(id, AL10.AL_LOOPING,loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }
    
    public boolean isPlaying(){
        return AL10.alGetSourcei(id,AL10.AL_SOURCE_STATE)==AL10.AL_PLAYING;
    }
    
    
    public void setVolume(float volume){
        AL10.alSourcef(id, AL10.AL_GAIN, volume);
    }
    
    public void setPitch(float pitch){
        AL10.alSourcef(id, AL10.AL_PITCH, pitch);
    }
    
    public void setPosition(float x, float y, float z){
        posX=x;posY=y;posZ=z;
        AL10.alSource3f(id, AL10.AL_POSITION, x, y, z);
    }
    
}
