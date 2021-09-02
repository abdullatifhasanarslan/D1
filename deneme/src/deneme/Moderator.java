/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdullatif
 */
public class Moderator extends Thread{
    public static Map<Integer, Source> sources = new HashMap<Integer, Source>();
    public byte[] receive_buffer;
    public Socket socket;
    public InputStream input;
    public int self_id;
    Moderator(){
        try {
            System.out.println("Moderator started");
            this.receive_buffer = new byte[50];
            this.socket = new Socket(Deneme.IP,1370);
            input = socket.getInputStream();
            self_id=input.read();
            //int count=input.read(receive_buffer);
            System.out.println("Your id:"+self_id);
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run(){
        try {    
            while(true){
                int id = input.read();
                input.read(receive_buffer);
                sources.put(id,new Source());
                System.out.println(id + ": " + receive_buffer.toString());
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
