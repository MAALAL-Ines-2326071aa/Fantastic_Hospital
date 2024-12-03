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
    public Creature type;
    private ServiceMed service;

    public Medecin(String nom, String sexe, int age, Creature type) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.type = type;
        this.service = null;
    }
    public void setService(ServiceMed service) {
        this.service = service;
    }
    public void examinerService(ServiceMed service) {
        System.out.println("Examen du service " + service.getNom() + " :");
        service.afficherCaracteristiques();
    }

    // Soigner une créature d'un service
    public void soignerUneCreature(ServiceMed service, Creature creature) {
        System.out.println("Le médecin " + this.nom + " soigne la créature " + creature.getNomCreature() + ".");

        // Augmenter le moral de la créature
        int moralActuel = creature.getMoral();
        int moralRendu = 10;
        creature.setMoral(Math.min(100, moralActuel + moralRendu));
        System.out.println("Le moral de " + creature.getNomCreature() + " est maintenant " + creature.getMoral() + ".");

        // Soigner les maladies
        List<Maladie> maladies = creature.getMaladies();
        List<Maladie> maladiesGuerries = new ArrayList<>();
        for (Maladie maladie : maladies) {
            maladie.diminuerNiveau();
            System.out.println("La maladie " + maladie.getNomMaladie() + " de " + creature.getNomCreature() + " a été réduite à un niveau de " + maladie.getNiveauActuel() + ".");
            if (maladie.getNiveauActuel() <= 0) {
                maladiesGuerries.add(maladie);
            }
        }
        for (Maladie maladie : maladiesGuerries) {
            creature.getMaladies().remove(maladie);
            System.out.println("La maladie " + maladie.getNomMaladie() + " a été complètement guérie chez " + creature.getNomCreature() + ".");
        }
    }

    // Soigner toutes les créatures dans un service
    public void soignerService(ServiceMed service) {
        System.out.println("Médecin " + nom + " soigne les créatures du service : " + service.getNom());
        for (Creature creature : service.creatures) {
            if (!creature.getMaladies().isEmpty()) {
                creature.getMaladies().clear();
                System.out.println(creature.getNomCreature() + " a été soignée.");
            } else {
                System.out.println(creature.getNomCreature() + " n'a aucune maladie.");
            }
        }
    }

    // Révision du budget d'un service
    public void reviserBudget(ServiceMed service, String nouveauBudget) {
        System.out.println("Révision du budget du service " + service.getNom() + " :");
        service.reviserBudget(nouveauBudget);
    }

    // Transfert d'une créature d'un service à un autre
    public void transfererCreature(ServiceMed source, ServiceMed destination, Creature creature) {
        if (source.creatures.contains(creature)) {
            if (destination.creatures.size() < destination.nbMaxCreatures) {
                source.supprimerCreature(creature);
                destination.ajouterCreature(creature);
                System.out.println(creature.getNomCreature() + " a été transférée de " + source.getNom() + " à " + destination.getNom());
            } else {
                System.out.println("Le service " + destination.getNom() + " est plein. Impossible de transférer " + creature.getNomCreature());
            }
        } else {
            System.out.println(creature.getNomCreature() + " n'est pas présente dans le service " + source.getNom());
        }
    }

    public ServiceMed getService() {
        return service;
    }
}

