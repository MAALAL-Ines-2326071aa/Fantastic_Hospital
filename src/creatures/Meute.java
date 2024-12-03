package creatures;

import org.junit.platform.engine.support.descriptor.FileSystemSource;

import java.util.*;

public class Meute {
    private String nom; // Nom de la meute
    private Lycanthrope alphaMale; // Mâle aplha
    private Lycanthrope alphaFemelle; // Femelle aplha
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
    public void ajouterLycanthrope(Lycanthrope lycan) {
        if (!membres.contains(lycan)) {
            membres.add(lycan);
            lycan.rejoindreMeute(this); // Met à jour la référence de la meute dans le lycan
        }
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
                System.out.println(lycan.nomCreature + " - : " + lycan.getRang());
            }
        }
    }

    public void mettreAJourHierarchie() {
        // Liste des rangs dans l'ordre hiérarchique
        List<String> ordreRangs = Arrays.asList("alpha", "beta", "gamma", "omega");

        // Trier les membres par force (ordre décroissant)
        membres.sort(Comparator.comparing(Lycanthrope::getForce).reversed());

        // Identifier les potentiels nouveaux alpha (le plus fort mâle et femelle)
        Lycanthrope nouveauAlphaMale = membres.stream()
                .filter(lycan -> lycan.getSexe().equals("Mâle"))
                .findFirst()
                .orElse(null);

        Lycanthrope nouveauAlphaFemelle = membres.stream()
                .filter(lycan -> lycan.getSexe().equals("Femelle"))
                .findFirst()
                .orElse(null);

        // Gestion des mâles alpha
        if (nouveauAlphaMale != null && !nouveauAlphaMale.equals(alphaMale)) {
            // Rétrograder l'ancien alpha mâle
            if (alphaMale != null) {
                alphaMale.setRang("beta");
            }
            // Promouvoir le nouveau alpha mâle
            nouveauAlphaMale.setRang("alpha");
            alphaMale = nouveauAlphaMale;
        }

        // Gestion des femelles alpha
        if (nouveauAlphaFemelle != null && !nouveauAlphaFemelle.equals(alphaFemelle)) {
            // Rétrograder l'ancienne alpha femelle
            if (alphaFemelle != null) {
                alphaFemelle.setRang("beta");
            }
            // Promouvoir la nouvelle alpha femelle
            nouveauAlphaFemelle.setRang("alpha");
            alphaFemelle = nouveauAlphaFemelle;
        }

        // Réassigner les autres rangs
        int indexM = 1; // Index pour les rangs des mâles
        int indexF = 1; // Index pour les rangs des femelles

        for (Lycanthrope lycan : membres) {
            // Ignorer les alpha
            if (lycan.equals(alphaMale) || lycan.equals(alphaFemelle)) {
                continue;
            }

            // Déterminer le rang à attribuer selon le sexe
            String nouveauRang;
            if (lycan.getSexe().equals("Mâle")) {
                nouveauRang = (indexM < ordreRangs.size()) ? ordreRangs.get(indexM) : "omega";
                indexM++;
            } else {
                nouveauRang = (indexF < ordreRangs.size()) ? ordreRangs.get(indexF) : "omega";
                indexF++;
            }

            // Assigner le rang
            lycan.setRang(nouveauRang);
        }
    }


    private void reassignerRangs(List<String> ordreRangs) {
        int indexM = 1; // Index pour les rangs des mâles
        int indexF = 1; // Index pour les rangs des femelles

        for (Lycanthrope lycan : membres) {
            // Ignorer les alpha
            if (lycan.equals(alphaMale) || lycan.equals(alphaFemelle)) {
                continue;
            }

            // Déterminer le rang à attribuer selon le sexe
            String nouveauRang;
            if (lycan.getSexe().equals("Mâle")) {
                nouveauRang = (indexM < ordreRangs.size()) ? ordreRangs.get(indexM) : "omega";
                indexM++;
            } else {
                nouveauRang = (indexF < ordreRangs.size()) ? ordreRangs.get(indexF) : "omega";
                indexF++;
            }

            // Assigner le rang
            lycan.setRang(nouveauRang);
        }
    }



    public void passerAuRangSuivant(Lycanthrope lycan) {
        // Définir l'ordre des rangs
        List<String> ordreRangs = Arrays.asList("omega", "gamma", "beta", "alpha");

        // Trouver le rang actuel
        String rangActuel = lycan.getRang();
        int indexActuel = ordreRangs.indexOf(rangActuel);

        // Vérifier si le rang actuel est valide
        if (indexActuel == -1 || indexActuel == ordreRangs.size() - 1) {
            // Si le rang est inconnu ou si le lycan est déjà "alpha", ne rien faire
            return;
        }

        // Déterminer le prochain rang
        String prochainRang = ordreRangs.get(indexActuel + 1);

        // Si le prochain rang est "alpha", rechercher l'actuel alpha du même sexe
        if ("alpha".equals(prochainRang)) {
            Lycanthrope alphaActuel = null;

            // Trouver l'alpha du même sexe dans la meute
            for (Lycanthrope membre : membres) {
                if (membre.getRang().equals("alpha") && membre.getSexe().equals(lycan.getSexe())) {
                    alphaActuel = membre;
                    break;
                }
            }

            // Si un alpha existe, rétrograder cet alpha à "beta" ou "omega"
            if (alphaActuel != null) {
                alphaActuel.setRang("beta");
            }

            // Assigner le rang "alpha" au lycanthrope
            lycan.setRang("alpha");

            // Mettre à jour les alpha dans la meute si nécessaire
            // Aucun besoin de mise à jour directe d'alphaMale ou alphaFemelle, c'est automatique via la recherche
        }

        // Assigner le nouveau rang si ce n'était pas déjà "alpha"
        if (!"alpha".equals(prochainRang)) {
            lycan.setRang(prochainRang);
        }
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

        if (nouveauAlphaMale != null && !nouveauAlphaMale.equals(alphaMale)) {
            if (alphaMale != null) {
                alphaMale.setRang("beta");
            }
            alphaMale = nouveauAlphaMale;
            alphaMale.setRang("alpha");
        }

        if (nouvelleAlphaFemelle != null && !nouvelleAlphaFemelle.equals(alphaFemelle)) {
            if (alphaFemelle != null) {
                alphaFemelle.setRang("beta");
            }
            alphaFemelle = nouvelleAlphaFemelle;
            alphaFemelle.setRang("alpha");
        }
    }




    public List<Lycanthrope> getMembres() {
        return membres;
    }

    public void reproduireCoupleAlpha() {
        if (alphaMale == null || alphaFemelle == null) {
            System.out.println("Pas de couple aplha pour la reproduction.");
            return;
        }

        Random random = new Random();
        int nbPortee = random.nextInt(7) + 1; // Genère entre 1 et 7 jeunes
        List<String> rangsDisponibles = Arrays.asList("gamma", "beta");

        for (int i = 0; i < nbPortee; i++) {
            String rang = membres.stream().anyMatch(l -> l.getRang().equals("beta")) ? "gamma" : "beta";
            Lycanthrope jeune = new Lycanthrope("Lycanthrope",
                    "Jeune" + (i + 1),
                    random.nextBoolean() ? "Mâle" : "Femelle",
                    10 + random.nextInt(10),
                    50 + random.nextInt(20),
                    0,
                    null, // Pas de maladie
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