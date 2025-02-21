package TD4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatGUI extends JFrame {
    private JTextField tfPortLocal, tfIPDestinataire, tfPortDestinataire, tfMessage;
    private JButton btnConnecter, btnDeconnecter, btnEnvoyer, btnSauvegarder, btnNettoyer, btnNouveau, btnQuitter;
    private JTextArea taChat;
    
    private DatagramSocket socket;
    private InetAddress adresseDest;
    private int portDest;
    private Thread threadReception;
    private boolean enEcoute = false;
    
    public ChatGUI() {
        super("Chat UDP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        initialiserComposants();
    }
    
    private void initialiserComposants() {
        
    	JPanel panelConnexion = new JPanel();
    	panelConnexion.setLayout(new BoxLayout(panelConnexion, BoxLayout.X_AXIS));
    	panelConnexion.setBorder(BorderFactory.createTitledBorder("Paramètres de connexion"));

    	panelConnexion.add(new JLabel("Port local :"));
    	tfPortLocal = new JTextField(5);
    	panelConnexion.add(tfPortLocal);

    	panelConnexion.add(new JLabel("IP destinataire :"));
    	tfIPDestinataire = new JTextField(10);
    	panelConnexion.add(tfIPDestinataire);

    	panelConnexion.add(new JLabel("Port destinataire :"));
    	tfPortDestinataire = new JTextField(5);
    	panelConnexion.add(tfPortDestinataire);

    	btnConnecter = new JButton("Se connecter");
    	panelConnexion.add(btnConnecter);

    	btnDeconnecter = new JButton("Déconnexion");
    	btnDeconnecter.setEnabled(false);
    	panelConnexion.add(btnDeconnecter);

    	JPanel panelFonctions = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	btnSauvegarder = new JButton("Sauvegarder la discussion");
    	btnNettoyer = new JButton("Vider la discussion"); 
    	btnNouveau = new JButton("Nouveau");
    	btnQuitter = new JButton("Quitter");

    	panelFonctions.add(btnSauvegarder);
    	panelFonctions.add(btnNettoyer);
    	panelFonctions.add(btnNouveau);
    	panelFonctions.add(btnQuitter);

    	taChat = new JTextArea();
    	taChat.setEditable(false);
    	JScrollPane scrollChat = new JScrollPane(taChat);

    	JPanel panelMessage = new JPanel(new BorderLayout(5, 5));
    	tfMessage = new JTextField();
    	btnEnvoyer = new JButton("Envoyer");
    	btnEnvoyer.setEnabled(false);
    	panelMessage.add(tfMessage, BorderLayout.CENTER);
    	panelMessage.add(btnEnvoyer, BorderLayout.EAST);

    	JPanel centerPanel = new JPanel(new BorderLayout());
    	centerPanel.add(panelFonctions, BorderLayout.NORTH);
    	centerPanel.add(scrollChat, BorderLayout.CENTER);

    	getContentPane().setLayout(new BorderLayout(5, 5));
    	getContentPane().add(panelConnexion, BorderLayout.NORTH);
    	getContentPane().add(centerPanel, BorderLayout.CENTER);
    	getContentPane().add(panelMessage, BorderLayout.SOUTH);



        btnConnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connecter();
            }
        });
        
        btnDeconnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deconnecter();
            }
        });
        
        btnEnvoyer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                envoyerMessage();
            }
        });
        
        tfMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                envoyerMessage();
            }
        });
        
        btnSauvegarder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sauvegarderDiscussion();
            }
        });
        
        btnNettoyer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                taChat.setText("");
            }
        });
        
        btnNouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taChat.setText("");
                tfPortLocal.setText("");
                tfIPDestinataire.setText("");
                tfPortDestinataire.setText("");
                btnConnecter.setEnabled(true);
                btnDeconnecter.setEnabled(false);
                btnEnvoyer.setEnabled(false);
            }
        });

        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
    
    private void connecter() {
        try {
            int portLocal = Integer.parseInt(tfPortLocal.getText().trim());
            socket = new DatagramSocket(portLocal);
            adresseDest = InetAddress.getByName(tfIPDestinataire.getText().trim());
            portDest = Integer.parseInt(tfPortDestinataire.getText().trim());
            enEcoute = true;
            
            threadReception = new Thread(new Runnable() {
                public void run() {
                    recevoirMessages();
                }
            });
            threadReception.start();
            
            ajouterMessage("Connecté sur le port local " + portLocal);
            btnConnecter.setEnabled(false);
            btnDeconnecter.setEnabled(true);
            btnEnvoyer.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion : " + ex.getMessage());
        }
    }
    
    private void deconnecter() {
        enEcoute = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        btnConnecter.setEnabled(true);
        btnDeconnecter.setEnabled(false);
        btnEnvoyer.setEnabled(false);
        ajouterMessage("Déconnecté.");
    }
    
    private void envoyerMessage() {
        if (socket == null || socket.isClosed()) {
            JOptionPane.showMessageDialog(this, "Vous n'êtes pas connecté !");
            return;
        }
        String texte = tfMessage.getText().trim();
        if (texte.isEmpty()) {
            return;
        }
        try {
            byte[] donnees = texte.getBytes();
            DatagramPacket paquet = new DatagramPacket(donnees, donnees.length, adresseDest, portDest);
            socket.send(paquet);
            ajouterMessage("Moi : " + texte);
            tfMessage.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'envoi : " + ex.getMessage());
        }
    }
    
    private void recevoirMessages() {
        byte[] tampon = new byte[256];
        while (enEcoute) {
            DatagramPacket paquet = new DatagramPacket(tampon, tampon.length);
            try {
                socket.receive(paquet);
                String messageRecu = new String(paquet.getData(), 0, paquet.getLength());
                ajouterMessage(paquet.getAddress().getHostAddress() + " : " + messageRecu);
            } catch (IOException ex) {
                if (enEcoute) {
                    ajouterMessage("Erreur de réception : " + ex.getMessage());
                }
                break;
            }
        }
    }
    
    private void ajouterMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String temps = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
                taChat.append(temps + message + "\n");
            }
        });
    }
    
    private void sauvegarderDiscussion() {
        String contenu = taChat.getText();
        if (contenu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La discussion est vide.");
            return;
        }
        String nomFichier = "chat_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        try (PrintWriter sortie = new PrintWriter(new FileWriter(nomFichier))) {
            sortie.print(contenu);
            JOptionPane.showMessageDialog(this, "Discussion sauvegardée dans " + nomFichier);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI().setVisible(true);
            }
        });
    }
}
