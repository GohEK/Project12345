/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardpanreceiver;

import java.awt.Component;
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Window 7
 */
public class UDPServer {
    private final DatagramSocket serverSocket;
    private byte[] data = null;
    
    public UDPServer(int port) throws IOException{
    serverSocket = new DatagramSocket(port);
    }
    
    public void sendRequest(String stationNo) throws IOException{
        String requestMessage = "p:" + stationNo;
        data = requestMessage.getBytes();
        InetAddress address = InetAddress.getLocalHost();
        
        DatagramPacket packet = new DatagramPacket(data,data.length,address,5231);
        
        serverSocket.send(packet);   
    }
    
    public String receiveMessage(JTextArea messageBox) throws IOException{
        try (serverSocket) {
            data = new byte[55555];
            serverSocket.setSoTimeout(20000);
            DatagramPacket packet = new DatagramPacket(data,data.length);
            serverSocket.receive(packet);
            String packetContent = new String(packet.getData(),0,packet.getLength());
            messageBox.append(packetContent + "\n");
            return packetContent;
        }
        catch (SocketTimeoutException ex){
            Component frame = null;
            JOptionPane.showMessageDialog(frame,
            "Run Time Error",
            "Error",
            JOptionPane.PLAIN_MESSAGE);
        }
        return "";
    }
}
