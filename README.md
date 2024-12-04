### **Hôpital Fantastique**

---

## **Description du Projet**

Le projet **Hôpital Fantastique** est une application Java qui modélise un univers peuplé de créatures mythiques (vampires, lycanthropes, elfes, etc.) nécessitant des soins spécialisés dans un hôpital dédié. Chaque créature possède des caractéristiques spécifiques et peut interagir avec son environnement, y compris d'autres créatures, au sein de l'hôpital. 

L'application inclut des fonctionnalités telles que la possibilité de soigné les créatures, la gestion des service de l'hopital, le tout contrôlé et testé via des classes dédiées et des tests unitaires.

---

## **Fonctionnalités Clés**

### **Création de créatures**
- Les créatures sont des entités ayant des attributs tels que :
  - Nom
  - Sexe
  - Poids
  - Taille
  - Âge
  - Maladie
  - Espèce spécifique (Lycanthrope, Vampire, Elfe, etc.)
- Une méthode statique permet de créer des créatures génériques ou spécifiques :
```java
Creature vampire = Creature.creerCreature("Vampire", "Dracula", "Mâle", 75, 180, 350, null);
```

### **Hôpital fantastique**
- L'hôpital prend en charge différentes espèces mythiques avec des services adaptés.
- Permet d'enregistrer les créatures, de suivre leur historique médical et de gérer leur interaction avec l'environnement.

---

## **Architecture**

### **Packages**
1. `creatures` :
   - Contient toutes les classes liées aux créatures et à leur gestion.
   - Exemple : `Creature`, `Lycanthrope`.
2. `hopital` :
   - Contient les classes relatives à l'hôpital.
   - Exemple : `HopitalFantastique`, `ServiceMedical`.
3. `servicesmed`:
   - Contient les classes relative aux services medicaux.
4. `medecins`:
   -Contient les classes relative aux médecins
5. `maladies`:
   -Contient les classes relative aux maladies.
6. `test` :
   - Contient les tests unitaires pour les fonctionnalités des deux modules précédents.

---

## **Exemples d'Utilisation**

### **Création d'un hôpital et ajout de créatures**
```java
Hopital hopital = new Hopital("Hôpital Fantastique", 3);

ServiceMed urgences = new ServiceMed("Urgences", 10, 0, 100, "médiocre");
ServiceMed quarantaine = new ServiceMed("Quarantaine", 5, 0, 80, "faible");
CentreQuarantaine MPT = new CentreQuarantaine("40aine", 7, 23, "faible", false);
hopital.ajouterService(urgences);
hopital.ajouterService(quarantaine);
hopital.ajouterService(MPT);

Creature creature1= Creature.creerCreature("Elfe", "Legolas", "Mâle", 100, 100, 5, genererMaladieAleatoire());
Creature creature2=Creature.creerCreature("Nain", "Damien", "Mâle", 100, 10, 5, genererMaladieAleatoire());

Medecin medecin1 = new Medecin("Dr. John", "Homme", 45, creature1);
Medecin medecin2 = new Medecin("Dr. Smith", "Femme", 39, creature2);
hopital.ajouterMedecin(medecin1);
hopital.ajouterMedecin(medecin2);

urgences.ajouterCreature(creature1);
urgences.ajouterCreature(creature2);

hopital.systemeDeTours();
```


## **Tests Unitaires**

- Les tests sont écrits avec **JUnit 5** et couvrent les fonctionnalités principales :
  - Gestion des créatures : création, modification, suppression...
  - Gestion des meutes : hiérarchie, reproduction, gestion des membres...
  - Gestion de l'hôpital : admission et suivi des créatures.
  - Gestion des maladie: monter de niveau, estletal.
  - Gestion des services: ajouterCreature, ReviserBudget...

Exemple de test :
```java
@Test
    void testAjouterServiceMaxCapacite() {
        // Ajouter un service à l'hôpital
        hopital.ajouterService(service);
        hopital.ajouterService(new ServiceMed("Crypte", 5, 0, 80, "faible"));
        hopital.ajouterService(new ServiceMed("Pediatrie", 5, 0, 80, "faible"));

        // Essayer d'ajouter un service supplémentaire, ce qui devrait échouer
        hopital.ajouterService(new ServiceMed("Quarantaine", 5, 0, 80, "faible"));

        // Vérifier que l'hôpital n'a pas dépassé sa capacité maximale
        assertEquals(3, hopital.services.size(), "L'hôpital ne devrait pas avoir plus de 3 services.");
    }
```

---

## **Auteur**

GUILLERM Mathis MAALAL Ines MINIERE Mathias
