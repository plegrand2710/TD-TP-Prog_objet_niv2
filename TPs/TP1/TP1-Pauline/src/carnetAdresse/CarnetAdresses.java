package carnetAdresse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class CarnetAdresses extends JFrame {
    private Annuaire annuaire;
    private JTabbedPane tabbedPane;
    private int currentIndex;
    private ImageIcon[] photos;
    private int currentMiniatureIndex;
    private JMenuItem menuNouv, menuSupp, menuSave;
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
    	
        slider = new JSlider(JSlider.VERTICAL, 0, 0, 0);
        slider.setInverted(true); 
        slider.addChangeListener(e -> showContact(slider.getValue()));
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
        
        JPanel customTab = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Marges personnalisées
        JLabel tabLabel = new JLabel("Contact " + (index + 1), photos[contact.get_miniature()], JLabel.LEFT);
        tabLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tabLabel.setIconTextGap(10); // Espace entre l'icône et le texte
        customTab.add(tabLabel);
        customTab.setOpaque(false);

        // Ajouter l'onglet avec le composant personnalisé
        tabbedPane.addTab(null, tabPanel);
        tabbedPane.setTabComponentAt(index, customTab);
        
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

        

        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == index) {
                contact.set_nom(tabFields[0].getText());
                contact.set_prenom(tabFields[1].getText());
                contact.set_tel(tabFields[2].getText());
                contact.set_adresse(tabFields[3].getText());
                contact.set_cp(tabFields[4].getText());
                contact.set_email(tabFields[5].getText());
                contact.set_metier(tabFields[6].getText());
                contact.set_situation(tabFields[7].getText());
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
    
    private void updateMiniature() {
        photoLabel.setIcon(photos[currentMiniatureIndex]); 
    }
    
    private void clearForm() {
        for (JTextField field : fields) {
            field.setText("");
        }
        currentMiniatureIndex = 0;
        updateMiniature();
    }
 

}
