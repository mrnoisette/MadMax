import java.io.File;
import java.util.ArrayList;

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
        // TODO 
    }

}