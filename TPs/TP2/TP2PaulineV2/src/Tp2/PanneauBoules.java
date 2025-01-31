package Tp2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanneauBoules extends JPanel {
    private static final long serialVersionUID = 1L;
    private BoulesModel model;

    public PanneauBoules(BoulesModel model) {
        this.model = model;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<Boule> boules = model.getBoules();
        for (Boule boule : boules) {
            if (boule.getImage() != null) {
                g.drawImage(boule.getImage(), (int) boule.getX(), (int) boule.getY(), 20, 20, this);
            } else {
                g.setColor(Color.RED);
                g.fillOval((int) boule.getX(), (int) boule.getY(), 10, 10);
            }
        }
    }
}