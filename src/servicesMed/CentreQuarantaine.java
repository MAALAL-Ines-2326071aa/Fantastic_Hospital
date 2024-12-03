package servicesMed;

import creatures.Creature;
import creatures.Contagieuse;

public class CentreQuarantaine extends ServiceMed {
    private boolean isolation;

    public CentreQuarantaine(String nom, int nbMaxCreatures, int superficie, String budget, boolean isolation) {
        super(nom, nbMaxCreatures, 0, superficie, budget);
        this.isolation = isolation;
    }

    public boolean isIsolation() {
        return isolation;
    }

    public void setIsolation(boolean isolation) {
        this.isolation = isolation;
    }

    @Override
    public boolean reviserBudget(String nouveauBudget) {
        if (!isolation) {
            System.out.println("Budget invalide en raison de l'absence d'isolation.");
        } else {
            super.reviserBudget(nouveauBudget);
        }
        return false;
    }

    @Override
    public void ajouterCreature(Creature creature) {
        if (creature instanceof Contagieuse && ((Contagieuse) creature).estContagieuse()) {
            super.ajouterCreature(creature);
            System.out.println(creature.getNomCreature() + " a été ajouté au Centre de Quarantaine.");
        } else {
            System.out.println(creature.getNomCreature() + " ne peut pas être ajouté au Centre de Quarantaine car elle n'est pas contagieuse.");
        }
    }



    @Override
    public void afficherCaracteristiques() {
        super.afficherCaracteristiques();
        System.out.println("Isolation: " + (isolation ? "Active" : "Inactive"));
    }
}
