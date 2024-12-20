package view;

import java.util.Scanner;


import Fabrique.Fabrique;
import entites.User;
import repository.*;
import sevices.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String repositoryType = "List"; 

        ClientRepository clientRepository = Fabrique.createClientRepository(repositoryType);
        UserRepository userRepository = Fabrique.createUserRepository(repositoryType);
        ArticleRepository articleRepository = Fabrique.createArticleRepository(repositoryType);
        DetteRepository detteRepository = Fabrique.createDetteRepository(repositoryType);
        PaymentRepository paymentRepository = Fabrique.createPaymentRepository(repositoryType);
        DetailRepository detailRepository = Fabrique.createDetailRepository(repositoryType);
        DetailADRepository detailADRepository = Fabrique.createDetailADRepository(repositoryType);
        DemandeRepository demandeRepository = Fabrique.createDemandeRepository(repositoryType);
        RelanceRepository relanceRepository = Fabrique.createRelanceRepository(repositoryType);
        

        ClientService clientService = new ClientServiceImpl(clientRepository, userRepository);
        UserService userService = new UserServiceImpl(userRepository);
        ArticleServices articleService = new ArticleServicesImpl(articleRepository);
        DetteServices detteService = new DetteServiceImpl(detteRepository, paymentRepository, detailRepository);
        DetailADService detailADService =new DetailADServiceImpl(detailADRepository);
        DetailService detailService =new DetailServiceImp(detailRepository);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository);
        DemandeService demandeService = new DemandeServiceImpl(demandeRepository, detailADRepository);
        RelanceService relanceService =new RelanceServiceImpl(relanceRepository);


        ClientView clientView = new ClientView(clientService, userService, scanner);
        UserView userView = new UserView(userService, scanner);
        ArticleView articleView = new ArticleView(articleService,detailService,detailADService, scanner);
        DetteView detteView = new DetteView(detteService, articleService, paymentService, clientService, scanner);
        DemandeView demandeView = new DemandeView(demandeService, clientService, articleService,relanceService, scanner);



// List<User> users = userRepository.selectAll();
//     for (User user : users) {
//         System.out.println(user);
//         System.out.println("dfghjklhgf");
//     }

        boolean isAuthenticated = false;
        User user = null;

        while (!isAuthenticated) {
            System.out.println("Entrez votre nom d'utilisateur : ");
            String inputUsername = scanner.nextLine();
            System.out.println("Entrez votre mot de passe : ");
            String inputPassword = scanner.nextLine();


            user = userService.authenticate(inputUsername, inputPassword);
           
            if (user != null && user.isActive()) {
                isAuthenticated = true;
                System.out.println("Connexion réussie !");

                switch (user.getRole()) {
                    case Admin:
                        afficherMenuAdmin(scanner, clientView, userView, articleView, detteView);
                        break;
                    case Boutiquier:
                        afficherMenuBoutiquier(scanner, clientView, detteView, demandeView);
                        break;
                    case Client:
                        afficherMenuClient(scanner, demandeView, detteView,user);
                        break;
                    default:
                        System.out.println("Rôle inconnu.");
                        break;
                }
            } else {
                System.out.println("Identifiants incorrects ou utilisateur inactif. Veuillez réessayer.");
            }
        }

        scanner.close(); 
    }

    private static void afficherMenuAdmin(Scanner scanner, ClientView clientView, UserView userView, ArticleView articleView, DetteView detteView) {
        int choix;
        do {
            System.out.println("-------- Menu Administrateur --------");
            System.out.println("1 - Créer un Compte User à un client existant");
            System.out.println("2 - Créer un Compte User");
            System.out.println("3 - Désactiver/Activer un compte utilisateur");
            System.out.println("4 - Lister les comptes User");
            System.out.println("5 - Lister les comptes User par Etat");
            System.out.println("6 - Lister les comptes User par Role");
            System.out.println("7 - Lister les comptes User par User et Role");
            System.out.println("8 - Créer un Article");
            System.out.println("9 - Lister les Articles");
            System.out.println("10 - Lister les Articles par disponibilité");
            System.out.println("11 - Mettre à jour la quantité en stock d’un article");
            System.out.println("12 - Archiver les dettes soldées");
            System.out.println("13 - Quitter");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: clientView.AffecterUserClient(); break;
                case 2: userView.creerUser(); break;
                case 3: userView.activerDesactiverUser(); break;
                case 4: userView.listerUsers(); break;
                case 5: userView.listerUsersByEtat(); break;
                case 6: userView.listerUsersByRole(); break;
                case 7: userView.listerUsersByEtatAndRole(); break;
                case 8: articleView.creerArticle(); break;
                case 9: articleView.listerArticles(); break;
                case 10: articleView.listerArticlesDisponibles(); break;
                case 11: articleView.mettreQteArticleAjour(); break;
                case 12: detteView.archiverDettesSoldées(); break;
                case 13: System.out.println("Retour au menu principal."); break;
                default: System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } while (choix != 13);
    }

    private static void afficherMenuBoutiquier(Scanner scanner, ClientView clientView, DetteView detteView, DemandeView demandeView) {
        int choix;
        do {
            System.out.println("-------- Menu Boutiquier --------");
            System.out.println("1 - Créer Client");
            System.out.println("2 - Lister Clients");
            System.out.println("3 - Rechercher Client Par Téléphone");
            System.out.println("4 - Créer une dette pour un client");
            System.out.println("5 - Lister les dettes");
            System.out.println("6 - Lister les dettes d'un Client");
            System.out.println("7 - Enregistrer un paiement pour une dette");
            System.out.println("8 - Lister les dettes par Etat");
            System.out.println("9 - Lister les demandes de dette");
            System.out.println("10 - Filtrer les demandes par Client");
            System.out.println("11 - Filtrer les demandes par Etat");
            System.out.println("12 - Action sur la demande");
            System.out.println("13 - Retour au menu principal");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: clientView.creerClient(); break;
                case 2: clientView.listerClients(); break;
                case 3: clientView.rechercherClientParTelephone(); break;
                case 4: detteView.creerDette(); break;
                case 5: detteView.listerDettes(); break;
                case 6: detteView.listerDettesParClient(); break;
                case 7: detteView.EnregistrerPayment(); break;
                case 8: detteView.listerDettesParEtat(); break;
                case 9: demandeView.listerDemandes(); break;
                case 10: demandeView.listerDemandesParClient(); break;
                case 11: demandeView.listerDemandesParEtat(); break;
                case 12: demandeView.validationDemande(); break;
                case 13: System.out.println("Retour au menu principal."); break;
                default: System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } while (choix != 13);
    }

    private static void afficherMenuClient(Scanner scanner, DemandeView demandeView, DetteView detteView,User user) {
        int choix;
        do {
            System.out.println("-------- Menu Client --------");
            System.out.println("1 - Faire une demande de dette");
            System.out.println("2 - Lister mes dettes");
            System.out.println("3 - Filtrer mes dettes par Etat");
            System.out.println("4 - Lister mes demandes de dette");
            System.out.println("5 - Envoyer une relance pour une demande de dette annulée");
            System.out.println("6 - Quitter");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: demandeView.creerDemande(user); break;
                case 2: detteView.listerMesDettes(user); break;
                case 3: detteView.listerMesDettesParEtat(user); break;
                case 4: demandeView.listerMesDemandes(user); break;
                case 5: demandeView.EnvoyerRelance(user); break;
                case 6: System.out.println("Au revoir !"); break;
                default: System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } while (choix != 6);
     }
}
