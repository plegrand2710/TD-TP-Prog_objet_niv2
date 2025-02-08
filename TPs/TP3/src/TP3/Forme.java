package TP3;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Forme implements Serializable {
    private static final long serialVersionUID = 1L;
    private OutilDessin outil;
    private ArrayList<Point> points;
    private Color couleur;
    private float epaisseur;
    private boolean rempli;
    
    public Forme(OutilDessin outil, Color couleur, float epaisseur) {
        this.outil = outil;
        this.couleur = couleur;
        this.epaisseur = epaisseur;
        this.points = new ArrayList<>();
        this.rempli = false;
    }
    
    public OutilDessin getOutil() {
        return outil;
    }
    
    public ArrayList<Point> getPoints() {
        return points;
    }
    
    public Color getCouleur() {
        return couleur;
    }
    
    public float getEpaisseur() {
        return epaisseur;
    }
    
    public boolean getRempli() {
        return rempli;
    }
    
    public void setRempli(boolean rempli) {
        this.rempli = rempli;
    }
    
    public void ajouterPoint(Point point) {
        points.add(point);
    }
}