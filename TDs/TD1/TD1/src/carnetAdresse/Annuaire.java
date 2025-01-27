package carnetAdresse;

import java.util.ArrayList;
import java.io.*;

public class Annuaire {
    private ArrayList<Contact> liste;

    public Annuaire() {
        this.liste = new ArrayList<>();
    }

    public void ajouter(Contact contact) {
        liste.add(contact);
    }

    public void supprimer(int index) {
        if (index >= 0 && index < liste.size()) {
            liste.remove(index);
        }
    }

    public ArrayList<Contact> getListe() {
        return liste;
    }

    public Contact getContact(int index) {
        if (index >= 0 && index < liste.size()) {
            return liste.get(index);
        }
        return null;
    }

    public int getTaille() {
        return liste.size();
    }

    public void sauvegarder(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact c : liste) {
                writer.write(c.toString());
            }
        }
    }

    public void charger(String filePath) throws IOException {
        liste.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
            	this.getTaille();
                String[] data = ligne.split(" ; ");
                if (data.length == 9) {
                    Contact c = new Contact(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], Integer.parseInt(data[8]));
                    ajouter(c);
                }
            }
        }
    }
}
