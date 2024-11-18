// hopital/HopitalFantastique.java
package hopital;

import servicesMed.ServiceMed;
import medecins.Medecin;
import creatures.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hopital {
    public String nom;
    public int nombreMaxServices;
    public List<ServiceMed> services;
    public List<Medecin> medecins;

    public Hopital(String nom, int nombreMaxServices) {
        this.nom = nom;
        this.nombreMaxServices = nombreMaxServices;
        this.services = new ArrayList<>();
        this.medecins = new ArrayList<>();
    }

    // Ajouter un service
    public void ajouterService(ServiceMed service) {
        if (services.size() < nombreMaxServices) {
            services.add(service);
            System.out.println("Service " + service.nom + " ajouté à l'hôpital.");
        } else {
            System.out.println("Capacité maximale atteinte. Impossible d'ajouter plus de services.");
        }
    }

    // Afficher les caractéristiques et les créatures
    public void afficherServices() {
        System.out.println("État des services dans l'hôpital :");
        for (ServiceMed service : services) {
            service.afficherCaracteristiques();
        }
    }

    // Point d'entrée (simulation via le menu)
    public static void main(String[] args) {
        Hopital hopital = new Hopital("Hôpital Fantastique", 3);

        // Créer des services
        ServiceMed urgences = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
        ServiceMed quarantaine = new ServiceMed("Quarantaine", 5, 0, 80, "faible");
        hopital.ajouterService(urgences);
        hopital.ajouterService(quarantaine);

        // Simulation du menu
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans la gestion de l'Hôpital Fantastique !");
        int choix;

        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Afficher les services et les créatures");
            System.out.println("2. Ajouter un service");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    hopital.afficherServices();
                    break;
                case 2:
                    System.out.print("Nom du service : ");
                    String nom = scanner.next();
                    ServiceMed nouveauService = new ServiceMed(nom, 10, 0, 50, "inexistant");
                    hopital.ajouterService(nouveauService);
                    break;
                case 3:
                    System.out.println("Merci d'avoir utilisé l'Hôpital Fantastique !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }
}
