package hopital;

import creatures.Demoralisante;
import creatures.Lycanthrope;
import creatures.Meute;
import maladies.Maladie;
import servicesMed.CentreQuarantaine;
import servicesMed.Crypte;
import servicesMed.ServiceMed;
import medecins.Medecin;
import creatures.Creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Hopital {
    public String nom;
    public int nombreMaxServices;
    public List<ServiceMed> services;
    public List<Medecin> medecins;
    private List<Medecin> medecinsDisponibles;

    public Hopital(String nom, int nombreMaxServices) {
        this.nom = nom;
        this.nombreMaxServices = nombreMaxServices;
        this.services = new ArrayList<>();
        this.medecins = new ArrayList<>();
        this.medecinsDisponibles = new ArrayList<>();
    }

    public List<Medecin> getMedecinsDisponibles() {
        return medecinsDisponibles;
    }

    public static Maladie genererMaladieAleatoire() {
        List<Maladie> listeMaladies = Arrays.asList(
                new Maladie("Maladie débilitante chronique", "MDC", 1, 5),
                new Maladie("Syndrome fear of missing out", "FOMO", 1, 5),
                new Maladie("Dépendance aux réseaux sociaux", "DRS", 1, 5),
                new Maladie("Porphyrie érythropoïétique congénitale", "PEC", 1, 5),
                new Maladie("Zoopathie paraphrénique lycanthropique", "ZPL", 1, 5),
                new Maladie("COVID-19", "CVD", 1, 5)
        );
        return listeMaladies.get((int) (Math.random() * listeMaladies.size()));
    }



    // Ajouter un service
    public void ajouterService(ServiceMed service) {
        if (services.size() < nombreMaxServices) {
            services.add(service);
            System.out.println("Service " + service.nom + " ajouté à l'hôpital.");
        } else {
            System.out.println("Capacité maximale atteinte. Impossible d'ajouter plus de services.");
        }
    }

    // Afficher les caractéristiques et les créatures
    public void afficherServices() {
        System.out.println("État des services dans l'hôpital :");
        for (ServiceMed service : services) {
            service.afficherCaracteristiques();
        }
    }

    // Ajouter un médecin
    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
        medecinsDisponibles.add(medecin);
        System.out.println("Médecin " + medecin.nom + " ajouté à l'hôpital.");
    }
    public void creerServicesParType() {
        // Liste des types de créatures
        String[] typesCreatures = {"Elfe", "Orque", "HommeBete", "Lycanthrope", "Nain", "Vampire", "Zombie", "Reptilien"};

        // Créer un service pour chaque type de créature (ServiceMed pour chaque type)
        for (String type : typesCreatures) {
            ServiceMed serviceMed = new ServiceMed(type + " Service", 10, 0, 100, "Budget pour " + type);
            services.add(serviceMed);
            System.out.println("Le service pour " + type + " a été créé : " + serviceMed.getNom());
        }

        // Créer une seule Crypte (instance unique)
        Crypte crypte = new Crypte("Crypte Centrale", 10, 0, 100, "Budget Crypte", 5, 18.0);
        services.add(crypte);
        System.out.println("La Crypte centrale a été créée : " + crypte.getNom());

        // Créer un seul Centre de Quarantaine (instance unique)
        CentreQuarantaine centreQuarantaine = new CentreQuarantaine("Centre de Quarantaine", 10, 100, "Budget Quarantaine", true);
        services.add(centreQuarantaine);
        System.out.println("Le Centre de Quarantaine a été créé : " + centreQuarantaine.getNom());
    }

    // Système de tours
    public void systemeDeTours() {
        Scanner scanner = new Scanner(System.in);
        ServiceMed urgences = services.get(0);
        GestionCompteursTours gestionCompteursTours = new GestionCompteursTours();

        boolean premierTour = true;
        creerServicesParType();
        while (true) {
            System.out.println("\n--- Nouveau tour ---");
            if (!premierTour) {
                System.out.println("Des créatures arrivent aux urgences...");
                medecinsDisponibles = new ArrayList<>(medecins);



                // Ajout aléatoire de créatures avec une maladie
                for (int i = 0; i < 3; i++) {
                    Maladie maladie = Hopital.genererMaladieAleatoire();
                    String[] types = {"Elfe", "HommeBete", "Nain", "Lycanthrope", "Orque", "Zombie", "Vampire", "Reptilien"};
                    String type = types[(int) (Math.random() * types.length)];

                    Creature nouvelleCreature = Creature.creerCreature(
                            type,
                            type + (urgences.creatures.size() + 1), // Le nom inclut le type
                            (Math.random() < 0.5) ? "Mâle" : "Femelle",
                            (int) (Math.random() * 100),
                            (int) (150 + Math.random() * 50),
                            (int) (10 + Math.random() * 50),
                            maladie
                    );

                    urgences.ajouterCreature(nouvelleCreature);
                    gestionCompteursTours.initialiserCompteur(nouvelleCreature);
                }
            }
            premierTour = false;

            boolean finTour = false; // Variable pour contrôler la fin du tour

            while (!finTour) {
                System.out.println("\nQue voulez-vous faire ?");
                System.out.println("1. Voir les services et les créatures");
                System.out.println("2. Gérer les médecins");
                System.out.println("3. Passer au tour suivant");
                System.out.println("4. Quitter");
                System.out.print("Votre choix : ");

                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme la nouvelle ligne
                System.out.println();

                switch (choix) {
                    case 1: // Voir les services et les créatures
                        afficherServices();
                        gestionCompteursTours.afficherCompteurs();
                        break;

                    case 2: // Gérer les médecins
                        if (medecinsDisponibles.isEmpty()) {
                            System.out.println("Tous les médecins ont déjà agi ce tour.");
                        } else {
                            gererMedecins(scanner);
                        }
                        break;

                    case 3: // Passer au tour suivant
                        nettoyerCreaturesSoignees();
                        for (ServiceMed service : services) {
                            List<Creature> creaturesTrepasses = new ArrayList<>();

                            for (Creature creature : service.creatures) {
                                gestionCompteursTours.incrementerCompteur(creature);
                                int moralActuel = creature.getMoral();
                                int diminutionMoral = (int) (Math.random() * 5) + 1;
                                creature.setMoral(Math.max(0, moralActuel - diminutionMoral));

                                // Aggraver une maladie ou en ajouter une nouvelle
                                List<Maladie> maladies = creature.getMaladies();
                                if (!maladies.isEmpty()) {
                                    Maladie maladieExistante = maladies.get((int) (Math.random() * maladies.size()));
                                    maladieExistante.aggraver();
                                    System.out.println("La maladie " + maladieExistante.getNomMaladie() + " de " + creature.getNomCreature() + " s'est aggravée.");
                                } else {
                                    creature.ajouterMaladie(genererMaladieAleatoire());
                                    System.out.println("La créature " + creature.getNomCreature() + " a contracté une nouvelle maladie : " + creature.getMaladies().get(0).getNomMaladie() + ".");
                                }

                                // Actions spécifiques aux lycanthropes
                                if (creature instanceof Lycanthrope) {
                                    Lycanthrope lycan = (Lycanthrope) creature;

                                    // Formation de meutes pour les lycanthropes solitaires
                                    if (lycan.getMeute() == null) {
                                        Meute nouvelleMeute = new Meute("Meute " + lycan.getNomCreature());
                                        nouvelleMeute.ajouterLycanthrope(lycan);
                                        System.out.println(lycan.getNomCreature() + " a formé une nouvelle meute.");
                                    }

                                    // Défier la hiérarchie au sein de la meute
                                    if (Math.random() < 0.3) { // 30% de chance de tenter une domination
                                        Meute meute = lycan.getMeute();
                                        if (meute != null) {
                                            Lycanthrope cible = meute.getMembres().stream()
                                                    .filter(m -> !m.equals(lycan))
                                                    .findAny()
                                                    .orElse(null);

                                            if (cible != null) {
                                                lycan.tenterDomination(cible);
                                            }
                                        }
                                    }

                                    // Hurlements pouvant influencer les autres créatures
                                    if (Math.random() < 0.2) { // 30% de chance de hurler
                                        String message = Math.random() < 0.5 ? "Domination" : "Soumission";
                                        lycan.hurlerLycanthrope(message);
                                        System.out.println(lycan.getNomCreature() + " pousse un hurlement de " + message + ".");

                                        // Influence sur le moral des autres créatures
                                        for (Creature autre : service.creatures) {
                                            if (autre != lycan) {
                                                autre.setMoral(Math.max(0, autre.getMoral() - 5));
                                                System.out.println("Le moral de " + autre.getNomCreature() + " diminue à cause du hurlement.");
                                            }
                                        }
                                    }
                                }

                                // Vérifier le trépas basé uniquement sur le moral et les maladies
                                if (creature.getMoral() <= 0 || creature.getMaladies().stream().anyMatch(m -> m.getNiveauActuel() >= m.niveauMax)) {
                                    System.out.println(creature.getNomCreature() + " a trépassé !");
                                    if (creature instanceof Demoralisante) {
                                        ((Demoralisante) creature).demoraliser(service.creatures);
                                    }
                                    creaturesTrepasses.add(creature);
                                }
                            }

                            // Mise à jour des hiérarchies des meutes
                            for (Lycanthrope lycan : service.creatures.stream().filter(c -> c instanceof Lycanthrope).map(c -> (Lycanthrope) c).toList()) {
                                if (lycan.getMeute() != null) {
                                    lycan.getMeute().mettreAJourHierarchie();
                                }
                            }

                            // Supprimer les créatures trépassées
                            service.creatures.removeAll(creaturesTrepasses);
                            service.nbCreaturesPresentes= service.nbCreaturesPresentes-creaturesTrepasses.size();
                        }

                        finTour = true;
                        break;

                    case 4: // Quitter
                        System.out.println("Merci d'avoir joué !");
                        return;

                    default:
                        System.out.println("Choix invalide.");
                }
            }
        }
    }




    // Méthode pour nettoyer les créatures complètement soignées et dont le morale et a 100
    private void nettoyerCreaturesSoignees() {
        for (ServiceMed service : services) {
            List<Creature> creaturesQuitte = new ArrayList<>();
            for (Creature creature : service.creatures) {
                if (creature.getMaladies().isEmpty() && creature.getMoral() == 100) {
                    System.out.println("La créature " + creature.getNomCreature() + " est complètement soignée avec un moral de 100 et quitte l'hôpital.");
                    creaturesQuitte.add(creature);
                }
            }
            service.creatures.removeAll(creaturesQuitte);
        }
    }


    // Gestion des médecins
    public void gererMedecins(Scanner scanner) {
        System.out.println("\n--- Gestion des Médecins ---");

        if (medecinsDisponibles.isEmpty()) {
            System.out.println("Tous les médecins ont déjà agi ce tour.");
            return;
        }

        System.out.println("Médecins disponibles :");
        for (int i = 0; i < medecinsDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + medecinsDisponibles.get(i).nom);
        }

        System.out.print("Choisissez un médecin (0 pour revenir) : ");
        int choixMedecin = scanner.nextInt();
        scanner.nextLine();

        if (choixMedecin == 0) return;

        if (choixMedecin < 1 || choixMedecin > medecinsDisponibles.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Medecin medecin = medecinsDisponibles.get(choixMedecin - 1);
        System.out.println("Médecin sélectionné : " + medecin.nom);

        boolean actionEffectuee = false;

        while (!actionEffectuee) {
            System.out.println("Actions possibles :");
            System.out.println("1. Soigner une créature");
            System.out.println("2. Réviser le budget d'un service");
            System.out.println("3. Transférer une créature entre services");
            System.out.println("4. Creer un médecin");
            System.out.print("Votre choix : ");

            int choixAction = scanner.nextInt();
            scanner.nextLine();

            switch (choixAction) {
                case 1: // Soigner une créature
                    boolean choixValide = false;
                    int indexService;
                    do {
                        System.out.println("Choisissez un service à soigner :");
                        afficherServicesAvecIndex();
                        indexService = scanner.nextInt();
                        if (indexService >= 1 && indexService <= services.size()) {
                            ServiceMed service = services.get(indexService - 1);
                            do {
                                System.out.println("Sélectionnez une créature à soigner :");
                                for (int i = 0; i < service.creatures.size(); i++) {
                                    System.out.println((i + 1) + ". " + service.creatures.get(i).getNomCreature());
                                }
                                int choixCreature = scanner.nextInt();
                                if (choixCreature >= 1 && choixCreature <= service.creatures.size()) {
                                    medecin.soignerUneCreature(service, service.creatures.get(choixCreature - 1));
                                    choixValide = true;
                                } else {
                                    System.out.println("Choix invalide.");
                                }
                            } while (choixValide == false);
                        } else {
                            System.out.println("Choix invalide.");
                        }
                    } while (choixValide == false);
                    actionEffectuee = true;
                    break;

                case 2: // Réviser le budget d'un service
                    choixValide = false;
                    do {
                        System.out.println("Choisissez un service pour réviser le budget :");
                        afficherServicesAvecIndex();
                        indexService = scanner.nextInt();
                        scanner.nextLine();
                        if (indexService >= 1 && indexService <= services.size()) {
                            System.out.print("Entrez le nouveau budget : ");
                            String nouveauBudget = scanner.nextLine();
                            medecin.reviserBudget(services.get(indexService - 1), nouveauBudget);
                            choixValide = true;
                        } else {
                            System.out.println("Choix invalide.");
                        }
                    } while (choixValide == false);
                    actionEffectuee = true;
                    break;

                case 3: // Transférer une créature entre services
                    System.out.println("Transfert de créatures entre services :");
                    afficherServicesAvecIndex();
                    choixValide = false;
                    int indexSource;
                    do {
                        System.out.print("Choisissez le service source : ");
                        indexSource = scanner.nextInt();
                        if (indexSource >= 1 && indexSource <= services.size()) {
                            choixValide = true;
                        } else {
                            System.out.println("Choix invalide.");
                        }
                    } while (!choixValide);

                    choixValide = false;
                    int indexDestination;
                    do {
                        System.out.print("Choisissez le service destination : ");
                        indexDestination = scanner.nextInt();
                        if (indexDestination >= 1 && indexDestination <= services.size()) {
                            choixValide = true;
                        } else {
                            System.out.println("Choix invalide.");
                        }
                    } while (!choixValide);

                    if (indexSource >= 1 && indexSource <= services.size() && indexDestination >= 1 && indexDestination <= services.size()) {
                        ServiceMed source = services.get(indexSource - 1);
                        ServiceMed destination = services.get(indexDestination - 1);

                        // Vérification de la compatibilité des types
                        System.out.println("Sélectionnez une créature à transférer :");
                        for (int i = 0; i < source.creatures.size(); i++) {
                            System.out.println((i + 1) + ". " + source.creatures.get(i).getNomCreature());
                        }

                        int choixCreature = scanner.nextInt();
                        if (choixCreature >= 1 && choixCreature <= source.creatures.size()) {
                            Creature creature = source.creatures.get(choixCreature - 1);

                            // Cas spécifiques pour Crypte et Centre de Quarantaine
                            if (destination instanceof Crypte) {
                                ((Crypte) destination).ajouterCreature(creature);
                            } else if (destination instanceof CentreQuarantaine) {
                                ((CentreQuarantaine) destination).ajouterCreature(creature);
                            } else {
                                // Vérification standard de la correspondance des types
                                String typeCreature = creature.getType(); // On récupère le type de la créature
                                String typeServiceDestination = Medecin.getServiceType(destination); // On récupère le type du service de destination
                                if (typeCreature.equals(typeServiceDestination)) {
                                    // Si les types correspondent, on effectue le transfert
                                    medecin.transfererCreature(source, destination, creature);
                                    System.out.println("Créature transférée.");
                                } else {
                                    System.out.println("Erreur : Le type de la créature ne correspond pas au type du service destination.");
                                }
                            }
                        } else {
                            System.out.println("Choix invalide.");
                        }
                        actionEffectuee = true;
                    } else {
                        System.out.println("Choix invalide.");
                    }
                    break;

                case 4: // Créer un médecin
                    System.out.println("Création d'un nouveau médecin :");
                    System.out.print("Entrez le nom du médecin : ");
                    String nomMedecin = scanner.nextLine();
                    System.out.print("Entrez le sexe du médecin : ");
                    String sexe = scanner.nextLine();
                    System.out.print("Entrez l'âge du médecin : ");
                    int age = scanner.nextInt();
                    Medecin nouveauMedecin = new Medecin(nomMedecin,sexe, age, "Medecin");
                    ajouterMedecin(nouveauMedecin);
                    System.out.println("Le médecin " + nomMedecin + " a été créé.");
                    actionEffectuee = true;
                    medecinsDisponibles.remove(nouveauMedecin);
                    break;

                default:
                    System.out.println("Action invalide.");
            }
        }
        // Marquer le médecin comme indisponible
        medecinsDisponibles.remove(medecin);
        System.out.println("Le médecin " + medecin.nom + " ne peut plus agir ce tour.");
    }


    public void assignerMedecinAuService(Scanner scanner) {
        System.out.println("\n--- Assignation des Médecins ---");

        // Lister les médecins
        System.out.println("Médecins disponibles :");
        for (int i = 0; i < medecins.size(); i++) {
            System.out.println((i + 1) + ". " + medecins.get(i).nom);
        }

        System.out.print("Choisissez un médecin à assigner (0 pour revenir) : ");
        int choixMedecin = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne

        if (choixMedecin == 0) return;

        if (choixMedecin < 1 || choixMedecin > medecins.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Medecin medecin = medecins.get(choixMedecin - 1);

        // Lister les services
        System.out.println("\nServices disponibles :");
        for (int i = 0; i < services.size(); i++) {
            System.out.println((i + 1) + ". " + services.get(i).nom);
        }

        System.out.print("Choisissez un service : ");
        int choixService = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne

        if (choixService < 1 || choixService > services.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        ServiceMed service = services.get(choixService - 1);
        medecin.setService(service);
        System.out.println("Le médecin " + medecin.nom + " est maintenant assigné au service " + service.nom + ".");
    }

    private void afficherServicesAvecIndex() {
        for (int i = 0; i < services.size(); i++) {
            System.out.println((i + 1) + ". " + services.get(i).getNom());
        }
    }

    public List<ServiceMed> getServices() {
        return services;
    }
}
