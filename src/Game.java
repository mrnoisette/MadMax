import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Game {

    public JFrame Fenetre;
    public JLabel ImageLabel;
    public ImageIcon ImageAffichage;
    public JTextArea ZoneTexte;
    public JPanel ZoneBoutons;
    public JTextArea ZoneInfos;
    public JPanel centerPanel ;

    // Constructeur
    public Game() {
        // Fenetre
        Fenetre = new JFrame("Mad Max");
        Fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Fenetre.setLayout(new BorderLayout());

        // Zone pour les infos à gauche
        ZoneInfos = new JTextArea("Infos joueur : Santé 100% - Essence 100%");
        ZoneInfos.setEditable(false);
        ZoneInfos.setBackground(Color.black);
        ZoneInfos.setForeground(Color.white);
        Fenetre.add(ZoneInfos, BorderLayout.WEST);

        // Zone pour les boutons en bas
        ZoneBoutons = new JPanel();
        Fenetre.add(ZoneBoutons, BorderLayout.SOUTH);

        // Panel central pour l'image et le texte
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.black);

        // Image
        ImageLabel = new JLabel();
        ImageAffichage = new ImageIcon("./assets/images/introduction.jpeg");
        Image scaledImage = ImageAffichage.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH);
        ImageLabel.setIcon(new ImageIcon(scaledImage));
        ImageLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageLabel.setBackground(Color.black);
        centerPanel.add(ImageLabel, BorderLayout.CENTER);

        // Zone pour le texte
        ZoneTexte = new JTextArea();
        ZoneTexte.setEditable(false);
        ZoneTexte.setBackground(Color.black);
        ZoneTexte.setForeground(Color.white);
        ZoneTexte.setFont(new Font("Arial", Font.PLAIN, 18));
        ZoneTexte.setLineWrap(true);
        ZoneTexte.setWrapStyleWord(true);
        ZoneTexte.setMargin(new Insets(10, 10, 100, 10));
        centerPanel.add(ZoneTexte, BorderLayout.SOUTH);
        Fenetre.add(centerPanel, BorderLayout.CENTER);

        Fenetre.setVisible(true);
    }

    // Affiche un noeud dans la fenetre
    public void AfficherNoeud(Noeud noeud) {

        // Affichage du texte
        if (noeud.Description != null && !noeud.Description.isEmpty()) {
            ZoneTexte.setText(noeud.Description);
        }

        // Affichage des infos
        // TODO

        // Affichage de l'illustration
        if (noeud.Illustration != null) {
            ImageLabel.setIcon(noeud.Illustration);
        } else {
            ImageLabel.setIcon(null);
        }

        // Lecture de l'audio
        if (noeud.Sound != null) {
            JouerSon(noeud.Sound);
        }

        // Affichage des choix
        ZoneBoutons.removeAll();
        if (noeud.ListeChoix != null && noeud.ListeChoix.size() > 0) {
            for (var choix : noeud.ListeChoix) {
                var bouton = new JButton();
                bouton.setText(choix.getLibelle());
                bouton.addActionListener(e -> {
                    AfficherNoeud(choix.getProchainNoeud());
                });
                ZoneBoutons.add(bouton);
            }
        } else { // Pas de choix
            // TODO
        }

        // Actualiser
        Fenetre.revalidate();
        Fenetre.repaint();
    }

    // Jouer un audio
    private void JouerSon(File file) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Attendre la fin de l'audio
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erreur lors de la lecture du son : " + e.getMessage());
        }
    }

}
