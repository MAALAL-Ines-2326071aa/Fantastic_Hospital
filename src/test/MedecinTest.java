package test;

import static hopital.Hopital.genererMaladieAleatoire;
import static org.junit.jupiter.api.Assertions.*;

import medecins.Medecin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicesMed.ServiceMed;
import creatures.Creature;
import maladies.Maladie;
import servicesMed.ServiceMed;

import java.util.List;
import java.util.ArrayList;

class MedecinTest {

    private Medecin medecin;
    private ServiceMed service;
    private Creature creature;
    private Maladie maladie;

    @BeforeEach
    void setUp() {
        // Initialisation des objets nécessaires pour les tests
        creature = new Creature("Legolas", "Mâle", 200.0, 3.5, 5, genererMaladieAleatoire(), "Elfe");
        maladie = new Maladie("Fièvre", "FR",1 ,5);
        creature.ajouterMaladie(maladie);

        service = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
        service.ajouterCreature(creature);

        medecin = new Medecin("Dr. Smith", "M", 45, creature);
        medecin.setService(service);
    }

    @Test
    void testSoignerUneCreature() {
        // Test du soin d'une créature
        medecin.soignerUneCreature(service, creature);

        // Vérifier que le moral de la créature a augmenté
        assertEquals(100, creature.getMoral(), "Le moral de la créature devrait augmenter après un soin.");

        // Vérifier que la maladie a diminué de niveau
        assertEquals(0, maladie.getNiveauActuel(), "Le niveau de la maladie devrait diminuer.");
    }

    @Test
    void testGuerrirMaladie() {
        // Test de la guérison d'une maladie
        creature.getMaladies().get(0).diminuerNiveau();
        creature.getMaladies().get(0).diminuerNiveau();
        creature.getMaladies().get(0).diminuerNiveau();

        // La maladie devrait être guérie
        medecin.soignerUneCreature(service, creature);

        assertTrue(creature.getMaladies().isEmpty(), "Les maladies devraient être guéries.");
    }

    @Test
    void testSoignerService() {
        // Test de la méthode soignerService pour tout le service
        medecin.soignerService(service);

        // Vérifier qu'après avoir soigné, la créature est débarrassée de ses maladies
        assertTrue(creature.getMaladies().isEmpty(), "La créature devrait être guérie après un soin du service.");
    }

    @Test
    void testReviserBudget() {
        // Révision du budget d'un service
        medecin.reviserBudget(service, "médiocre");

        // Vérifier que le budget a bien été révisé
        assertEquals("médiocre", service.getBudget(), "Le budget du service devrait être révisé.");
    }

    @Test
    void testTransfererCreature() {
        // Création d'un autre service
        ServiceMed serviceDestination = new ServiceMed("Quarantaine", 5, 0, 80, "faible");

        // Transfert de la créature
        medecin.transfererCreature(service, serviceDestination, creature);

        // Vérification que la créature a été transférée
        assertTrue(serviceDestination.creatures.contains(creature), "La créature devrait être transférée dans le service destination.");
        assertFalse(service.creatures.contains(creature), "La créature ne devrait plus être dans le service source.");
    }

    @Test
    void testExaminerService() {
        // Test de l'examen d'un service
        medecin.examinerService(service);
        // Le test est plus fonctionnel, en effet il se base sur l'affichage.
        // Pas d'affirmation ici, mais la méthode devrait exécuter correctement l'affichage des caractéristiques du service.
    }
}
