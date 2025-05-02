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
    }

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

        // Bouton Création Profil
        if (DataGame.getInstance().ListePlayer.size() < 4) {
            panel.add(CreerBtnMenu("Nouveau profil", e -> {
                TransitionFenetre(fenetre, () -> AfficherMenuCreationProfil(fenetre));
            }));
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // Boutons Liste Profils
        for (var player : DataGame.getInstance().ListePlayer) {

            var panelProfils = new JPanel();
            panelProfils.setLayout(new BoxLayout(panelProfils, BoxLayout.X_AXIS));
            panelProfils.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelProfils.add(Box.createHorizontalGlue()); // Espace automatique entre les boutons

            panelProfils.add(CreerBtnMenu(player.Nom, e -> {
                // Définir le joueur sélectionné
                DataGame.getInstance().CurrentPlayer = player;
                ClearFenetre(fenetre);
                // Lancer le jeu
                TransitionFenetre(fenetre, () -> {
                    // Création de la classe Gameplay
                    Gameplay gameplay = new Gameplay(fenetre);
                    // Affichage du noeud du profil
                    gameplay.AfficherNoeud(player.NoeudActuel);
                });
            }));

            // Margin
            panelProfils.add(Box.createRigidArea(new Dimension(10, 0)));

            // Bouton Supprimer Profil
            var btnCroix = new JButton("X");
            btnCroix.setFont(new Font("Arial", Font.BOLD, 16));
            btnCroix.setPreferredSize(new Dimension(20, 20));
            btnCroix.setMaximumSize(new Dimension(20, 20));
            btnCroix.setBackground(Color.RED);
            btnCroix.setForeground(Color.WHITE);
            btnCroix.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btnCroix.addActionListener(e -> {
                int reponse = JOptionPane.showConfirmDialog(fenetre,
                        "Voulez-vous vraiment supprimer le profil \"" + player.Nom + "\" ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                // Vérifier la réponse de l'utilisateur
                if (reponse == JOptionPane.YES_OPTION) {
                    DataGame.getInstance().SupprimerPlayer(player);
                }
                AfficherMenuSelectionProfil(fenetre);
            });

            panelProfils.add(btnCroix);

            panelProfils.add(Box.createHorizontalGlue());
            panel.add(panelProfils);
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

    private static void AfficherMenuCreationProfil(JFrame fenetre) {
        ClearFenetre(fenetre);

        // Crée un panel principal avec marges
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());

        // Label (texte au-dessus)
        var label = new JLabel("Entrez votre nom :");
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        // Margin
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Champ de texte
        var textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setBorder(
                BorderFactory.createCompoundBorder(
                        textField.getBorder(), // Garde la bordure noire d'origine
                        BorderFactory.createEmptyBorder(10, 10, 10, 10) // Ajoute un padding intérieur
                ));
        panel.add(textField);

        // Margin
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Bouton Valider
        panel.add(CreerBtnMenu("Valider", e -> {
            if (TexteSaisieConforme(textField.getText())) {
                // Creation du profil
                DataGame.getInstance()
                        .AjouterPlayer(new Player(textField.getText(), 100, 50, 1, 50, Story.Introduction()));

                // Retour au menu
                TransitionFenetre(fenetre, () -> AfficherMenuSelectionProfil(fenetre));
            } else {
                // Prevenir mauvaise saisie
                JOptionPane.showMessageDialog(fenetre,
                        "Le nom doit contenir uniquement des lettres (sans espaces ni caractères spéciaux).", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }));

        // Margin
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Bouton Retour
        panel.add(CreerBtnMenu("Retour", e -> TransitionFenetre(fenetre, () -> AfficherMenuSelectionProfil(fenetre))));

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

    // Texte uniquement en string (abcd)
    private static boolean TexteSaisieConforme(String texte) {
        if (texte.isBlank() || !texte.matches("[a-zA-ZÀ-ÿ]+")) {
            return false;
        } else {
            return true;
        }
    }

}