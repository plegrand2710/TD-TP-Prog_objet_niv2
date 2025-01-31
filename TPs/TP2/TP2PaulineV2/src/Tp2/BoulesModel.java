package Tp2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class BoulesModel {
    private List<Boule> boules;
    private int nombreBoules = 1;
    private int vitesse = 50;
    private Image imageBoule;
    private int currentImageIndex = 5; 

    public BoulesModel() {
        boules = new ArrayList<>();
        for (int i = 0; i < nombreBoules; i++) {
            boules.add(new Boule(new Dimension(800, 600), getCurrentImage()));
        }
    }

    public List<Boule> getBoules() {
        return boules;
    }

    public void setNombreBoules(int nombre) {
        nombreBoules = nombre;
        boules.clear();
        for (int i = 0; i < nombreBoules; i++) {
            boules.add(new Boule(new Dimension(800, 600), getCurrentImage()));
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
            currentImageIndex = index;
            imageBoule = new ImageIcon(images[index - 1]).getImage();
            for (Boule boule : boules) {
                boule.setImage(imageBoule);
            }
        }
    }

    public Image getCurrentImage() {
        String[] images = {
            "gifs/bleue.gif", "gifs/bleue2.gif", "gifs/gris.gif", "gifs/jaune.gif", 
            "gifs/rose.gif", "gifs/rouge.gif", "gifs/rouge2.gif", "gifs/vert.gif",
            "gifs/vert2.gif", "gifs/violet.gif"
        };
        return new ImageIcon(images[currentImageIndex - 1]).getImage();
    }

    public int getNombreBoules() {
        return boules.size();
    }

    public void miseAJourBoules(Dimension dim) {
        for (Boule boule : boules) {
            boule.deplacer(dim, vitesse);
        }
    }
}