package medecins;

import maladies.Maladie;
import servicesMed.ServiceMed;
import creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class Medecin {
    public String nom;
    public String sexe;
    public int age;
    private ServiceMed service;

    public Medecin(String nom, String sexe, int age) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.service = null;
    }

    public void setService(ServiceMed service) {
        this.service = service;
    }

    public void examinerService(ServiceMed service) {
        System.out.println("Examen du service " + service.getNom() + " :");
        service.afficherCaracteristiques();
    }

    public void soignerUneCreature(ServiceMed service, Creature creature) {
        System.out.println("Le médecin " + this.nom + " soigne la créature " + creature.getNom() + ".");

        // Augmenter le moral de la créature
        int moralActuel = creature.getMoral();
        int moralRendu = 10; // Par exemple, le médecin augmente le moral de 10 points
        creature.setMoral(Math.min(100, moralActuel + moralRendu)); // Limite le moral à 100
        System.out.println("Le moral de " + creature.getNom() + " est maintenant " + creature.getMoral() + ".");

        // Parcourir les maladies et réduire leur niveau
        List<Maladie> maladies = creature.getMaladies();
        List<Maladie> maladiesGuerries = new ArrayList<>();
        for (Maladie maladie : maladies) {
            maladie.diminuerNiveau(); // Diminue le niveau de la maladie
            System.out.println("La maladie " + maladie.getNom() + " de " + creature.getNom() + " a été réduite à un niveau de " + maladie.getNiveau() + ".");
            if (maladie.getNiveau() <= 0) {
                maladiesGuerries.add(maladie); // Ajouter à la liste des maladies guéries
            }
        }

        // Retirer les maladies qui ont atteint le niveau 0
        for (Maladie maladie : maladiesGuerries) {
            creature.getMaladies().remove(maladie);
            System.out.println("La maladie " + maladie.getNom() + " a été complètement guérie chez " + creature.getNom() + ".");
        }
    }


    public void reviserBudget(ServiceMed service, String nouveauBudget) {
        System.out.println("Révision du budget du service " + service.getNom() + " :");
        service.reviserBudget(nouveauBudget);
    }

    public void transfererCreature(ServiceMed source, ServiceMed destination, Creature creature) {
        if (source.creatures.contains(creature) && destination.nbCreaturesPresentes < destination.nbMaxCreatures) {
            source.supprimerCreature(creature);
            destination.ajouterCreature(creature);
            System.out.println(creature.nomCreature + " a été transférée de " + source.getNom() + " à " + destination.getNom());
        } else {
            System.out.println("Transfert impossible : service complet ou créature absente.");
        }
    }
}
