package Tp2;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Dimension;

public class BoulesController {
    private BoulesModel model;
    private PanneauBoules view;

    public BoulesController(BoulesModel model, PanneauBoules view) {
        this.model = model;
        this.view = view;
    }

    public ChangeListener createSliderNombreListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                setNombreBoules(slider.getValue());
            }
        };
    }

    public ChangeListener createSliderVitesseListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                setVitesseBoules(slider.getValue());
            }
        };
    }

    public ChangeListener createSliderImageListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                setImage(slider.getValue());
            }
        };
    }

    public ChangeListener createSliderRougeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                setBackgroundColor(slider.getValue() * 255 / 100, -1, -1);
            }
        };
    }

    public ChangeListener createSliderVertListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                setBackgroundColor(-1, slider.getValue() * 255 / 100, -1);
            }
        };
    }

    public ChangeListener createSliderBleuListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                setBackgroundColor(-1, -1, slider.getValue() * 255 / 100);
            }
        };
    }

    public void setNombreBoules(int nombre) {
        model.setNombreBoules(nombre);
        view.repaint();
    }

    public void setVitesseBoules(int vitesse) {
        model.setVitesseBoules(vitesse);
    }

    public void setImage(int index) {
        model.setImage(index);
        view.repaint();
    }

    public void setBackgroundColor(int r, int g, int b) {
        view.setBackground(new Color(
            (r == -1) ? view.getBackground().getRed() : r,
            (g == -1) ? view.getBackground().getGreen() : g,
            (b == -1) ? view.getBackground().getBlue() : b
        ));
        view.repaint();
    }

    public void miseAJourBoules(Dimension dim) {
        model.miseAJourBoules(dim);
        view.repaint();
    }
}