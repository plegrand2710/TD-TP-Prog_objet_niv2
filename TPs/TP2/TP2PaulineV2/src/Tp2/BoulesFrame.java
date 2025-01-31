package Tp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoulesFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTabbedPane onglets;
    private JPanel menuGauche;

    public BoulesFrame() {
        setTitle("Boules Paramétrables avec Onglets");
        setSize(900, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        onglets = new JTabbedPane();

        menuGauche = new JPanel();
        menuGauche.setLayout(new BoxLayout(menuGauche, BoxLayout.Y_AXIS));
        menuGauche.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton ajouterOnglet = new JButton("Ajouter Onglet");
        ajouterOnglet.setAlignmentX(Component.CENTER_ALIGNMENT);
        ajouterOnglet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterNouvelOnglet();
            }
        });

        JButton supprimerOnglet = new JButton("Supprimer Onglet");
        supprimerOnglet.setAlignmentX(Component.CENTER_ALIGNMENT);
        supprimerOnglet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerOngletActif();
            }
        });

        menuGauche.add(ajouterOnglet);
        menuGauche.add(Box.createRigidArea(new Dimension(0, 10)));
        menuGauche.add(supprimerOnglet);

        ajouterNouvelOnglet();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuGauche, onglets);
        splitPane.setDividerLocation(150); 
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void ajouterNouvelOnglet() {
        BoulesModel model = new BoulesModel();
        PanneauBoules view = new PanneauBoules(model);
        BoulesController controller = new BoulesController(model, view);
        
        AnimationThread animationThread = new AnimationThread(controller, view);
        animationThread.start();

        JPanel panneauControle = new ControlPanel(controller);

        JPanel ongletPanel = new JPanel(new BorderLayout());
        ongletPanel.add(panneauControle, BorderLayout.WEST);
        ongletPanel.add(view, BorderLayout.CENTER);

        onglets.addTab("Onglet " + (onglets.getTabCount() + 1), ongletPanel);
    }

    
    private void supprimerOngletActif() {
        int index = onglets.getSelectedIndex();
        if (index != -1) {
            onglets.remove(index);
        }
    }
}





