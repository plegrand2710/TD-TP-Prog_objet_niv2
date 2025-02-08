package Tp2;

import java.awt.*;
import javax.swing.ImageIcon;

public class Boule {
    private double x, y;
    private double vx, vy;
    private Image image;

    public Boule(Dimension dim, Image image) {
        x = Math.random() * dim.width;
        y = Math.random() * dim.height;
        double angle = Math.random() * Math.PI * 2;
        vx = Math.cos(angle);
        vy = Math.sin(angle);
        this.image = image;
    }

    public void deplacer(Dimension dim, int vitesse) {
        x += vx * vitesse / 10.0;
        y += vy * vitesse / 10.0;
        
        int diameter = 20;

        if (x < 0) {
            x = 0;
            vx = -vx;
        } else if (x > dim.width - diameter) {
            x = dim.width - diameter;
            vx = -vx;
        }

        if (y < 0) {
            y = 0;
            vy = -vy;
        } else if (y > dim.height - diameter) {
            y = dim.height - diameter;
            vy = -vy;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}