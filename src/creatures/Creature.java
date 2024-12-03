package creatures;

import maladies.Maladie;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Creature {
    public String nomCreature;
    public String sexe;
    public int age;
    public double poids;
    public double taille;
    public int moral;
    public List<Maladie> maladies;
    private String type;
    public static List<Creature> creaturesEmportees = new ArrayList<>();


    public Creature(String nomCreature, String sexe, double poids, double taille, int age, Maladie maladie, String type) {
        this.nomCreature = nomCreature;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = 100;

        this.maladies = new ArrayList<>();
        // Chaque créature arrive avec une maladie
        if (maladie != null) {
            this.maladies.add(maladie);
            System.out.println(nomCreature + " a attrapé la maladie : " + maladie.nomMaladie);
        }
    }

    public String getNomCreature() {
        return nomCreature;
    }

    public void ajouterMaladie(Maladie maladie) {
        this.maladies.add(maladie);
    }

    public int getMoral() {
        return moral;
    }

    public void setMoral(int moral) {
        this.moral = moral;
    }

    public List<Maladie> getMaladies() {
        return maladies;
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
        if (this.moral <= 10 && !creaturesEmportees.contains(this)) {
            System.out.println(nomCreature + " S'EMPORTE !");
            creaturesEmportees.add(this);
        }
    }

    public void contaminer() {
        Random random = new Random();
        double chance = 0.5;

        for (Creature creature : creaturesEmportees) {
            if (!creature.maladies.isEmpty()) {
                Maladie maladie = creature.maladies.get(random.nextInt(creature.maladies.size()));
                for (Creature cible : creaturesEmportees) {
                    if (!cible.equals(creature) && random.nextDouble() < chance) {
                        cible.tomberMalade(maladie);
                        System.out.println(creature.nomCreature + " a contaminé " + cible.nomCreature + " avec " + maladie.nomMaladie);
                    }
                }
            }
        }
    }

    public void tomberMalade(Maladie maladie) {
        this.maladies.add(maladie);
        System.out.println(nomCreature + " a attrapé " + maladie.nomMaladie);
    }

    public void etreSoignee(Maladie maladie) {
        if (this.maladies.contains(maladie)) {
                this.maladies.remove(maladie);
                this.moral += 20;
                if (this.moral > 100) {
                    this.moral = 100;
                }
                System.out.println(nomCreature + " est soignée de " + maladie.nomMaladie + " et retrouve du moral : " + this.moral);
        } else {
            System.out.println(nomCreature + " n'est pas atteinte par " + maladie.nomMaladie);
        }
    }

    public void trepasser(List<Creature> creaturesDansLeService) {
        System.out.println(nomCreature + " n'a pas survécu.");
        // Affecter négativement le moral des autres créatures dans le service
        System.out.println("La mort de " + nomCreature + " affecte le moral des autres créatures !");
        for (Creature creature : creaturesDansLeService) {
            if (!creature.equals(this)) {
                creature.moral -= 10;
                if (creature.moral < 0) {
                    creature.moral = 0;
                }
                System.out.println("Le moral de " + creature.nomCreature + " a diminué, il est maintenant à : " + creature.moral);
            }
        }

        creaturesEmportees.add(this);
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}


