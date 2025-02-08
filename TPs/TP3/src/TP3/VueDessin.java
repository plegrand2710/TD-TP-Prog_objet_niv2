package TP3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.JColorChooser;

public class VueDessin extends JFrame {
    private ModeleDessin modele;
    private ControleurDessin controleur;
    private ZoneDessin zoneDessin;
    private JRadioButton rbPinceau, rbLigne, rbRectangle, rbCercle;
    private JRadioButton rbContour, rbRempli;
    private JSlider sliderTaille;
    private JButton btnUndo, btnRedo, btnGomme;
    private JButton boutonCouleurTrait, boutonCouleurFond;
    private OutilDessin outilSelectionne;
    private Color couleurTrait = Color.BLACK;
    
    public VueDessin(ModeleDessin modele) {
        this.modele = modele;
        outilSelectionne = OutilDessin.PINCEAUX;
        setTitle("Application de Dessin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel panneauGauche = new JPanel();
        panneauGauche.setPreferredSize(new Dimension(200, 600));
        panneauGauche.setLayout(new BoxLayout(panneauGauche, BoxLayout.Y_AXIS));
        
        // Panel Couleur
        JPanel panelCouleur = new JPanel();
        panelCouleur.setLayout(new BoxLayout(panelCouleur, BoxLayout.Y_AXIS));
        panelCouleur.setBorder(BorderFactory.createTitledBorder("Couleur"));
        boutonCouleurTrait = new JButton("Couleur Trait");
        boutonCouleurFond = new JButton("Couleur Fond");
        panelCouleur.add(boutonCouleurTrait);
        panelCouleur.add(boutonCouleurFond);
        
        // Panel Forme
        JPanel panelForme = new JPanel();
        panelForme.setLayout(new BoxLayout(panelForme, BoxLayout.Y_AXIS));
        panelForme.setBorder(BorderFactory.createTitledBorder("Forme"));
        rbPinceau = new JRadioButton("Pinceau", true);
        rbLigne = new JRadioButton("Ligne");
        rbRectangle = new JRadioButton("Rectangle");
        rbCercle = new JRadioButton("Cercle");
        ButtonGroup bgForme = new ButtonGroup();
        bgForme.add(rbPinceau);
        bgForme.add(rbLigne);
        bgForme.add(rbRectangle);
        bgForme.add(rbCercle);
        panelForme.add(rbPinceau);
        panelForme.add(rbLigne);
        panelForme.add(rbRectangle);
        panelForme.add(rbCercle);
        
        // Panel Remplir
        JPanel panelRemplir = new JPanel();
        panelRemplir.setLayout(new BoxLayout(panelRemplir, BoxLayout.Y_AXIS));
        panelRemplir.setBorder(BorderFactory.createTitledBorder("Remplir"));
        rbContour = new JRadioButton("Contour", true);
        rbRempli = new JRadioButton("Rempli");
        ButtonGroup bgRemplir = new ButtonGroup();
        bgRemplir.add(rbContour);
        bgRemplir.add(rbRempli);
        panelRemplir.add(rbContour);
        panelRemplir.add(rbRempli);
        
        // Panel Taille (curseur sans valeur affichée)
        JPanel panelTaille = new JPanel();
        panelTaille.setBorder(BorderFactory.createTitledBorder("Taille"));
        sliderTaille = new JSlider(JSlider.HORIZONTAL, 1, 20, 3);
        sliderTaille.setMajorTickSpacing(1);
        sliderTaille.setPaintTicks(true);
        sliderTaille.setPaintLabels(false);
        panelTaille.add(sliderTaille);
        
        // Panel Gomme
        JPanel panelGomme = new JPanel();
        panelGomme.setBorder(BorderFactory.createTitledBorder("Gomme"));
        btnUndo = new JButton("Undo");
        btnRedo = new JButton("Redo");
        btnGomme = new JButton("Gomme");
        panelGomme.add(btnUndo);
        panelGomme.add(btnRedo);
        panelGomme.add(btnGomme);
        
        // Ajout de tous les panels au panneau de gauche
        panneauGauche.add(panelCouleur);
        panneauGauche.add(panelForme);
        panneauGauche.add(panelRemplir);
        panneauGauche.add(panelTaille);
        panneauGauche.add(panelGomme);
        
        zoneDessin = new ZoneDessin(modele);
        zoneDessin.setBackground(modele.getCouleurFond());
        
        getContentPane().add(panneauGauche, BorderLayout.WEST);
        getContentPane().add(zoneDessin, BorderLayout.CENTER);
        
        controleur = new ControleurDessin(modele, this);
        zoneDessin.addMouseListener(controleur);
        zoneDessin.addMouseMotionListener(controleur);
        
        rbPinceau.addActionListener(e -> outilSelectionne = OutilDessin.PINCEAUX);
        rbLigne.addActionListener(e -> outilSelectionne = OutilDessin.LIGNE);
        rbRectangle.addActionListener(e -> outilSelectionne = OutilDessin.RECTANGLE);
        rbCercle.addActionListener(e -> outilSelectionne = OutilDessin.CERCLE);
        btnGomme.addActionListener(e -> outilSelectionne = OutilDessin.GOMME);
        btnUndo.addActionListener(e -> { modele.annuler(); zoneDessin.repaint(); });
        btnRedo.addActionListener(e -> { modele.retablir(); zoneDessin.repaint(); });
        boutonCouleurTrait.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Choisir la couleur du trait", couleurTrait);
            if (c != null) {
                couleurTrait = c;
            }
        });
        boutonCouleurFond.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Choisir la couleur du fond", zoneDessin.getBackground());
            if (c != null) {
                modele.setCouleurFond(c);
                zoneDessin.setBackground(c);
                zoneDessin.repaint();
            }
        });
    }
    
    public OutilDessin getOutilSelectionne() {
        return outilSelectionne;
    }
    
    public float getEpaisseurSelectionnee() {
        return sliderTaille.getValue();
    }
    
    public boolean getEstRempli() {
        return rbRempli.isSelected();
    }
    
    public Color getCouleurTrait() {
        return couleurTrait;
    }
    
    public ZoneDessin getZoneDessin() {
        return zoneDessin;
    }
}