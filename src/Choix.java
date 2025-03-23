public class Choix {

    private int _numero;
    private String _description;

    public int getNumero() {
        return _numero;
    }

    public void setNumero(int numero) {
        this._numero = Math.max(0, numero);
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }
}
