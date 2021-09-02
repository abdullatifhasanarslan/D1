/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme;

import util.Packet;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author abdullatif
 */
public class Sender {
    
    DatagramSocket socket;
    InetAddress ip;
    ByteArrayOutputStream baos;
    ObjectOutputStream oos;
    
    Sender(){
        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(Deneme.IP);
            baos = new ByteArrayOutputStream(5000);
            oos = new ObjectOutputStream(baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void send(Packet packet){
        try {
            //baos.reset();
            oos.writeObject(packet);
            oos.flush();
            byte[] data = baos.toByteArray();
            
            DatagramPacket byte_packet = new DatagramPacket(data,data.length,ip,1369);
            socket.send(byte_packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void receive(){
    }
}
