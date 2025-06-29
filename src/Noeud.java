import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    public void JouerAudios() {
        var clipNarration = DataGame.getInstance().Clip_Narration;
        var clipMusique = DataGame.getInstance().Clip_Musique;

        // Arreter les anciens audios
        if (clipNarration != null && clipNarration.isActive()) {
            FermerAudio(clipNarration);
        }
        if (clipMusique != null && clipMusique.isActive()) {
            FermerAudio(clipMusique);
        }

        // Lecture des audios
        Clip nouveauClipNarration = JouerAudio(this.Narration);
        Clip nouveauClipMusique = JouerAudio(this.Musique);

        // Mise à jour dans DataGame
        DataGame.getInstance().Clip_Narration = nouveauClipNarration;
        DataGame.getInstance().Clip_Musique = nouveauClipMusique;
    }

    private void FermerAudio(Clip clip) {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    private Clip JouerAudio(File fichierAudio) {
        if (fichierAudio == null)
            return null;

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fichierAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Joue une seule fois
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }

}