package test;

import creatures.Creature;
import creatures.Lycanthrope;
import creatures.Meute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MeuteTest {
    private Meute meute;
    private Lycanthrope maleAlpha;
    private Lycanthrope femelleAlpha;
    private Lycanthrope lycanOrdinaire;

    @BeforeEach
    void setUp() {
        meute = new Meute("Meute Sauvage");

        // Création des lycanthropes
        this.maleAlpha = (Lycanthrope) Creature.creerCreature("Lycanthrope","AlphaM", "Mâle", 90, 180, 30, null);
        this.femelleAlpha = (Lycanthrope) Creature.creerCreature("Lycanthrope","AlphaF", "Femelle", 80, 170, 28, null);
        this.lycanOrdinaire = (Lycanthrope) Creature.creerCreature("Lycanthrope", "Gamma", "Mâle", 70, 160, 20, null);

        // Ajout initial des membres
        meute.ajouterLycanthrope(maleAlpha);
        meute.ajouterLycanthrope(femelleAlpha);
        meute.ajouterLycanthrope(lycanOrdinaire);
    }

    @Test
    void testAjouterLycanthrope() {
        Lycanthrope nouveauLycan = (Lycanthrope) Creature.creerCreature("Lycanthrope","Nouveau", "Mâle", 60, 150, 15, null);
        meute.ajouterLycanthrope(nouveauLycan);

        assertTrue(meute.getMembres().contains(nouveauLycan), "Le nouveau lycanthrope devrait être ajouté à la meute.");
    }

    @Test
    void testSupprimerLycanthrope() {
        meute.supprimerLycanthrope(lycanOrdinaire);
        assertFalse(meute.getMembres().contains(lycanOrdinaire), "Le lycanthrope devrait être supprimé de la meute.");
    }

    @Test
    void testConstituerCoupleAlpha() {
        meute.mettreAJourHierarchie();

        // Vérification que les alpha sont correctement assignés
        assertEquals("alpha", maleAlpha.getRang(), "Le mâle α devrait être assigné.");
        assertEquals("alpha", femelleAlpha.getRang(), "La femelle α devrait être assignée.");
    }


    @Test
    void testReproduireCoupleAlpha() {
        meute.mettreAJourHierarchie();

        int tailleInitiale = meute.getMembres().size();
        meute.reproduireCoupleAlpha();

        // Vérification du nombre de membres après reproduction
        int tailleFinale = meute.getMembres().size();
        assertTrue(tailleFinale > tailleInitiale, "De nouveaux lycanthropes devraient être ajoutés après la reproduction.");
    }

    @Test
    void testReproductionAvecFacteurImpetuosite() {
        meute.mettreAJourHierarchie();

        int impetuositeMale = maleAlpha.getImpetuosite();
        int impetuositeFemelle = femelleAlpha.getImpetuosite();

        meute.reproduireCoupleAlpha();
        List<Lycanthrope> membres = meute.getMembres();

        for (Lycanthrope lycan : membres) {
            if (lycan.getCategorieAge().equals("jeune")) {
                assertTrue(lycan.getImpetuosite() <= Math.max(impetuositeMale, impetuositeFemelle),
                        "Les jeunes devraient avoir un facteur d'impétuosité cohérent avec leurs parents.");
            }
        }
    }

    @Test
    void testGererSolitaires() {
        // Création d'une meute et d'un lycanthrope
        Meute meute = new Meute("Meute Lunaire");
        Lycanthrope lycan = (Lycanthrope) Creature.creerCreature("Lycanthrope","Nouveau", "Mâle", 60, 150, 15, null);
        meute.ajouterLycanthrope(lycan);

        // Vérifier que le lycan est bien dans la meute
        assertTrue(meute.getMembres().contains(lycan), "Le lycanthrope devrait être dans la meute initialement.");
        assertNotNull(lycan.getMeute(), "Le lycanthrope devrait avoir une référence à la meute après avoir été ajouté.");

        // Appeler gererSolitaires sur le lycan
        lycan.gererSolitaires();

        // Vérifier qu'il a quitté la meute
        assertFalse(meute.getMembres().contains(lycan), "Le lycanthrope devrait être retiré de la meute.");
        assertNull(lycan.getMeute(), "Le lycanthrope ne devrait plus appartenir à une meute.");
    }

    @Test
    void testRangSuivant() {
        // Créer une meute et des lycanthropes
        Meute meute = new Meute("Meute Lunaire");
        Lycanthrope betaFemelle = (Lycanthrope) Creature.creerCreature("Lycanthrope","Beta Femelle", "Femelle", 70, 160, 20, null);
        Lycanthrope alphaFemelle = (Lycanthrope) Creature.creerCreature("Lycanthrope","Alpha Femelle", "Femelle", 80, 165, 25, null);
        meute.ajouterLycanthrope(alphaFemelle);
        meute.ajouterLycanthrope(betaFemelle);

        // Vérifier les rangs initiaux
        assertEquals("beta", betaFemelle.getRang(), "Beta Femelle devrait initialement être beta.");
        assertEquals("alpha", alphaFemelle.getRang(), "Alpha Femelle devrait initialement être alpha.");

        // Faire passer Beta Femelle au rang suivant
        meute.passerAuRangSuivant(betaFemelle);
        System.out.println(betaFemelle.getRang());
        System.out.println(alphaFemelle.getRang());
        // Vérifier que Beta Femelle est devenue alpha et Alpha Femelle est devenue omega
        assertEquals("alpha", betaFemelle.getRang(), "Beta Femelle devrait maintenant être alpha.");
        assertEquals("beta", alphaFemelle.getRang(), "Alpha Femelle devrait maintenant être omega.");
    }


}
