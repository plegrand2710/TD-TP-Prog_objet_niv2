package td3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private double[] coefficients; // coefficients[0] = terme constant, coefficients[1] = coefficient de x, etc.
    private Color color;
    private String style; // "Linéaire", "Points" ou "Croix"
    private int thickness; // Épaisseur du trait pour ce polynôme

    // Liste des points calculés (coordonnées pixels) pour l'interaction souris
    private List<Point> computedPoints;

    public Polynomial(double[] coefficients, Color color, String style, int thickness) {
        this.coefficients = coefficients;
        this.color = color;
        this.style = style;
        this.thickness = thickness;
        computedPoints = new ArrayList<>();
    }
    
    // Évalue le polynôme en x
    public double evaluate(double x) {
        double y = 0;
        for (int i = 0; i < coefficients.length; i++) {
            y += coefficients[i] * Math.pow(x, i);
        }
        return y;
    }

    // Accesseurs
    public double[] getCoefficients() {
        return coefficients;
    }
    public Color getColor() {
        return color;
    }
    public String getStyle() {
        return style;
    }
    public int getThickness() {
        return thickness;
    }
    public List<Point> getComputedPoints() {
        return computedPoints;
    }
    public void clearComputedPoints() {
        computedPoints.clear();
    }
    public void addComputedPoint(Point p) {
        computedPoints.add(p);
    }
}