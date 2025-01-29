package Td2;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanneauBoules extends JPanel {
    private List<Boule> boules;
    private Color couleurFond = Color.WHITE;
    private int vitesse = 10;

    public PanneauBoules() {
        boules = new ArrayList<>();
        setPreferredSize(new Dimension(600, 400));
        creerBoules(150);
    }

    public void setNombreBoules(int nombre) {
        creerBoules(nombre);
    }

    public void setVitesseBoules(int vitesse) {
        this.vitesse = vitesse;
    }

    public void setCouleurFond(int r, int g, int b) {
        if (r != -1) couleurFond = new Color(r, couleurFond.getGreen(), couleurFond.getBlue());
        if (g != -1) couleurFond = new Color(couleurFond.getRed(), g, couleurFond.getBlue());
        if (b != -1) couleurFond = new Color(couleurFond.getRed(), couleurFond.getGreen(), b);
        repaint();
    }

    private void creerBoules(int nombre) {
        boules.clear();
        Dimension dim = getSize();
        for (int i = 0; i < nombre; i++) {
            boules.add(new Boule(dim));
        }
    }

    public List<Boule> getBoules() {
        return boules;
    }

    public void miseAJourBoules() {
        for (Boule boule : boules) {
            boule.deplacer(getSize(), vitesse);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(couleurFond);

        for (Boule boule : boules) {
            g.setColor(Color.RED);
            g.fillOval((int) boule.getX(), (int) boule.getY(), 10, 10);
        }
    }
}
