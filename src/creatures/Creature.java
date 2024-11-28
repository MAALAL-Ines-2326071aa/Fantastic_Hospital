package creatures;

import maladies.Maladie;
import java.util.List;
import java.util.ArrayList;

public class Creature {
    public String nomCreature;
    public String sexe;
    public int age;
    public double poids;
    public double taille;
    public int moral;
    public List<Maladie> maladies;

    public Creature(String nomCreature, String sexe, double poids, double taille, int age) {
        this.nomCreature = nomCreature;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = 100;
        this.maladies = new ArrayList<>();
    }
    public String getNom() {
        return nomCreature;
    }

    public void attendre() {
        this.moral -= 10;
        if (this.moral < 0) {
            this.moral = 0;
        }
        System.out.println(nomCreature + " attend, son moral actuel est de : " + this.moral);
    }
    public void hurler() {
        if (this.moral <= 20) {
            System.out.println(nomCreature + " hurle !");
        }
    }
    public void semporter() {
        System.out.println(nomCreature + " S'EMPORTE !");
    }
    public void tomberMalade(Maladie maladie) {
        this.maladies.add(maladie);
        System.out.println(nomCreature + " a attrapé " + maladie.nomMaladie );
    }
    public void etreSoignee(Maladie maladie) {
        if (this.maladies.contains(maladie)) {
            if (this.moral == 100) {
                System.out.println(nomCreature + " est déjà au max et n'a donc pas besoin de soins");
            } else {
                this.maladies.remove(maladie);
                this.moral += 20;
                if (this.moral > 100) {
                    this.moral = 100;
                }
                System.out.println(nomCreature + " est soignée de " + maladie.nomMaladie + " et retrouve du moral : " + this.moral);
            }
        } else {
            System.out.println(nomCreature + " n'est pas atteinte par " + maladie.nomMaladie);
        }
    }
    public void trepasser() {
        System.out.println(nomCreature + " n'a pas survécu");
    }
}
