import java.util.ArrayList;

import javax.sound.sampled.Clip;

// Singleton thread safe 
public class DataGame {
    private static DataGame _instance;

    // Acces au fichier CSV (Players)
    private AccesCSV _accesCSV;

    // Acces au fichier JSON (Noeuds)
    private AccesXML _accesXML;

    private DataGame() {
        _accesCSV = new AccesCSV();
        _accesXML = new AccesXML();
    }

    // Synchronized pour eviter le partage de données obsolettes entre deux threads
    public static synchronized DataGame getInstance() {
        if (_instance == null) {
            _instance = new DataGame();
        }
        return _instance;
    }

    // ------------------------------------------------

    public Clip Clip_Narration;
    public Clip Clip_Musique;

    public Player Player;

    private ArrayList<Noeud> _listeNoeud;
    public ArrayList<Noeud> getListeNoeuds() {
        if (_listeNoeud == null) {
            _listeNoeud = new ArrayList<Noeud>(_accesXML.LireListeNoeud());
        }
        return _listeNoeud;
    }

    private ArrayList<Player> _listePlayer;
    public ArrayList<Player> getListePlayer() {
        if (_listePlayer == null) {
            _listePlayer = new ArrayList<Player>(_accesCSV.LireListePlayer());
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
            _accesCSV.InsererPlayer(player);
            _listePlayer.add(player);
        } catch (Exception e) {

        }
    }

    public void Sauvegarder(Player player) {
        try {
            _accesCSV.Sauvegarder(player);
        } catch (Exception e) {

        }
    }

    public void SupprimerPlayer(Player player) {
        try {
            _accesCSV.SupprimerPlayer(player);
            _listePlayer.remove(player);
        } catch (Exception e) {

        }
    }

}
