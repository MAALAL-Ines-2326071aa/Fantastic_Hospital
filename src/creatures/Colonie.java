package creatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colonie {
    private List<Meute> meutes; // Liste des meutes dans la colonie

    public Colonie() {
        meutes = new ArrayList<>();
    }

    // Ajouter une meute à la colonie
    public void ajouterMeute(Meute meute) {
        meutes.add(meute);
    }

    // Afficher les lycanthropes de toutes les meutes de la colonie
    public void afficherLycanthropes() {
        for (Meute meute : meutes) {
            meute.afficherHierarchie();
        }
    }

    // Méthode principale de gestion de la colonie
    public void gererColonie() {
        Random random = new Random();

        // Déterminer si une nouvelle meute doit être créée
        if (random.nextBoolean()) {
            creerNouvelleMeute();
        }

        // Déterminer si la saison des amours est arrivée et lancer une reproduction
        if (random.nextInt(12) == 0) {
            saisonDesAmours();
        }

        // Faire évoluer la hiérarchie des meutes
        pourChaqueMeute(this::mettreAJourHierarchie);

        // Faire vieillir certains lycanthropes
        pourChaqueMeute(this::vieillirLycanthropes);

        // Générer des hurlements aléatoires
        pourChaqueMeute(this::genererHurlements);

        // Transformer certains lycanthropes en humains
        pourChaqueMeute(this::transformerEnHumain);
    }

    // Créer une nouvelle meute
    private void creerNouvelleMeute() {
        Meute nouvelleMeute = new Meute("Nouvelle Meute");
        ajouterMeute(nouvelleMeute);
        System.out.println("Une nouvelle meute a été créée.");
    }

    // Lancer la saison des amours et la reproduction
    private void saisonDesAmours() {
        for (Meute meute : meutes) {
            meute.reproduireCoupleAlpha();
        }
    }

    // Mettre à jour la hiérarchie de chaque meute
    private void mettreAJourHierarchie(Meute meute) {
        meute.mettreAJourHierarchie();
    }

    // Faire vieillir certains lycanthropes dans la meute
    private void vieillirLycanthropes(Meute meute) {
        for (Lycanthrope lycan : meute.getMembres()) {
            lycan.age++;
            System.out.println(lycan.nomCreature + " vieillit. Nouveau âge : " + lycan.age);
        }
    }

    // Générer des hurlements aléatoires entre les lycanthropes
    private void genererHurlements(Meute meute) {
        Random random = new Random();
        for (Lycanthrope lycan : meute.getMembres()) {
            if (random.nextInt(5) == 0) { // 20% de chance de hurler
                String message = random.nextBoolean() ? "Domination" : "Soumission";
                lycan.hurlerLycanthrope(message);
            }
        }
    }

    // Transformer certains lycanthropes en humains
    private void transformerEnHumain(Meute meute) {
        Random random = new Random();
        for (Lycanthrope lycan : meute.getMembres()) {
            if (random.nextInt(10) == 0) { // 10% de chance de transformation
                lycan.transformerEnHumain();
            }
        }
    }

    // Appliquer une action à chaque meute
    private void pourChaqueMeute(ActionMeute action) {
        for (Meute meute : meutes) {
            action.apply(meute);
        }
    }

    // Interface pour les actions à effectuer sur chaque meute
    private interface ActionMeute {
        void apply(Meute meute);
    }
}
