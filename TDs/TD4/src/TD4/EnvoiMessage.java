package TD4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class EnvoiMessage {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage : java EnvoiMessage <adresseIP> <portDestinataire>");
            return;
        }
        
        DatagramSocket socketUDP = null;
        try {
            socketUDP = new DatagramSocket();
            System.out.println("Port local : " + socketUDP.getLocalPort());
            
            InetAddress adresseIP = InetAddress.getByName(args[0]);
            int portDestinataire = Integer.parseInt(args[1]);
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            
            while (true) {
                String ligne = entree.readLine();
                if (ligne == null) break; 
                
                byte[] tampon = ligne.getBytes();
                DatagramPacket message = new DatagramPacket(tampon, tampon.length, adresseIP, portDestinataire);
                socketUDP.send(message);
                
                byte[] tamponReception = new byte[256];
                DatagramPacket ack = new DatagramPacket(tamponReception, tamponReception.length);
                socketUDP.receive(ack);
                String ackMessage = new String(tamponReception, 0, ack.getLength());
                System.out.println("Du port " + ack.getPort() +
                                   " de la machine " + ack.getAddress().getHostName() +
                                   " : " + ackMessage);
            }
        } catch (UnknownHostException e) {
            System.out.println("Destinataire inconnu");
        } catch (SocketException e) {
            System.out.println("Problème d'ouverture de la socket");
        } catch (IOException e) {
            System.out.println("Problème lors de l'envoi ou de la réception du message");
        } catch (NumberFormatException e) {
            System.out.println("Le second argument doit être un entier");
        } finally {
            if (socketUDP != null) {
                socketUDP.close();
            }
        }
    }
}
