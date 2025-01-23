package carnetAdresse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class CarnetAdresseUI extends JFrame {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private int currentIndex = 0;

    private JTextField numeroField, nomField, prenomField, telephoneField, adresseField, codePostalField, emailField, metierField, situationField;
    private JSlider slider;

    public CarnetAdresseUI() {
        setTitle("Carnet d'Adresses");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(9, 2));
        numeroField = createField(formPanel, "Numéro:");
        nomField = createField(formPanel, "Nom:");
        prenomField = createField(formPanel, "Prénom:");
        telephoneField = createField(formPanel, "Téléphone:");
        adresseField = createField(formPanel, "Adresse:");
        codePostalField = createField(formPanel, "Code Postal:");
        emailField = createField(formPanel, "Email:");
        metierField = createField(formPanel, "Métier:");
        situationField = createField(formPanel, "Situation:");

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        String[] buttonLabels = {"Début", "Préc.", "Suivant", "Fin", "Milieu", "Nouveau", "Supprimer", "Save"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonHandler(label));
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        // Slider pour navigation
        slider = new JSlider(JSlider.VERTICAL, 0, 0, 0);
        slider.addChangeListener(e -> {
            currentIndex = slider.getValue();
            afficherContact();
        });
        add(slider, BorderLayout.EAST);

        chargerContacts();
        if (!contacts.isEmpty()) afficherContact();
    }

    private JTextField createField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField field = new JTextField();
        panel.add(field);
        return field;
    }

    private void afficherContact() {
        if (contacts.isEmpty()) {
            clearFields();
            return;
        }
        Contact contact = contacts.get(currentIndex);
        numeroField.setText(String.valueOf(contact.getNumero()));
        nomField.setText(contact.getNom());
        prenomField.setText(contact.getPrenom());
        telephoneField.setText(contact.getTelephone());
        adresseField.setText(contact.getAdresse());
        codePostalField.setText(contact.getCodePostal());
        emailField.setText(contact.getEmail());
        metierField.setText(contact.getMetier());
        situationField.setText(contact.getSituation());
    }

    private void clearFields() {
        numeroField.setText("");
        nomField.setText("");
        prenomField.setText("");
        telephoneField.setText("");
        adresseField.setText("");
        codePostalField.setText("");
        emailField.setText("");
        metierField.setText("");
        situationField.setText("");
    }

    private void nouveauContact() {
        int numero = contacts.size() + 1;
        Contact contact = new Contact(numero, "", "", "", "", "", "", "", "");
        contacts.add(contact);
        currentIndex = contacts.size() - 1;
        slider.setMaximum(currentIndex);
        afficherContact();
    }

    private void supprimerContact() {
        if (!contacts.isEmpty()) {
            contacts.remove(currentIndex);
            if (currentIndex > 0) currentIndex--;
            slider.setMaximum(contacts.size() - 1);
            afficherContact();
        }
    }

    private void sauvegarderContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : contacts) {
                writer.println(contact.toFileString());
            }
            JOptionPane.showMessageDialog(this, "Contacts sauvegardés avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des contacts.");
        }
    }

    private void chargerContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                int numero = Integer.parseInt(parts[0]);
                contacts.add(new Contact(numero, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]));
            }
            slider.setMaximum(contacts.size() - 1);
        } catch (IOException e) {
            // Aucun fichier trouvé, aucun problème
        }
    }

    private class ButtonHandler implements ActionListener {
        private String action;

        public ButtonHandler(String action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case "Début":
                    currentIndex = 0;
                    break;
                case "Préc.":
                    if (currentIndex > 0) currentIndex--;
                    break;
                case "Suivant":
                    if (currentIndex < contacts.size() - 1) currentIndex++;
                    break;
                case "Fin":
                    currentIndex = contacts.size() - 1;
                    break;
                case "Milieu":
                    currentIndex = contacts.size() / 2;
                    break;
                case "Nouveau":
                    nouveauContact();
                    break;
                case "Supprimer":
                    supprimerContact();
                    break;
                case "Save":
                    sauvegarderContacts();
                    break;
            }
            slider.setValue(currentIndex);
            afficherContact();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarnetAdresseUI().setVisible(true));
    }
}

class Contact {
    private int numero;
    private String nom, prenom, telephone, adresse, codePostal, email, metier, situation;

    public Contact(int numero, String nom, String prenom, String telephone, String adresse, String codePostal, String email, String metier, String situation) {
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.email = email;
        this.metier = metier;
        this.situation = situation;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getEmail() {
        return email;
    }

    public String getMetier() {
        return metier;
    }

    public String getSituation() {
        return situation;
    }

    public String toFileString() {
        return numero + ";" + nom + ";" + prenom + ";" + telephone + ";" + adresse + ";" + codePostal + ";" + email + ";" + metier + ";" + situation;
    }
}
