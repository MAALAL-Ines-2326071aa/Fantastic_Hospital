package medecins;

import maladies.Maladie;
import servicesMed.ServiceMed;
import creatures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Medecin {
    public String nom;
    public String sexe;
    public int age;
    public String type;
    private ServiceMed service;

    public Medecin(String nom, String sexe, int age, String type) {
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


    // Révision du budget d'un service
    public void reviserBudget(ServiceMed service, String nouveauBudget) {
        System.out.println("Révision du budget du service " + service.getNom() + " :");
        while(!service.reviserBudget(nouveauBudget)) {
            System.out.println("Le budget " + nouveauBudget + " n'est pas valide. Veuillez entrer un nouveau budget :");
            Scanner scanner = new Scanner(System.in);
            nouveauBudget = scanner.nextLine();
            scanner.nextLine();
            System.out.println();
        }
    }

    // Transfert d'une créature d'un service à un autre
    public void transfererCreature(ServiceMed source, ServiceMed destination, Creature creature) {
        System.out.println("Tentative de transfert de " + creature.getNomCreature() + " de " + source.getNom() + " à " + destination.getNom());
        // Vérification si la créature est présente dans le service source
        if (!source.creatures.contains(creature)) {
            System.out.println(creature.getNomCreature() + " n'est pas présente dans le service " + source.getNom());
            return;
        }

        // Vérification de la compatibilité entre la créature et le service de destination
        if (!estCompatible(creature, destination)) {
            System.out.println("Erreur : Le type de la créature ne correspond pas au type du service destination.");
            return;
        }

        // Vérification si le service de destination a de la place
        if (destination.creatures.size() < destination.nbMaxCreatures) {
            source.supprimerCreature(creature); // Suppression de la créature du service source
            destination.ajouterCreature(creature); // Ajout de la créature au service destination
            System.out.println(creature.getNomCreature() + " a été transférée de " + source.getNom() + " à " + destination.getNom());
        } else {
            System.out.println("Le service " + destination.getNom() + " est plein. Impossible de transférer " + creature.getNomCreature());
        }
    }

    // Vérifie si le type de la créature est compatible avec le service de destination
    public boolean estCompatible(Creature creature, ServiceMed service) {
        // Récupérer le type de la créature et le type du service
        String serviceType = getServiceType(service);
        String creatureType = creature.getType();

        // Débogage
        System.out.println("Type du service : " + serviceType);
        System.out.println("Type de la créature : " + creatureType);

        // Comparer les types
        return serviceType.equals(creatureType);
    }



    // Méthode pour obtenir le type d'un service
    public static String getServiceType(ServiceMed service) {
        System.out.println("Service: " + service.getNom());

        // Vérification du service et retour du type
        if (service.getNom().equals("Urgences")) {
            return "Urgences";
        } else if (service.getNom().equals("Quarantaine")) {
            return "Quarantaine";
        } else if (service.getNom().equals("40aine")) {
            return "40aine";
        } else if (service.getNom().equals("Elfe Service")) {
            return "Elfe";
        } else if (service.getNom().equals("Orque Service")) {
            return "Orque";
        } else if (service.getNom().equals("HommeBete Service")) {
            return "HommeBete";
        } else if (service.getNom().equals("Lycanthrope Service")) {
            return "Lycanthrope";
        } else if (service.getNom().equals("Nain Service")) {
            return "Nain";
        } else if (service.getNom().equals("Vampire Service")) {
            return "Vampire";
        } else if (service.getNom().equals("Zombie Service")) {
            return "Zombie";
        } else if (service.getNom().equals("Reptilien Service")) {
            return "Reptilien";
        }

        return "Inconnu";
    }

    public ServiceMed getService() {
        return service;
    }
}
