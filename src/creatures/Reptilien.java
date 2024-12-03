package creatures;

import maladies.Maladie;

public class Reptilien extends Creature {

    private String type;
    public Reptilien(String nomCreature, String sexe, double poids, double taille, int age, Maladie maladie,String type) {
        super(type,nomCreature, sexe, poids, taille, age, maladie);
        this.type=type;
    }

    public String getType() {
        return type;
    }
}

