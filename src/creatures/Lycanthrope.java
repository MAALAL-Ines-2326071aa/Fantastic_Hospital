package creatures;

import maladies.Maladie;

public class Lycanthrope extends Creature {

    public Lycanthrope(String nomCreature, String sexe, double poids, double taille, int age) {
        super(nomCreature, sexe, poids, taille, age);
    }

    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
    }
}