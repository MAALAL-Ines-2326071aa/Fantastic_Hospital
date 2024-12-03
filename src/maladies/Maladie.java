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
    public String getNomMaladie() {
        return nomMaladie;
    }

    public String getNomAbrege() {
        return nomAbrege;
    }

    public int getNiveauActuel() {
        return niveauActuel;
    }

    public int getNiveauMax() {
        return niveauMax;
    }

    public void setNiveauActuel(int niveauActuel) {
        if (niveauActuel <= niveauMax) {
            this.niveauActuel = niveauActuel;
        } else {
            System.out.println("Le niveau actuel est supérieur au niveau maximum");
        }
    }
    public void aggraver() {
        if (niveauActuel < niveauMax) {
            niveauActuel++;
            System.out.println(nomMaladie + " s'aggrave, son niveau actuel est de : " + niveauActuel);
        } else {
            System.out.println(nomMaladie + " est au niveau maximum ");
        }
    }
    public void diminuerNiveau() {
        final int DECREMENT = 1;

        if (niveauActuel > 0) {
            niveauActuel -= DECREMENT;
            if (niveauActuel < 0) {
                niveauActuel = 0;
            }
            System.out.println(nomMaladie + " s'améliore, son niveau actuel est de : " + niveauActuel);
        } else {
            System.out.println(nomMaladie + " est déjà soigné");
        }
    }

    public boolean estLetale() {
        return niveauActuel == niveauMax;
    }
    public boolean estGuerie() {
        return niveauActuel == 0;
    }
}

