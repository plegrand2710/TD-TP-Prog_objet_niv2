package Tp2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class ControlPanel extends JPanel {
    public ControlPanel(BoulesController controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JSlider sliderNombre = new JSlider(0, 1000, 150);
        sliderNombre.addChangeListener(controller.createSliderNombreListener());
        personnaliserSlider(sliderNombre, 200, 50);
        sliderNombre.setBorder(BorderFactory.createTitledBorder("Ball amount"));

        JSlider sliderVitesse = new JSlider(0, 100, 50);
        sliderVitesse.addChangeListener(controller.createSliderVitesseListener());
        personnaliserSlider(sliderVitesse, 25, 5);
        sliderVitesse.setBorder(BorderFactory.createTitledBorder("Ball speed"));

        JPanel panelCouleur = new JPanel();
        panelCouleur.setLayout(new BoxLayout(panelCouleur, BoxLayout.Y_AXIS));
        panelCouleur.setBorder(BorderFactory.createTitledBorder("Background color (RGB)"));

        JSlider sliderRouge = new JSlider(0, 100, 25);
        sliderRouge.addChangeListener(controller.createSliderRougeListener());
        personnaliserSlider(sliderRouge, 25, 5);
        panelCouleur.add(sliderRouge);

        JSlider sliderVert = new JSlider(0, 100, 75);
        sliderVert.addChangeListener(controller.createSliderVertListener());
        personnaliserSlider(sliderVert, 25, 5);
        panelCouleur.add(sliderVert);

        JSlider sliderBleu = new JSlider(0, 100, 25);
        sliderBleu.addChangeListener(controller.createSliderBleuListener());
        personnaliserSlider(sliderBleu, 25, 5);
        panelCouleur.add(sliderBleu);

        add(sliderNombre);
        add(sliderVitesse);
        add(creerPanneauImage(controller));
        add(panelCouleur);
    }
    
    private void personnaliserSlider(JSlider slider, int spacingNb, int spacingTick) {
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(spacingNb); 
        slider.setMinorTickSpacing(spacingTick);     
    }
    
    private JPanel creerPanneauImage(BoulesController controller ) {
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
        
        panelImage.add(sliderImage);
        
        sliderImage.addChangeListener(controller.createSliderImageListener());

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
}