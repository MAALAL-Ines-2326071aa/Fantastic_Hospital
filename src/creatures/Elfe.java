package creatures;

import maladies.Maladie;

import java.util.List;

public class Elfe extends Creature {

    public Elfe(String nomCreature, String sexe, double poids, double taille, int age, Maladie maladie, String type) {
        super(nomCreature, sexe, poids, taille, age,maladie,type);
    }

    public void demoraliser(List<Creature> creaturesDansLeService) {
        System.out.println("L'elfe " + nomCreature + " affecte négativement certaines créatures !");
        for (int i = 0; i < creaturesDansLeService.size(); i++) {
            Creature creature = creaturesDansLeService.get(i);
            if (!creature.equals(this) && i % 2 == 0) {
                creature.moral -= 15;
                if (creature.moral < 0) {
                    creature.moral = 0;
                }
                System.out.println("Le moral de " + creature.nomCreature + " a diminué, il est maintenant à : " + creature.moral);
            }
        }
    }
}
