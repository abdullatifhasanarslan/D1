/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme_server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Packet;

/**
 *
 * @author abdullatif
 */
public class PacketManager extends Thread{
    
    PacketManager(){
        
    }
    
    public void run(){
        try {
            DatagramSocket receiver = new DatagramSocket(1369);
            DatagramSocket sender = new DatagramSocket();
            byte[] receive_buffer = new byte[5000];
            DatagramPacket receive_packet = new DatagramPacket(receive_buffer,receive_buffer.length);

            while(true){
                receiver.receive(receive_packet);
                ByteArrayInputStream bais = new ByteArrayInputStream(receive_buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Packet packet = (Packet) ois.readObject();
                Iterator<Integer> iter = Deneme_server.clients.keySet().iterator();
                while(iter.hasNext()){
                    int id = iter.next().intValue();
                    if(packet.id==id){
                        continue;
                    }
                    InetAddress address = Deneme_server.clients.get(id).getInetAddress();
                    receive_packet.setAddress(address);
                    receive_packet.setPort(1371);
                    sender.send(receive_packet);
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
