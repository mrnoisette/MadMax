
public class App {

    static DataGame Data; // Static == instance ?

    public static void main(String[] args) {

        // Initialisation de Data
        Data = new DataGame();

        // Lancement du jeu
        var game = new Game();

        game.AfficherNoeud(Story.Introduction());

    }
}
