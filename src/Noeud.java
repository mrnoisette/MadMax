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

    public Noeud(int id, String description, ImageIcon illustration, File narration, File musique, ArrayList<Choix> listeChoix,
            boolean mort, int degat) {
        this.Id = id;
        this.Description = description;
        this.Illustration = illustration;
        this.Narration = narration;
        this.Musique = musique;
        this.ListeChoix = (listeChoix != null) ? listeChoix : new ArrayList<>(); // Bonus : sécurise aussi les choix
        this.EstMortel = mort;
        this.Degat = degat;
    }

    public void JouerAudio() {

        var systemeAudio = DataGame.getInstance().SystemeAudio;

        // Arrêter et fermer l'ancien audio 
        if (systemeAudio != null && systemeAudio.isRunning()) {
            systemeAudio.stop();
            if (systemeAudio != null) { // A revoir & tester si nécéssaire
                systemeAudio.close();
            }
        }

        if (this.Musique != null && this.Musique.isRunning()) {
            this.Musique .stop();
        }
        if (this.Narration != null && this.Narration.isRunning()) {
            this.Narration.stop();
        }

        if (file == null)
            return;

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Réduction du volume uniquement pour la musique
            if (estMusique) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
            }

            clip.start();

            // Écouter la fin du son pour réinitialiser l'état
            Clip finalClip = clip;
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    finalClip.close();
                    if (estMusique) {
                        _musique = null;
                    } else {
                        _voix = null;
                    }
                }
            });

            // Mettre à jour le bon champ
            if (estMusique) {
                _musique = clip;
            } else {
                _voix = clip;
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erreur lors de la lecture du son : " + e.getMessage());
        }

    }

}
