package td3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class GraphPanel extends JPanel {
    private BufferedImage canvas;
    private PolynomialManager polyManager;
    
    private double xMin = -10, xMax = 10, yMin = -10, yMax = 10;
    
    private double xMinorSpacing = 1, xMajorSpacing = 5;
    private double yMinorSpacing = 1, yMajorSpacing = 5;
    
    private Point highlightedPoint = null;
    
    public GraphPanel(PolynomialManager polyManager) {
        this.polyManager = polyManager;
        setBackground(Color.WHITE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                render();
                repaint();
            }
        });
    }
    
    public void setBoundsParameters(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    public void setSpacingParameters(double xMinor, double xMajor, double yMinor, double yMajor) {
        this.xMinorSpacing = xMinor;
        this.xMajorSpacing = xMajor;
        this.yMinorSpacing = yMinor;
        this.yMajorSpacing = yMajor;
    }
    
    public double getXMin() { return xMin; }
    public double getXMax() { return xMax; }
    public double getYMin() { return yMin; }
    public double getYMax() { return yMax; }
    
    public void setHighlightedPoint(Point p) {
        highlightedPoint = p;
    }
    
    public void render() {
        if(getWidth() <= 0 || getHeight() <= 0) return;
        canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        GraphRenderer renderer = new GraphRenderer(
                xMin, xMax, yMin, yMax,
                getWidth(), getHeight(),
                xMinorSpacing, xMajorSpacing,
                yMinorSpacing, yMajorSpacing);
        List<Polynomial> polys = polyManager.getPolynomials();
        renderer.render(canvas, polys);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (canvas == null) {
            render();
        }
        g.drawImage(canvas, 0, 0, null);
        if (highlightedPoint != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.MAGENTA);
            g2d.setStroke(new BasicStroke(2));
            int proximity = 7;
            g2d.drawOval(highlightedPoint.x - proximity, highlightedPoint.y - proximity, proximity * 2, proximity * 2);
            g2d.dispose();
        }
    }
}