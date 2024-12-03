package creatures;


import maladies.Maladie;

public class Orque extends Creature {

    private String type;
    public Orque(String nomCreature, String sexe, double poids, double taille, int age,Maladie maladie,String type) {
        super(type,nomCreature, sexe, poids, taille, age,maladie);
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
    }
}
