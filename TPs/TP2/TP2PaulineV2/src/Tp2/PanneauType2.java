package Tp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class PanneauType2 extends JPanel {
    private JRadioButton[] boutonsRadio;
    private JPanel sousCase2B;  
    private HashMap<JPanel, JRadioButton> caseToRadioButtonMap = new HashMap<>();
    private HashMap<JPanel, AnimationThread> caseToThreadMap = new HashMap<>();
    private HashMap<JPanel, PanneauBoules> caseToViewMap = new HashMap<>();
    private HashMap<JPanel, BoulesController> caseToControllerMap = new HashMap<>();
    private ButtonGroup groupeCases = new ButtonGroup(); 

    public PanneauType2() {
        setLayout(new BorderLayout()); 
        JPanel sousCase3A = creerPanelAvecThread("Sous-case 3A");
        JPanel sousCase3B = creerPanelAvecThread("Sous-case 3B");

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
        JPanel sousCase1 = creerPanelAvecThread(nomSousCase1);

        JPanel sousCase2 = creerPanelAvecThread(nomSousCase2);

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

        sousCase2B = creerSliders(controller);

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
        JPanel sousCase1 = creerPanelAvecThread(nomSousCase1);

        JPanel sousCase2 = creerPanelAvecThread(nomSousCase2);

        JSplitPane splitSousCases = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sousCase1, sousCase2);

        splitSousCases.setDividerLocation(150); 
        splitSousCases.setResizeWeight(0.5); 
        splitSousCases.setOneTouchExpandable(true);
        return splitSousCases;
    }
    
    private JPanel creerPanelAvecThread(String texte) {
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        caseToRadioButtonMap.put(panel, null); 

        BoulesModel model = new BoulesModel();
        PanneauBoules view = new PanneauBoules(model);
        BoulesController controller = new BoulesController(model, view);
        AnimationThread thread = new AnimationThread(controller, view);

        caseToThreadMap.put(panel, thread);
        caseToViewMap.put(panel, view);
        caseToControllerMap.put(panel, controller); 

        panel.add(view, BorderLayout.CENTER);
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (caseToRadioButtonMap.containsKey(panel) && caseToRadioButtonMap.get(panel) != null) {
                    caseToRadioButtonMap.get(panel).setSelected(true);

                    SwingUtilities.invokeLater(() -> {
                        caseToRadioButtonMap.get(panel).revalidate();
                        caseToRadioButtonMap.get(panel).repaint();
                    });
                }

                SwingUtilities.invokeLater(() -> {
                    mettreAJourSelectionCase(panel);
                });

                SwingUtilities.invokeLater(() -> {
                    AnimationThread selectedThread = caseToThreadMap.get(panel);
                    if (selectedThread != null && !selectedThread.isAlive()) {
                        selectedThread.start();
                    }
                    sousCase2B.revalidate();
                    sousCase2B.repaint();
                });
            }
        });
        return panel;
    }
    
    
    private JPanel creerSliders(BoulesController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Le contrôleur est null, impossible de créer les sliders.");
        }

        JPanel panelSliders = new JPanel();
        panelSliders.setLayout(new BoxLayout(panelSliders, BoxLayout.Y_AXIS));

        ControlPanel panneauControle = new ControlPanel(controller);
        panelSliders.add(panneauControle);
        panelSliders.add(Box.createVerticalStrut(15));

        panelSliders.add(getPanelBoutonsRadio());

        return panelSliders;
    }
    
    private JPanel getPanelBoutonsRadio() {
        if (boutonsRadio == null) {
            creerBoutonsRadio();  
        }
        JPanel panelBoutonsRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBoutonsRadio.setBorder(BorderFactory.createTitledBorder("Sélection de la case"));

        for (JRadioButton bouton : boutonsRadio) {
            panelBoutonsRadio.add(bouton);
        }

        return panelBoutonsRadio;
    }
    
    private void creerBoutonsRadio() {
        boutonsRadio = new JRadioButton[caseToRadioButtonMap.size()];
        int i = 0;

        for (JPanel casePanel : caseToRadioButtonMap.keySet()) {
            boutonsRadio[i] = new JRadioButton("" + (i + 1));
            boutonsRadio[i].setActionCommand("" + (i + 1));

            groupeCases.add(boutonsRadio[i]);
            caseToRadioButtonMap.put(casePanel, boutonsRadio[i]);

            boutonsRadio[i].addActionListener(e -> {
                mettreAJourSelectionCase(casePanel);
            });

            i++;
        }
    }
   
    private void mettreAJourSelectionCase(JPanel caseSelectionnee) {
        BoulesController controller;

        if (caseToControllerMap.containsKey(caseSelectionnee)) {
            controller = caseToControllerMap.get(caseSelectionnee);
        } else {
            BoulesModel model = new BoulesModel();
            PanneauBoules view = new PanneauBoules(model);
            controller = new BoulesController(model, view);
            caseToControllerMap.put(caseSelectionnee, controller);
        }

        int nombreBoules = controller.getNombreBoules();
        int vitesse = controller.getVitesseBoules();
        int image = controller.getImage();
        int rouge = controller.getBackgroundRouge();
        int vert = controller.getBackgroundVert();
        int bleu = controller.getBackgroundBleu();

        sousCase2B.removeAll();
        JPanel panneauControle = creerSliders(controller);
        sousCase2B.add(panneauControle, BorderLayout.CENTER);
        sousCase2B.revalidate();
        sousCase2B.repaint();

        SwingUtilities.invokeLater(() -> setValeursSliders(panneauControle, controller, nombreBoules, vitesse, image, rouge, vert, bleu));

        AnimationThread selectedThread = caseToThreadMap.get(caseSelectionnee);
        if (selectedThread != null) {
            if (!selectedThread.isAlive()) {
                selectedThread.start();
            } else {
                selectedThread.resumeAnimation();
            }
        }
    }
        
    private void setValeursSliders(JPanel panneauControle, BoulesController controller, 
        int nombreBoules, int vitesse, int image, int rouge, int vert, int bleu) {
		if (controller == null) return;		
		HashMap<String, JSlider> slidersTrouves = new HashMap<>();
		trouverSliders(panneauControle, slidersTrouves);
		
		if (slidersTrouves.containsKey("nombreBoules")) {
			slidersTrouves.get("nombreBoules").setValue(nombreBoules);
		}
		if (slidersTrouves.containsKey("vitesseBoules")) {
			slidersTrouves.get("vitesseBoules").setValue(vitesse);
		}
		if (slidersTrouves.containsKey("imageBoules")) {
			slidersTrouves.get("imageBoules").setValue(image);
		}
		if (slidersTrouves.containsKey("rouge")) {
			slidersTrouves.get("rouge").setValue(rouge);
		}
		if (slidersTrouves.containsKey("vert")) {
			slidersTrouves.get("vert").setValue(vert);
		}
		if (slidersTrouves.containsKey("bleu")) {
			slidersTrouves.get("bleu").setValue(bleu);
		}

		panneauControle.revalidate();
		panneauControle.repaint();
    }
    
    
    private void trouverSliders(JComponent parent, HashMap<String, JSlider> slidersTrouves) {
        for (Component comp : parent.getComponents()) {
            if (comp instanceof JSlider) {
                JSlider slider = (JSlider) comp;
                slidersTrouves.put(slider.getName(), slider);
            } else if (comp instanceof JComponent) {
                trouverSliders((JComponent) comp, slidersTrouves);
            }
        }
    }
}