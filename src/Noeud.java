import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Noeud {

    public int Id;
    public String Description;
    public ImageIcon Illustration;
    public File Narration;
    public File Musique;
    public ArrayList<Choix> ListeChoix;
    public boolean EstMortel;
    public int Degat;

    public Noeud(int id, String description, ImageIcon illustration, File narration, File musique,
            ArrayList<Choix> listeChoix,
            boolean mort, int degat) {
        this.Id = id;
        this.Description = description;
        this.Illustration = illustration;
        this.Narration = narration;
        this.Musique = musique;
        this.ListeChoix = (listeChoix != null) ? listeChoix : new ArrayList<>();
        this.EstMortel = mort;
        this.Degat = degat;
    }

    // Méthode utilitaire pour arrêter et fermer un Clip
    private void stopAndCloseClip(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (clip != null) {
            clip.close();
        }
    }

    public void JouerAudio() {
        // Récupère les références actuelles des clips depuis le singleton
        Clip musiqueClipActuel = DataGame.getInstance().Clip_Musique;
        Clip narrationClipActuel = DataGame.getInstance().Clip_Narration;

        // Arrêter et fermer les clips existants avant d'en ouvrir de nouveaux
        stopAndCloseClip(musiqueClipActuel);
        stopAndCloseClip(narrationClipActuel);

        // Jouer la musique
        if (this.Musique != null) {
            try {
                AudioInputStream audioStreamMusique = AudioSystem.getAudioInputStream(this.Musique);
                // Crée un nouveau Clip et l'assigne directement au champ du singleton
                DataGame.getInstance().Clip_Musique = AudioSystem.getClip();
                DataGame.getInstance().Clip_Musique.open(audioStreamMusique);

                // Réduction du volume pour la musique
                FloatControl gainControlMusique = (FloatControl) DataGame.getInstance().Clip_Musique
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControlMusique.setValue(-15.0f); // Ajuste cette valeur selon tes besoins

                DataGame.getInstance().Clip_Musique.start();

                // Écouter la fin de la musique pour la fermer et réinitialiser la référence dans le singleton
                Clip finalMusiqueClip = DataGame.getInstance().Clip_Musique; // Capture la référence finale
                finalMusiqueClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        finalMusiqueClip.close();
                        // Important : réinitialiser la référence dans le singleton, pas une variable locale
                        DataGame.getInstance().Clip_Musique = null;
                    }
                });

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Erreur lors de la lecture de la musique : " + e.getMessage());
            }
        }

        // Jouer la narration
        if (this.Narration != null) {
            try {
                AudioInputStream audioStreamNarration = AudioSystem.getAudioInputStream(this.Narration);
                // Crée un nouveau Clip et l'assigne directement au champ du singleton
                DataGame.getInstance().Clip_Narration = AudioSystem.getClip();
                DataGame.getInstance().Clip_Narration.open(audioStreamNarration);

                DataGame.getInstance().Clip_Narration.start();

                // Écouter la fin de la narration pour la fermer et réinitialiser la référence dans le singleton
                Clip finalNarrationClip = DataGame.getInstance().Clip_Narration; // Capture la référence finale
                finalNarrationClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        finalNarrationClip.close();
                        // Important : réinitialiser la référence dans le singleton, pas une variable locale
                        DataGame.getInstance().Clip_Narration = null;
                    }
                });

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Erreur lors de la lecture de la narration : " + e.getMessage());
            }
        }
    }
}