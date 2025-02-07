package td3;

import javax.swing.*;
import java.awt.*;

public class GeogebraApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Visualiseur Graphique de Polynômes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLayout(new BorderLayout());

            GraphPanel graphPanel = new GraphPanel();
            frame.add(graphPanel, BorderLayout.CENTER);

            ControlPanel controlPanel = new ControlPanel(graphPanel);
            frame.add(controlPanel, BorderLayout.WEST);

            frame.setVisible(true);
        });
    }
}