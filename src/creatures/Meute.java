package creatures;

import java.util.*;

public class Meute {
    private String nom; // Nom de la meute
    private Lycanthrope alphaMale; // Mâle α
    private Lycanthrope alphaFemelle; // Femelle α
    private List<Lycanthrope> membres; // Liste des membres de la meute

    public Meute(String nom) {
        this.nom = nom;
        this.membres = new ArrayList<>();
        this.alphaMale = null;
        this.alphaFemelle = null;
    }

    // Getter pour le nom
    public String getNom() {
        return nom;
    }

    // Ajouter un lycanthrope à la meute
    public void ajouterLycanthrope(Lycanthrope lycanthrope) {
        membres.add(lycanthrope);
        mettreAJourHierarchie();
    }

    // Supprimer un lycanthrope de la meute
    public void supprimerLycanthrope(Lycanthrope lycanthrope) {
        membres.remove(lycanthrope);
        mettreAJourHierarchie();
    }

    // Afficher la hierarchie de la meute
    public void afficherHierarchie() {
        if (membres.isEmpty()) {
            System.out.println("La meute " + nom + " n'a pas de membres.");
        } else {
            System.out.println("Hiérarchie de la meute " + nom + ":");
            for (Lycanthrope lycan : membres) {
                System.out.println(lycan.nomCreature + " - Rang: " + lycan.getRang());
            }
        }
    }

    public void mettreAJourHierarchie() {
        List<String> ordreRangs = Arrays.asList("α", "β", "γ", "δ", "ε", "ζ", "η", "θ", "ι", "κ", "λ", "μ", "ν", "ξ", "ο", "π", "ρ", "σ", "τ", "υ", "φ", "χ", "ψ", "ω");

        membres.sort((l1, l2) -> {
            int rang1 = ordreRangs.indexOf(l1.getRang());
            int rang2 = ordreRangs.indexOf(l2.getRang());
            return Integer.compare(rang1, rang2);
        });

        constituerCoupleAlpha(); // Mettre à jour le couple alpha à chaque changement de hiérarchie
    }

    public String rangSuivant(String actuel, String sexe) {
        List<String> rangs = Arrays.asList("α", "β", "γ", "δ", "ε", "ζ", "η", "θ", "ι", "κ", "λ", "μ", "ν", "ξ", "ο", "π", "ρ", "σ", "τ", "υ", "φ", "χ", "ψ", "ω");
        int indexActuel = rangs.indexOf(actuel);
        if (indexActuel < rangs.size() - 1) {
            String prochainRang = rangs.get(indexActuel + 1);

            // Verifier si quelqu'un du même sexe a dejà ce rang
            boolean libre = membres.stream().noneMatch(l -> l.getRang().equals(prochainRang) && l.sexe.equals(sexe));
            return libre ? prochainRang : null;
        }
        return null;
    }

    private void constituerCoupleAlpha() {
        Lycanthrope nouveauAlphaMale = membres.stream()
                .filter(l -> l.getCategorieAge().equals("adulte") && l.sexe.equals("Mâle"))
                .max(Comparator.comparing(Lycanthrope::getForce))
                .orElse(null);

        Lycanthrope nouvelleAlphaFemelle = membres.stream()
                .filter(l -> l.getCategorieAge().equals("adulte") && l.sexe.equals("Femelle"))
                .max(Comparator.comparing(Lycanthrope::getNiveau))
                .orElse(null);

        // Si le mâle α change, réattribuer les rangs de domination
        if (nouveauAlphaMale != null && !nouveauAlphaMale.equals(alphaMale)) {
            if (alphaMale != null) {
                alphaMale.setRang("β");  // L'ancien mâle α perd son rang
            }
            alphaMale = nouveauAlphaMale;
            alphaMale.setRang("α");
        }

        // Si la femelle α change, réattribuer les rangs
        if (nouvelleAlphaFemelle != null && !nouvelleAlphaFemelle.equals(alphaFemelle)) {
            if (alphaFemelle != null) {
                alphaFemelle.setRang("β");  // L'ancienne femelle α perd son rang
            }
            alphaFemelle = nouvelleAlphaFemelle;
            alphaFemelle.setRang("α");
        }
    }


    public void gererSolitaires() {
        List<Lycanthrope> solitaires = new ArrayList<>();

        for (Lycanthrope lycan : membres) {
            if (lycan.getRang().equals("ω") || (lycan.getRang().equals("α") && lycan != alphaMale)) {
                lycan.quitterMeute(); // Quitter la meute
                solitaires.add(lycan);
                lycan.setRang(null); // Les solitaires n'ont pas de rang
                System.out.println(lycan.nomCreature + " devient solitaire.");
            }
        }

        // Verifier si une nouvelle meute peut être constituee
        Optional<Lycanthrope> male = solitaires.stream().filter(l -> l.sexe.equals("Mâle")).findFirst();
        Optional<Lycanthrope> femelle = solitaires.stream().filter(l -> l.sexe.equals("Femelle")).findFirst();

        if (male.isPresent() && femelle.isPresent()) {
            Meute nouvelleMeute = new Meute("Nouvelle Meute");
            male.get().rejoindreMeute(nouvelleMeute);
            femelle.get().rejoindreMeute(nouvelleMeute);
            System.out.println("Une nouvelle meute a ete constituee.");
        }
    }

    public List<Lycanthrope> getMembres() {
        return membres;
    }

    public void reproduireCoupleAlpha() {
        if (alphaMale == null || alphaFemelle == null) {
            System.out.println("Pas de couple α pour la reproduction.");
            return;
        }

        Random random = new Random();
        int nbPortee = random.nextInt(7) + 1; // Genère entre 1 et 7 jeunes
        List<String> rangsDisponibles = Arrays.asList("γ", "β");

        for (int i = 0; i < nbPortee; i++) {
            String rang = membres.stream().anyMatch(l -> l.getRang().equals("β")) ? "γ" : "β";
            Lycanthrope jeune = new Lycanthrope(
                    "Jeune" + (i + 1),
                    random.nextBoolean() ? "Mâle" : "Femelle",
                    10 + random.nextInt(10),
                    50 + random.nextInt(20),
                    0,
                    null, // Pas de maladie
                    "Lycanthrope",
                    "jeune",
                    random.nextInt(5), // Force aleatoire
                    0,
                    rang,
                    random.nextInt(5) // Impetuosite aleatoire
            );
            ajouterLycanthrope(jeune);
            System.out.println(jeune.nomCreature + " est ne avec le rang " + rang + ".");
        }
    }




}