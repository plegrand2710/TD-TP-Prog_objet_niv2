package td3;
import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    public GraphPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int midX = width / 2;
        int midY = height / 2;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, midY, width, midY);
        g2d.drawLine(midX, 0, midX, height); 

        int scale = 50;
        int minorTickLength = 5;
        int majorTickLength = 10;
        int majorTickInterval = 5; 

        for (int x = midX; x <= width; x += scale) {
            int unit = (x - midX) / scale;
            int tickSize = (unit % majorTickInterval == 0) ? majorTickLength : minorTickLength;
            g2d.drawLine(x, midY - tickSize, x, midY + tickSize);
        }
        for (int x = midX; x >= 0; x -= scale) {
            int unit = (midX - x) / scale;
            int tickSize = (unit % majorTickInterval == 0) ? majorTickLength : minorTickLength;
            g2d.drawLine(x, midY - tickSize, x, midY + tickSize);
        }

        for (int y = midY; y <= height; y += scale) {
            int unit = (y - midY) / scale;
            int tickSize = (unit % majorTickInterval == 0) ? majorTickLength : minorTickLength;
            g2d.drawLine(midX - tickSize, y, midX + tickSize, y);
        }
        for (int y = midY; y >= 0; y -= scale) {
            int unit = (midY - y) / scale;
            int tickSize = (unit % majorTickInterval == 0) ? majorTickLength : minorTickLength;
            g2d.drawLine(midX - tickSize, y, midX + tickSize, y);
        }

        g2d.setColor(Color.RED);
        int prevX = 0, prevY = midY;
        for (int x = 0; x < width; x++) {
            double normX = (x - midX) / (double) scale;
            double normY = normX * normX;  
            int y = midY - (int) (normY * scale);
            if (x > 0) {
                g2d.drawLine(prevX, prevY, x, y);
            }
            prevX = x;
            prevY = y;
        }
    }
}