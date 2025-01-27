package carnetAdresse;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class CarnetAdresses extends JFrame {
    private Annuaire annuaire;
    private JTextField[] fields;
    private JSlider slider;
    private int currentIndex;
    private int currentMiniatureIndex;
    private JLabel photoLabel; 
    private ImageIcon[] photos; 
    private JTextField numberField;     
    private JMenuItem menuNouv, menuSupp, menuSave;




    public CarnetAdresses() {
        annuaire = new Annuaire();
        fields = new JTextField[8];
        currentIndex = 0;
        currentMiniatureIndex = 0;

        setTitle("Carnet d'Adresses");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JMenu menuEdit = new JMenu("Edit");
        menuEdit.add(this.menuSave = new JMenuItem("Enregistrer"));
        menuEdit.add(this.menuSupp = new JMenuItem("Supprimer"));
        menuEdit.add(this.menuNouv = new JMenuItem("Nouveau"));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuEdit);

        setJMenuBar(menuBar);
        
        slider = new JSlider(JSlider.VERTICAL, 0, 0, 0);
        slider.setInverted(true); 
        slider.addChangeListener(e -> showContact(slider.getValue()));
        add(slider, BorderLayout.EAST);


        JPanel photoPanel = new JPanel();
        photos = new ImageIcon[7]; 
        for (int i = 0; i < 7; i++) {
            photos[i] = new ImageIcon("./images/Client" + (i + 1) + ".png");
        }

        photoLabel = new JLabel(photos[currentMiniatureIndex]);
        JButton prevImageBtn = new JButton("<");
        JButton nextImageBtn = new JButton(">");

        photoPanel.add(prevImageBtn);
        photoPanel.add(photoLabel);
        photoPanel.add(nextImageBtn);

        this.add(photoPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        
        formPanel.add(new JLabel("Numéro :"));
        numberField = new JTextField();
        numberField.setEditable(false);
        formPanel.add(numberField);

        String[] labels = {"Nom", "Prénom", "Téléphone", "Adresse", "Code Postal", "Email", "Métier", "Situation"};
        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField();
            formPanel.add(fields[i]);
        }
        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        JButton startBtn = new JButton("Début");
        JButton prevBtn = new JButton("Précédent");
        JButton nextBtn = new JButton("Suivant");
        JButton endBtn = new JButton("Fin");
        JButton midBtn = new JButton("Milieu");
        JButton nthBtn = new JButton("Nième");
        JButton newBtn = new JButton("Nouveau");
        JButton saveBtn = new JButton("Enregistrer");
        JButton deleteBtn = new JButton("Supprimer");
        JButton exitBtn = new JButton("Quitter");

        bottomPanel.add(startBtn);
        bottomPanel.add(prevBtn);
        bottomPanel.add(nextBtn);
        bottomPanel.add(endBtn);
        bottomPanel.add(midBtn);
        bottomPanel.add(nthBtn);
        bottomPanel.add(newBtn);
        bottomPanel.add(saveBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(exitBtn);

        add(bottomPanel, BorderLayout.SOUTH);
        
        startBtn.addActionListener(e -> {
            saveCurrentContact();
            if (annuaire.getTaille() > 0) {
                showContact(0); 
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à afficher.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        prevBtn.addActionListener(e -> {
            saveCurrentContact();
            if (currentIndex > 0) {
                showContact(currentIndex - 1);
            } else {
                JOptionPane.showMessageDialog(this, "Vous êtes déjà au premier contact.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nextBtn.addActionListener(e -> {
            saveCurrentContact();
            if (currentIndex < annuaire.getTaille() - 1) {
                showContact(currentIndex + 1); 
            } else {
                JOptionPane.showMessageDialog(this, "Vous êtes déjà au dernier contact.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        endBtn.addActionListener(e -> {
            saveCurrentContact();
            if (annuaire.getTaille() > 0) {
                showContact(annuaire.getTaille() - 1); 
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à afficher.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        midBtn.addActionListener(e -> {
            saveCurrentContact();
            if (annuaire.getTaille() > 0) {
                showContact(annuaire.getTaille() / 2); 
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à afficher.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nthBtn.addActionListener(e -> {
            saveCurrentContact();
            goToNthContact();
        });


        newBtn.addActionListener(e -> {
            saveCurrentContact();
            createNewContact();
        });

        saveBtn.addActionListener(e -> {
            saveContact();
        });

        deleteBtn.addActionListener(e -> {
            saveCurrentContact();
            deleteContact();
        });

        exitBtn.addActionListener(e -> {
        	saveCurrentContact();
        	System.exit(0);
        });

        prevImageBtn.addActionListener(e -> changeMiniature(-1));
        nextImageBtn.addActionListener(e -> changeMiniature(1));

        menuSave.addActionListener(e -> {
            saveContact();
            JOptionPane.showMessageDialog(this, "Contact sauvegardé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        });

        menuSupp.addActionListener(e -> {
            saveCurrentContact();
            deleteContact();
        });

        menuNouv.addActionListener(e -> {
            saveCurrentContact();
            createNewContact();
        });

        
        try {
            annuaire.charger("contacts.txt");
            updateSlider();

            if (annuaire.getTaille() > 0) {
                currentIndex = 0; 
                showContact(currentIndex);
            } else {
                clearForm();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des contacts.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
	        
    }


    private void showContact(int index) {
        if (index >= 0 && index < annuaire.getTaille()) {
            currentIndex = index;
            Contact c = annuaire.getContact(index);
            numberField.setText(String.valueOf(index + 1));
            fields[0].setText(c.get_nom());
            fields[1].setText(c.get_prenom());
            fields[2].setText(c.get_tel());
            fields[3].setText(c.get_adresse());
            fields[4].setText(c.get_cp());
            fields[5].setText(c.get_email());
            fields[6].setText(c.get_metier());
            fields[7].setText(c.get_situation());
            currentMiniatureIndex = c.get_miniature();
            updateMiniature();
            slider.setValue(index);

        } else {
            clearForm(); 
            numberField.setText("");

        }
    }

    
    private void updateAnnuaireFromForm() {
        if (currentIndex >= 0 && currentIndex < annuaire.getTaille()) {
            Contact currentContact = annuaire.getContact(currentIndex);
            currentContact.set_nom(fields[0].getText().trim());
            currentContact.set_prenom(fields[1].getText().trim());
            currentContact.set_tel(fields[2].getText().trim());
            currentContact.set_adresse(fields[3].getText().trim());
            currentContact.set_cp(fields[4].getText().trim());
            currentContact.set_email(fields[5].getText().trim());
            currentContact.set_metier(fields[6].getText().trim());
            currentContact.set_situation(fields[7].getText().trim());
            currentContact.set_miniature(currentMiniatureIndex);
        }
    }

    
    private void clearForm() {
        for (JTextField field : fields) {
            field.setText("");
        }
        currentMiniatureIndex = 0;
        updateMiniature();
    }
 

    private void saveContact() {
        try {
            updateAnnuaireFromForm();

            annuaire.sauvegarder("contacts.txt");
            JOptionPane.showMessageDialog(this, "Contacts sauvegardés avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des contacts.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void deleteContact() {
        if (annuaire.getTaille() > 0) {
            annuaire.supprimer(currentIndex);
            try {
                annuaire.sauvegarder("contacts.txt");
                updateSlider();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (currentIndex > 0) {
                currentIndex--;
            }
            showContact(currentIndex);
        }
    }

    private void goToNthContact() {
        String input = JOptionPane.showInputDialog(this, "Entrez le numéro du contact (1-" + annuaire.getTaille() + "):");
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < annuaire.getTaille()) {
                showContact(index);
            } else {
                JOptionPane.showMessageDialog(this, "Index hors des limites. Veuillez entrer un nombre entre 1 et " + annuaire.getTaille() + ".", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrée invalide. Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void changeMiniature(int direction) {
        currentMiniatureIndex += direction;
        if (currentMiniatureIndex < 0) {
            currentMiniatureIndex = photos.length - 1; 
        } else if (currentMiniatureIndex >= photos.length) {
            currentMiniatureIndex = 0; 
        }
        updateMiniature();
    }

    private void updateMiniature() {
        photoLabel.setIcon(photos[currentMiniatureIndex]); 
    }

    private void updateSlider() {
        slider.setMaximum(annuaire.getTaille() - 1);
        slider.setValue(currentIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarnetAdresses().setVisible(true));
    }


    private void createNewContact() {
        Contact newContact = new Contact("", "", "", "", "", "", "", "", 0);

        annuaire.ajouter(newContact);

        currentIndex = annuaire.getTaille() - 1;
        System.out.println("Nouveau contact créé à l'index : " + currentIndex);

        for (ChangeListener listener : slider.getChangeListeners()) {
            slider.removeChangeListener(listener);
        }

        slider.setMaximum(annuaire.getTaille() - 1); 
        slider.setValue(currentIndex); 

        slider.addChangeListener(e -> showContact(slider.getValue()));

        showContact(currentIndex);
        System.out.println("Slider mis à jour sur l'index : " + slider.getValue());
    }




    private void saveCurrentContact() {
        if (currentIndex >= 0 && currentIndex < annuaire.getTaille()) {
            Contact currentContact = annuaire.getContact(currentIndex);

            String nom = fields[0].getText().trim();
            String prenom = fields[1].getText().trim();

            currentContact.set_nom(nom);
            currentContact.set_prenom(prenom);
            currentContact.set_tel(fields[2].getText().trim());
            currentContact.set_adresse(fields[3].getText().trim());
            currentContact.set_cp(fields[4].getText().trim());
            currentContact.set_email(fields[5].getText().trim());
            currentContact.set_metier(fields[6].getText().trim());
            currentContact.set_situation(fields[7].getText().trim());
            currentContact.set_miniature(currentMiniatureIndex);

            try {
                annuaire.sauvegarder("contacts.txt");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
