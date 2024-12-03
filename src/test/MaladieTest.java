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

        creature1 = new Creature("Legolas", "Mâle", 200.0, 3.5, 5, Hopital.genererMaladieAleatoire(), "Elfe");
        System.out.println(creature1.getMaladies());
        urgences.ajouterCreature(creature1);
        if (!creature1.getMaladies().isEmpty()) {
            Maladie maladieExistante = creature1.getMaladies().get((int) (Math.random() * creature1.getMaladies().size()));
            System.out.println(creature1.getMaladies());
        }
    }


    @Test
    void testAggraver() {
        List<Maladie> maladies = creature1.getMaladies();
        Maladie maladieExistante = maladies.get((int) (Math.random() * maladies.size()));
        maladieExistante.aggraver();
        assertEquals(2, maladieExistante.getNiveauActuel());
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
