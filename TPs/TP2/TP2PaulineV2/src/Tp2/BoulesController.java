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
        this.setModel(model);
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
        getModel().setNombreBoules(nombre);
        view.repaint();
    }

    public void setVitesseBoules(int vitesse) {
        getModel().setVitesseBoules(vitesse);
    }

    public void setImage(int index) {
        getModel().setImage(index);
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
        getModel().miseAJourBoules(dim);
        view.repaint();
    }
    
    public int getNombreBoules() {
        return getModel().getNombreBoules();
    }

    public int getVitesseBoules() {
        return getModel().getVitesseBoules();
    }

    public int getImage() {
        return getModel().getImage();
    }

    public int getBackgroundRouge() {
        return view.getBackground().getRed() * 100 / 255;
    }

    public int getBackgroundVert() {
        return view.getBackground().getGreen() * 100 / 255;
    }

    public int getBackgroundBleu() {
        return view.getBackground().getBlue() * 100 / 255;
    }

	public BoulesModel getModel() {
		return model;
	}

	public void setModel(BoulesModel model) {
		this.model = model;
	}
}