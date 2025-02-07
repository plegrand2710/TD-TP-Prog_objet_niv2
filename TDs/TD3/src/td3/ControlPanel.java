package td3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

public class ControlPanel extends JPanel {
    private JSlider degreeSlider;
    private JTextField[] coefficientFields;
    private JPanel coefficientsPanel;
    
    private JTextField xMinField, xMaxField, yMinField, yMaxField;
    private JButton validateButton, resetButton;
    
    private JComboBox<String> presetComboBox;
    
    // Champs pour les espacements
    private JTextField xMinorField, xMajorField, yMinorField, yMajorField;
    
    private StylePanel stylePanel;
    
    private PolynomialManager polyManager;
    private GraphPanel graphPanel;
    
    public ControlPanel(PolynomialManager polyManager, GraphPanel graphPanel) {
        this.polyManager = polyManager;
        this.graphPanel = graphPanel;
        setPreferredSize(new Dimension(300, 800));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        addDegreeSlider();
        addPresetSelection();
        addCoefficientInput();
        addBoundsParameters();
        addSpacingParameters();
        addStylePanel();
        addButtons();
    }
    
    // Slider pour choisir le degré (entre 1 et 10)
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
    
    // Menu déroulant pour sélectionner un polynôme prédéfini
    private void addPresetSelection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Polynômes prédéfinis"));
        presetComboBox = new JComboBox<>();
        Map<String, double[]> presets = PresetLoader.getPresets();
        presetComboBox.addItem("Sélectionnez...");
        for (String key : presets.keySet()) {
            presetComboBox.addItem(key);
        }
        presetComboBox.addActionListener(e -> {
            String selected = (String) presetComboBox.getSelectedItem();
            if (!"Sélectionnez...".equals(selected)) {
                double[] coeffs = presets.get(selected);
                // Ajuste le slider selon le degré (coeffs.length - 1)
                degreeSlider.setValue(coeffs.length - 1);
                updateCoefficientFields();
                // Remplit automatiquement les champs avec les coefficients prédéfinis
                for (int i = 0; i < coeffs.length; i++) {
                    coefficientFields[i].setText(String.valueOf(coeffs[i]));
                }
                // Appel immédiat à la validation pour afficher le polynôme
                onValidate();
            }
        });
        panel.add(presetComboBox, BorderLayout.CENTER);
        add(panel);
    }
    
    // Création dynamique des champs de saisie pour les coefficients
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
    
    // Mise à jour dynamique des champs de coefficient lorsque le degré change
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
    
    // Saisie des bornes d'affichage
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
    
    // Ajoute les paramètres d'espacement pour les graduations
    private void addSpacingParameters() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Paramètres d'espacement"));
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
    
    // Ajoute le panneau StylePanel pour le choix du mode et de la couleur
    private void addStylePanel() {
        stylePanel = new StylePanel();
        add(stylePanel);
    }
    
    // Boutons d'action
    private void addButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        validateButton = new JButton("Valider");
        resetButton = new JButton("Réinitialiser");
        panel.add(validateButton);
        panel.add(resetButton);
        
        // La validation crée le polynôme, met à jour les bornes et espacements, puis déclenche le rendu
        validateButton.addActionListener(e -> onValidate());
        resetButton.addActionListener(e -> onReset());
        
        add(panel);
    }
    
    private void onValidate() {
        try {
            // Récupération des bornes
            double xMin = Double.parseDouble(xMinField.getText());
            double xMax = Double.parseDouble(xMaxField.getText());
            double yMin = Double.parseDouble(yMinField.getText());
            double yMax = Double.parseDouble(yMaxField.getText());
            graphPanel.setBoundsParameters(xMin, xMax, yMin, yMax);
            
            // Récupération des coefficients
            int degree = degreeSlider.getValue();
            double[] coeffs = new double[degree + 1];
            for (int i = 0; i < coeffs.length; i++) {
                try {
                    coeffs[i] = Double.parseDouble(coefficientFields[i].getText());
                } catch (NumberFormatException ex) {
                    coeffs[i] = 0;
                }
            }
            
            // Récupération des espacements
            double xMinor = Double.parseDouble(xMinorField.getText());
            double xMajor = Double.parseDouble(xMajorField.getText());
            double yMinor = Double.parseDouble(yMinorField.getText());
            double yMajor = Double.parseDouble(yMajorField.getText());
            graphPanel.setSpacingParameters(xMinor, xMajor, yMinor, yMajor);
            
            // Récupération du style et de la couleur
            String style = stylePanel.getSelectedStyle();
            Color color = stylePanel.getSelectedColor();
            
            // Création du polynôme et ajout au gestionnaire
            Polynomial poly = new Polynomial(coeffs, color, style);
            polyManager.addPolynomial(poly);
            
            // Mise à jour immédiate du rendu graphique
            graphPanel.render();
            graphPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la validation : " + ex.getMessage());
        }
    }
    
    private void onReset() {
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
        polyManager.clearPolynomials();
        graphPanel.render();
        graphPanel.repaint();
    }
}