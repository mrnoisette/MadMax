public class DataGame {

    public DataGame() {
        _acces = new AccesGame();
    }

    private AccesGame _acces;

    private Player _currentPlayer;

    // Obtenir le joueur actuel
    public Player GetCurrentPlayer() {
        if (_currentPlayer == null) {
            return _acces.LirePlayer();
        } else {
            return _currentPlayer;
        }
    }

    // Modifier le joueur actuel
    public void SetCurrentPlayer(Player player) {
        _currentPlayer = player;
    }

    // Affiche avec TAB les infos du joueur
    public void AfficherInfo(){

    }

}
