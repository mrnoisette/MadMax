import java.util.ArrayList;

import javax.sound.sampled.Clip;

// Singleton thread safe 
public class DataGame {
    private static DataGame _instance;

    private AccesCSV _acces;

    private DataGame() {
        _acces = new AccesCSV();
    }

    // Synchronized pour eviter le partage de données obsolettes entre deux threads
    public static synchronized DataGame getInstance() {
        if (_instance == null) {
            _instance = new DataGame();
        }
        return _instance;
    }

    // ------------------------------------------------

    public Clip SystemeAudio;

    public Player CurrentPlayer;

    private ArrayList<Noeud> _listeNoeud;
    public ArrayList<Noeud> getListeNoeuds() {
        if (_listeNoeud == null) {
            _listeNoeud = new ArrayList<Noeud>();
            Story story = new Story();

            _listeNoeud.add(story.Introduction());
            _listeNoeud.add(story.Commencement());
            _listeNoeud.add(story.PanneAutoroute());
            _listeNoeud.add(story.FaireStop());
            _listeNoeud.add(story.SeCacher());

            return _listeNoeud;
        } else {
            return _listeNoeud;
        }
    }

    private ArrayList<Player> _listePlayer;
    public ArrayList<Player> getListePlayer() {
        if (_listePlayer == null) {
            _listePlayer = new ArrayList<Player>(_acces.LireListePlayer());
            return _listePlayer;
        } else {
            return _listePlayer;
        }
    }

    public void AjouterPlayer(Player player) {
        try {
            if (_listePlayer.contains(player)) {
                return;
            }
            _acces.InsererPlayer(player);
            _listePlayer.add(player);
        } catch (Exception e) {

        }
    }

    public void Sauvegarder(Player player) {
        try {
            _acces.Sauvegarder(player);
        } catch (Exception e) {

        }
    }

    public void SupprimerPlayer(Player player) {
        try {
            _acces.SupprimerPlayer(player);
            _listePlayer.remove(player);
        } catch (Exception e) {

        }
    }

}
