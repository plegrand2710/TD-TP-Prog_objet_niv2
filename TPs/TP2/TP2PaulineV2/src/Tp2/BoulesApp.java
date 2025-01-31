package Tp2;

import javax.swing.*;

public class BoulesApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BoulesFrame frame = new BoulesFrame();
            frame.setVisible(true);
        });
    }
}