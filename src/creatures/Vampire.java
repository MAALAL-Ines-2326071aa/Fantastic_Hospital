package creatures;

import maladies.Maladie;

import java.util.List;

public class Vampire extends Creature {
    public Vampire(String nomCreature, String sexe, double poids, double taille, int age) {
        super(nomCreature, sexe, poids, taille, age);
    }

    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
    }

    public void demoraliser(List<Creature> creaturesDansLeService) {
        System.out.println("L'elfe " + nomCreature + " affecte négativement certaines créatures !");
        for (int i = 0; i < creaturesDansLeService.size(); i++) {
            Creature creature = creaturesDansLeService.get(i);
            if (!creature.equals(this) && i % 2 == 0) { // Affecte une créature sur deux
                creature.moral -= 15; // Réduit le moral de 15
                if (creature.moral < 0) {
                    creature.moral = 0;
                }
                System.out.println("Le moral de " + creature.nomCreature + " a diminué, il est maintenant à : " + creature.moral);
            }
        }
    }
    public void regenerer(List<Creature> creaturesDansLeService) {
        super.trepasser(creaturesDansLeService);
        System.out.println(nomCreature + " revient à la vie grâce à sa nature de mort-vivant !");
        this.moral = 50;
        this.maladies.clear(); // Suppression des maladies après régénération
    }
}
