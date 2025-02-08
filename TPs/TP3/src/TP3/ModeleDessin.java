package TP3;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class ModeleDessin implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Forme> formes;
    private Stack<Forme> pileAnnuler;
    private Stack<Forme> pileRetablir;
    private Color couleurFond;
    
    public ModeleDessin() {
        formes = new ArrayList<>();
        pileAnnuler = new Stack<>();
        pileRetablir = new Stack<>();
        couleurFond = Color.WHITE;
    }
    
    public void ajouterForme(Forme forme) {
        formes.add(forme);
        pileAnnuler.push(forme);
        pileRetablir.clear();
    }
    
    public ArrayList<Forme> getFormes() {
        return formes;
    }
    
    public void annuler() {
        if (!pileAnnuler.isEmpty()) {
            Forme forme = pileAnnuler.pop();
            formes.remove(forme);
            pileRetablir.push(forme);
        }
    }
    
    public void retablir() {
        if (!pileRetablir.isEmpty()) {
            Forme forme = pileRetablir.pop();
            formes.add(forme);
            pileAnnuler.push(forme);
        }
    }
    
    public void effacer() {
        formes.clear();
        pileAnnuler.clear();
        pileRetablir.clear();
    }
    
    public Color getCouleurFond() {
        return couleurFond;
    }
    
    public void setCouleurFond(Color couleur) {
        couleurFond = couleur;
    }
}