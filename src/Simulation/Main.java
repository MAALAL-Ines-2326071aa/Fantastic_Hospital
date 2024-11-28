package Simulation;

import hopital.Hopital;
import medecins.Medecin;
import servicesMed.ServiceMed;
import creatures.Creature;
import maladies.Maladie;

public class Main {
    public static void main(String[] args) {
        Hopital hopital = new Hopital("Hôpital Fantastique", 3);
        ServiceMed urgences = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
        ServiceMed quarantaine = new ServiceMed("Quarantaine", 5, 0, 80, "faible");
        hopital.ajouterService(urgences);
        hopital.ajouterService(quarantaine);
        Maladie maladie1 = new Maladie("Fievre", "FV", 50, 100);
        Maladie maladie2 = new Maladie("Grippe", "GR", 30, 80);
        Creature creature1 = new Creature("Dragon", "Mâle", 200.0, 3.5, 5, maladie1, "Mythique");
        Creature creature2 = new Creature("Griffon", "Femelle", 80.0, 1.8, 3, maladie2, "Légendaire");
        Medecin medecin1 = new Medecin("Dr. John", "Male", 45, creature1);
        Medecin medecin2 = new Medecin("Dr. Smith", "Femelle", 39, creature2);
        hopital.ajouterMedecin(medecin1);
        hopital.ajouterMedecin(medecin2);
        urgences.ajouterCreature(creature1);
        urgences.ajouterCreature(creature2);
        hopital.systemeDeTours();
    }
}
