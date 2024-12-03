package test;

import hopital.Hopital;
import medecins.Medecin;
import servicesMed.ServiceMed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maladies.Maladie;
import servicesMed.ServiceMed;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HopitalTest {

    private Hopital hopital;
    private ServiceMed service;
    private Medecin medecin;

    @BeforeEach
    void setUp() {
        // Création d'un hôpital avec une capacité maximale de services de 3
        hopital = new Hopital("Hopital Central", 3);

        // Création d'un service
        service = new ServiceMed("Urgence", 5, 0, 80, "faible");

        // Création d'un médecin
        medecin = new Medecin("Dr. Dupont", "Homme", 45, null);
    }

    @Test
    void testAjouterService() {
        // Ajouter un service à l'hôpital
        hopital.ajouterService(service);
        assertEquals(1, hopital.services.size(), "Le service devrait être ajouté à l'hôpital.");
    }

    @Test
    void testAjouterServiceMaxCapacite() {
        // Ajouter un service à l'hôpital
        hopital.ajouterService(service);
        hopital.ajouterService(new ServiceMed("Crypte", 5, 0, 80, "faible"));
        hopital.ajouterService(new ServiceMed("Pediatrie", 5, 0, 80, "faible"));

        // Essayer d'ajouter un service supplémentaire, ce qui devrait échouer
        hopital.ajouterService(new ServiceMed("Quarantaine", 5, 0, 80, "faible"));

        // Vérifier que l'hôpital n'a pas dépassé sa capacité maximale
        assertEquals(3, hopital.services.size(), "L'hôpital ne devrait pas avoir plus de 3 services.");
    }

    @Test
    void testAjouterMedecin() {
        hopital.ajouterMedecin(medecin);
        assertTrue(hopital.medecins.contains(medecin), "Le médecin devrait être ajouté à l'hôpital.");
        assertTrue(hopital.getMedecinsDisponibles().contains(medecin), "Le médecin devrait être dans la liste des médecins disponibles.");
    }

    @Test
    void testGenererMaladieAleatoire() {
        Maladie maladie = Hopital.genererMaladieAleatoire();
        assertNotNull(maladie, "La maladie générée ne doit pas être nulle.");
        assertTrue(maladie.getNomMaladie().length() > 0, "Le nom de la maladie générée ne doit pas être vide.");
    }

    @Test
    void testAssignerMedecinAuService() {
        // Ajouter un médecin et un service à l'hôpital
        hopital.ajouterMedecin(medecin);
        hopital.ajouterService(service);

        // Simuler l'entrée utilisateur : sélectionner le médecin (1) et le service (1)
        String input = "1\n1\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            // Appel de la méthode avec un Scanner utilisant l'entrée simulée
            Scanner scanner = new Scanner(System.in);
            hopital.assignerMedecinAuService(scanner);

            // Vérifier que le médecin est assigné au bon service
            assertEquals(service, medecin.getService(), "Le médecin devrait être assigné au service.");
        } finally {
            System.setIn(stdin); // Restaurer l'entrée standard originale
        }
    }

}
