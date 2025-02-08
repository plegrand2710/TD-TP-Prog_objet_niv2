package TP3;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ZoneDessin extends JPanel {
    private ModeleDessin modele;
    
    public ZoneDessin(ModeleDessin modele) {
        this.modele = modele;
    }
    
    public void setModele(ModeleDessin modele) {
        this.modele = modele;
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Forme> formes = modele.getFormes();
        for (Forme f : formes) {
            g2.setColor(f.getCouleur());
            g2.setStroke(new BasicStroke(f.getEpaisseur()));
            ArrayList<Point> pts = f.getPoints();
            switch(f.getOutil()) {
                case PINCEAUX:
                case GOMME:
                    for (int i = 0; i < pts.size() - 1; i++) {
                        g2.drawLine(pts.get(i).x, pts.get(i).y, pts.get(i+1).x, pts.get(i+1).y);
                    }
                    break;
                case LIGNE:
                    if (pts.size() >= 2) {
                        g2.drawLine(pts.get(0).x, pts.get(0).y, pts.get(1).x, pts.get(1).y);
                    }
                    break;
                case RECTANGLE:
                    if (pts.size() >= 2) {
                        int x = Math.min(pts.get(0).x, pts.get(1).x);
                        int y = Math.min(pts.get(0).y, pts.get(1).y);
                        int largeur = Math.abs(pts.get(0).x - pts.get(1).x);
                        int hauteur = Math.abs(pts.get(0).y - pts.get(1).y);
                        if (f.getRempli()) {
                            g2.fillRect(x, y, largeur, hauteur);
                        } else {
                            g2.drawRect(x, y, largeur, hauteur);
                        }
                    }
                    break;
                case CERCLE:
                    if (pts.size() >= 2) {
                        int x1 = pts.get(0).x;
                        int y1 = pts.get(0).y;
                        int x2 = pts.get(1).x;
                        int y2 = pts.get(1).y;
                        int largeur = Math.abs(x2 - x1);
                        int hauteur = Math.abs(y2 - y1);
                        int diameter = Math.min(largeur, hauteur);
                        int topLeftX = Math.min(x1, x2);
                        int topLeftY = Math.min(y1, y2);
                        if (f.getRempli()) {
                            g2.fillOval(topLeftX, topLeftY, diameter, diameter);
                        } else {
                            g2.drawOval(topLeftX, topLeftY, diameter, diameter);
                        }
                    }
                    break;
            }
        }
    }
}