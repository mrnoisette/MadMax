import java.io.File;
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
        noeud.Illustration = new ImageIcon("../assets/images/introduction.jpeg");
        noeud.ListeChoix = null;

        return noeud;
    }

    public static Noeud Commencement() {
        var noeud = new Noeud();

        noeud.Description = "En tant qu'agent de police, votre rôle est de vous rendre à San Pedro pour récupérer une citerne d'eau, qui pourra donner un espoir de vie à votre colonie"
                + "\n\n"
                + "Pour cela, votre équipe vous a spécialement préparée une Dodge Interceptor afin de pouvoir braver cet impitoyable dessert et ses danger."
                + "\n\n"
                + "Vous embarquez donc dans votre véhicule, et partez à l'aventure.";
        noeud.Illustration = new ImageIcon("../assets/images/interceptor.jpeg");
        noeud.Sound = new File("./assets/sounds/motor.wav");
        noeud.ListeChoix = null;

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
        choix1.setNumero(1);
        choix1.setDescription("Faire du stop.");
        noeud.ListeChoix.add(choix1);

        var choix2 = new Choix();
        choix2.setNumero(2);
        choix2.setDescription("Se cacher.");
        noeud.ListeChoix.add(choix2);

        return noeud;
    }

    public static Noeud SeCacher() {
        return null;
    }

    public static Noeud FaireStop() {
        return null;
    }


}
