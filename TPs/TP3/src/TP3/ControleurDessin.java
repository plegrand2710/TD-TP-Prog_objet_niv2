package TP3;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControleurDessin extends MouseAdapter {

    private ModeleDessin modele;
    private VueDessin vue;
    private Forme formeEnCours;

    public ControleurDessin(ModeleDessin modele, VueDessin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        OutilDessin outil = vue.getOutilSelectionne();
        Color couleur = (outil == OutilDessin.GOMME) ? vue.getZoneDessin().getBackground() : vue.getCouleurTrait();
        float epaisseur = vue.getEpaisseurSelectionnee();
        formeEnCours = new Forme(outil, couleur, epaisseur);
        
        if (outil == OutilDessin.RECTANGLE || outil == OutilDessin.CERCLE) {
            formeEnCours.setRempli(vue.getEstRempli());
        }
        
        formeEnCours.ajouterPoint(e.getPoint());
        
        vue.getZoneDessin().setFormeEnCours(formeEnCours);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (formeEnCours != null) {
            if (formeEnCours.getOutil() == OutilDessin.PINCEAUX || 
                formeEnCours.getOutil() == OutilDessin.GOMME) {
                formeEnCours.ajouterPoint(e.getPoint());
            } else if (formeEnCours.getOutil() == OutilDessin.LIGNE ||
                       formeEnCours.getOutil() == OutilDessin.RECTANGLE ||
                       formeEnCours.getOutil() == OutilDessin.CERCLE) {
                if (formeEnCours.getPoints().size() == 1) {
                    formeEnCours.ajouterPoint(e.getPoint());
                } else {
                    formeEnCours.getPoints().set(1, e.getPoint());
                }
            }
            vue.getZoneDessin().setFormeEnCours(formeEnCours);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (formeEnCours != null) {
            if (formeEnCours.getOutil() == OutilDessin.LIGNE ||
                formeEnCours.getOutil() == OutilDessin.RECTANGLE ||
                formeEnCours.getOutil() == OutilDessin.CERCLE) {
                if (formeEnCours.getPoints().size() == 1) {
                    formeEnCours.ajouterPoint(e.getPoint());
                } else {
                    formeEnCours.getPoints().set(1, e.getPoint());
                }
            }
            modele.ajouterForme(formeEnCours);
            
            vue.getZoneDessin().setFormeEnCours(null);
            formeEnCours = null;
        }
    }
}