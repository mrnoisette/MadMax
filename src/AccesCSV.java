import java.io.*;
import java.util.ArrayList;

public class AccesCSV {

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

                int idNoeud = Integer.parseInt(colonnes[1]);
                Noeud noeud = null;
                
                for (var n : DataGame.getInstance().getListeNoeuds()) {
                    if (n.Id == idNoeud) {
                        noeud = n;
                        break;
                    }
                }

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

    public void Sauvegarder(Player player) {
        ArrayList<String> listeLigne = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(_cheminSave))) {

            // Ignorer la première ligne (en-tête)
            br.readLine();

            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] colonnes = ligne.split(",");

                // Mettre à jour si c'est le bon joueur
                if (colonnes[0].equals(player.Nom)) {
                    String nouvelleLigne = player.Nom + "," +
                            player.NoeudActuel.Id + "," +
                            player.Sante + "," +
                            player.Chance + "," +
                            player.NbMedikit + "," +
                            player.Argent;
                    listeLigne.add(nouvelleLigne);
                } else {
                    listeLigne.add(ligne);
                }
            }

        } catch (IOException e) {
            System.out.println("Erreur Sauvegarder (lecture) : " + e.getMessage());
            return;
        }

        // Réécrire le fichier entier avec la ligne mise à jour
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(_cheminSave))) {
            // Réécrire la première ligne (en-tête) si nécessaire
            bw.write("nom,idNoeud,sante,chance,nbmedikit,argent");
            bw.newLine();

            for (String l : listeLigne) {
                bw.write(l);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erreur Sauvegarder (ecriture) : " + e.getMessage());
        }
    }

    public void SupprimerPlayer(Player player) {
        ArrayList<String> listeLigne = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(_cheminSave))) {

            // Ignorer la première ligne (en-tête)
            br.readLine();

            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] colonnes = ligne.split(",");

                // Comparer les noms de manière correcte avec equals()
                if (colonnes[0].equals(player.Nom)) {
                    continue; // Ne pas ajouter la ligne avec le joueur à supprimer
                } else {
                    listeLigne.add(ligne);
                }
            }

        } catch (IOException e) {
            System.out.println("Erreur SupprimerPlayer (lecture) :" + e.getMessage());
        }

        // Réécrire les lignes sans la ligne supprimée
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(_cheminSave))) {
            // Réécrire la première ligne (en-tête) si nécessaire
            bw.write("nom,idNoeud,sante,chance,nbmedikit,argent");
            bw.newLine();

            // Réécrire les profils
            for (String l : listeLigne) {
                bw.write(l);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erreur SupprimerPlayer (ecriture) :" + e.getMessage());
        }
    }

}
