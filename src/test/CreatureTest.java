package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import maladies.Maladie;
import creatures.Creature;

import java.util.ArrayList;
import java.util.List;

class CreatureTest {

    private Creature legolas;
    private Maladie grippe;
    private Maladie rhume;
    private List<Creature> creaturesDansLeService;
    private Creature gimli;

    @BeforeEach
    void setUp() {
        grippe = new Maladie("Grippe", "GRP", 50, 100);
        rhume = new Maladie("Rhume", "RHM", 10, 50);

        this.legolas = Creature.creerCreature("Elfe","Legolas", "Mâle", 200, 3, 5, grippe);
        creaturesDansLeService = new ArrayList<>();
        creaturesDansLeService.add(legolas);
    }

    @Test
    void testNomCreature() {
        assertEquals("Legolas", legolas.getNomCreature());
    }

    @Test
    void testAjouterMaladie() {
        legolas.ajouterMaladie(rhume);
        assertTrue(legolas.getMaladies().contains(rhume));
    }

    @Test
    void testAttendre() {
        legolas.attendre();
        assertEquals(90, legolas.getMoral());
        legolas.attendre();
        legolas.attendre();
        assertEquals(70, legolas.getMoral());
    }

    @Test
    void testHurler() {
        legolas.setMoral(20);
        legolas.hurler();
        assertEquals(20, legolas.getMoral()); // Le moral reste inchangé
    }

    @Test
    void testSemporter() {
        legolas.setMoral(10);
        legolas.semporter();
        assertTrue(Creature.creaturesEmportees.contains(legolas));
    }

    @Test
    void testContaminer() {
        Creature gimli = new Creature("Nain","Gimli", "Mâle", 250, 1.5, 10, null);
        creaturesDansLeService.add(gimli);
        gimli.ajouterMaladie(rhume);

        gimli.contaminer(); // Test de contamination aléatoire
        assertTrue(gimli.getMaladies().contains(rhume));
    }

    @Test
    void testTomberMalade() {
        legolas.tomberMalade(rhume);
        assertTrue(legolas.getMaladies().contains(rhume));
    }

    @Test
    void testEtreSoigneeAvecMaladie() {
        legolas.etreSoignee(grippe);
        assertFalse(legolas.getMaladies().contains(grippe));
        assertEquals(100, legolas.getMoral()); // Le moral augmente et ne dépasse pas 100
    }

    @Test
    void testEtreSoigneeSansMaladie() {
        legolas.etreSoignee(rhume);
        assertFalse(legolas.getMaladies().contains(rhume));
    }

    @Test
    void testTrepasse() {
        this.gimli=Creature.creerCreature("Nain","Gimli", "Mâle", 250, 1, 10, null);
        creaturesDansLeService.add(gimli);

        legolas.trepasser(creaturesDansLeService);

        assertTrue(Creature.creaturesEmportees.contains(legolas));
        assertEquals(90, gimli.getMoral()); // Le moral de Gimli diminue
    }
}

