import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

public class App {
    public static void main(String[] args) {

        // Créer un JFrame (fenêtre)
        JFrame cadre = new JFrame("Java Game");

        // Récupérer la taille de l'écran
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // Définir la taille de la fenêtre à la taille de l'écran
        cadre.setSize(screenSize); // La fenêtre prendra toute la taille de l'écran

        // Placer la fenêtre en plein écran (maximisée)
        cadre.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Définir l'action par défaut lorsque la fenêtre est fermée
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Rendre la fenêtre visible
        cadre.setVisible(true);

        // Ajouter un MouseListener pour détecter les clics de souris
        cadre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Afficher les coordonnées du clic de souris
                System.out.println("Clic de souris détecté à la position : (" + e.getX() + ", " + e.getY() + ")");
            }
        });

        // Rendre la fenêtre visible
        cadre.setVisible(true);

        var fenetre = cadre.getContentPane();
        fenetre.setBackground((Color.red));

    }
}
