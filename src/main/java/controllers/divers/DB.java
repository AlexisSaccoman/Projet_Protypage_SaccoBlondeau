package controllers.divers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DB {
    public ArrayList<Personne> personnes = new ArrayList<Personne>();

    public DB(){
        readAndAdd();
    }

    public void readAndAdd(){ // lit le fichier users.txt et ajoute les utilisateurs à la liste de personnes
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Alexis\\Documents\\files\\Cours\\master\\Semestre 8\\UE Prototypage\\Projet_Protypage_SaccoBlondeau\\src\\main\\java\\db\\users.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(";");
                personnes.add(new Personne(parts[0], parts[1], parts[2]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Personne verify(String login, String pw){ // vérifie pour des identifiants donnés si ils sont corrects. Si oui on return un objet Personne
        for(Personne p : personnes){
            if(p.login.equals(login) && p.password.equals(pw)){
                System.out.println("Personne connectée --> " + p.login + " est " + p.statut);
                return p;
            }
        }
        System.out.println("Login ou mot de passe incorrect");
        return null;
    }
}
