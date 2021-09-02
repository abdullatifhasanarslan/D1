/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme;

import util.Packet;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Iterator;

/**
 *
 * @author abdullatif
 */
public class Receiver extends Thread{
    
    public Receiver(){
        System.out.println("Receiver started");
        start();
    }
    
    public void run(){
        try {
            DatagramSocket server = new DatagramSocket(1371);
            byte[] receive_buffer = new byte[5000];
            ByteArrayInputStream bais = new ByteArrayInputStream(receive_buffer);
            DatagramPacket receive_packet;
            while(true){
                receive_packet = new DatagramPacket(receive_buffer, receive_buffer.length);
                server.receive(receive_packet);
                System.out.println("Now received");
                //bais.reset();
                ObjectInputStream ois = new ObjectInputStream(bais);
                Packet packet = (Packet) ois.readObject();
                Moderator.sources.get(packet.id).update(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
