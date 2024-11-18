package medecins;

import creatures.Creature;

public class Medecin {
    public String nom;
    public String sexe;
    public int age;
    Creature type;

    public Medecin(String nom, String sexe, int age, Creature type) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.type = type;
    }
    
}
