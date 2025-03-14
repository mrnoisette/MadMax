package ObjetMemoire;

import java.util.List;

public class Humain {

    public String Name;
    public int Habilite;
    public int Endurence;
    public int Chance;
    public List<Medikit> ListeMedikit;
    public List<Equipement> ListeEquipement;
    public int Credit;

    public void CoupDePoing(Humain cible){
        cible.Endurence -= 1;
    }
}


