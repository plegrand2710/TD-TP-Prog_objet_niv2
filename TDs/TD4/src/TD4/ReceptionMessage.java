package TD4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ReceptionMessage {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : java ReceptionMessage <portLocal>");
            return;
        }
        
        DatagramSocket socketUDP = null;
        try {
            int portLocal = Integer.parseInt(args[0]);
            socketUDP = new DatagramSocket(portLocal);
            byte[] tamponAccuse = "accuse de reception".getBytes();
            
            while (true) {
                byte[] tampon = new byte[256];
                DatagramPacket message = new DatagramPacket(tampon, tampon.length);
                socketUDP.receive(message);
                
                InetAddress adresseIP = message.getAddress();
                int portDistant = message.getPort();
                String texte = new String(message.getData(), 0, message.getLength());
                System.out.println("Réception du port " + portDistant +
                                   " de la machine " + adresseIP.getHostName() +
                                   " : " + texte);
                
                DatagramPacket ack = new DatagramPacket(tamponAccuse, tamponAccuse.length, adresseIP, portDistant);
                socketUDP.send(ack);
            }
        } catch (SocketException e) {
            System.out.println("Problème d'ouverture du socket");
        } catch (IOException e) {
            System.out.println("Problème lors de la réception ou de l'envoi du message");
        } catch (NumberFormatException e) {
            System.out.println("Le port doit être un entier");
        } finally {
            if (socketUDP != null) {
                socketUDP.close();
            }
        }
    }
}
