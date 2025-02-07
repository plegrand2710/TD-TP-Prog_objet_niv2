package td3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private double[] coefficients; // coefficients[0]=constant, coefficients[1]=coefficient de x, etc.
    private Color color;
    private String style; // "Linéaire", "Points" ou "Croix"
    
    // Liste des points calculés (coordonnées pixels) pour l'interaction avec la souris
    private List<Point> computedPoints;

    public Polynomial(double[] coefficients, Color color, String style) {
        this.coefficients = coefficients;
        this.color = color;
        this.style = style;
        computedPoints = new ArrayList<>();
    }
    
    // Évaluation du polynôme en x
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