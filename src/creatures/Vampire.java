package creatures;

import maladies.Maladie;

import java.util.List;

public class Vampire extends Creature implements Contagieuse, Regenerante, Demoralisante {

    private String type;
    public Vampire(String nomCreature, String sexe, double poids, double taille, int age,Maladie maladie,String type) {
        super(type,nomCreature, sexe, poids, taille, age, maladie);
        this.type=type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean estContagieuse() {
        return getMaladies() != null;
    }

    @Override
    public void contaminer(Creature cible, Maladie maladie) {
        if (estContagieuse()) {
            cible.tomberMalade(maladie);
            System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
        } else {
            System.out.println(nomCreature + " n'est pas contagieux.");
        }
    }

    public void demoraliser(List<Creature> creaturesDansLeService) {
        System.out.println("L'elfe " + nomCreature + " affecte négativement certaines créatures !");
        for (int i = 0; i < creaturesDansLeService.size(); i++) {
            Creature creature = creaturesDansLeService.get(i);
            if (!creature.equals(this) && i % 2 == 0) {
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
        this.maladies.clear();
    }
}