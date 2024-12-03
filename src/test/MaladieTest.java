package test;

import hopital.Hopital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import maladies.Maladie;
import creatures.Creature;
import servicesMed.ServiceMed;

import java.util.ArrayList;
import java.util.List;

class MaladieTest {

    private ServiceMed urgences;
    private Creature creature1;
    private Maladie grippe;

    @BeforeEach
    void setUp() {
        urgences = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
        grippe = new Maladie("Grippe", "GRP", 50, 100);

        List<Maladie> maladies = new ArrayList<>();
        maladies.add(grippe);

        creature1 = new Creature("Legolas", "Mâle", 200.0, 3.5, 5, Hopital.genererMaladieAleatoire(), "Elfe");
        urgences.ajouterCreature(creature1);
    }

    @Test
    void testGetters() {
        assertEquals("Grippe", grippe.getNomMaladie());
        assertEquals("GRP", grippe.getNomAbrege());
        assertEquals(50, grippe.getNiveauActuel());
        assertEquals(100, grippe.getNiveauMax());
    }

    @Test
    void testSetNiveauActuelValid() {
        grippe.setNiveauActuel(80);
        assertEquals(80, grippe.getNiveauActuel());
    }

    @Test
    void testSetNiveauActuelInvalid() {
        grippe.setNiveauActuel(150);
        assertEquals(50, grippe.getNiveauActuel()); // Le niveau ne doit pas changer
    }

    @Test
    void testAggraver() {
        grippe.aggraver();
        assertEquals(51, grippe.getNiveauActuel());
    }

    @Test
    void testAggraverAtMaxLevel() {
        grippe.setNiveauActuel(100);
        grippe.aggraver();
        assertEquals(100, grippe.getNiveauActuel());
    }

    @Test
    void testDiminuerNiveau() {
        grippe.diminuerNiveau();
        assertEquals(15, grippe.getNiveauActuel());
    }

    @Test
    void testDiminuerNiveauBelowZero() {
        grippe.setNiveauActuel(20);
        grippe.diminuerNiveau();
        assertEquals(0, grippe.getNiveauActuel());
    }

    @Test
    void testEstLetale() {
        grippe.setNiveauActuel(100);
        assertTrue(grippe.estLetale());
    }

    @Test
    void testEstGuerie() {
        grippe.setNiveauActuel(0);
        assertTrue(grippe.estGuerie());
    }
}
