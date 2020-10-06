package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class AtomicMulticast {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Uso: AtomicMulticast <grupo> <porta> <nickname>");
            return;
        }

        int porta = Integer.parseInt(args[1]);
        MulticastSocket socket = new MulticastSocket(porta);
        InetAddress grupo = InetAddress.getByName(args[0]);
        socket.joinGroup(grupo);
        String nick = args[2];

        Scanner scanner = new Scanner(System.in);
        long last_time = 0;

        while (true) {
            try {
                byte[] entrada = new byte[1024];
                DatagramPacket pacote = new DatagramPacket(entrada,entrada.length);
                socket.setSoTimeout(100);
                socket.receive(pacote);
                String recebido = new String(pacote.getData(),0,pacote.getLength());

                String vars[] = recebido.split("\\s");
                try {
                    if (vars[0].equals(Long.toString(last_time))) {
                        System.out.println("old: " + recebido);
                    } else {
                        System.out.println("new: " + recebido);
                        last_time = Long.parseLong(vars[0]);

                        byte[] saida = new byte[1024];
                        saida = recebido.getBytes();
                        DatagramPacket pacote2 = new DatagramPacket(saida, saida.length, grupo, porta);
                        socket.send(pacote2);

                        if ("fim".equals(vars[2]))
                            break;
                    }

                } catch(ArrayIndexOutOfBoundsException e) {
                }

            } catch (IOException e){
            }

            if (System.in.available() > 0) {
                String mens = scanner.nextLine();
                long time = System.currentTimeMillis();
                byte[] saida = new byte[1024];
                saida = (Long.toString(time) + " " + nick + " " + mens).getBytes();
                DatagramPacket pacote = new DatagramPacket(saida, saida.length, grupo, porta);
                socket.send(pacote);
            }
        }

        socket.leaveGroup(grupo);
        socket.close();
    }
}
