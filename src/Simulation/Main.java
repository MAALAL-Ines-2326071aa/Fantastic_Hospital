package Simulation;

import hopital.Hopital;
import medecins.Medecin;
import servicesMed.ServiceMed;
import creatures.Creature;
import maladies.Maladie;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static hopital.Hopital.genererMaladieAleatoire;

public class Main {
    public static void main(String[] args) {
        Hopital hopital = new Hopital("Hôpital Fantastique", 3);
        ServiceMed urgences = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
        ServiceMed quarantaine = new ServiceMed("Quarantaine", 5, 0, 80, "faible");
        hopital.ajouterService(urgences);
        hopital.ajouterService(quarantaine);
        Creature creature1 = new Creature("Dragon", "Mâle", 200.0, 3.5, 5, genererMaladieAleatoire(), "Mythique");
        Creature creature2 = new Creature("Griffon", "Femelle", 80.0, 1.8, 3, genererMaladieAleatoire(), "Légendaire");
        Medecin medecin1 = new Medecin("Dr. John", "Homme", 45, creature1);
        Medecin medecin2 = new Medecin("Dr. Smith", "Femme", 39, creature2);
        hopital.ajouterMedecin(medecin1);
        hopital.ajouterMedecin(medecin2);
        urgences.ajouterCreature(creature1);
        urgences.ajouterCreature(creature2);
        hopital.systemeDeTours();
    }
    private static Maladie assignerMaladie(List<Maladie> maladies) {
        Random random = new Random();
        return maladies.get(random.nextInt(maladies.size())); // Sélectionne une maladie aléatoire
    }
}
