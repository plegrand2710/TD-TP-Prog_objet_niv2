package Td2;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 

        JPanel panelCouleur = new JPanel();
        panelCouleur.setLayout(new BoxLayout(panelCouleur, BoxLayout.Y_AXIS));
        panelCouleur.setBorder(BorderFactory.createTitledBorder("Background color (RGB)"));
        panelCouleur.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Background color (RGB)"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JSlider sliderRouge = new JSlider(0, 100, 25);
        personnaliserSlider(sliderRouge, 25, 5);
        sliderRouge.addChangeListener(e -> panneauBoules.setBackgroundColor(sliderRouge.getValue() * 255 / 100, -1, -1));
        panelCouleur.add(sliderRouge);

        JSlider sliderVert = new JSlider(0, 100, 75);
        personnaliserSlider(sliderVert, 25, 5);
        sliderVert.addChangeListener(e -> panneauBoules.setBackgroundColor(-1, sliderVert.getValue() * 255 / 100, -1));
        panelCouleur.add(sliderVert);

        JSlider sliderBleu = new JSlider(0, 100, 25);
        personnaliserSlider(sliderBleu, 25, 5);
        sliderBleu.addChangeListener(e -> panneauBoules.setBackgroundColor(-1, -1, sliderBleu.getValue() * 255 / 100));
        panelCouleur.add(sliderBleu);

        panel.add(panelCouleur);

        JSlider sliderVitesse = new JSlider(0, 100, 50);
        personnaliserSlider(sliderVitesse, 25, 5);
        sliderVitesse.addChangeListener(e -> panneauBoules.setVitesseBoules(sliderVitesse.getValue()));
        sliderVitesse.setBorder(BorderFactory.createTitledBorder("Ball speed"));
        panel.add(sliderVitesse);

        JSlider sliderNombre = new JSlider(0, 1000, 500);
        personnaliserSlider(sliderNombre, 200, 50);
        sliderNombre.addChangeListener(e -> panneauBoules.setNombreBoules(sliderNombre.getValue()));
        sliderNombre.setBorder(BorderFactory.createTitledBorder("Ball amount"));

        panel.add(sliderNombre);
        
        panel.add(creerPanneauImage());

        return panel;
    }

    private JPanel creerPanneauImage() {
        JPanel panelImage = new JPanel();
        panelImage.setLayout(new BoxLayout(panelImage, BoxLayout.Y_AXIS));
        panelImage.setBorder(BorderFactory.createTitledBorder("Ball image"));

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, creerLabelCouleur(Color.BLUE));     
        labelTable.put(2, creerLabelCouleur(new Color(30, 144, 255)));
        labelTable.put(3, creerLabelCouleur(Color.GRAY));     
        labelTable.put(4, creerLabelCouleur(Color.YELLOW));   
        labelTable.put(5, creerLabelCouleur(Color.PINK));    
        labelTable.put(6, creerLabelCouleur(Color.RED));     
        labelTable.put(7, creerLabelCouleur(new Color(139, 0, 0))); 
        labelTable.put(8, creerLabelCouleur(Color.GREEN));    
        labelTable.put(9, creerLabelCouleur(new Color(0, 128, 0))); 
        labelTable.put(10, creerLabelCouleur(Color.MAGENTA));

 

        JSlider sliderImage = new JSlider(1, 10, 5);
        sliderImage.setMajorTickSpacing(1);
        sliderImage.setPaintTicks(true);
        sliderImage.setPaintLabels(true);
        sliderImage.setSnapToTicks(true);
        sliderImage.setLabelTable(labelTable); 

        sliderImage.addChangeListener(e -> {
            int valeur = sliderImage.getValue();
            panneauBoules.setImage(valeur); 
        });

        panelImage.add(sliderImage);

        return panelImage;
    }

 
    private JLabel creerLabelCouleur(Color color) {
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(dessinerCercle(color, 10))); 
        return label;
    }

    private Image dessinerCercle(Color color, int taille) {
        int dimension = taille + 4;
        BufferedImage image = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(2, 2, taille, taille);
        g2.setColor(Color.BLACK);
        g2.drawOval(1, 1, taille, taille); 
        
        g2.dispose();
        return image;
    }


    private void personnaliserSlider(JSlider slider, int spacingNb, int spacingTick) {
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(spacingNb); 
        slider.setMinorTickSpacing(spacingTick);     
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoulesApp::new);
    }
}


