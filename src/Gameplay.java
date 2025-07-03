import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Gameplay {

    // Fenetre
    private JFrame Fenetre;

    // Illustration
    private JLabel _zoneImage;

    // Description
    private JTextArea _zoneDescription;

    // Infos sur le joueur
    private JTextArea _zoneInfos;

    // Actions
    private JPanel _zoneBoutons;

    private JPanel _zoneChoix;

    // Joueur
    private Player _player = DataGame.getInstance().Player;

    // Constructeur
    public Gameplay(JFrame fenetre) {

        App.ClearFenetre(fenetre);
        Fenetre = fenetre;

        Fenetre = fenetre;
        Fenetre.setTitle("MadMax");
        Fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Fenetre.setLayout(new BorderLayout());

        // Bandeau latéral
        JPanel bandeauLateral = new JPanel();
        bandeauLateral.setLayout(new BoxLayout(bandeauLateral, BoxLayout.Y_AXIS));
        bandeauLateral.setPreferredSize(new Dimension(200, 600));

        // zoneInfos (enfant du bandeau latéral)
        _zoneInfos = new JTextArea("");
        _zoneInfos.setEditable(false);
        _zoneInfos.setBackground(Color.BLACK);
        _zoneInfos.setForeground(Color.WHITE);
        _zoneInfos.setBorder(BorderFactory.createTitledBorder("Infos"));
        bandeauLateral.add(_zoneInfos);

        // zoneBoutons (enfant du bandeau latéral)
        _zoneBoutons = new JPanel();
        _zoneBoutons.setBorder(BorderFactory.createTitledBorder("Actions"));
        _zoneBoutons.setLayout(new FlowLayout());
        _zoneBoutons.add(new JButton("Action 1"));
        _zoneBoutons.add(new JButton("Action 2"));
        bandeauLateral.add(_zoneBoutons);

        Fenetre.add(bandeauLateral, BorderLayout.WEST);

        // === CENTRE : zoneImage au centre, zoneDescription en bas ===
        JPanel centrePanel = new JPanel(new BorderLayout());

        // zoneImage
        _zoneImage = new JLabel("Image ici", SwingConstants.CENTER);
        _zoneImage.setPreferredSize(new Dimension(400, 300));
        _zoneImage.setBorder(BorderFactory.createTitledBorder("Illustration"));
        centrePanel.add(_zoneImage, BorderLayout.CENTER);

        // zoneDescription
        _zoneDescription = new JTextArea("Description...");
        _zoneDescription.setEditable(false);
        _zoneDescription.setBackground(Color.BLACK);
        _zoneDescription.setForeground(Color.WHITE);
        _zoneDescription.setLineWrap(true);
        _zoneDescription.setWrapStyleWord(true);
        _zoneDescription.setBorder(BorderFactory.createTitledBorder("Zone Description"));
        _zoneDescription.setPreferredSize(new Dimension(400, 100));
        centrePanel.add(_zoneDescription, BorderLayout.SOUTH);

        Fenetre.add(centrePanel, BorderLayout.CENTER);

        // === SUD : zoneChoix ===
        _zoneChoix = new JPanel();
        _zoneChoix.setBorder(BorderFactory.createTitledBorder("Zone Choix"));
        _zoneChoix.setLayout(new FlowLayout());
        _zoneChoix.add(new JButton("Choix 1"));
        _zoneChoix.add(new JButton("Choix 2"));

        Fenetre.add(_zoneChoix, BorderLayout.SOUTH);

        Fenetre.setSize(800, 600);

        Fenetre.setVisible(true);
    }

    // Affiche un noeud dans la fenetre
    public void AfficherNoeud(Noeud noeud) {

        // Sauvegarde
        _player.NoeudActuel = noeud;
        DataGame.getInstance().Sauvegarder(_player);

        // Infliger les dégats s'il y en a
        _player.TakeDamage(noeud.Degat);

        if (noeud.EstMortel || _player.Sante <= 0) { // Le joueur est mort
            AfficherEcranMort(Fenetre, noeud);
        }

        // Affichage du texte
        if (noeud.Description != null && !noeud.Description.isEmpty()) {
            _zoneDescription.setText(noeud.Description);
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
            _zoneImage.setIcon(noeud.Illustration);
        } else {
            _zoneImage.setIcon(null);
        }

        // Lecture de l'audio
        noeud.JouerAudios();

        // Affichage des choix
        _zoneChoix.removeAll();
        if (noeud.ListeChoix != null && noeud.ListeChoix.size() > 0) {
            for (var choix : noeud.ListeChoix) {
                var bouton = new JButton();
                bouton.setText(choix.Libelle);
                bouton.addActionListener(e -> {
                    AfficherNoeud(choix.ProchainNoeud);
                });
                _zoneChoix.add(bouton);
            }
        }

        App.ActualiserFenetre(Fenetre);
    }

    private void AfficherEcranMort(JFrame fenetre, Noeud noeud) {
        App.ClearFenetre(fenetre);

        // Panneau principal en noir avec disposition verticale
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Titre "Vous êtes mort"
        JLabel texte = new JLabel("Vous êtes mort");
        texte.setAlignmentX(Component.CENTER_ALIGNMENT);
        texte.setFont(new Font("Arial", Font.BOLD, 48));
        texte.setForeground(Color.RED);
        texte.setBackground(Color.BLACK);

        // Description (juste en dessous du titre)
        JLabel description = new JLabel(_player.Sante <= 0 ? "" : noeud.Description);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setFont(new Font("Arial", Font.PLAIN, 18));
        description.setForeground(Color.WHITE);
        description.setBackground(Color.BLACK);

        // Espacement autour des labels
        panel.add(Box.createVerticalGlue());
        panel.add(texte);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(description);
        panel.add(Box.createVerticalGlue());

        fenetre.add(panel, BorderLayout.CENTER);

        App.ActualiserFenetre(fenetre);
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
