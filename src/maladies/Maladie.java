package maladies;

public class Maladie {
    public String nomMaladie;
    public String nomAbrege;
    public int niveauActuel;
    public int niveauMax;

    public Maladie(String nomMaladie, String nomAbrege, int niveauActuel, int niveauMax) {
        this.nomMaladie = nomMaladie;
        this.nomAbrege = nomAbrege;
        this.niveauActuel = niveauActuel;
        this.niveauMax = niveauMax;
    }


    public String getNomAbrege() {
        return nomAbrege;
    }

    public int getNiveauMax() {
        return niveauMax;
    }

    public String getNom() {
        return nomMaladie;
    }

    public int getNiveau() {
        return niveauActuel;
    }

    public void setNiveauActuel(int niveauActuel) {
        if (niveauActuel <= niveauMax) {
            this.niveauActuel = niveauActuel;
        } else {
            System.out.println("Le niveau actuel est supérieur au niveau maximum");
        }
    }
    public void aggraver() {
        this.niveauActuel = Math.min(this.niveauActuel + 1, this.niveauMax);
    }

    public void diminuerNiveau() {
        if (niveauActuel > 0) {
            niveauActuel--;
        }
    }

    public boolean estLetale() {
        return niveauActuel == niveauMax;
    }
    public boolean estGuerie() {
        return niveauActuel == 0;
    }


}
