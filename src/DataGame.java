import java.util.ArrayList;

// Singleton thread safe 
public class DataGame {
    private static DataGame _instance;

    private AccesGame _acces;

    private DataGame() {
        _acces = new AccesGame();
        ListePlayer = _acces.LireListePlayer();
    }

    // Synchronized pour eviter le partage de données obsolettes entre deux threads
    public static synchronized DataGame getInstance() {
        if (_instance == null) {
            _instance = new DataGame();
        }
        return _instance;
    }

    public ArrayList<Player> ListePlayer;

    public void AjouterPlayer(Player player) {
        try {
            if (ListePlayer.contains(player)) {
                return;
            }
            _acces.InsererPlayer(player);
            ListePlayer.add(player);
        } catch (Exception e) {

        }
    }

    public void ModifierPlayer(Player player) {
        // TODO
    }

    public void SupprimerPlayer(Player player) {
        try {
            _acces.SupprimerPlayer(player);
            ListePlayer.remove(player);
        } catch (Exception e) {

        }
    }

    public Player CurrentPlayer;

}
