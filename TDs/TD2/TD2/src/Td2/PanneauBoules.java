package Td2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanneauBoules extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<Boule> boules;
    private int nombreBoules = 150;
    private int vitesse = 50;
    private Image imageBoule;

    public PanneauBoules() {
        boules = new ArrayList<>();
        for (int i = 0; i < nombreBoules; i++) {
            boules.add(new Boule(getSize()));
        }
        setBackground(Color.WHITE);
    }

    public void miseAJourBoules() {
        for (Boule boule : boules) {
            boule.deplacer(getSize(), vitesse);
        }
        repaint();
    }

    public List<Boule> getBoules() {
        return boules;
    }

    public void setNombreBoules(int nombre) {
        nombreBoules = nombre;
        boules.clear();
        for (int i = 0; i < nombreBoules; i++) {
            boules.add(new Boule(getSize()));
        }
    }

    public void setVitesseBoules(int vitesse) {
        this.vitesse = vitesse;
    }

    public void setImage(int index) {
        String[] images = {
            "gifs/bleue.gif", "gifs/bleue2.gif", "gifs/gris.gif", "gifs/jaune.gif", 
            "gifs/rose.gif", "gifs/rouge.gif", "gifs/rouge2.gif", "gifs/vert.gif",
            "gifs/vert2.gif", "gifs/violet.gif"
        };

        if (index >= 1 && index <= images.length) {
            imageBoule = new ImageIcon(images[index - 1]).getImage();
            for (Boule boule : boules) {
                boule.setImage(imageBoule);
            }
        }
    }

    public void setBackgroundColor(int r, int g, int b) {
        Color newColor = new Color(
            (r == -1) ? getBackground().getRed() : r,
            (g == -1) ? getBackground().getGreen() : g,
            (b == -1) ? getBackground().getBlue() : b
        );
        setBackground(newColor);
        repaint();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Boule boule : boules) {
            if (imageBoule != null) {
                g.drawImage(imageBoule, (int) boule.getX(), (int) boule.getY(), this);
            } else {
                g.setColor(Color.RED);
                g.fillOval((int) boule.getX(), (int) boule.getY(), 10, 10);
            }
        }
    }
}
