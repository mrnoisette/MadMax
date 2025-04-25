import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class App {

    public static void main(String[] args) {

        // Fenetre principale que les différents menus vont se partager
        var fenetre = new JFrame("Mad Max");
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());
        fenetre.setVisible(true);

        AfficherMenuPrincipal(fenetre);
    } // hello pd

    private static void AfficherMenuPrincipal(JFrame fenetre) {
        ClearFenetre(fenetre);

        // Panel vertical pour les boutons
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        var btnJouer = CreerBtnMenu("Jouer",
                e -> TransitionFenetre(fenetre, () -> AfficherMenuSelectionProfil(fenetre)));
        var btnParam = CreerBtnMenu("Paramètres",
                e -> TransitionFenetre(fenetre, () -> AfficherMenuParametre(fenetre)));
        var btnQuitter = CreerBtnMenu("Quitter", e -> System.exit(0));

        // Espacement et ajout
        panel.add(Box.createVerticalGlue());
        panel.add(btnJouer);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace entre les boutons
        panel.add(btnParam);
        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Espace entre les boutons
        panel.add(btnQuitter);
        panel.add(Box.createVerticalGlue());

        fenetre.add(panel, BorderLayout.CENTER);

        ActualiserFenetre(fenetre);
    }

    private static void AfficherMenuSelectionProfil(JFrame fenetre) {
        ClearFenetre(fenetre);

        // Panel vertical pour les boutons
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());

        for (var player : DataGame.getInstance().ListePlayer) {
            var btnProfil = CreerBtnMenu(player.Nom, e -> {
                // 1. Définir le joueur sélectionné
                DataGame.getInstance().CurrentPlayer = player;
                ClearFenetre(fenetre);
                // 2. Lancer le jeu
                TransitionFenetre(fenetre, () -> {
                    // Création de la classe Gameplay
                    Gameplay gameplay = new Gameplay(fenetre);
                    // Affichage du noeud
                    gameplay.AfficherNoeud(player.NoeudActuel);
                });
            });

            panel.add(btnProfil);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // Bouton Retour
        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Espace entre les boutons
        var btnRetour = CreerBtnMenu("Retour",
                e -> TransitionFenetre(fenetre, () -> AfficherMenuPrincipal(fenetre)));
        panel.add(btnRetour);
        panel.add(Box.createVerticalGlue());

        fenetre.add(panel, BorderLayout.CENTER);

        ActualiserFenetre(fenetre);
    }

    private static void AfficherMenuParametre(JFrame fenetre) {
        ClearFenetre(fenetre);

        var sliderVolume = new JSlider();
        var cbxLangue = new JComboBox<>();
        var btnRetour = CreerBtnMenu("Retour", e -> AfficherMenuPrincipal(fenetre));

        ActualiserFenetre(fenetre);
    }

    public static void TransitionFenetre(JFrame fenetre, Runnable chargementNouveauContenu) {
        // Crée un panneau noir par-dessus le contenu
        var overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, (int) (opacity * 255)));
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            float opacity = 0f;
        };

        overlay.setOpaque(false);
        overlay.setBounds(0, 0, fenetre.getWidth(), fenetre.getHeight());
        overlay.setLayout(null);

        fenetre.getLayeredPane().add(overlay, JLayeredPane.DRAG_LAYER);
        overlay.repaint();

        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            float opacity = 0f;
            boolean versNoir = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (versNoir) {
                    opacity += 0.05f;
                    if (opacity >= 1f) {
                        opacity = 1f;
                        versNoir = false;

                        // Change le contenu
                        chargementNouveauContenu.run();
                    }
                } else {
                    opacity -= 0.05f;
                    if (opacity <= 0f) {
                        ((Timer) e.getSource()).stop();
                        fenetre.getLayeredPane().remove(overlay);
                        fenetre.repaint();
                        return;
                    }
                }

                overlay.opacity = opacity;
                overlay.repaint();
            }
        });

        timer.start();
    }

    // Nettoyer la fenetre
    public static void ClearFenetre(JFrame fenetre) {
        fenetre.getContentPane().removeAll();
        fenetre.repaint();
        fenetre.revalidate();
    }

    // Actualiser la fenetre
    public static void ActualiserFenetre(JFrame fenetre) {
        fenetre.revalidate();
        fenetre.repaint();
    }

    // Creer un bouton de menu
    private static JButton CreerBtnMenu(String texte, ActionListener action) {
        var button = new JButton(texte);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.addActionListener(action);
        return button;
    }

}