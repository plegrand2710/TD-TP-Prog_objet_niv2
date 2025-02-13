package TP3;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ZoneDessin extends JPanel {

    private ModeleDessin modele;
    private Forme formeEnCours; 

    public ZoneDessin(ModeleDessin modele) {
        this.modele = modele;
    }

    public void setModele(ModeleDessin modele) {
        this.modele = modele;
    }

    public void setFormeEnCours(Forme f) {
        this.formeEnCours = f;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Forme> formes = modele.getFormes();

        for (Forme f : formes) {
            dessinerForme(g2, f);
        }

        if (formeEnCours != null) {
            dessinerForme(g2, formeEnCours);
        }
    }

    private void dessinerForme(Graphics2D g2, Forme f) {
        g2.setColor(f.getCouleur());
        g2.setStroke(new BasicStroke(f.getEpaisseur()));
        ArrayList<Point> pts = f.getPoints();

        switch (f.getOutil()) {
            case PINCEAUX:
            case GOMME:
                for (int i = 0; i < pts.size() - 1; i++) {
                    g2.drawLine(pts.get(i).x, pts.get(i).y, pts.get(i + 1).x, pts.get(i + 1).y);
                }
                break;

            case LIGNE:
                if (pts.size() >= 2) {
                    g2.drawLine(pts.get(0).x, pts.get(0).y, pts.get(1).x, pts.get(1).y);
                }
                break;

            case RECTANGLE:
                if (pts.size() >= 2) {
                    Point center = pts.get(0);
                    Point current = pts.get(1);
                    int dx = Math.abs(current.x - center.x);
                    int dy = Math.abs(current.y - center.y);
                    int x = center.x - dx;
                    int y = center.y - dy;
                    int largeur = 2 * dx;
                    int hauteur = 2 * dy;
                    if (f.getRempli()) {
                        g2.fillRect(x, y, largeur, hauteur);
                    } else {
                        g2.drawRect(x, y, largeur, hauteur);
                    }
                }
                break;

            case CERCLE:
                if (pts.size() >= 2) {
                    Point center = pts.get(0);
                    Point current = pts.get(1);
                    int dx = Math.abs(current.x - center.x);
                    int dy = Math.abs(current.y - center.y);
                    int radius = (int) Math.hypot(dx, dy);
                    int topLeftX = center.x - radius;
                    int topLeftY = center.y - radius;
                    int diameter = 2 * radius;
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