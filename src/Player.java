public class Player {

    private String _nom;
    private int _habilite;
    private int _endurance;
    private int _chance;
    private int _nbMedikit;
    private int _credit;

    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public int getHabilite() {
        return _habilite;
    }

    public void setHabilite(int habilite) {
        this._habilite = Math.max(0, habilite);
    }

    public int getEndurance() {
        return _endurance;
    }

    public void setEndurance(int endurance) {
        this._endurance = Math.max(0, endurance);
    }

    public int getChance() {
        return _chance;
    }

    public void setChance(int chance) {
        this._chance = Math.max(0, chance);
    }

    public int getNbMedikit() {
        return _nbMedikit;
    }

    public void setNbMedikit(int nbMedikit) {
        this._nbMedikit = Math.max(0, nbMedikit);
    }

    public int getCredit() {
        return _credit;
    }

    public void setCredit(int credit) {
        this._credit = Math.max(0, credit);
    }
}
