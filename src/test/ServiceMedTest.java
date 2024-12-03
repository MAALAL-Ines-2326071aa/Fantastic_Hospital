package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hopital.Hopital.genererMaladieAleatoire;
import static org.junit.jupiter.api.Assertions.*;

import servicesMed.ServiceMed;
import creatures.Creature;
import maladies.Maladie;
import creatures.Elfe;
import java.util.ArrayList;
import java.util.List;

class ServiceMedTest {

    private ServiceMed service;
    private Creature Elfe;
    private Creature Nain;

    @BeforeEach
    void setUp() {
        service = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
        this.Elfe = Creature.creerCreature("Elfe","Legolas", "Mâle", 200, 3, 5, genererMaladieAleatoire());
        this.Nain = Creature.creerCreature("Nain","Damien", "Mâle", 200, 3, 5, genererMaladieAleatoire());
    }

    @Test
    void testCreerService() {
        assertEquals("Urgences", service.getNom());
        assertEquals(10, service.nbMaxCreatures);
        assertEquals(0, service.nbCreaturesPresentes);
        assertEquals(100, service.superficie);
        assertEquals("médiocre", service.getBudget());
        assertTrue(service.creatures.isEmpty());
    }

    @Test
    void testAjouterCreature() {
        service.ajouterCreature(Elfe);
        service.ajouterCreature(Nain);

        assertEquals(2, service.nbCreaturesPresentes);
        assertTrue(service.creatures.contains(Elfe));
        assertTrue(service.creatures.contains(Nain));
    }

    @Test
    void testAjouterCreatureDepassement() {
        service = new ServiceMed("Urgences", 1, 0, 100, "faible"); // Limite à 1 créature
        service.ajouterCreature(Elfe);
        service.ajouterCreature(Nain);

        assertEquals(1, service.nbCreaturesPresentes);
        assertTrue(service.creatures.contains(Elfe));
        assertFalse(service.creatures.contains(Nain));
    }

    @Test
    void testSupprimerCreature() {
        service.ajouterCreature(Elfe);
        service.supprimerCreature(Elfe);

        assertEquals(0, service.nbCreaturesPresentes);
        assertFalse(service.creatures.contains(Elfe));
    }

    @Test
    void testReviserBudgetValide() {
        service.reviserBudget("insuffisant");
        assertEquals("insuffisant", service.getBudget());
    }

    @Test
    void testReviserBudgetInvalide() {
        service.reviserBudget("faible");
        assertEquals("faible", service.getBudget());
    }

    @Test
    void testSoignerCreature() {
        List<Maladie> maladies = new ArrayList<>();
        maladies.add(genererMaladieAleatoire());
        Elfe.maladies = maladies;

        service.ajouterCreature(Elfe);
        service.soignerCreature(Elfe);

        assertTrue(Elfe.maladies.isEmpty());
    }

    @Test
    void testModifierEtat() {
        service.modifierEtat();

        List<String> budgetsPossibles = List.of("inexistant", "médiocre", "insuffisant", "faible");
        assertTrue(budgetsPossibles.contains(service.getBudget()));
    }
}
