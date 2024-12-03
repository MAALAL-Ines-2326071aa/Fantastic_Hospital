package servicesMed;

import java.util.ArrayList;
import java.util.List;
import creatures.Creature;
import maladies.Maladie;

public class ServiceMed {
    public String nom;
    public int nbMaxCreatures;
    public int nbCreaturesPresentes;
    public int superficie;
    public String budget;
    public List<Creature> creatures;

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public ServiceMed(String nom, int nbMaxCreatures, int nbCreaturesPresentes, int superficie, String budget) {
        this.nom = nom;
        this.nbMaxCreatures = nbMaxCreatures;
        this.superficie = superficie;
        this.budget = budget;
        this.nbCreaturesPresentes = 0;
        this.creatures = new ArrayList<>();
    }
    public void afficherCaracteristiques() {
        System.out.println("Nom du service: " + nom);
        System.out.println("Nombre max de créatures: " + nbMaxCreatures);
        System.out.println("Nombre de créatures présentes: " + nbCreaturesPresentes);
        System.out.println("Superficie du service: " + superficie + " m²");
        System.out.println("Budget: " + budget);
        System.out.println();
        if (creatures.isEmpty()) {
            System.out.println("Aucune créature présente dans le service.");
        } else {
            System.out.println("Créatures présentes:");
            for (Creature creature : creatures) {
                afficherCreatures(creature);
            }
        }
    }
    public void afficherCreatures(Creature creature) {
        System.out.println("Nom: " + creature.getNomCreature());
        System.out.println("Sexe: " + creature.sexe);
        System.out.println("Âge: " + creature.age + " ans");
        System.out.println("Poids: " + creature.poids + " kg");
        System.out.println("Taille: " + creature.taille + " m");
        System.out.println("Moral: " + creature.moral + "%");
        if (creature.maladies.isEmpty()) {
            System.out.println("Aucune maladie.");
        } else {
            System.out.print("Maladies: ");
            for (Maladie maladie : creature.maladies) {
                System.out.print(maladie.getNomMaladie() + " " + maladie.getNiveauActuel());
            }
            System.out.println();
            System.out.println();
        }
    }
    public void ajouterCreature(Creature creature){
        if (nbCreaturesPresentes < nbMaxCreatures) {
            creatures.add(creature);
            nbCreaturesPresentes++;
            System.out.println(creature.getNomCreature() + " a été ajoutée au service.");
        } else {
            System.out.println("Impossible d'ajouter " + creature.getNomCreature() + ": le service est plein.");
        }
    }

    public String getBudget() {
        return budget;
    }

    public void supprimerCreature(Creature creature){
        if (creatures.remove(creature)) {
            nbCreaturesPresentes--;
            System.out.println(creature.getNomCreature() + " a été retirée du service.");
        } else {
            System.out.println("Cette créature n'est pas présente dans le service");
        }
    }
    public void reviserBudget(String nouveauBudget) {
        if (nouveauBudget.equals("inexistant") || nouveauBudget.equals("médiocre")
                || nouveauBudget.equals("insuffisant") || nouveauBudget.equals("faible")) {
            this.budget = nouveauBudget;
            System.out.println("Le budget a été modifié à : " + nouveauBudget);
        } else {
            System.out.println("Budget invalide");
        }
    }

    public void soignerCreature(Creature creature){
        if (creatures.contains(creature)) {
            creature.maladies = new ArrayList<>();
            System.out.println(creature.getNomCreature() + " a été soignée.");
        } else {
            System.out.println("Cette créature n'est pas dans ce service.");
        }

    }
    public void modifierEtat() {
        String[] budgetsPossibles = {"inexistant", "médiocre", "insuffisant", "faible"};
        int i = (int) (Math.random() * budgetsPossibles.length);
        budget = budgetsPossibles[i];
        System.out.println("Le budget du service a été modifié à : " + budget);
    }

}

