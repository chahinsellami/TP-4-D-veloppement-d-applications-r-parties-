package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.io.IOException;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(null);
            socket.bind(new InetSocketAddress(1234));
            ArrayList<SocketAddress> clients = new ArrayList<>();

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                SocketAddress sender = packet.getSocketAddress();

                if (!clients.contains(sender)) {
                    clients.add(sender);
                }

                System.out.println("Message de " + sender + " : " + message);

                for (SocketAddress client : clients) {
                    if (!client.equals(sender)) {
                        byte[] data = message.getBytes();
                        DatagramPacket answer =
                                new DatagramPacket(data, data.length, client);
                        socket.send(answer);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
