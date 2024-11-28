package creatures;

import maladies.Maladie;

import java.util.List;

public class Zombie extends Creature {
    public Zombie(String nomCreature, String sexe, double poids, double taille, int age,Maladie maladie, String type) {
        super(nomCreature, sexe, poids, taille, age,maladie, type);
    }

    public void regenerer(List<Creature> creaturesDansLeService) {
        super.trepasser(creaturesDansLeService);
        System.out.println(nomCreature + " revient à la vie grâce à sa nature de mort-vivant !");
        this.moral = 50;
        this.maladies.clear();
    }

    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
    }
}