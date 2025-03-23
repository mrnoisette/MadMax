public class Interceptor {

    private int _puissance;
    private int _blindage;
    private int _carburant;
    private int _nbRoquette;
    private int _nbRoueSecours;

    public int getPuissance() {
        return _puissance;
    }

    public void setPuissance(int puissance) {
        this._puissance = Math.max(0, puissance);
    }

    public int getBlindage() {
        return _blindage;
    }

    public void setBlindage(int blindage) {
        this._blindage = Math.max(0, blindage);
    }

    public int getCarburant() {
        return _carburant;
    }

    public void setCarburant(int carburant) {
        this._carburant = Math.max(0, carburant);
    }

    public int getNbRoquette() {
        return _nbRoquette;
    }

    public void setNbRoquette(int nbRoquette) {
        this._nbRoquette = Math.max(0, nbRoquette);
    }

    public int getNbRoueSecours() {
        return _nbRoueSecours;
    }

    public void setNbRoueSecours(int nbRoueSecours) {
        this._nbRoueSecours = Math.max(0, nbRoueSecours);
    }
}
