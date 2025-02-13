package TP3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GestionnaireOnglets extends JFrame {

    private JTabbedPane tabbedPane;

    public GestionnaireOnglets() {
        setTitle("Gestionnaire d'Onglets - Dessins");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFichier = new JMenu("Fichier");

        JMenuItem itemAjouter = new JMenuItem("Ajouter onglet");
        JMenuItem itemSupprimer = new JMenuItem("Supprimer onglet");
        JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder dessin");

        menuFichier.add(itemAjouter);
        menuFichier.add(itemSupprimer);
        menuFichier.addSeparator();
        menuFichier.add(itemSauvegarder);
        menuBar.add(menuFichier);
        setJMenuBar(menuBar);

        itemAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterOnglet();
            }
        });

        itemSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerOnglet();
            }
        });

        itemSauvegarder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sauvegarderDessin();
            }
        });

        ajouterOnglet();

        setVisible(true);
    }

    private void ajouterOnglet() {
    	VueDessin vue = new VueDessin(new ModeleDessin());
    	tabbedPane.addTab("Dessin " + (tabbedPane.getTabCount() + 1), vue);
        tabbedPane.setSelectedComponent(vue);
    }

    private void supprimerOnglet() {
        int index = tabbedPane.getSelectedIndex();
        if (index != -1) {
            tabbedPane.removeTabAt(index);
        }
    }

    private void sauvegarderDessin() {
        int index = tabbedPane.getSelectedIndex();
        if (index != -1) {
            VueDessin vue = (VueDessin) tabbedPane.getComponentAt(index);
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getParentFile(), file.getName() + ".txt");
                }
                if (file.exists()) {
                    int rep = JOptionPane.showConfirmDialog(this,
                            "Le fichier existe déjà. Voulez-vous l'écraser ?",
                            "Confirmer",
                            JOptionPane.YES_NO_OPTION);
                    if (rep != JOptionPane.YES_OPTION) {
                        return; 
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("TYPE,EP,REMPLI,COLOR,POINTS");
                    writer.newLine();
                    for (Forme f : vue.getModele().getFormes()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(f.getOutil().toString()).append(",");
                        sb.append(f.getEpaisseur()).append(",");
                        sb.append(f.getRempli()).append(",");
                        sb.append(colorToHex(f.getCouleur())).append(",");
                        for (int i = 0; i < f.getPoints().size(); i++) {
                            Point p = f.getPoints().get(i);
                            sb.append(p.x).append(";").append(p.y);
                            if (i < f.getPoints().size() - 1) {
                                sb.append("|");
                            }
                        }
                        writer.write(sb.toString());
                        writer.newLine();
                    }
                    writer.flush();
                    JOptionPane.showMessageDialog(this, "Dessin sauvegardé avec succès !");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + ex.getMessage());
                }
            }
        }
    }

    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GestionnaireOnglets();
            }
        });
    }
}