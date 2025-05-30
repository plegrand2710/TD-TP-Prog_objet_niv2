package td3;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GraphRenderer {
    private double xMin, xMax, yMin, yMax;
    private int width, height;
    
    private double xMinorSpacing, xMajorSpacing, yMinorSpacing, yMajorSpacing;

    public GraphRenderer(double xMin, double xMax, double yMin, double yMax,
                         int width, int height,
                         double xMinorSpacing, double xMajorSpacing,
                         double yMinorSpacing, double yMajorSpacing) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.width = width;
        this.height = height;
        this.xMinorSpacing = xMinorSpacing;
        this.xMajorSpacing = xMajorSpacing;
        this.yMinorSpacing = yMinorSpacing;
        this.yMajorSpacing = yMajorSpacing;
    }
    
    private int toPixelX(double x) {
        return (int) ((x - xMin) / (xMax - xMin) * width);
    }
    
    private int toPixelY(double y) {
        return (int) (height - ((y - yMin) / (yMax - yMin) * height));
    }
    
    public void render(BufferedImage img, List<Polynomial> polynomials) {
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawAxes(g2d);
        
        for (Polynomial poly : polynomials) {
            drawPolynomial(g2d, poly);
        }
        
        g2d.dispose();
    }
    
    private void drawAxes(Graphics2D g2d) {
        int posY = (int) (height * (-yMin) / (yMax - yMin));
        int posX = (int) (width * (-xMin) / (xMax - xMin));
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, posY, width, posY);
        
        g2d.drawLine(posX, 0, posX, height);
        
        g2d.setStroke(new BasicStroke(1));
        int minorTickLength = 5;
        int majorTickLength = 10;
        double startX = Math.ceil(xMin / xMinorSpacing) * xMinorSpacing;
        for (double x = startX; x <= xMax; x += xMinorSpacing) {
            int px = toPixelX(x);
            boolean isMajor = Math.abs(x - Math.round(x / xMajorSpacing) * xMajorSpacing) < 1e-6;
            int tickSize = isMajor ? majorTickLength : minorTickLength;
            g2d.drawLine(px, posY - tickSize, px, posY + tickSize);
        }
        
        double startY = Math.ceil(yMin / yMinorSpacing) * yMinorSpacing;
        for (double y = startY; y <= yMax; y += yMinorSpacing) {
            int py = toPixelY(y);
            boolean isMajor = Math.abs(y - Math.round(y / yMajorSpacing) * yMajorSpacing) < 1e-6;
            int tickSize = isMajor ? majorTickLength : minorTickLength;
            g2d.drawLine(posX - tickSize, py, posX + tickSize, py);
        }
    }
    
    private void drawPolynomial(Graphics2D g2d, Polynomial poly) {
        g2d.setColor(poly.getColor());
        g2d.setStroke(new BasicStroke(poly.getThickness()));
        poly.clearComputedPoints();
        
        String style = poly.getStyle();
        int numSamples = width; 
        Point prevPoint = null;
        for (int i = 0; i < numSamples; i++) {
            double xVal = xMin + i * (xMax - xMin) / (numSamples - 1);
            double yVal = poly.evaluate(xVal);
            int px = toPixelX(xVal);
            int py = toPixelY(yVal);
            Point currPoint = new Point(px, py);
            poly.addComputedPoint(currPoint);
            
            if ("Linéaire".equals(style)) {
                if (prevPoint != null) {
                    g2d.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y);
                }
            } else if ("Points".equals(style)) {
                g2d.fillRect(px, py, 2, 2);
            } else if ("Croix".equals(style)) {
                int size = 4;
                g2d.drawLine(px - size, py - size, px + size, py + size);
                g2d.drawLine(px - size, py + size, px + size, py - size);
            }
            prevPoint = currPoint;
        }
    }
}