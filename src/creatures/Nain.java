package creatures;

import maladies.Maladie;

public class Nain extends Creature {

    private String type;

    public Nain(String nomCreature, String sexe, double poids, double taille, int age,Maladie maladie,String type) {
        super(type,nomCreature, sexe, poids, taille, age, maladie);
        this.type=type;
    }

    public String getType() {
        return type;
    }
}