package TP3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GestionnaireOnglets extends JFrame {

    private JTabbedPane tabbedPane;
    private final File sessionFile = new File("autosave.ser");


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
        JMenuItem itemImporter = new JMenuItem("Importer dessin");


        menuFichier.add(itemAjouter);
        menuFichier.add(itemSupprimer);
        menuFichier.addSeparator();
        menuFichier.add(itemSauvegarder);
        menuFichier.add(itemImporter);

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
        
        itemImporter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	importerDessin();
            }
        });
        
        if (!restaurerSession()) {
            ajouterOnglet();
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sauvegarderSession();
            }
        });

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

    private void importerDessin() {
        int index = tabbedPane.getSelectedIndex();
        if (index != -1) {
            VueDessin vue = (VueDessin) tabbedPane.getComponentAt(index);
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    vue.getModele().effacer();
                    line = reader.readLine();
                    if (line != null && line.trim().toUpperCase().startsWith("TYPE")) {
                    } else {
                        if (line != null) {
                            traiterLigne(line, vue);
                        }
                    }
                    while ((line = reader.readLine()) != null) {
                        traiterLigne(line, vue);
                    }
                    vue.getZoneDessin().repaint();
                    JOptionPane.showMessageDialog(this, "Dessin importé avec succès !");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'importation : " + ex.getMessage());
                }
            }
        }
    }
    
    private void traiterLigne(String line, VueDessin vue) {
        if (line.trim().isEmpty()) {
            return;
        }
        String[] parts = line.split(",");
        if (parts.length < 5) {
            return; 
        }
        try {
            OutilDessin outil = OutilDessin.valueOf(parts[0].trim());
            float epaisseur = Float.parseFloat(parts[1].trim());
            boolean rempli = Boolean.parseBoolean(parts[2].trim());
            Color couleur = Color.decode(parts[3].trim());
            Forme f = new Forme(outil, couleur, epaisseur);
            if (outil == OutilDessin.RECTANGLE || outil == OutilDessin.CERCLE) {
                f.setRempli(rempli);
            }
            String pointsStr = parts[4].trim();
            String[] pts = pointsStr.split("\\|");
            for (String pt : pts) {
                String[] xy = pt.split(";");
                if (xy.length == 2) {
                    int x = Integer.parseInt(xy[0].trim());
                    int y = Integer.parseInt(xy[1].trim());
                    f.ajouterPoint(new Point(x, y));
                }
            }
            vue.getModele().ajouterForme(f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
    
    private void sauvegarderSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sessionFile))) {
            ArrayList<ModeleDessin> session = new ArrayList<>();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                VueDessin vue = (VueDessin) tabbedPane.getComponentAt(i);
                session.add(vue.getModele());
            }
            oos.writeObject(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean restaurerSession() {
        if (!sessionFile.exists()) return false;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sessionFile))) {
            @SuppressWarnings("unchecked")
			ArrayList<ModeleDessin> session = (ArrayList<ModeleDessin>) ois.readObject();
            for (ModeleDessin m : session) {
                VueDessin vue = new VueDessin(m);
                tabbedPane.addTab("Dessin " + (tabbedPane.getTabCount() + 1), vue);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}