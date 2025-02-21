package TD4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatEnvoyerRecevoir {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage : java ChatEnvoyerRecevoir <portLocal> <adresseIP_Destinataire> <portDestinataire>");
            return;
        }
        
        int portLocal;
        int portDestinataire;
        String adresseIPStr = args[1];
        DatagramSocket socket = null;
        
        try {
            portLocal = Integer.parseInt(args[0]);
            portDestinataire = Integer.parseInt(args[2]);
            socket = new DatagramSocket(portLocal);
            System.out.println("Socket ouverte sur le port local " + portLocal);
            
            Thread threadReception = new Thread(new Reception(socket));
            threadReception.start();
            
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            InetAddress adresseIP = InetAddress.getByName(adresseIPStr);
            System.out.println("Prêt à envoyer des messages à " + adresseIP.getHostAddress() + " sur le port " + portDestinataire);
            
            while (true) {
                String message = entree.readLine();
                if (message == null || message.equalsIgnoreCase("exit")) {
                    break;
                }
                byte[] donnees = message.getBytes();
                DatagramPacket paquet = new DatagramPacket(donnees, donnees.length, adresseIP, portDestinataire);
                socket.send(paquet);
            }
        } catch (NumberFormatException e) {
            System.out.println("Les ports doivent être des nombres entiers.");
        } catch (UnknownHostException e) {
            System.out.println("L'adresse IP du destinataire est invalide.");
        } catch (SocketException e) {
            System.out.println("Problème lors de l'ouverture de la socket : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi ou de la réception du message : " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Socket fermée.");
        }
    }
}

class Reception implements Runnable {
    private DatagramSocket socket;
    
    public Reception(DatagramSocket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        byte[] tampon = new byte[256];
        while (true) {
            DatagramPacket paquet = new DatagramPacket(tampon, tampon.length);
            try {
                socket.receive(paquet);
                String messageRecu = new String(paquet.getData(), 0, paquet.getLength());
                System.out.println("Message reçu de " + paquet.getAddress().getHostName() +
                                   " (port " + paquet.getPort() + ") : " + messageRecu);
            } catch (IOException e) {
                System.out.println("Erreur lors de la réception : " + e.getMessage());
                break;
            }
        }
    }
}
