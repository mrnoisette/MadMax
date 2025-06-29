import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.swing.ImageIcon;
import java.io.File;
import java.util.ArrayList;

public class AccesXML {

    private String _cheminFichier = "src/story.xml";

    public ArrayList<Noeud> LireListeNoeud() {
        var listeNoeud = new ArrayList<Noeud>();

        try {
            File xmlFile = new File(_cheminFichier);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList noeudList = doc.getElementsByTagName("noeud");

            // ----- 1ère passe : créer les noeuds sans les choix
            for (int i = 0; i < noeudList.getLength(); i++) {
                Element eNoeud = (Element) noeudList.item(i);

                int id = Integer.parseInt(eNoeud.getAttribute("id"));
                String description = getTagText(eNoeud, "description");
                String illustrationPath = getTagText(eNoeud, "illustration");
                String narrationPath = getTagText(eNoeud, "narration");
                String musiquePath = getTagText(eNoeud, "musique");

                ImageIcon illustration = (illustrationPath != null && !illustrationPath.isEmpty())
                        ? new ImageIcon(illustrationPath)
                        : null;

                File narration = (narrationPath != null && !narrationPath.isEmpty())
                        ? new File(narrationPath)
                        : null;

                File musique = (musiquePath != null && !musiquePath.isEmpty())
                        ? new File(musiquePath)
                        : null;

                boolean estMortel = Boolean.parseBoolean(getTagText(eNoeud, "estMortel"));
                String degatStr = getTagText(eNoeud, "degat");
                int degat = (degatStr != null && !degatStr.isEmpty()) ? Integer.parseInt(degatStr) : 0;

                Noeud noeud = new Noeud(id, description, illustration, narration, musique, null, estMortel, degat);
                listeNoeud.add(noeud);
            }

            // ----- 2ème passe : ajouter les choix aux noeuds
            for (int i = 0; i < noeudList.getLength(); i++) {
                Element eNoeud = (Element) noeudList.item(i);
                int id = Integer.parseInt(eNoeud.getAttribute("id"));

                ArrayList<Choix> listeChoix = new ArrayList<>();
                NodeList listeChoixNode = eNoeud.getElementsByTagName("listeChoix");
                if (listeChoixNode.getLength() > 0) {
                    NodeList choixNodes = ((Element) listeChoixNode.item(0)).getElementsByTagName("choix");

                    for (int j = 0; j < choixNodes.getLength(); j++) {
                        Element eChoix = (Element) choixNodes.item(j);
                        String libelle = eChoix.getAttribute("libelle");
                        int idDestination = Integer.parseInt(eChoix.getTextContent().trim());

                        Noeud destination = listeNoeud.stream()
                                .filter(n -> n.Id == idDestination)
                                .findFirst()
                                .orElse(null);

                        listeChoix.add(new Choix(libelle, destination));
                    }
                }

                // On récupère le noeud déjà créé et on lui ajoute ses choix
                for (Noeud n : listeNoeud) {
                    if (n.Id == id) {
                        n.ListeChoix = listeChoix;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listeNoeud;
    }

    private String getTagText(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0)
            return null;
        return nodes.item(0).getTextContent().trim();
    }
}
