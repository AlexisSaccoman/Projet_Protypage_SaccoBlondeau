import parsing.ICSParsing;


public class Main {
    public static void main(String[] args) {

        ICSParsing parseur = new ICSParsing();

        parseur.parse("parsing/data/sacco_1.ics");
    }
}