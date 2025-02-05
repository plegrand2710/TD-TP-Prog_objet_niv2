package Tp2;

import javax.swing.*;
import java.awt.*;

public class BoulesFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTabbedPane onglets;
    private JPanel menuGauche;
    private JRadioButton type1Button, type2Button;
    private ButtonGroup groupeTypeOnglet;

    public BoulesFrame() {
        setTitle("Boules Paramétrables avec Onglets");
        setSize(1300, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        onglets = new JTabbedPane();

        menuGauche = new JPanel();
        menuGauche.setLayout(new BoxLayout(menuGauche, BoxLayout.Y_AXIS));
        menuGauche.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBoutonsRadio = new JPanel();
        panelBoutonsRadio.setLayout(new BoxLayout(panelBoutonsRadio, BoxLayout.Y_AXIS));

        groupeTypeOnglet = new ButtonGroup();
        type1Button = new JRadioButton("Type 1", true);
        type2Button = new JRadioButton("Type 2");

        groupeTypeOnglet.add(type1Button);
        groupeTypeOnglet.add(type2Button);

        panelBoutonsRadio.add(type1Button);
        panelBoutonsRadio.add(type2Button);
        panelBoutonsRadio.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuGauche.add(panelBoutonsRadio);
        menuGauche.add(Box.createVerticalStrut(10)); 

        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
        panelBoutons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton ajouterOnglet = new JButton("Ajouter Onglet");
        ajouterOnglet.setAlignmentX(Component.LEFT_ALIGNMENT);
        ajouterOnglet.addActionListener(e -> ajouterNouvelOnglet(getTypeSelectionne()));

        JButton supprimerOnglet = new JButton("Supprimer Onglet");
        supprimerOnglet.setAlignmentX(Component.LEFT_ALIGNMENT);
        supprimerOnglet.addActionListener(e -> supprimerOngletActif());

        panelBoutons.add(ajouterOnglet);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBoutons.add(supprimerOnglet);

        menuGauche.add(panelBoutons);

        ajouterNouvelOnglet(getTypeSelectionne()); 

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuGauche, onglets);
        splitPane.setDividerLocation(150);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private int getTypeSelectionne() {
        return type1Button.isSelected() ? 1 : 2;
    }

    private void ajouterNouvelOnglet(int type) {
        if (type == 1) {
            BoulesModel model = new BoulesModel();
            PanneauBoules view = new PanneauBoules(model);
            BoulesController controller = new BoulesController(model, view);

            AnimationThread animationThread = new AnimationThread(controller, view);
            animationThread.start();

            JPanel panneauControle = new ControlPanel(controller);

            JSplitPane splitOnglet = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panneauControle, view);
            splitOnglet.setDividerLocation(300);
            splitOnglet.setOneTouchExpandable(true);

            String titreOnglet = "Onglet " + (onglets.getTabCount() + 1) + " (Type " + type + ")";
            onglets.addTab(titreOnglet, splitOnglet);
        } else if (type == 2) {
            PanneauType2 panneauType2 = new PanneauType2();

            String titreOnglet = "Onglet " + (onglets.getTabCount() + 1) + " (Type " + type + ")";
            onglets.addTab(titreOnglet, panneauType2);
        }
    }

    private void supprimerOngletActif() {
        int index = onglets.getSelectedIndex();
        if (index != -1) {
            onglets.remove(index);
        }
    }
}