package carnetAdresse;

import javax.swing.*;
import java.awt.*;

public class ContactGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Edit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

     // Panel pour l'image et les boutons de navigation
        JPanel imagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Création des boutons flèches
        JButton leftButton = new JButton("<");
        leftButton.setPreferredSize(new Dimension(30, 10)); // Taille personnalisée pour le bouton gauche
        JButton rightButton = new JButton(">");
        rightButton.setPreferredSize(new Dimension(30, 10)); // Taille personnalisée pour le bouton droit

        // Chargement de l'image
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./images/Client1.png"); // Remplacez par le chemin de votre image
        imageLabel.setIcon(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Ajout des composants au panel (division en 3 colonnes)

        // Bouton gauche
        gbc.gridx = 0; // Première colonne
        gbc.gridy = 0;
        gbc.weightx = 0.02; // Occupe 20 % de l'espace horizontal
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        imagePanel.add(leftButton, gbc);

        // Image au centre
        gbc.gridx = 1; // Deuxième colonne
        gbc.gridy = 0;
        gbc.weightx = 0.6; // Occupe 60 % de l'espace horizontal
        gbc.fill = GridBagConstraints.BOTH;
        imagePanel.add(imageLabel, gbc);

        // Bouton droit
        gbc.gridx = 2; // Troisième colonne
        gbc.gridy = 0;
        gbc.weightx = 0.02; // Occupe 20 % de l'espace horizontal
        gbc.fill = GridBagConstraints.BOTH;
        imagePanel.add(rightButton, gbc);

        // Ajout du panel principal à la fenêtre
        frame.add(imagePanel, BorderLayout.NORTH);

        frame.add(imagePanel, BorderLayout.NORTH);

        // Panel pour les champs de formulaire avec barre de défilement
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcs = new GridBagConstraints();
        gbcs.insets = new Insets(5, 5, 5, 5);
        gbcs.fill = GridBagConstraints.HORIZONTAL;

        // Ajout des étiquettes et champs de texte
        String[] labels = {
            "Numéro", "Nom", "Prénom", "Téléphone", "Adresse",
            "Code Postal", "Email", "Métier", "Situation"
        };
        String[] values = {
            "2", "Einstein", "Franck", "+4523568", "???",
            "98846", "e=mc^2@hotmail", "physicien", "décédé"
        };

        for (int i = 0; i < labels.length; i++) {
            gbcs.gridx = 0;
            gbcs.gridy = i;
            gbcs.weightx = 0.3;
            formPanel.add(new JLabel(labels[i]), gbcs);

            gbcs.gridx = 1;
            gbcs.weightx = 0.7;
            JTextField textField = new JTextField(values[i]);
            textField.setColumns(20); // Largeur des champs ajustée
            formPanel.add(textField, gbcs);
        }

        JScrollPane scrollPane = new JScrollPane(formPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel pour les boutons de navigation en bas
        JPanel buttonPanel = new JPanel(new GridLayout(2, 7, 5, 5));
        String[] buttonLabels = {"Début", "Suivant", "Préc.", "Fin", "Milieu", "Nouv...", "Save"};
        for (String label : buttonLabels) {
            buttonPanel.add(new JButton(label));
        }
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}