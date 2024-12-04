package creatures;

import maladies.Maladie;
import java.util.ArrayList;
import java.util.List;

public class Lycanthrope extends Creature implements Contagieuse {
    private String categorieAge;
    private int force;
    private int facteurDomination;
    private String rang;
    private int niveau;
    private int impetuosite;
    private Meute meute;
    private String type;

    public Lycanthrope(String type,String nomCreature, String sexe, double poids, double taille, int age, Maladie maladie,
                       String categorieAge, int force, int facteurDomination, String rang, int impetuosite) {
        super(type,nomCreature, sexe, poids, taille, age, maladie);
        this.categorieAge = categorieAge;
        this.force = force;
        this.facteurDomination = facteurDomination;
        this.rang = rang;
        this.impetuosite = impetuosite;
        this.meute = null;
        this.type=type;
        calculerNiveau();
    }

    public String getType() {
        return type;
    }

    // Affiche les caracteristiques du lycanthrope
    public void afficherCaracteristiques() {
        System.out.println("Nom : " + nomCreature);
        System.out.println("Sexe : " + sexe);
        System.out.println("Categorie d'âge : " + categorieAge);
        System.out.println("Poids : " + poids + " kg");
        System.out.println("Taille : " + taille + " cm");
        System.out.println("Force : " + force);
        System.out.println("Facteur de domination : " + facteurDomination);
        System.out.println("Rang : " + rang);
        System.out.println("Niveau : " + niveau);
        System.out.println("Facteur d'impetuosite : " + impetuosite);
        System.out.println("Meute : " + (meute != null ? meute : "Solitaire"));
    }

    // Hurler pour communiquer
    public void hurler(String message) {
        System.out.println(nomCreature + " hurle : " + message);
    }

    // Entendre un hurlement, si la creature n'est pas trop malade
    public void entendreHurlement(String message) {
        if (!estTropMalade()) {
            System.out.println(nomCreature + " entend un hurlement : " + message);
        } else {
            System.out.println(nomCreature + " est trop malade pour entendre un hurlement.");
        }
    }

    // Verifie si la creature est trop malade
    private boolean estTropMalade() {
        return maladies.stream().anyMatch(m -> m.getNiveauActuel() > m.niveauMax-1); // Par exemple, gravite > 3 = trop malade
    }

    // Se separer de la meute
    public void separerMeute() {
        System.out.println(nomCreature + " se separe de la meute " + (meute != null ? meute : "inconnue") + ".");
        this.meute = null;
    }

    // Se transformer en humain
    public void transformerEnHumain() {
        System.out.println(nomCreature + " se transforme en humain.");
        // Reduction de certaines caracteristiques pour simuler la transformation
        this.force /= 2;
        this.impetuosite /= 2;
        this.rang = null; // Plus de rang en forme humaine
    }
    @Override
    public boolean estContagieuse() {
        return getMaladies() != null;
    }

    @Override
    public void contaminer(Creature cible, Maladie maladie) {
        if (estContagieuse()) {
            cible.tomberMalade(maladie);
            System.out.println(nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
        } else {
            System.out.println(nomCreature + " n'est pas contagieux.");
        }
    }





    // Getters pour les autres proprietes
    public String getCategorieAge() {
        return categorieAge;
    }

    public int getForce() {
        return force;
    }
    public void setForce(int force) {
        this.force = force;
    }

    public int getFacteurDomination() {
        return facteurDomination;
    }

    public void setFacteurDomination(int facteurDomination) {
        this.facteurDomination = facteurDomination;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public Meute getMeute() {
        return meute;
    }

    public int getImpetuosite() {
        return impetuosite;
    }

    public int getNiveau() {
        return niveau;
    }
    // Rejoindre une meute
    public void rejoindreMeute(Meute meute) {
        this.meute = meute; // Met à jour la référence vers la meute
    }


    // Quitter la meute actuelle
    public void quitterMeute() {
        if (this.meute != null) {
            System.out.println(nomCreature + " quitte la meute " + this.meute.getNom() + ".");
            this.meute.supprimerLycanthrope(this);
            this.meute = null;
            this.rang = null;
        } else {
            System.out.println(nomCreature + " est déjà solitaire.");
        }
    }


    // Afficher les informations de la hierarchie
    public void afficherHierarchie() {
        if (meute != null) {
            meute.afficherHierarchie();
        } else {
            System.out.println(nomCreature + " est solitaire.");
        }
    }

    public void tenterDomination(Lycanthrope cible) {
        if (this.meute == null || cible.meute == null || !this.meute.equals(cible.meute)) {
            System.out.println(nomCreature + " et " + cible.nomCreature + " ne sont pas dans la même meute.");
            return;
        }

        if (cible.getRang().equals("α") && cible.sexe.equals("Femelle")) {
            System.out.println(nomCreature + " ne peut pas dominer la femelle α.");
            return;
        }

        int seuilForce = this.force + this.impetuosite;
        if (cible.getForce() > seuilForce) {
            System.out.println(cible.nomCreature + " est trop fort pour être domine par " + nomCreature + ".");
            return;
        }

        if (cible.getRang().equals("omega") || this.calculerNiveau() > cible.calculerNiveau()) {
            accomplirDomination(cible);
        } else {
            cible.reagirAggression(this);
        }
    }

    // Accomplir une domination
    private void accomplirDomination(Lycanthrope cible) {
        System.out.println(nomCreature + " domine " + cible.nomCreature + ".");
        this.facteurDomination++;
        cible.setFacteurDomination(cible.getFacteurDomination() - 1);

        // echange des rangs
        String ancienRang = this.rang;
        this.setRang(cible.getRang());
        cible.setRang(ancienRang);

        this.meute.mettreAJourHierarchie();
    }

    // Reagir à une tentative de domination (agression)
    public void reagirAggression(Lycanthrope agresseur) {
        System.out.println(nomCreature + " devient agressif envers " + agresseur.nomCreature + ".");
        this.facteurDomination--; // Baisse du facteur de domination de la cible agressee
    }

    // Calcul du niveau d'un lycanthrope
    public int calculerNiveau() {
        int ageFactor = (age < 20) ? 1 : (age < 40) ? 2 : 3; // Jeune: 1, Adulte: 2, Vieux: 3
        return (ageFactor * 2) + force + facteurDomination;
    }

    public void hurlerLycanthrope(String typeHurlement) {
        System.out.println(nomCreature + " hurle : " + typeHurlement);

        // Hurler en fonction du type de communication
        switch (typeHurlement) {
            case "Domination":
                System.out.println(nomCreature + " exprime sa domination.");
                break;
            case "Soumission":
                System.out.println(nomCreature + " exprime sa soumission.");
                break;
            case "Agressivité":
                System.out.println(nomCreature + " devient agressif.");
                break;
            case "Appartenance":
                System.out.println(nomCreature + " hurle pour signifier son appartenance à la meute.");
                break;
            default:
                System.out.println(nomCreature + " hurle sans précision.");
                break;
        }
    }
    public void gererSolitaires() {
        if (this.meute != null) {
            // Retirer ce lycanthrope de la liste des membres de la meute
            this.meute.getMembres().remove(this);

            // Réinitialiser la référence à la meute
            this.quitterMeute();

            // Informer qu'il est devenu solitaire
            System.out.println(this.nomCreature + " devient solitaire.");
        } else {
            System.out.println(this.nomCreature + " n'appartient à aucune meute.");
        }
    }

}