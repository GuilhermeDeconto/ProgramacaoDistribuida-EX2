package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    InetAddress localHost;
    int sendPort, recPort;
    int ssend = 0;
    int scounter = 0;

    Server(InetAddress localHost) {
        this.localHost = localHost;
    }

    public void setSendPort(int sendPort) {
        this.sendPort = sendPort;
    }

    public void setRecPort(int recPort) {
        this.recPort = recPort;
    }

    public void sendData() throws Exception {
        DatagramSocket datagramSocket;
        DatagramPacket datagramPacket;
        String data = "";
        if (scounter < 2 && ssend < 2) {
            data = "VOTE_REQUEST";
        }
        if (scounter < 2 && ssend > 1) {
            data = "GLOBAL_ABORT";
            data = data + " TRANSACTION ABORTED";
        }
        if (scounter == 2 && ssend > 1) {
            data = "GLOBAL_COMMIT";
            data = data + " TRANSACTION COMMITTED";
        }
        datagramSocket = new DatagramSocket(sendPort);
        datagramPacket = new DatagramPacket(data.getBytes(), data.length(), localHost, sendPort - 1000);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
        ssend++;
    }

    public void recData() {
        byte[] buf = new byte[256];
        DatagramPacket datagramPacket = null;
        DatagramSocket datagramSocket = null;
        String msgStr = "";
        try {
            datagramSocket = new DatagramSocket(recPort);
            datagramPacket = new DatagramPacket(buf, buf.length);
            datagramSocket.receive(datagramPacket);
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        msgStr = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        System.out.println("String = " + msgStr);
        if (msgStr.equalsIgnoreCase("VOTE_COMMIT")) {
            scounter++;
        }
        System.out.println("Counter value = " + scounter + "n Send value = " + ssend);
    }
}
