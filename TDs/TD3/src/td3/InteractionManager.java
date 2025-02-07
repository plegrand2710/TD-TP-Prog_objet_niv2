package td3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InteractionManager {
    private GraphPanel graphPanel;
    private PolynomialManager polyManager;
    private JLabel coordLabel;
    private final int proximity = 7; // Seuil en pixels

    public InteractionManager(GraphPanel graphPanel, PolynomialManager polyManager, JLabel coordLabel) {
        this.graphPanel = graphPanel;
        this.polyManager = polyManager;
        this.coordLabel = coordLabel;
        addMouseListeners();
    }
    
    private void addMouseListeners() {
        graphPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMove(e);
            }
        });
    }
    
    private void handleMouseMove(MouseEvent e) {
        Point mousePoint = e.getPoint();
        Point nearestPoint = null;
        // Parcours de tous les points de tous les polynômes
        for (Polynomial poly : polyManager.getPolynomials()) {
            List<Point> points = poly.getComputedPoints();
            for (Point p : points) {
                if (mousePoint.distance(p) < proximity) {
                    nearestPoint = p;
                    break;
                }
            }
            if (nearestPoint != null) break;
        }
        
        if (nearestPoint != null) {
            // Conversion pixel -> coordonnées mathématiques
            double xMin = graphPanel.getXMin();
            double xMax = graphPanel.getXMax();
            double yMin = graphPanel.getYMin();
            double yMax = graphPanel.getYMax();
            int width = graphPanel.getWidth();
            int height = graphPanel.getHeight();
            double mathX = xMin + nearestPoint.x * (xMax - xMin) / width;
            double mathY = yMax - nearestPoint.y * (yMax - yMin) / height;
            coordLabel.setText(String.format("Coordonnées: (%.2f, %.2f)", mathX, mathY));
            graphPanel.setHighlightedPoint(nearestPoint);
            graphPanel.repaint();
        } else {
            coordLabel.setText("Coordonnées: ");
            graphPanel.setHighlightedPoint(null);
            graphPanel.repaint();
        }
    }
}