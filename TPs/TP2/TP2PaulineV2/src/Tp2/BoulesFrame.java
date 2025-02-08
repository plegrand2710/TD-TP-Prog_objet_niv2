package Tp2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoulesFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTabbedPane onglets;
    private JPanel menuGauche;
    private JRadioButton type1Button, type2Button;
    private ButtonGroup groupeTypeOnglet;
    
    private JLabel infoLabel;      
    private JTable positionsTable;  
    private BoulesTableModel tableModel;
    
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

        tableModel = new BoulesTableModel(new ArrayList<>());
        positionsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(positionsTable);
        scrollPane.setPreferredSize(new Dimension(400, 0)); 
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(scrollPane, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoLabel = new JLabel("Infos...");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        southPanel.add(infoLabel);
        add(southPanel, BorderLayout.SOUTH);

        new Timer(500, e -> updateBottomInfo()).start();

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
            animationThread.setName("Thread Type 1 - Onglet " + (onglets.getTabCount() + 1));
            animationThread.start();
            JPanel panneauControle = new ControlPanel(controller);
            JSplitPane splitOnglet = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panneauControle, view);
            splitOnglet.setDividerLocation(300);
            splitOnglet.setOneTouchExpandable(true);
            splitOnglet.putClientProperty("controller", controller);
            splitOnglet.putClientProperty("thread", animationThread);
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

   
    private void updateBottomInfo() {
        int ongletCount = onglets.getTabCount();
        StringBuilder sb = new StringBuilder();
        sb.append("Onglets ouverts : ").append(ongletCount).append(" | ");

        BoulesController activeController = null;
        String threadName = "N/A";
        int nbBoules = 0;

        Component comp = onglets.getSelectedComponent();
        if (comp != null) {
            if (comp instanceof JSplitPane) {
                JSplitPane split = (JSplitPane) comp;
                activeController = (BoulesController) split.getClientProperty("controller");
                AnimationThread t = (AnimationThread) split.getClientProperty("thread");
                if (t != null) {
                    threadName = t.getName();
                }
            }
            else if (comp instanceof PanneauType2) {
                PanneauType2 pt2 = (PanneauType2) comp;
                activeController = pt2.getSelectedController();
                AnimationThread t = pt2.getSelectedThread();
                if (t != null) {
                    threadName = t.getName();
                }
            }
        }

        if (activeController != null) {
            nbBoules = activeController.getNombreBoules();
            sb.append("Thread actif : ").append(threadName)
              .append(" | Nombre de boules : ").append(nbBoules);
            tableModel.setBoules(activeController.getModel().getBoules());
        } else {
            sb.append("Thread actif : N/A | Nombre de boules : 0");
            tableModel.setBoules(new ArrayList<>());
        }
        infoLabel.setText(sb.toString());
    }
}