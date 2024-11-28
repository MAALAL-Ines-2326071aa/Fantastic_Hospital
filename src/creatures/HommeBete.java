package creatures;

import maladies.Maladie;

public class HommeBete extends Creature{
    public HommeBete(String nomCreature, String sexe, double poids, double taille, int age,Maladie maladie, String type) {
        super(nomCreature, sexe, poids, taille, age, maladie,type);
    }

    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
    }
}

