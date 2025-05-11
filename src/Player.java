public class Player {

    public String Nom;
    public int Sante;
    public int Chance;
    public int NbMedikit;
    public int Argent;
    public Noeud NoeudActuel;

    public Player(String nom, int sante, int chance, int nbMedikit, int argent, Noeud noeudActuel) {
        Nom = nom;
        Sante = sante;
        Chance = chance;
        NbMedikit = nbMedikit;
        Argent = argent;
        NoeudActuel = noeudActuel;
    }

}
