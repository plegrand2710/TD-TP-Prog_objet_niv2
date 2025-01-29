package Td2;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoulesApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private PanneauBoules panneauBoules;
    private JTable tableCoordonnees;
    private BoulesTableModel tableModel;

    public BoulesApp() {
        setTitle("Boules Paramétrables avec SplitPane");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panneauControle = creerPanneauControle();

        panneauBoules = new PanneauBoules();

        tableModel = new BoulesTableModel(panneauBoules.getBoules());
        tableCoordonnees = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(tableCoordonnees);

        JSplitPane splitHorizontal = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                panneauControle,
                panneauBoules
        );
        splitHorizontal.setDividerLocation(300); 
        splitHorizontal.setOneTouchExpandable(true); 

        JSplitPane splitVertical = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                splitHorizontal,
                scrollTable
        );
        splitVertical.setDividerLocation(400); 
        splitVertical.setOneTouchExpandable(true);

        add(splitVertical, BorderLayout.CENTER);

        new Thread(() -> {
            while (true) {
                panneauBoules.miseAJourBoules();
                tableModel.fireTableDataChanged();
                panneauBoules.repaint();
                try {
                    Thread.sleep(16); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        setVisible(true);
    }

    private JPanel creerPanneauControle() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JSlider sliderNombre = new JSlider(0, 1000, 150);
        sliderNombre.setBorder(BorderFactory.createTitledBorder("Nombre de boules"));
        sliderNombre.addChangeListener(e -> panneauBoules.setNombreBoules(sliderNombre.getValue()));
        panel.add(sliderNombre);

        JSlider sliderVitesse = new JSlider(1, 100, 10);
        sliderVitesse.setBorder(BorderFactory.createTitledBorder("Vitesse des boules"));
        sliderVitesse.addChangeListener(e -> panneauBoules.setVitesseBoules(sliderVitesse.getValue()));
        panel.add(sliderVitesse);

        JSlider sliderRouge = new JSlider(0, 255, 0);
        sliderRouge.setBorder(BorderFactory.createTitledBorder("Fond - Rouge"));
        sliderRouge.addChangeListener(e -> panneauBoules.setCouleurFond(sliderRouge.getValue(), -1, -1));
        panel.add(sliderRouge);

        JSlider sliderVert = new JSlider(0, 255, 255);
        sliderVert.setBorder(BorderFactory.createTitledBorder("Fond - Vert"));
        sliderVert.addChangeListener(e -> panneauBoules.setCouleurFond(-1, sliderVert.getValue(), -1));
        panel.add(sliderVert);

        JSlider sliderBleu = new JSlider(0, 255, 0);
        sliderBleu.setBorder(BorderFactory.createTitledBorder("Fond - Bleu"));
        sliderBleu.addChangeListener(e -> panneauBoules.setCouleurFond(-1, -1, sliderBleu.getValue()));
        panel.add(sliderBleu);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoulesApp::new);
    }
}


