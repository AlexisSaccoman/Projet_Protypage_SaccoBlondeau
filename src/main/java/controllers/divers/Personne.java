package controllers.divers;

public class Personne {
    public String statut;
    public String login;
    public String password;

    public String css;

    public Personne(String statut, String login, String password) {
        this.statut = statut;
        this.login = login;
        this.password = password;
    }

    public Personne(String statut, String login, String password, String css) {
        this.statut = statut;
        this.login = login;
        this.password = password;
        this.css = css;
    }
}
