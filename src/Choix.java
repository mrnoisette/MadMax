public class Choix {

    private String _libelle;
    private Noeud _prochainNoeud;

    public String getLibelle() {
        return _libelle;
    }

    public void setLibelle(String description) {
        this._libelle = description;
    }

    public Noeud getProchainNoeud() {
        return _prochainNoeud;
    }

    public void setProchainNoeud(Noeud noeud) {
        this._prochainNoeud = noeud;
    }
}
