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

            PolynomialManager polyManager = new PolynomialManager();

            GraphPanel graphPanel = new GraphPanel(polyManager);
            frame.add(graphPanel, BorderLayout.CENTER);

            ControlPanel controlPanel = new ControlPanel(polyManager, graphPanel);
            JScrollPane scrollPane = new JScrollPane(controlPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(320, 800));
            frame.add(scrollPane, BorderLayout.WEST);

            JLabel coordLabel = new JLabel("Coordonnées: ");
            frame.add(coordLabel, BorderLayout.SOUTH);
            new InteractionManager(graphPanel, polyManager, coordLabel);

            frame.setVisible(true);
        });
    }
}