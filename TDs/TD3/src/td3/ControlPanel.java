package td3;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JSlider degreeSlider;
    private JTextField[] coefficientFields; 
    private JPanel coefficientsPanel;
    private JTextField xMinField, xMaxField, yMinField, yMaxField;
    private JTextField xMinorField, xMajorField, yMinorField, yMajorField;
    private JButton validateButton, resetButton;

    private StylePanel stylePanel;

    private GraphPanel graphPanel;

    public ControlPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        setPreferredSize(new Dimension(300, 800));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addDegreeSlider();
        addCoefficientInput();
        addBoundsParameters();
        addSpacingParameters();
        addStylePanel();
        addButtons();
    }

    private void addDegreeSlider() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Degré du polynôme"));

        degreeSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        degreeSlider.setMajorTickSpacing(1);
        degreeSlider.setPaintTicks(true);
        degreeSlider.setPaintLabels(true);
        degreeSlider.addChangeListener(e -> updateCoefficientFields());
        panel.add(degreeSlider, BorderLayout.CENTER);
        add(panel);
    }

    private void addCoefficientInput() {
        coefficientsPanel = new JPanel();
        coefficientsPanel.setLayout(new GridLayout(0, 2, 5, 5));
        coefficientsPanel.setBorder(BorderFactory.createTitledBorder("Saisie des coefficients"));

        int degree = degreeSlider.getValue();
        coefficientFields = new JTextField[degree + 1];
        for (int i = 0; i < coefficientFields.length; i++) {
            JLabel label = new JLabel("Coefficient x^" + i + ": ");
            coefficientFields[i] = new JTextField("0", 5);
            coefficientsPanel.add(label);
            coefficientsPanel.add(coefficientFields[i]);
        }
        add(coefficientsPanel);
    }

    private void updateCoefficientFields() {
        coefficientsPanel.removeAll();
        int degree = degreeSlider.getValue();
        coefficientFields = new JTextField[degree + 1];
        for (int i = 0; i < coefficientFields.length; i++) {
            JLabel label = new JLabel("Coefficient x^" + i + ": ");
            coefficientFields[i] = new JTextField("0", 5);
            coefficientsPanel.add(label);
            coefficientsPanel.add(coefficientFields[i]);
        }
        coefficientsPanel.revalidate();
        coefficientsPanel.repaint();
    }

    private void addBoundsParameters() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Paramètres des bornes"));

        xMinField = new JTextField("-10", 5);
        xMaxField = new JTextField("10", 5);
        yMinField = new JTextField("-10", 5);
        yMaxField = new JTextField("10", 5);

        panel.add(new JLabel("X Min:"));
        panel.add(xMinField);
        panel.add(new JLabel("X Max:"));
        panel.add(xMaxField);
        panel.add(new JLabel("Y Min:"));
        panel.add(yMinField);
        panel.add(new JLabel("Y Max:"));
        panel.add(yMaxField);

        add(panel);
    }

    private void addSpacingParameters() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Paramètres d’espacement"));

        xMinorField = new JTextField("1", 5);
        xMajorField = new JTextField("5", 5);
        yMinorField = new JTextField("1", 5);
        yMajorField = new JTextField("5", 5);

        panel.add(new JLabel("X Mineur:"));
        panel.add(xMinorField);
        panel.add(new JLabel("X Majeur:"));
        panel.add(xMajorField);
        panel.add(new JLabel("Y Mineur:"));
        panel.add(yMinorField);
        panel.add(new JLabel("Y Majeur:"));
        panel.add(yMajorField);

        add(panel);
    }

    private void addStylePanel() {
        stylePanel = new StylePanel();
        add(stylePanel);
    }

    private void addButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        validateButton = new JButton("Valider");
        resetButton = new JButton("Réinitialiser");
        panel.add(validateButton);
        panel.add(resetButton);

        validateButton.addActionListener(e -> graphPanel.repaint());
        resetButton.addActionListener(e -> resetParameters());

        add(panel);
    }

    private void resetParameters() {
        degreeSlider.setValue(1);
        updateCoefficientFields();

        xMinField.setText("-10");
        xMaxField.setText("10");
        yMinField.setText("-10");
        yMaxField.setText("10");

        xMinorField.setText("1");
        xMajorField.setText("5");
        yMinorField.setText("1");
        yMajorField.setText("5");

        stylePanel.reset();

        graphPanel.repaint();
    }

}