package Tp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class PanneauType2 extends JPanel {
    private JLabel labelParametres;
    private HashMap<JPanel, JRadioButton> caseToRadioButtonMap = new HashMap<>();
    private JRadioButton[] boutonsRadio;

    public PanneauType2() {
        setLayout(new BorderLayout()); 
        JPanel sousCase3A = creerPanelAvecTexte("Sous-case 3A", "Vous avez cliqué sur Sous-case 3A");
        JPanel sousCase3B = creerPanelAvecTexte("Sous-case 3B", "Vous avez cliqué sur Sous-case 3B");

        caseToRadioButtonMap.put(sousCase3A, null);
        caseToRadioButtonMap.put(sousCase3B, null);

        JSplitPane splitCase1 = creerSousCases("Sous-case 1A", "Sous-case 1B");

        BoulesModel model = new BoulesModel();
        PanneauBoules view = new PanneauBoules(model);
        BoulesController controller = new BoulesController(model, view);

        JSplitPane splitCase2 = creerTroisSousCasesAvecSubdivisionsVerticales(
                "Sous-case 2A", "Sous-case 2B", "Sous-case 2C", controller);
        splitCase2.setResizeWeight(0.6); 

        JSplitPane splitCase3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sousCase3A, sousCase3B);
        splitCase3.setDividerSize(5);
        splitCase3.setDividerLocation(350);
        splitCase3.setOneTouchExpandable(true);

        JSplitPane splitPaneVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitCase1, splitCase2);
        splitPaneVertical.setOneTouchExpandable(true);
        splitPaneVertical.setDividerLocation(120); 
        splitPaneVertical.setResizeWeight(0.05);  	

        JSplitPane splitPaneFinal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneVertical, splitCase3);
        splitPaneFinal.setOneTouchExpandable(true);
        splitPaneFinal.setDividerLocation(650);
        splitPaneFinal.setResizeWeight(0.95);   

        add(splitPaneFinal, BorderLayout.CENTER); 
    }
    
    private JSplitPane creerSousCases(String nomSousCase1, String nomSousCase2) {
        JPanel sousCase1 = creerPanelAvecTexte(nomSousCase1, "Vous avez cliqué sur " + nomSousCase1);

        JPanel sousCase2 = creerPanelAvecTexte(nomSousCase2, "Vous avez cliqué sur " + nomSousCase2);

        JSplitPane splitSousCases = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sousCase1, sousCase2);
        splitSousCases.setDividerSize(5);
        splitSousCases.setDividerLocation(350); 
        splitSousCases.setOneTouchExpandable(true);
        return splitSousCases;
    }
    
    private JSplitPane creerTroisSousCasesAvecSubdivisionsVerticales(
            String nomSousCase1, String nomSousCase2, String nomSousCase3, BoulesController controller) {

        JSplitPane sousCase2A = creerSousCasesVerticales(nomSousCase1 + "A", nomSousCase1 + "B");
        JSplitPane sousCase2C = creerSousCasesVerticales(nomSousCase3 + "A", nomSousCase3 + "B");

        JPanel sousCase2B = creerSliders(controller);

        JSplitPane splitSousCases1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sousCase2A, sousCase2B);
        splitSousCases1.setDividerSize(5);
        splitSousCases1.setResizeWeight(0.80); 
        splitSousCases1.setOneTouchExpandable(true);

        JSplitPane splitSousCases2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitSousCases1, sousCase2C);
        splitSousCases2.setDividerSize(5);
        splitSousCases2.setOneTouchExpandable(true);

        return splitSousCases2;
    }

    private JSplitPane creerSousCasesVerticales(String nomSousCase1, String nomSousCase2) {
        JPanel sousCase1 = creerPanelAvecTexte(nomSousCase1, "Vous avez cliqué sur " + nomSousCase1);

        JPanel sousCase2 = creerPanelAvecTexte(nomSousCase2, "Vous avez cliqué sur " + nomSousCase2);

        JSplitPane splitSousCases = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sousCase1, sousCase2);

        splitSousCases.setDividerLocation(150); 
        splitSousCases.setResizeWeight(0.5); 
        splitSousCases.setOneTouchExpandable(true);
        return splitSousCases;
    }

    private JPanel creerPanelAvecTexte(String texte, String message) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel label = new JLabel(texte, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);

        caseToRadioButtonMap.put(panel, null); 
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (caseToRadioButtonMap.containsKey(panel) && caseToRadioButtonMap.get(panel) != null) {
                    caseToRadioButtonMap.get(panel).setSelected(true);
                }
            }
        });

        return panel;
    }

    private JPanel creerPanneauControle() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Paramètres"));

        labelParametres = new JLabel("Sélectionnez une case pour voir ses paramètres.", SwingConstants.CENTER);
        panel.add(labelParametres, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel creerSliders(BoulesController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Le contrôleur est null, impossible de créer les sliders.");
        }
        JPanel panelSliders = new JPanel();
        panelSliders.setLayout(new BoxLayout(panelSliders, BoxLayout.Y_AXIS));
        panelSliders.setBorder(BorderFactory.createTitledBorder("Contrôle des Boules"));

        ControlPanel panneauControle = new ControlPanel(controller);
        panelSliders.add(panneauControle);

        panelSliders.add(Box.createVerticalStrut(15));

        ButtonGroup groupeCases = new ButtonGroup();
        JPanel panelBoutonsRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); 
        panelBoutonsRadio.setBorder(BorderFactory.createTitledBorder("Sélection de la case"));

        boutonsRadio = new JRadioButton[caseToRadioButtonMap.size()]; 
        int i = 0;
        System.out.println("🔍 Contenu actuel de caseToRadioButtonMap :");
        for (JPanel key : caseToRadioButtonMap.keySet()) {
            System.out.println(" - Case associée : " + key + " -> Bouton radio : " + caseToRadioButtonMap.get(key));
        }
        for (JPanel casePanel : caseToRadioButtonMap.keySet()) {
            boutonsRadio[i] = new JRadioButton("" + (i + 1));
            boutonsRadio[i].setActionCommand("" + (i + 1));
            groupeCases.add(boutonsRadio[i]);
            panelBoutonsRadio.add(boutonsRadio[i]);
            caseToRadioButtonMap.put(casePanel, boutonsRadio[i]);
            boutonsRadio[i].addActionListener(e -> {
                casePanel.setBackground(Color.YELLOW);
            });

            i++;
        }

        panelSliders.add(panelBoutonsRadio);
        return panelSliders;
    }
}