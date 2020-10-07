package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    InetAddress localHost;
    int sendPort, recPort;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private String data = "";

    Client(InetAddress localHost) {
        this.localHost = localHost;
    }

    public void setSendPort(int sendPort) {
        this.sendPort = sendPort;
    }

    public void setRecPort(int recPort) {
        this.recPort = recPort;
    }

    public void sendData() throws Exception {
        System.out.println("Enter the Response 'VOTE_COMMIT' || 'VOTE_ABORT' ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        data = bufferedReader.readLine();
        System.out.println("Data is " + data);
        datagramSocket = new DatagramSocket(sendPort);
        datagramPacket = new DatagramPacket(data.getBytes(), data.length(), localHost, sendPort - 1000);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    public void recData() throws Exception {
        byte[] buf = new byte[256];
        datagramSocket = new DatagramSocket(recPort);
        datagramPacket = new DatagramPacket(buf, buf.length);
        datagramSocket.receive(datagramPacket);
        datagramSocket.close();
        data = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        System.out.println("Client1 data " + data);
    }
}