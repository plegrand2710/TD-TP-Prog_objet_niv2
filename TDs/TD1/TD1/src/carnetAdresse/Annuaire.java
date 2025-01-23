package carnetAdresse;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Annuaire {

    private int num = 0;
    private ArrayList<ContactIni> liste;

    public ArrayList<ContactIni> get_liste() {
        return this.liste;
    }

    public void set_liste(ArrayList<ContactIni> a) {
        this.liste = a;
    }

    public int get_num() {
        return num;
    }

    public void set_num(int i) {
        num = i;
    }

    public void inc_num() {
        num++;
    }

    public void dec_num() {
        num--;
    }

    public Annuaire() {
        set_liste(new ArrayList<ContactIni>());
    }

    public void ajout(ContactIni c) {
        get_liste().add(c);
        inc_num();
    }

    public void supprimer(int i, String filePath) {
        get_liste().remove(i);
        dec_num();
        this.sauvegarder(filePath);
    }

    public void ecritureContact(String filePath, ContactIni ctt) {
        try (FileOutputStream fichier = new FileOutputStream(filePath, true)) {
            fichier.write(ctt.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sauvegarder(String filePath) {
        try (FileOutputStream fichier = new FileOutputStream(filePath)) {
            for (int i = 0; i < get_num(); i++) {
                fichier.write(get_liste().get(i).toString().getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lectureContacts(String filePath) {
        try (FileInputStream fichier = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(fichier);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String ligne;
            get_liste().clear();

            while ((ligne = bufferedReader.readLine()) != null) {
                String[] infos = ligne.split(" ; ");
                if (infos.length == 9) {
                    String nom = infos[0];
                    String prenom = infos[1];
                    String tel = infos[2];
                    String adresse = infos[3];
                    String cp = infos[4];
                    String email = infos[5];
                    String metier = infos[6];
                    String situation = infos[7];
                    int miniature = Integer.parseInt(infos[8]);
                    ContactIni temp = new ContactIni(nom, prenom, tel, adresse, cp, email, situation, metier, miniature);
                    this.ajout(temp);
                }
            }
            this.set_num(this.get_liste().size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
