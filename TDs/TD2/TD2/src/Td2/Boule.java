package Td2;

import java.awt.*;

public class Boule {
    private double x, y;
    private double vx, vy;
    private Image image;

    public Boule(Dimension dim) {
        x = Math.random() * dim.width;
        y = Math.random() * dim.height;
        double angle = Math.random() * Math.PI * 2;
        vx = Math.cos(angle);
        vy = Math.sin(angle);
    }

    public void deplacer(Dimension dim, int vitesse) {
        x += vx * vitesse / 10.0;
        y += vy * vitesse / 10.0;

        if (x < 0 || x > dim.width) vx = -vx;
        if (y < 0 || y > dim.height) vy = -vy;
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
