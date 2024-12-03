package creatures;

import maladies.Maladie;

public interface Contagieuse {
    void contaminer(Creature cible, Maladie maladie);
    boolean estContagieuse();
}
