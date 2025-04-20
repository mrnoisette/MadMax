import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Noeud {

    public int Id;
    public String Description;
    public ImageIcon Illustration;
    public File Audio;
    public ArrayList<Choix> ListeChoix;

    public Noeud(int id, String description, ImageIcon illustration, File audio, ArrayList<Choix> listeChoix) {
        Id = id;
        Description = description;
        Illustration = illustration;
        Audio = audio;
        ListeChoix = listeChoix;
    }

    public static Noeud TrouverNoeudSelonId(int id) {
        for (var noeud : Story.ListeNoeud) {
            if (noeud.Id == id) {
                return noeud;
            }
        }
        return null;
    }

}
