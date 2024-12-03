package Simulation;

import hopital.Hopital;
import medecins.Medecin;
import servicesMed.CentreQuarantaine;
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
        CentreQuarantaine MPT = new CentreQuarantaine("40aine", 7, 23, "faible", false);
        hopital.ajouterService(urgences);
        hopital.ajouterService(quarantaine);
        hopital.ajouterService(MPT);
        Creature creature1= Creature.creerCreature("Elfe", "Legolas", "Mâle", 100, 100, 5, genererMaladieAleatoire());
        Creature creature2=Creature.creerCreature("Nain", "Damien", "Mâle", 100, 10, 5, genererMaladieAleatoire());
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
