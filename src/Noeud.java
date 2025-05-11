import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Noeud {

    public int Id;
    public String Description;
    public ImageIcon Illustration;
    public File Audio;
    public ArrayList<Choix> ListeChoix;
    public boolean Mort;

    public Noeud(int id, String description, ImageIcon illustration, File audio, ArrayList<Choix> listeChoix,
            boolean mort) {
        Id = id;
        Description = description;
        Illustration = illustration;
        Audio = audio;
        ListeChoix = listeChoix;
        Mort = mort;
    }

}
