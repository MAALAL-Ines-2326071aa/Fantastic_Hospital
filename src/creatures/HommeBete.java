package creatures;

import maladies.Maladie;

public class HommeBete extends Creature implements Contagieuse {
    private String type;

    public HommeBete(String nomCreature, String sexe, double poids, double taille, int age, Maladie maladie, String type) {
        super(type, nomCreature, sexe, poids, taille, age, maladie);
        this.type = type;
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
}
