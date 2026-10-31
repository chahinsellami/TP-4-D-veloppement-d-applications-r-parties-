package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress server = InetAddress.getByName("localhost");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nom : ");
            String username = scanner.nextLine();

            Thread receivingThread = new Thread(() -> {
                try {
                    while (true) {
                        byte[] buffer = new byte[1024];
                        DatagramPacket packet =
                                new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String message =
                                new String(packet.getData(), 0, packet.getLength());
                        System.out.println("\n" + message);
                        System.out.print("Message : ");
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        System.out.println("Erreur de reception : " + e.getMessage());
                    }
                }
            }, "receiving-thread");

            Thread sendingThread = new Thread(() -> {
                try {
                    while (true) {
                        System.out.print("Message : ");
                        String text = scanner.nextLine();
                        if (text.equalsIgnoreCase("exit")) {
                            socket.close();
                            break;
                        }

                        String message = "[" + username + "] : " + text;
                        byte[] data = message.getBytes();
                        DatagramPacket packet =
                                new DatagramPacket(data, data.length, server, 1234);
                        socket.send(packet);
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        System.out.println("Erreur d'envoi : " + e.getMessage());
                    }
                }
            }, "sending-thread");

            receivingThread.start();
            sendingThread.start();

            sendingThread.join();
            receivingThread.join();
            scanner.close();
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrompu.");
        }
    }
}
