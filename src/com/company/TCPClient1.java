package com.company;

import java.net.InetAddress;

public class TCPClient1 {
    public static void main(String[] args) throws Exception {
        InetAddress localHost;
        localHost = InetAddress.getLocalHost();
        Client client = new Client(localHost);
        client.setSendPort(9001);  //recport=8000
        client.setRecPort(8000);  //sendport=9001
        client.recData();
        client.sendData();
        client.recData();
    }
}