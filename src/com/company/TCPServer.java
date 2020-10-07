package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class TCPServer {
    public static void main(String[] args) {
        try {
            InetAddress localHost;
            localHost = InetAddress.getLocalHost();
            Server server = new Server(localHost);
            System.out.println("Server in sending mode…..");

            //Sending data to clients
            sendDataToClient(server, 9000, 8001, "Send request data to client1...", "Waiting for response from client1...");
            sendDataToClient(server, 9002, 8003, "Send request data to client2...", "Waiting for response from client2...");

            //Sending the result to clients
            sendResult(server, 9000);
            sendResult(server, 9002);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendResult(Server server, int port) {
        try {
            server.setSendPort(port);
            server.sendData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendDataToClient(Server server, int sendPort, int receivingPort, String sendMessage, String waitingMessage) {
        try {
            BufferedReader bufferedReader;
            server.setSendPort(sendPort);
            server.setRecPort(receivingPort);

            System.out.println(sendMessage);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferedReader.readLine();
            System.out.println("Data is " + s);

            server.sendData();
            System.out.println(waitingMessage);
            server.recData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}