/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme_server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Misyon: Küresel çapta kabil, çağın ötesinde bir bakış açısına sahip medeniyetimizin değerlerini içselleştirmiş, 
 * öncü ve özgün projeler üretecek nitelikli mühendis yetişmesine katkı sağlamak
 */
public class Deneme_server {
    public static Map<Integer, Socket> clients = new HashMap<Integer, Socket>();

    public static void main(String[] args) {
        try {
            PacketManager packet_manager = new PacketManager();
            packet_manager.start();
            
            int id=0;
            ServerSocket server = new ServerSocket(1370);
            
            //This part for learning ip----------------------------
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostAddress());
                }
            }
            //----------------------------------------------------
            
            System.out.println("Server ready");
            while(true){
                Socket new_client=server.accept();
                InputStream in= new_client.getInputStream();
                InetAddress address=new_client.getInetAddress();
                System.out.println(address.getHostAddress());
                DataOutputStream new_client_out=new DataOutputStream(new_client.getOutputStream());
                new_client_out.write(id);
                for(Integer i : clients.keySet()){
                    DataOutputStream out= new DataOutputStream(clients.get(i).getOutputStream());
                    out.writeInt(id);
                    out.writeChars(address.toString());
                    new_client_out.write(clients.get(i).getInetAddress().toString().getBytes());
                }
                clients.put(id,new_client);
                id++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
