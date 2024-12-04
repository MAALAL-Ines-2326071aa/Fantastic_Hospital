package servicesMed;

import creatures.*;
import creatures.Regenerante;

public class Crypte extends ServiceMed {
    private int niveauVentilation;
    private double temperature;

    public Crypte(String nom, int nbMaxCreatures,int nbCreaturesPresentes, int superficie, String budget, int niveauVentilation, double temperature) {
        super(nom,nbMaxCreatures,nbCreaturesPresentes,superficie,budget);
        this.niveauVentilation = niveauVentilation;
        this.temperature = temperature;
    }

    public int getNiveauVentilation() {
        return niveauVentilation;
    }

    public void setNiveauVentilation(int niveauVentilation) {
        this.niveauVentilation = niveauVentilation;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    /*
    @Override
    public void reviserBudget(String nouveauBudget) {
        super.reviserBudget(nouveauBudget);
        System.out.println("Niveau de ventilation : " + niveauVentilation);
        System.out.println("Température : " + temperature + "°C");
    }
    */
    @Override
    public void afficherCaracteristiques() {
        super.afficherCaracteristiques();
        System.out.println("Niveau de ventilation : " + niveauVentilation);
        System.out.println("Température : " + temperature + "°C");
    }
    @Override
    public void ajouterCreature(Creature creature) {
        if (creatures.size() >= nbMaxCreatures) {
            System.out.println("Le service " + getNom() + " est plein. Impossible d'ajouter " + creature.getNomCreature());
            return;
        }
        if (creature instanceof Regenerante) {
            creatures.add(creature);
            System.out.println(creature.getNomCreature() + " a été ajoutée à la crypte " + getNom());
        } else {
            System.out.println(creature.getNomCreature() + " ne peut pas être ajoutée à la crypte " + getNom() + " car elle n'est pas régénérante.");
        }
    }

}

