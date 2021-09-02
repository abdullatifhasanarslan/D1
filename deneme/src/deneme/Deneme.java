package deneme;

import util.Packet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author abdullatif
 */
public class Deneme {

    /**
     * @param args the command line arguments
     */
    public static float xPos=0;
    public static float yPos=0;
    public static float angle=0;
    public static AtomicBoolean ALIVE=new AtomicBoolean(true);
    public static String IP;
    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("Please enter ip as parameter");
            IP="127.0.0.1";
        }else{
            IP=args[0];
        }
        
        
        AudioMaster.init();
        AudioMaster.setListenerData(xPos,yPos,2);
        

        //Mic mic=new Mic();
        Mic2 mic=new Mic2();
        Window window=new Window();
        Moderator moderator = new Moderator();
        Receiver receiver = new Receiver();
        Sender sender = new Sender();
        
        
        try {
            
            Packet packet = new Packet();
            packet.id = moderator.self_id;
            while(ALIVE.get()){
//                int[] processed = new int[1];
//                source.setPosition((float)(window.getCursorPosX()-window.getWidth()/2)/10, 
//                                   (float)(window.getCursorPosY()-window.getHeight()/2)/10, 
//                                   0);
//                AL10.alGetSourcei(source.id, AL10.AL_BUFFERS_PROCESSED,processed);
//                while(processed[0]>0){
//                    AL10.alDeleteBuffers(AL10.alSourceUnqueueBuffers(source.id));
//                    int new_buffer=AL10.alGenBuffers();
//                    AL10.alBufferData(new_buffer, AL10.AL_FORMAT_MONO16, ByteBuffer.wrap(mic.read()), 44100);
//                    AL10.alSourceQueueBuffers(source.id, new_buffer);
//                    processed[0]--;
//                }
                packet.posX = (int)xPos;
                packet.posY = (int)yPos;
                packet.posZ = 2;
                packet.data=mic.read();
                sender.send(packet);
            }
//            source2.delete();
//            source.delete();
            AudioMaster.cleanUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
}
