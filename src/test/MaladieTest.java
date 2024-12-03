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
        grippe = new Maladie("Grippe", "GRP", 2, 10);

        this.creature1 = Creature.creerCreature("Elfe","Legolas", "Mâle", 200, 3, 5, Hopital.genererMaladieAleatoire());
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
        grippe.setNiveauActuel(10);
        grippe.aggraver();
        assertEquals(10, grippe.getNiveauActuel());
    }

    @Test
    void testDiminuerNiveau() {
        grippe.diminuerNiveau();
        assertEquals(1, grippe.getNiveauActuel());
    }

    @Test
    void testDiminuerNiveauBelowZero() {
        grippe.setNiveauActuel(1);
        grippe.diminuerNiveau();
        assertEquals(0, grippe.getNiveauActuel());
    }

    @Test
    void testEstLetale() {
        grippe.setNiveauActuel(10);
        assertTrue(grippe.estLetale());
    }

    @Test
    void testEstGuerie() {
        grippe.setNiveauActuel(0);
        assertTrue(grippe.estGuerie());
    }
}
