package TP3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JColorChooser;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class VueDessin extends JPanel {

    private ModeleDessin modele;
    private ControleurDessin controleur;
    private ZoneDessin zoneDessin;
    
    private JRadioButton rbPinceau, rbLigne, rbRectangle, rbCercle;
    
    private JRadioButton rbFill;
    
    private JSlider sliderTaille;
    
    private JRadioButton rbNoir, rbRouge, rbBleu, rbVert, rbJaune;
    private JButton btnChooseColor;
    
    private JRadioButton rbBgBlanc, rbBgRouge, rbBgBleu, rbBgVert, rbBgJaune;
    private JButton btnChooseBg;
    
    private JButton btnUndo, btnClear, btnRedo, btnGomme;
    
    private OutilDessin outilSelectionne;
    private Color couleurTrait = Color.BLACK;
    
    public VueDessin(ModeleDessin modele) {
        this.modele = modele;
        outilSelectionne = OutilDessin.PINCEAUX;
        setSize(800, 600);
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel panneauGauche = new JPanel();
        panneauGauche.setPreferredSize(new Dimension(400, 600));
        panneauGauche.setLayout(new BoxLayout(panneauGauche, BoxLayout.Y_AXIS));
        panneauGauche.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JPanel panelForme = new JPanel(new GridLayout(2, 2, 5, 5));
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
        
        JPanel panelRemplir = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRemplir.setBorder(BorderFactory.createTitledBorder("Remplir"));
        
        rbFill = new JRadioButton("Fill", false);
        panelRemplir.add(rbFill);
        
        JPanel panelTaille = new JPanel(new BorderLayout());
        panelTaille.setBorder(BorderFactory.createTitledBorder("Taille"));
        
        sliderTaille = new JSlider(JSlider.HORIZONTAL, 1, 20, 3);
        sliderTaille.setMajorTickSpacing(1);
        sliderTaille.setPaintTicks(true);
        sliderTaille.setPaintLabels(false);
        panelTaille.add(sliderTaille, BorderLayout.CENTER);
        panelTaille.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JPanel panelCouleur = new JPanel();
        panelCouleur.setLayout(new BoxLayout(panelCouleur, BoxLayout.Y_AXIS));
        panelCouleur.setBorder(BorderFactory.createTitledBorder("Color"));
        
        JPanel panelColorRadios = new JPanel(new GridLayout(1, 5, 5, 5));
        rbNoir = new JRadioButton("Noir", true);
        rbRouge = new JRadioButton("Rouge", false);
        rbBleu = new JRadioButton("Bleu", false);
        rbVert = new JRadioButton("Vert", false);
        rbJaune = new JRadioButton("Jaune", false);
        
        ButtonGroup bgColor = new ButtonGroup();
        bgColor.add(rbNoir);
        bgColor.add(rbRouge);
        bgColor.add(rbBleu);
        bgColor.add(rbVert);
        bgColor.add(rbJaune);
        
        panelColorRadios.add(rbNoir);
        panelColorRadios.add(rbRouge);
        panelColorRadios.add(rbBleu);
        panelColorRadios.add(rbVert);
        panelColorRadios.add(rbJaune);
        
        JPanel panelColorChoose = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnChooseColor = new JButton("Choose");
        panelColorChoose.add(btnChooseColor);
        
        panelCouleur.add(panelColorRadios);
        panelCouleur.add(panelColorChoose);
        
        JPanel panelBg = new JPanel();
        panelBg.setLayout(new BoxLayout(panelBg, BoxLayout.Y_AXIS));
        panelBg.setBorder(BorderFactory.createTitledBorder("BackgroundColor"));
        
        JPanel panelBgRadios = new JPanel(new GridLayout(1, 5, 5, 5));
        rbBgBlanc = new JRadioButton("Blanc", true);
        rbBgRouge = new JRadioButton("Rouge", false);
        rbBgBleu = new JRadioButton("Bleu", false);
        rbBgVert = new JRadioButton("Vert", false);
        rbBgJaune = new JRadioButton("Jaune", false);
        
        ButtonGroup bgBg = new ButtonGroup();
        bgBg.add(rbBgBlanc);
        bgBg.add(rbBgRouge);
        bgBg.add(rbBgBleu);
        bgBg.add(rbBgVert);
        bgBg.add(rbBgJaune);
        
        panelBgRadios.add(rbBgBlanc);
        panelBgRadios.add(rbBgRouge);
        panelBgRadios.add(rbBgBleu);
        panelBgRadios.add(rbBgVert);
        panelBgRadios.add(rbBgJaune);
        
        JPanel panelBgChoose = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnChooseBg = new JButton("Choisir");
        panelBgChoose.add(btnChooseBg);
        
        panelBg.add(panelBgRadios);
        panelBg.add(panelBgChoose);
        
        JPanel panelGomme = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelGomme.setBorder(BorderFactory.createTitledBorder("Gomme"));
        
        btnUndo = new JButton("Undo");
        btnRedo = new JButton("Redo");

        btnClear = new JButton("Clear");
        btnGomme = new JButton("Gomme");
        
        panelGomme.add(btnUndo);
        panelGomme.add(btnRedo);

        panelGomme.add(btnClear);
        panelGomme.add(btnGomme);
        
        panneauGauche.add(panelForme);
        panneauGauche.add(panelRemplir);
        panneauGauche.add(panelTaille);
        panneauGauche.add(panelCouleur);
        panneauGauche.add(panelBg);
        panneauGauche.add(panelGomme);
        
        zoneDessin = new ZoneDessin(modele);
        zoneDessin.setBackground(modele.getCouleurFond());
        
        add(panneauGauche, BorderLayout.WEST);
        add(zoneDessin, BorderLayout.CENTER);
        
        controleur = new ControleurDessin(modele, this);
        zoneDessin.addMouseListener(controleur);
        zoneDessin.addMouseMotionListener(controleur);
        
        rbPinceau.addActionListener(e -> outilSelectionne = OutilDessin.PINCEAUX);
        rbLigne.addActionListener(e -> outilSelectionne = OutilDessin.LIGNE);
        rbRectangle.addActionListener(e -> outilSelectionne = OutilDessin.RECTANGLE);
        rbCercle.addActionListener(e -> outilSelectionne = OutilDessin.CERCLE);
        
        btnGomme.addActionListener(e -> outilSelectionne = OutilDessin.GOMME);
        btnUndo.addActionListener(e -> {
            modele.annuler();
            zoneDessin.repaint();
        });
        btnRedo.addActionListener(e -> {
            modele.retablir();
            zoneDessin.repaint();
        });
        btnClear.addActionListener(e -> {
            modele.effacer();
            zoneDessin.repaint();
        });
        
        btnChooseColor.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Choisir la couleur du trait", couleurTrait);
            if (c != null) {
                couleurTrait = c;
                rbNoir.setSelected(false);
                rbRouge.setSelected(false);
                rbBleu.setSelected(false);
                rbVert.setSelected(false);
                rbJaune.setSelected(false);
            }
        });
        rbNoir.addActionListener(e -> couleurTrait = Color.BLACK);
        rbRouge.addActionListener(e -> couleurTrait = Color.RED);
        rbBleu.addActionListener(e -> couleurTrait = Color.BLUE);
        rbVert.addActionListener(e -> couleurTrait = Color.GREEN);
        rbJaune.addActionListener(e -> couleurTrait = Color.YELLOW);
        
        btnChooseBg.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Choisir la couleur d'arrière-plan", zoneDessin.getBackground());
            if (c != null) {
                modele.setCouleurFond(c);
                zoneDessin.setBackground(c);
                rbBgBlanc.setSelected(false);
                rbBgRouge.setSelected(false);
                rbBgBleu.setSelected(false);
                rbBgVert.setSelected(false);
                rbBgJaune.setSelected(false);
            }
        });
        rbBgBlanc.addActionListener(e -> {
            modele.setCouleurFond(Color.WHITE);
            zoneDessin.setBackground(Color.WHITE);
        });
        rbBgRouge.addActionListener(e -> {
            modele.setCouleurFond(Color.RED);
            zoneDessin.setBackground(Color.RED);
        });
        rbBgBleu.addActionListener(e -> {
            modele.setCouleurFond(Color.BLUE);
            zoneDessin.setBackground(Color.BLUE);
        });
        rbBgVert.addActionListener(e -> {
            modele.setCouleurFond(Color.GREEN);
            zoneDessin.setBackground(Color.GREEN);
        });
        rbBgJaune.addActionListener(e -> {
            modele.setCouleurFond(Color.YELLOW);
            zoneDessin.setBackground(Color.YELLOW);
        });
    }
    
    public OutilDessin getOutilSelectionne() {
        return outilSelectionne;
    }
    
    public float getEpaisseurSelectionnee() {
        return sliderTaille.getValue();
    }
    
    public boolean getEstRempli() {
        return rbFill.isSelected();
    }
    
    public Color getCouleurTrait() {
        return couleurTrait;
    }
    
    public ZoneDessin getZoneDessin() {
        return zoneDessin;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeleDessin modele = new ModeleDessin();
            new VueDessin(modele);
        });
    }
    
    public ModeleDessin getModele() {
        return this.modele;
    }
}