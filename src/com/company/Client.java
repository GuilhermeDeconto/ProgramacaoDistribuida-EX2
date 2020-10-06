package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    InetAddress localHost;
    int sendPort, recPort;

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
        BufferedReader br;
        DatagramSocket ds;
        DatagramPacket dp;
        String data = "";
        System.out.println("Enter the Response 'VOTE_COMMIT' || 'VOTE_ABORT' ");
        br = new BufferedReader(new InputStreamReader(System.in));
        data = br.readLine();
        System.out.println("Data is " + data);
        ds = new DatagramSocket(sendPort);
        dp = new DatagramPacket(data.getBytes(), data.length(), localHost, sendPort - 1000);
        ds.send(dp);
        ds.close();
    }

    public void recData() throws Exception {
        byte[] buf = new byte[256];
        DatagramPacket datagramPacket;
        DatagramSocket datagramSocket;
        datagramSocket = new DatagramSocket(recPort);
        datagramPacket = new DatagramPacket(buf, buf.length);
        datagramSocket.receive(datagramPacket);
        datagramSocket.close();
        String msgStr = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        System.out.println("Client1 data " + msgStr);
    }
}