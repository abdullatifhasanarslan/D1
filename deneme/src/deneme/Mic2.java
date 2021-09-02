/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Mic2
{
    public byte[] data;
    private int port;
    static AudioInputStream ais;
    public static int buffsize;
    public static TargetDataLine line;

    Mic2()
    {
        DatagramPacket dgp; 

        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 44100;
        int channels = 1;
        int sampleSize = 16;
        boolean bigEndian = false;
        InetAddress addr;


        AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " not supported.");
            return;
        }
        
        try
        {
            line = (TargetDataLine) AudioSystem.getLine(info);

            //buffsize = line.getBufferSize()/5;
            buffsize=4500;
            
            //System.out.println(buffsize);
//            buffsize = 4096; 
//            System.out.println(buffsize);

            line.open(format);

            line.start();   

            //int numBytesRead;
            data = new byte[buffsize];
            

//            addr = InetAddress.getByName("127.0.0.1");
//            DatagramSocket socket = new DatagramSocket();
            //while (true) {
                   // Read the next chunk of data from the TargetDataLine.
                   //numBytesRead =  line.read(data, 0, data.length);
                   // Save this chunk of data.
//                   dgp = new DatagramPacket (data,data.length,addr,50005);
//
//                   socket.send(dgp);
                //}

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public byte[] read(){
        int length;
        length=line.read(data,0,data.length);
        
        //System.out.println(length);
        return data;
    }
}
