package com.company;

import java.net.InetAddress;

public class TCPClient2 {
    public static void main(String[] args) throws Exception {
        InetAddress localHost;
        localHost = InetAddress.getLocalHost();
        Client client = new Client(localHost);
        client.setSendPort(9003);
        client.setRecPort(8002);
        client.recData();
        client.sendData();
        client.recData();
    }
}