// package view;

// import java.util.Scanner;


// import Fabrique.Fabrique;
// import entites.User;
// import repository.*;
// import sevices.*;

// public class Main {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         String repositoryType = "BD"; 

//         ClientRepository clientRepository = Fabrique.createClientRepository(repositoryType);
//         UserRepository userRepository = Fabrique.createUserRepository(repositoryType);
//         ArticleRepository articleRepository = Fabrique.createArticleRepository(repositoryType);
//         DetteRepository detteRepository = Fabrique.createDetteRepository(repositoryType);
//         PaymentRepository paymentRepository = Fabrique.createPaymentRepository(repositoryType);
//         DetailRepository detailRepository = Fabrique.createDetailRepository(repositoryType);
//         DetailADRepository detailADRepository = Fabrique.createDetailADRepository(repositoryType);
//         DemandeRepository demandeRepository = Fabrique.createDemandeRepository(repositoryType);
//         RelanceRepository relanceRepository = Fabrique.createRelanceRepository(repositoryType);
        

//         ClientService clientService = new ClientServiceImpl(clientRepository, userRepository);
//         UserService userService = new UserServiceImpl(userRepository);
//         ArticleServices articleService = new ArticleServicesImpl(articleRepository);
//         DetteServices detteService = new DetteServiceImpl(detteRepository, paymentRepository, detailRepository);
//         PaymentService paymentService = new PaymentServiceImpl(paymentRepository);
//         DemandeService demandeService = new DemandeServiceImpl(demandeRepository, detailADRepository);
//         RelanceService relanceService =new RelanceServiceImpl(relanceRepository);

//         ClientView clientView = new ClientView(clientService, userService, scanner);
//         UserView userView = new UserView(userService, scanner);
//         ArticleView articleView = new ArticleView(articleService, scanner);
//         DetteView detteView = new DetteView(detteService, articleService, paymentService, clientService, scanner);
//         DemandeView demandeView = new DemandeView(demandeService, clientService, articleService,relanceService, scanner);

//         int choix;
//         do {
//             System.out.println("--------Utilisateur Boutiquier----------");
//             System.out.println("1 - Créer Client");
//             System.out.println("2 - Lister Client");
//             System.out.println("3 - Rechercher Client Par Téléphone");
//             System.out.println("4 - Créer une dette pour un client");
//             System.out.println("5 - Lister les dettes");
//             System.out.println("6 - Lister les dettes d'un Client");
//             System.out.println("7 - Enregistrer un paiement pour une dette");
//             System.out.println("8 - Lister les dettes par Etat");
//             System.out.println("9 - Lister les demandes de dette");
//             System.out.println("10 - Filtrer les demandes par Client");
//             System.out.println("11 - Filtrer les demandes par Etat");
//             System.out.println("12 - Action sur la demande");
            
//             System.out.println("--------Utilisateur Admin----------");
//             System.out.println("13 - Créer un Compte User à un client existant");
//             System.out.println("14 - Créer un Compte User");
//             System.out.println("15 - Désactiver/Activer un compte utilisateur");
//             System.out.println("16 - Lister les comptes User");
//             System.out.println("17 - Lister les comptes User par Etat");
//             System.out.println("18 - Lister les comptes User par Role");
//             System.out.println("19 - Lister les comptes User par User et Role");
//             System.out.println("20 - Créer un Article");
//             System.out.println("21 - Lister les Articles");
//             System.out.println("22 - Lister les Articles par disponibilité");
//             System.out.println("23 - Mettre à jour la quantité en stock d’un article");
//             System.out.println("24 - Archiver les dettes soldées");
            
//             System.out.println("--------Utilisateur Client----------");
//             System.out.println("25 - Faire une demande de dette");
//             System.out.println("26 - Lister mes dettes");
//             System.out.println("27 - Filtrer mes dettes par Etat");
//             System.out.println("28 - Lister mes demandes de dette");
//             System.out.println("29 - Filtrer mes demandes par Etat");
//             System.out.println("30 - Envoyer une relance pour une demande de dette annulée");
            
//             System.out.println("31 - Quitter");
        
//             choix = scanner.nextInt();
//             scanner.nextLine();
        
//             switch (choix) {
//                 case 1:
//                     clientView.creerClient();
//                     break;
//                 case 2:
//                     clientView.listerClients();
//                     break;
//                 case 3:
//                     clientView.rechercherClientParTelephone();
//                     break;
//                 case 4:
//                     detteView.creerDette();
//                     break;
//                 case 5:
//                     detteView.listerDettes();
//                     break;
//                 case 6:
//                     detteView.listerDettesParClient();
//                     break;
//                 case 7:
//                     detteView.EnregistrerPayment();
//                     break;
//                 case 8:
//                     detteView.listerDettesParEtat();
//                     break;
//                 case 9:
//                     demandeView.listerDemandes();
//                     break;
//                 case 10:
//                     demandeView.listerDemandesParClient();
//                     break;
//                 case 11:
//                     demandeView.listerDemandesParEtat();
//                     break;
//                 case 12:
//                     demandeView.validationDemande();
//                     break;
//                 case 13:
//                     clientView.AffecterUserClient();
//                     break;
//                 case 14:
//                     userView.creerUser();
//                     break;
//                 case 15:
//                     userView.activerDesactiverUser();
//                     break;
//                 case 16:
//                     userView.listerUsers();
//                     break;
//                 case 17:
//                     userView.listerUsersByEtat();
//                     break;
//                 case 18:
//                     userView.listerUsersByRole();
//                     break;
//                 case 19:
//                     userView.listerUsersByEtatAndRole();
//                     break;
//                 case 20:
//                     articleView.creerArticle();
//                     break;
//                 case 21:
//                     articleView.listerArticles();
//                     break;
//                 case 22:
//                     articleView.listerArticlesDisponibles();
//                     break;
//                 case 23:
//                     articleView.mettreQteArticleAjour();
//                     break;
//                 case 24:
//                    detteView.archiverDettesSoldées();
//                     break;
//                 case 25:
//                     demandeView.creerDemande();
//                     break;
//                 // case 26:
//                 //    detteView.listerMesDettes();
//                 //     break;
//                 // case 27:
//                 //    detteView.listerMesDettesParEtat();
//                 //     break;
//                 // case 28:
//                 //     demandeView.listerMesDemandes();
//                 //     break;
//                 // case 29:
//                 //     demandeView.listerMesDemandesParEtat();
//                     // break;
//                 case 30:
//                     demandeView.EnvoyerRelance();
//                     break;
//                 case 31:
//                     System.out.println("Au revoir !");
//                     break;
//                 default:
//                     System.out.println("Choix non valide.");
//             }
//         } while (choix != 31);
        
//         scanner.close();
//        }         
//    }






