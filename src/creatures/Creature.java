package creatures;

public class Creature {
    public String nom;
    public String sexe;
    public int age;
    public double poids;
    public double taille;
    public int moral;
    public List<Maladie> maladies;

    public Creature(String nom, String sexe,  double poids, double taille, int age) {
        this.nom = nom;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = 100;
    }
    public void hurler()
}
