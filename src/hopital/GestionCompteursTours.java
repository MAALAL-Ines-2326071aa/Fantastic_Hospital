package hopital;

import creatures.Creature;
import creatures.ClientVIP;

import java.util.HashMap;
import java.util.Map;

public class GestionCompteursTours {
    private Map<Creature, Integer> compteurs;
    private Map<Creature, Integer> moraux;
    private Map<Creature, Boolean> contamination;

    public GestionCompteursTours() {
        this.compteurs = new HashMap<>();
        this.moraux = new HashMap<>();
        this.contamination = new HashMap<>();
    }

    public void initialiserCompteur(Creature creature) {
        if (!compteurs.containsKey(creature)) {
            compteurs.put(creature, 0);
            moraux.put(creature, 100);
            contamination.put(creature, false);
        }
    }
    public void afficherCompteurs() {
        for (Creature creature : compteurs.keySet()) {
            System.out.println(creature.getNomCreature() + " a attendu " + compteurs.get(creature) + " tours.");
        }
    }

    public void incrementerCompteur(Creature creature) {
        if (compteurs.containsKey(creature)) {
            int compteur = compteurs.get(creature) + 1;
            compteurs.put(creature, compteur);
            gererMoral(creature);
            gererContamination(creature);
        }
    }
    private void gererMoral(Creature creature) {
        int moral = moraux.get(creature);
        if (estVIP(creature)) {
            if (compteurs.get(creature) > 5) {
                moral -= 2;
            }
        } else {
            if (compteurs.get(creature) > 10) {
                moral -= 1;
            }
        }
        if (moral <= 0) {
            moral = 0;
            sEmporter(creature);
        }

        moraux.put(creature, moral);
    }

    private boolean estVIP(Creature creature) {
        return creature instanceof ClientVIP;
    }

    private void sEmporter(Creature creature) {
        System.out.println(creature.getNomCreature() + " s'emporte et risque de contaminer une autre créature.");
        contamination.put(creature, true);
    }
    private void gererContamination(Creature creature) {
        if (contamination.get(creature)) {
            for (Creature autreCreature : compteurs.keySet()) {
                if (autreCreature != creature && !contamination.get(autreCreature)) {
                    System.out.println(creature.getNomCreature() + " a contaminé " + autreCreature.getNomCreature());
                    contamination.put(autreCreature, true);
                }
            }
        }
    }
    public void resetCompteur(Creature creature) {
        if (compteurs.containsKey(creature)) {
            compteurs.put(creature, 0);
            moraux.put(creature, 100);
            contamination.put(creature, false);
        }
    }
    public void supprimerCreature(Creature creature) {
        compteurs.remove(creature);
        moraux.remove(creature);
        contamination.remove(creature);
    }
    public int getMoral(Creature creature) {
        return moraux.getOrDefault(creature, 100);
    }
}
//////////////