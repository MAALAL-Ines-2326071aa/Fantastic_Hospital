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

    public ServiceMed(String nom, int nbMaxCreatures, int nbCreaturesPresentes, int superficie, String budget) {
        this.nom = nom;
        this.nbMaxCreatures = nbMaxCreatures;
        this.superficie = superficie;
        this.budget = budget;
        this.nbCreaturesPresentes = 0;
        this.creatures = new ArrayList<>();
    }
    public void afficherCaracteristiques(){
        System.out.println("Nom du service: " + nom);
        System.out.println("Nombre max de creatures: " + nbMaxCreatures);
        System.out.println("Nombre de creatures présentes: " + nbCreaturesPresentes);
        System.out.println("Superficie du service: " + superficie + "m²");
        System.out.println("Budget: " + budget);
        System.out.println("Créatures: ");
        for (Creature creature : creatures) {
            System.out.println("- " + creature.getNom());
            System.out.println("Moral : " + creature.getMoral());
            System.out.println("  Maladies :");
            for (Maladie maladie : creature.getMaladies()) {
                System.out.println("    * " + maladie.getNom() + " (Niveau : " + maladie.getNiveau() + ")");
            }
        }
        System.out.println();
        System.out.println("====================================");// pour avoir un design plus clair pour différencier les services
        System.out.println();
    }
    public void ajouterCreature(Creature creature){
        if (nbCreaturesPresentes < nbMaxCreatures) {
            creatures.add(creature);
            nbCreaturesPresentes++;
            System.out.println(creature.getNom() + " a été ajoutée au service.");
        } else {
            System.out.println("Impossible d'ajouter " + creature.getNom() + ": le service est plein.");
        }
    }
    public void supprimerCreature(Creature creature){
        if (creatures.remove(creature)) {
            nbCreaturesPresentes--;
            System.out.println(creature.getNom() + " a été retirée du service.");
        } else {
            System.out.println("Cette créature n'est pas présente dans le service");
        }
    }
    public void reviserBudget(String nouveauBudget) {
        if (nouveauBudget.equals("inexistant") || nouveauBudget.equals("médiocre")
                || nouveauBudget.equals("insuffisant") || nouveauBudget.equals("faible")) {
            this.budget = nouveauBudget;
            System.out.println("Le budget a été révisé à : " + nouveauBudget);
        } else {
            System.out.println("Budget invalide");
        }
    }

    public void soignerCreature(Creature creature){
        if (creatures.contains(creature)) {
            creature.maladies = new ArrayList<>();
            System.out.println(creature.getNom() + " a été soignée.");
        } else {
            System.out.println("Cette créature n'est pas dans ce service.");
        }
    }

    public String getNom() {
        return nom;
    }

    public int getNbCreaturesPresentes() {
        return nbCreaturesPresentes;
    }
}
