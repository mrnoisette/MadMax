import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Story {

    public static ArrayList<Noeud> ListeNoeud = initialiserListe();

    private static ArrayList<Noeud> initialiserListe() {
        ArrayList<Noeud> liste = new ArrayList<>();

        liste.add(Introduction());
        liste.add(Commencement());
        liste.add(PanneAutoroute());
        liste.add(SeCacher());
        liste.add(FaireStop());

        return liste;
    }
    
    public static Noeud Introduction() {

        String description = "A la suite d'innombrables années d'ignorance, et malgré les alertes que notre planètes nous a envoyé, celle-ci ne pouvait plus encaisser le poids de nos actes.."
                + "\n\n"
                + "La sécheresse, issues de l'épuisement de l'ensemble des ressources planétaires, emmena l'humanité dans ses tréfonds, et l'avènement d'une époque sinitre faisait surface."
                + "\n\n"
                + "Désormais, le pétrol, et l'eau, étaient les seules denrées qui avaient de la valeur dans ce monde...";

        ImageIcon illustration = new ImageIcon("./assets/images/introduction.jpeg");

        var listeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.Libelle = "Continuer";
        choix1.ProchainNoeud = Commencement();
        listeChoix.add(choix1);

        var noeud = new Noeud(1, description, illustration, null, listeChoix);
        return noeud;
    }

    public static Noeud Commencement() {

        String description = "En tant qu'agent de police, votre rôle est de vous rendre à San Pedro pour récupérer une citerne d'eau, qui pourra donner un espoir de vie à votre colonie"
                + "\n\n"
                + "Pour cela, votre équipe vous a spécialement préparée une Dodge Interceptor afin de pouvoir braver cet impitoyable dessert et ses danger."
                + "\n\n"
                + "Vous embarquez donc dans votre véhicule, et partez à l'aventure.";

        ImageIcon illustration = new ImageIcon("./assets/images/interceptor.jpeg");

        var audio = new File("./assets/sounds/motor.wav");

        var listeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.Libelle = "Continuer";
        choix1.ProchainNoeud = PanneAutoroute();
        listeChoix.add(choix1);

        var noeud = new Noeud(2, description, illustration, audio, listeChoix);
        return noeud;
    }

    public static Noeud PanneAutoroute() {

        String description = "Après de longue heures de route, vous avez enfin quitter la ville. Vous ne reconnaissez toutefois aucun des paysages alentour, dans lesquels vous avez pourtant grandi."
                + "\n\n"
                + "Plongez dans vos souvenir, un bruit sourd vous secous, faisant vibrer la caisse de l'interceptor dans tous les sens. Pris de panique, vous vous arretez sur le bas coté, ouvrez le capot, et vous êtes pris d'une toux éttoufante avec toute cette fumée qui sort du moteur."
                + "\n\n"
                + "Votre véhicule à surchauffer, et le liquide de refroidissement s'est évaporée. Vous devez en retrouver pour continuer."
                + "\n\n"
                + "Vous appercevez néanmoins une silhouette au loins. Il s'agit d'un camion qui approche. Que faites-vous ?";

        ImageIcon illustration = new ImageIcon("./assets/images/panneAutoroute.png");

        var listeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.Libelle = "Faire du stop.";
        choix1.ProchainNoeud = FaireStop();
        listeChoix.add(choix1);

        var choix2 = new Choix();
        choix2.Libelle = "Se cacher.";
        choix2.ProchainNoeud = SeCacher();
        listeChoix.add(choix2);

        var noeud = new Noeud(3, description, illustration, null, listeChoix);
        return noeud;
    }

    public static Noeud SeCacher() {

        String description = "Un panneau publicitaire se trouvant à votre gauche, vous vous y précipité rapidement pour vous y cacher."
                + "\n\n"
                + "Le véhicule approchant peu à peu, vous dicerner une bande de bandit. Vous avez bien fais de vous cacher. Qui sait ce qu'ils vous auraient fait !";

        ImageIcon illustration = null; // TODO

        var listeChoix = new ArrayList<Choix>();

        var audio = new File("./assets/sounds/gunfire.wav");

        var choix1 = new Choix();
        choix1.Libelle = "Continuer";
        choix1.ProchainNoeud = null; // TODO

        listeChoix.add(choix1);

        return new Noeud(4, description, illustration, audio, listeChoix);
    }

    public static Noeud FaireStop() {

        // TODO

        return null;
    }

}
