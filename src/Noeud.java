import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Noeud {

    public int Id;
    public String Description;
    public ImageIcon Illustration;
    public File[] Audio;
    public ArrayList<Choix> ListeChoix;
    public boolean Mort;
    public int Degat;

    public Noeud(int id, String description, ImageIcon illustration, File[] audio, ArrayList<Choix> listeChoix,
            boolean mort, int degat) {
        this.Id = id;
        this.Description = description;
        this.Illustration = illustration;
        this.Audio = (audio != null) ? audio : new File[0]; // Sécurisation ici
        this.ListeChoix = (listeChoix != null) ? listeChoix : new ArrayList<>(); // Bonus : sécurise aussi les choix
        this.Mort = mort;
        this.Degat = degat;
    }

}
