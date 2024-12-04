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
    public static List<Creature> creaturesEmportees = new ArrayList<>();
    private String type;
    private int compteurDeTours;


    public Creature(String type, String nomCreature, String sexe, double poids, double taille, int age, Maladie maladie) {
        this.nomCreature = nomCreature;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = 100;
        this.type=type;

        this.maladies = new ArrayList<>();
        // Chaque créature arrive avec une maladie
        if (maladie != null) {
            this.maladies.add(maladie);
            System.out.println(nomCreature + " a attrapé la maladie : " + maladie.nomMaladie);
        }
    }
    public int getCompteurDeTours() {
        return compteurDeTours;
    }

    public void setCompteurDeTours(int compteurDeTours) {
        this.compteurDeTours = compteurDeTours;
    }
    public String getType() {
        return type;
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

    public static Creature creerCreature(String type, String nom, String sexe, int moral, int poids, int age, Maladie maladie) {
        switch (type) {
            case "Elfe":
                return new Elfe(nom, sexe, moral, poids, age, maladie,type);
            case "HommeBete":
                return new HommeBete(nom, sexe, moral, poids, age, maladie,type);
            case "Nain":
                return new Nain(nom, sexe, moral, poids, age, maladie,type);
            case "Lycanthrope":
                return new Lycanthrope(type,nom, sexe, moral, poids, age, maladie, Math.random()>0.5 ? "jeune":"vieux", (int) (Math.random()*10),(int) (Math.random()*10), Math.random()<0.5 ? "omega": "beta", (int) (Math.random()*10) );
            case "Orque":
                return new Orque(nom, sexe, moral, poids, age, maladie,type);
            case "Zombie":
                return new Zombie(nom, sexe, moral, poids, age, maladie,type);
            case "Vampire":
                return new Vampire(nom, sexe, moral, poids, age, maladie,type);
            case "Reptilien":
                return new Reptilien(nom, sexe, moral, poids, age, maladie,type);
            default:
                throw new IllegalArgumentException("Type de créature inconnu : " + type);
        }
    }

}