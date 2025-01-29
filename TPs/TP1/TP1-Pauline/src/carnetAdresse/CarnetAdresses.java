package carnetAdresse;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CarnetAdresses extends JFrame {
    private Annuaire annuaire;
    private JTabbedPane tabbedPane;
    private int currentIndex;
    private ImageIcon[] photos;
    private int currentMiniatureIndex;
    private JSlider slider;
    private JTextField[] fields;
    private JTextField numberField;     
    private JLabel photoLabel; 


    public CarnetAdresses() {
        annuaire = new Annuaire();
        currentIndex = 0;
        photos = new ImageIcon[7];
        currentMiniatureIndex = 0;
        fields = new JTextField[8];

        for (int i = 0; i < photos.length; i++) {
            photos[i] = new ImageIcon("./images/Client" + (i + 1) + ".png");
        }

        setTitle("Carnet d'Adresses avec Onglets");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Ajouter Onglet");
        JButton removeButton = new JButton("Supprimer Onglet");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            Contact newContact = new Contact("Nouveau", "Contact", "", "", "", "", "", "", 0);
            annuaire.ajouter(newContact);
            createTab(newContact, annuaire.getTaille() - 1);
        });

        removeButton.addActionListener(e -> {
            if (tabbedPane.getTabCount() > 0) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                annuaire.supprimer(selectedIndex);
                tabbedPane.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Aucun onglet à supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        try {
            annuaire.charger("contacts.txt");
            for (int i = 0; i < annuaire.getTaille(); i++) {
                createTab(annuaire.getContact(i), i);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des contacts.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    private void createTab(Contact contact, int index) {
    	
    	slider = new JSlider(JSlider.VERTICAL, 0, Math.max(0, annuaire.getTaille() - 1), 0);
    	slider.setInverted(true);
    	slider.setMajorTickSpacing(1);
    	slider.setPaintTicks(true);
    	slider.setSnapToTicks(true);

        add(slider, BorderLayout.EAST);
    	
    	JPanel tabPanel = new JPanel(new BorderLayout());

        JPanel photoPanel = new JPanel();
        JLabel photoLabel = new JLabel(photos[contact.get_miniature()]);
        JButton prevImageBtn = new JButton("<");
        JButton nextImageBtn = new JButton(">");

        prevImageBtn.addActionListener(e -> {
            changeMiniature(-1, contact, photoLabel);
        });

        nextImageBtn.addActionListener(e -> {
            changeMiniature(1, contact, photoLabel);
        });

        photoPanel.add(prevImageBtn);
        photoPanel.add(photoLabel);
        photoPanel.add(nextImageBtn);

        tabPanel.add(photoPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        formPanel.add(new JLabel("Numéro :"));
        JTextField tabNumberField = new JTextField(String.valueOf(index + 1));
        tabNumberField.setEditable(false);
        formPanel.add(tabNumberField);

        String[] labels = {"Nom", "Prénom", "Téléphone", "Adresse", "Code Postal", "Email", "Métier", "Situation"};
        JTextField[] tabFields = new JTextField[8];

        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i] + ":"));
            tabFields[i] = new JTextField();
            formPanel.add(tabFields[i]);
        }

        tabFields[0].setText(contact.get_nom());
        tabFields[1].setText(contact.get_prenom());
        tabFields[2].setText(contact.get_tel());
        tabFields[3].setText(contact.get_adresse());
        tabFields[4].setText(contact.get_cp());
        tabFields[5].setText(contact.get_email());
        tabFields[6].setText(contact.get_metier());
        tabFields[7].setText(contact.get_situation());

        tabPanel.add(formPanel, BorderLayout.CENTER);
                
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

        tabbedPane.addTab("Contact " + (index + 1), photos[contact.get_miniature()], tabPanel);

        startBtn.addActionListener(e -> {
            if (tabbedPane.getTabCount() > 0) {
                tabbedPane.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à afficher.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        prevBtn.addActionListener(e -> {
            int currentTab = tabbedPane.getSelectedIndex();
            if (currentTab > 0) {
                tabbedPane.setSelectedIndex(currentTab - 1); 
            } else {
                JOptionPane.showMessageDialog(this, "Vous êtes déjà au premier contact.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nextBtn.addActionListener(e -> {
            int currentTab = tabbedPane.getSelectedIndex();
            if (currentTab < tabbedPane.getTabCount() - 1) {
                tabbedPane.setSelectedIndex(currentTab + 1); 
            } else {
                JOptionPane.showMessageDialog(this, "Vous êtes déjà au dernier contact.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        endBtn.addActionListener(e -> {
            if (tabbedPane.getTabCount() > 0) {
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1); 
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à afficher.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        midBtn.addActionListener(e -> {
            if (tabbedPane.getTabCount() > 0) {
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount() / 2); 
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à afficher.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nthBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Entrez le numéro du contact (1-" + tabbedPane.getTabCount() + "):");
            try {
                int i = Integer.parseInt(input) - 1;
                if (i >= 0 && i < tabbedPane.getTabCount()) {
                    tabbedPane.setSelectedIndex(i); 
                } else {
                    JOptionPane.showMessageDialog(this, "Index hors des limites. Veuillez entrer un nombre entre 1 et " + tabbedPane.getTabCount() + ".", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Entrée invalide. Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        newBtn.addActionListener(e -> {
            Contact newContact = new Contact("Nouveau", "Contact", "", "", "", "", "", "", 0);
            annuaire.ajouter(newContact);
            createTab(newContact, annuaire.getTaille() - 1);
            tabbedPane.setSelectedIndex(annuaire.getTaille() - 1);
        });

        saveBtn.addActionListener(e -> {
            saveContact();
        });

        deleteBtn.addActionListener(e -> {
            int currentTab = tabbedPane.getSelectedIndex();
            if (currentTab >= 0 && tabbedPane.getTabCount() > 0) {
                deleteContact();
                tabbedPane.remove(currentTab);
                if (tabbedPane.getTabCount() > 0) {
                    tabbedPane.setSelectedIndex(Math.max(0, currentTab - 1));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Aucun contact à supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        tabbedPane.addChangeListener(e -> {
        	saveContact();
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < slider.getMaximum() + 1) {
                slider.setValue(selectedIndex); 
            }
        });

        slider.addChangeListener(e -> {
            int sliderValue = slider.getValue();
            if (sliderValue >= 0 && sliderValue < tabbedPane.getTabCount() && slider.getValueIsAdjusting()) {
                tabbedPane.setSelectedIndex(sliderValue); 
            }
        });

    }


    private void changeMiniature(int direction, Contact contact, JLabel photoLabel) {
        currentMiniatureIndex = contact.get_miniature();
        currentMiniatureIndex += direction;

        if (currentMiniatureIndex < 0) {
            currentMiniatureIndex = photos.length - 1;
        } else if (currentMiniatureIndex >= photos.length) {
            currentMiniatureIndex = 0;
        }

        contact.set_miniature(currentMiniatureIndex);
        photoLabel.setIcon(photos[currentMiniatureIndex]);

        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            tabbedPane.setIconAt(selectedIndex, photos[currentMiniatureIndex]);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarnetAdresses::new);
    }

   
    private void updateAnnuaireFromForm() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < annuaire.getTaille()) {
            Contact contact = annuaire.getContact(selectedIndex);

            Component tabComponent = tabbedPane.getComponentAt(selectedIndex);
            if (tabComponent instanceof JPanel) {
                JPanel tabPanel = (JPanel) tabComponent;
                Component[] components = tabPanel.getComponents();

                for (Component component : components) {
                    if (component instanceof JPanel) { 
                        JPanel formPanel = (JPanel) component;
                        Component[] formComponents = formPanel.getComponents();
                        int fieldIndex = 0;
                        boolean firstTextFieldSkipped = false; 

                        for (Component formComponent : formComponents) {
                            if (formComponent instanceof JTextField) {
                                if (!firstTextFieldSkipped) { 
                                    firstTextFieldSkipped = true;
                                    continue;
                                }
                                
                                String text = ((JTextField) formComponent).getText().trim();
                                switch (fieldIndex) {
                                    case 0 -> contact.set_nom(text);
                                    case 1 -> contact.set_prenom(text);
                                    case 2 -> contact.set_tel(text);
                                    case 3 -> contact.set_adresse(text);
                                    case 4 -> contact.set_cp(text);
                                    case 5 -> contact.set_email(text);
                                    case 6 -> contact.set_metier(text);
                                    case 7 -> contact.set_situation(text);
                                }
                                fieldIndex++;
                                if (fieldIndex >= 8) break; 
                            }
                        }
                    }
                }
            }
        }
    }

   

    private void saveContact() {
        try {
            updateAnnuaireFromForm();
            annuaire.sauvegarder("contacts.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des contacts.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deleteContact() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        
        if (selectedIndex >= 0 && annuaire.getTaille() > 0) {
            annuaire.supprimer(selectedIndex);

            try {
                annuaire.sauvegarder("contacts.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des contacts.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
