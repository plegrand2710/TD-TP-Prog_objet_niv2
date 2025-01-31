package Tp2;

import java.awt.Dimension;

public class AnimationThread extends Thread {
    private BoulesController controller;
    private PanneauBoules view;

    public AnimationThread(BoulesController controller, PanneauBoules view) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            controller.miseAJourBoules(view.getSize());
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}