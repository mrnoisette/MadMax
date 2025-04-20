import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AccesGame {

    // Format du fichier csv :
    // nom,idNoeud,sante,chance,nbmedikit,argent

    private String _cheminSave = "src/save.csv";

    public ArrayList<Player> LireListePlayer() {

        ArrayList<Player> listePlayer = new ArrayList<Player>();

        try (BufferedReader br = new BufferedReader(new FileReader(_cheminSave))) {

            // Ignorer la première ligne
            br.readLine();

            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] colonnes = ligne.split(",");

                String nom = colonnes[0];
                Noeud noeud = Noeud.TrouverNoeudSelonId(Integer.parseInt(colonnes[1]));
                int sante = Integer.parseInt(colonnes[2]);
                int chance = Integer.parseInt(colonnes[3]);
                int nbmedikit = Integer.parseInt(colonnes[4]);
                int argent = Integer.parseInt(colonnes[5]);

                var player = new Player(nom, sante, chance, nbmedikit, argent, noeud);
                listePlayer.add(player);
            }

        } catch (IOException e) {
            System.out.println("Erreur LireListePlayer :" + e.getMessage());
        }

        return listePlayer;
    }

    public void InsererPlayer(Player player) {
        try (FileWriter fw = new FileWriter(_cheminSave, true)) {
            fw.write(
                    player.Nom + "," +
                            player.NoeudActuel.Id + "," +
                            player.Sante + "," +
                            player.Chance + "," +
                            player.NbMedikit + "," +
                            player.Argent + "\n");
        } catch (IOException e) {
            System.out.println("Erreur InsererPlayer :" + e.getMessage());
        }
    }

    public void ModifierPlayer(Player player) {
        // TODO
    }

    public void SupprimerPlaye(Player player) {
        // TODO
    }

}
