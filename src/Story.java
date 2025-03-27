import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Story {

    public static Noeud Introduction() {
        var noeud = new Noeud();

        noeud.Description = "A la suite d'innombrables années d'ignorance, et malgré les alertes que notre planètes nous a envoyé, celle-ci ne pouvait plus encaisser le poids de nos actes.."
                + "\n\n"
                + "La sécheresse, issues de l'épuisement de l'ensemble des ressources planétaires, emmena l'humanité dans ses tréfonds, et l'avènement d'une époque sinitre faisait surface."
                + "\n\n"
                + "Désormais, le pétrol, et l'eau, étaient les seules denrées qui avaient de la valeur dans ce monde...";

        noeud.Illustration = new ImageIcon("./assets/images/introduction.jpeg");

        noeud.ListeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.setLibelle("Continuer");
        choix1.setProchainNoeud(Commencement());
        noeud.ListeChoix.add(choix1);

        return noeud;
    }

    public static Noeud Commencement() {
        var noeud = new Noeud();

        noeud.Description = "En tant qu'agent de police, votre rôle est de vous rendre à San Pedro pour récupérer une citerne d'eau, qui pourra donner un espoir de vie à votre colonie"
                + "\n\n"
                + "Pour cela, votre équipe vous a spécialement préparée une Dodge Interceptor afin de pouvoir braver cet impitoyable dessert et ses danger."
                + "\n\n"
                + "Vous embarquez donc dans votre véhicule, et partez à l'aventure.";

        noeud.Illustration = new ImageIcon("./assets/images/interceptor.jpeg");

        noeud.Sound = new File("./assets/sounds/motor.wav");

        noeud.ListeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.setLibelle("Continuer");
        choix1.setProchainNoeud(PanneAutoroute());
        noeud.ListeChoix.add(choix1);

        return noeud;
    }

    public static Noeud PanneAutoroute() {
        var noeud = new Noeud();

        noeud.Description = "Après de longue heures de route, vous avez enfin quitter la ville. Vous ne reconnaissez toutefois aucun des paysages alentour, dans lesquels vous avez pourtant grandi."
                + "\n\n"
                + "Plongez dans vos souvenir, un bruit sourd vous secous, faisant vibrer la caisse de l'interceptor dans tous les sens. Pris de panique, vous vous arretez sur le bas coté, ouvrez le capot, et vous êtes pris d'une toux éttoufante avec toute cette fumée qui sort du moteur."
                + "\n\n"
                + "Votre véhicule à surchauffer, et le liquide de refroidissement s'est évaporée. Vous devez en retrouver pour continuer."
                + "\n\n"
                + "Vous appercevez néanmoins une silhouette au loins. Il s'agit d'un camion qui approche. Que faites-vous ?";

        noeud.Illustration = null;

        noeud.ListeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.setLibelle("Faire du stop.");
        choix1.setProchainNoeud(FaireStop());
        noeud.ListeChoix.add(choix1);

        var choix2 = new Choix();
        choix2.setLibelle("Se cacher.");
        choix2.setProchainNoeud(SeCacher());
        noeud.ListeChoix.add(choix2);

        return noeud;
    }

    public static Noeud SeCacher() {
        var noeud = new Noeud();

        noeud.Description = "Un panneau publicitaire se trouvant à votre gauche, vous vous y précipité rapidement pour vous y cacher."
                + "\n\n"
                + "Le véhicule approchant peu à peu, vous dicerner une bande de bandit. Vous avez bien fais de vous cacher. Qui sait ce qu'ils vous auraient fait !";

        noeud.Illustration = null;

        noeud.ListeChoix = new ArrayList<Choix>();

        var choix1 = new Choix();
        choix1.setLibelle("Continuer");
        choix1.setProchainNoeud(null); // TODO
        noeud.ListeChoix.add(choix1);

        return noeud;
    }

    public static Noeud FaireStop() {
        return null;
    }

}
