package controllers.divers;

public class Personne {
    public String statut;
    public String login;

    public String password;

    public Personne(String statut, String login, String password) {
        this.statut = statut;
        this.login = login;
        this.password = password;
        System.out.println("Personne créée --> " + login + " est " + statut);
    }
}
