package td3;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class StylePanel extends JPanel {
    private JRadioButton lineButton;
    private JRadioButton pointsButton;
    private JRadioButton crossButton;
    private JSlider redSlider, greenSlider, blueSlider;

    public StylePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Style et Couleur de la courbe"));

        // Boutons radio pour le style
        JPanel styleSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lineButton = new JRadioButton("Linéaire", true);
        pointsButton = new JRadioButton("Points");
        crossButton = new JRadioButton("Croix");

        ButtonGroup group = new ButtonGroup();
        group.add(lineButton);
        group.add(pointsButton);
        group.add(crossButton);

        styleSelectionPanel.add(lineButton);
        styleSelectionPanel.add(pointsButton);
        styleSelectionPanel.add(crossButton);
        add(styleSelectionPanel, BorderLayout.NORTH);

        // Sliders pour la couleur (RGB)
        JPanel colorPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Couleur (RGB)"));

        redSlider = new JSlider(0, 255, 0);
        greenSlider = new JSlider(0, 255, 0);
        blueSlider = new JSlider(0, 255, 255);

        colorPanel.add(new JLabel("Rouge:"));
        colorPanel.add(redSlider);
        colorPanel.add(new JLabel("Vert:"));
        colorPanel.add(greenSlider);
        colorPanel.add(new JLabel("Bleu:"));
        colorPanel.add(blueSlider);

        add(colorPanel, BorderLayout.CENTER);
    }

    public Color getSelectedColor() {
        return new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
    }

    public String getSelectedStyle() {
        if (lineButton.isSelected()) return "Linéaire";
        if (pointsButton.isSelected()) return "Points";
        if (crossButton.isSelected()) return "Croix";
        return "Linéaire";
    }

    public void reset() {
        lineButton.setSelected(true);
        redSlider.setValue(0);
        greenSlider.setValue(0);
        blueSlider.setValue(255);
    }
}