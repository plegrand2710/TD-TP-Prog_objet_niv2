package Tp2;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

public class AnimationThread extends Thread {
    private BoulesController controller;
    private PanneauBoules view;
    private volatile boolean running = true; 

    public AnimationThread(BoulesController controller, PanneauBoules view) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void run() {
        while (running) {
            Dimension size = view.getSize();
            
            SwingUtilities.invokeLater(() -> {
                controller.miseAJourBoules(size);
                view.repaint();
            });

            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void stopAnimation() {
        running = false;
    }
    
    public void resumeAnimation() {
        if (!running) {
            running = true;
            new Thread(this).start(); 
        }
    }
}