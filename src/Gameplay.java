import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Gameplay {

    // Fenetre
    private JFrame Fenetre;

    // Illustration
    private JLabel _imageLabel;

    // Description
    private JTextArea _zoneTexte;

    // Infos sur le joueur
    private JTextArea _zoneInfos;

    // Choix possibles
    private JPanel _zoneBoutons;

    // Audio
    private Clip _audio;

    // Joueur
    private Player _player = DataGame.getInstance().CurrentPlayer;

    // Constructeur
    public Gameplay(JFrame fenetre) {
        App.ClearFenetre(fenetre);
        Fenetre = fenetre;

        // Infos sur le joueur
        _zoneInfos = new JTextArea();
        _zoneInfos.setEditable(false);
        _zoneInfos.setBackground(Color.black);
        _zoneInfos.setForeground(Color.white);
        _zoneInfos.setFont(new Font("Arial", Font.PLAIN, 18));
        Fenetre.add(_zoneInfos, BorderLayout.WEST);

        // Choix possibles
        _zoneBoutons = new JPanel();
        Fenetre.add(_zoneBoutons, BorderLayout.SOUTH);

        // Panel central pour l'illusatration et la description
        var centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.black);

        // Illustration
        _imageLabel = new JLabel();
        _imageLabel.setHorizontalAlignment(JLabel.CENTER);
        _imageLabel.setBackground(Color.black);
        centerPanel.add(_imageLabel, BorderLayout.CENTER);

        // Description
        _zoneTexte = new JTextArea();
        _zoneTexte.setEditable(false);
        _zoneTexte.setBackground(Color.black);
        _zoneTexte.setForeground(Color.white);
        _zoneTexte.setFont(new Font("Arial", Font.PLAIN, 18));
        _zoneTexte.setLineWrap(true);
        _zoneTexte.setWrapStyleWord(true);
        _zoneTexte.setMargin(new Insets(10, 10, 100, 10));
        centerPanel.add(_zoneTexte, BorderLayout.SOUTH);

        Fenetre.add(centerPanel, BorderLayout.CENTER);

        Fenetre.setVisible(true);

    }

    // Affiche un noeud dans la fenetre
    public void AfficherNoeud(Noeud noeud) {

        if (JoueurMort(_player)) { // Le joueur est mort
            AfficherEcranMort(Fenetre);
        }

        // Affichage du texte
        if (noeud.Description != null && !noeud.Description.isEmpty()) {
            _zoneTexte.setText(noeud.Description);
        }

        // Affichage des infos
        _zoneInfos.setText(
                _player.Nom + " : \n"
                        + "\n - Sante = " + _player.Sante 
                        + "\n - Chance = " + _player.Chance + " %"
                        + "\n - Medikit = " + _player.NbMedikit
                        + "\n - Argent = " + _player.Argent + " $");

        // Affichage de l'illustration
        if (noeud.Illustration != null) {
            _imageLabel.setIcon(noeud.Illustration);
        } else {
            _imageLabel.setIcon(null);
        }

        // Lecture de l'audio
        JouerSon(noeud.Audio);

        // Affichage des choix
        _zoneBoutons.removeAll();
        if (noeud.ListeChoix != null && noeud.ListeChoix.size() > 0) {
            for (var choix : noeud.ListeChoix) {
                var bouton = new JButton();
                bouton.setText(choix.Libelle);
                bouton.addActionListener(e -> {
                    AfficherNoeud(choix.ProchainNoeud);
                });
                _zoneBoutons.add(bouton);
            }
        } else { // Pas de choix
            // TODO
        }

        App.ActualiserFenetre(Fenetre);
    }

    // Jouer un audio
    private void JouerSon(File file) {
        if (_audio != null && _audio.isRunning()) {
            _audio.stop();
            _audio.close();
        }
        if (file == null)
            return;

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            _audio = AudioSystem.getClip();
            _audio.open(audioStream);
            _audio.start();

            // Écouter la fin du son pour réinitialiser l'état
            _audio.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    _audio.close();
                    _audio = null;
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erreur lors de la lecture du son : " + e.getMessage());
        }
    }

    private void AfficherEcranMort(JFrame fenetre) {
        App.ClearFenetre(fenetre);

        JLabel texte = new JLabel("Vous êtes mort");
        texte.setHorizontalAlignment(JLabel.CENTER);
        texte.setVerticalAlignment(JLabel.CENTER);
        texte.setFont(new Font("Arial", Font.BOLD, 48));
        texte.setForeground(Color.RED);
        texte.setBackground(Color.BLACK);
        texte.setOpaque(true);

        fenetre.add(texte, BorderLayout.CENTER);

        App.ActualiserFenetre(fenetre);
    }

    // Verifie si le joueur est vivant
    private static boolean JoueurMort(Player player) {
        if (player.Sante <= 0) {
            return true;
        } else {
            return false;
        }
    }

    // Une chance sur deux de retourner vrai (la chance va de 0 à 100)
    private static boolean EstChanceux(int chance) {
        Random rand = new Random();

        int tirage = rand.nextInt(100);

        if (tirage < chance) {
            return true;
        } else {
            return false;
        }
    }

}
