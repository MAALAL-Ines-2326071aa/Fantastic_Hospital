package medecins;

import servicesMed.ServiceMed;
import creatures.Creature;

public class Medecin {
    public String nom;
    public String sexe;
    public int age;

    public Medecin(String nom, String sexe, int age) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
    }

    public void examinerService(ServiceMed service) {
        System.out.println("Examen du service " + service.getNom() + " :");
        service.afficherCaracteristiques();
    }

    public void soignerService(ServiceMed service) {
        System.out.println("Soins dans le service " + service.getNom() + " :");
        for (Creature creature : service.creatures) {
            service.soignerCreature(creature);
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
