package view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entites.Article;
import entites.Client;
import entites.Detail;
import entites.Dette;
import entites.Payment;
import entites.User;
import sevices.ArticleServices;
import sevices.ClientService;
import sevices.DetteServices;
import sevices.PaymentService;

public class DetteView {
    private DetteServices detteService;
    private ArticleServices articleService;
    private PaymentService paymentService;
    private ClientService clientService;
    private Scanner scanner;

    public DetteView(DetteServices detteService,ArticleServices articleService,PaymentService paymentService,ClientService clientService, Scanner scanner) {
        this.detteService = detteService;
        this.articleService = articleService;
        this.paymentService = paymentService;
        this.clientService = clientService;
        this.scanner = scanner;
    }


  


    // public void creerDette() {
    //     System.out.println("Entrer le Telephone du client:");
    //     String telephone = scanner.nextLine();
    //     Client client = clientService.searchClient(telephone);
    
    //     if (client != null) {
    //         LocalDateTime date = LocalDateTime.now();
    //         System.out.println("Entrez le montant de la dette:");
    //         double montant = scanner.nextDouble();
    //         if (montant <= 0) {
    //             System.out.println("Le montant doit être supérieur à zéro.");
    //             return;
    //         }
    //         System.out.println("Entrez le montant versé:");
    //         double montantVerser = scanner.nextDouble();
    //         if (montantVerser < 0) {
    //             System.out.println("Le montant versé ne peut pas être négatif.");
    //             return;
    //         }
    //         scanner.nextLine();
    //         double montantRestant = montant - montantVerser;
    
    //         // Créer la dette une seule fois
    //         Dette dette = new Dette();
    //         dette.setDate(date);
    //         dette.setMontant(montant);
    //         dette.setMontantRestant(montantRestant);
    //         dette.setMontantVerser(montantVerser);
    //         dette.setClient(client);
    //         dette.setArchived(false);
    //         detteService.createDebt(dette);
    
    //         List<Article> articles = new ArrayList<>();
    //         List<Detail> details = new ArrayList<>();
    
    //         System.out.println("Combien d'articles ?");
    //         int nbArticles = scanner.nextInt();
    //         scanner.nextLine();
    
    //         for (int i = 0; i < nbArticles; i++) {
    //             System.out.println("Entrez le nom de l'article " + (i + 1) + ":");
    //             String articleName = scanner.nextLine();
    //             Article article = articleService.searchArticle(articleName);
    
    //             if (article != null) {
    //                 System.out.println("Entrez la quantité de l'article " + (i + 1) + ":");
    //                 int articleQte = scanner.nextInt();
    //                 scanner.nextLine();
    
    //                 if (articleQte <= 0) {
    //                     System.out.println("La quantité doit être supérieure à zéro.");
    //                     i--;
    //                     continue;
    //                 }
    //                 if (articleQte > article.getQteStock()) {
    //                     System.out.println("Quantité demandée dépasse le stock disponible de " + article.getQteStock() + ".");
    //                     i--;
    //                     continue;
    //                 }
    
    //                 // Mise à jour du stock
    //                 article.setQteStock(article.getQteStock() - articleQte);
    //                 articleService.updateArticle(article);
    //                 articles.add(article);
    
    //                 // Création du détail lié à la dette
    //                 Detail detail = new Detail();
    //                 detail.setArticle(article);
    //                 detail.setQte(articleQte);
    //                 detail.setDette(dette);
    //                 details.add(detail);
    //                 detteService.createDetail(detail);
    
    //             } else {
    //                 System.out.println("Article non trouvé: " + articleName);
    //                 i--; // Redemander le même article
    //             }
    //         }
    
    //         // Ajouter les paiements si montant versé > 0
    //         List<Payment> paiements = new ArrayList<>();
    //         if (montantVerser > 0) {
    //             Payment payment = new Payment();
    //             payment.setDate(LocalDateTime.now());
    //             payment.setMontant(montantVerser);
    //             payment.setDette(dette);
    //             paiements.add(payment);
    //             paymentService.createPayment(payment);
    //         }
    //         dette.setPaiements(paiements);
    
    //         System.out.println("Dette créée avec succès.");
    //     } else {
    //         System.out.println("Client non trouvé avec le téléphone: " + telephone);
    //     }
    // }

    public void creerDette() {
        Client client = null;
        String telephone;
    
        // Validation du client (do-while pour s'assurer que le client existe)
        do {
            System.out.println("Entrer le Téléphone du client :");
            telephone = scanner.nextLine();
            client = clientService.searchClient(telephone);
            if (client == null) {
                System.out.println("Client non trouvé avec le téléphone : " + telephone + ". Réessayez.");
            }
        } while (client == null);
    
        // Date actuelle
        LocalDateTime date = LocalDateTime.now();
    
        // Validation du montant de la dette (do-while pour montant > 0)
        double montant;
        do {
            System.out.println("Entrez le montant de la dette :");
            montant = scanner.nextDouble();
            if (montant <= 0) {
                System.out.println("Le montant doit être supérieur à zéro. Réessayez.");
            }
        } while (montant <= 0);
    
        // Validation du montant versé (do-while pour montant >= 0 et <= montant)
        double montantVerser;
        do {
            System.out.println("Entrez le montant versé :");
            montantVerser = scanner.nextDouble();
            if (montantVerser < 0) {
                System.out.println("Le montant versé ne peut pas être négatif. Réessayez.");
            } else if (montantVerser > montant) {
                System.out.println("Le montant versé ne peut pas dépasser le montant total de la dette. Réessayez.");
            }
        } while (montantVerser < 0 || montantVerser > montant);
        scanner.nextLine(); // Consommer le reste de la ligne
    
        double montantRestant = montant - montantVerser;
    
        // Création de la dette
        Dette dette = new Dette();
        dette.setDate(date);
        dette.setMontant(montant);
        dette.setMontantRestant(montantRestant);
        dette.setMontantVerser(montantVerser);
        dette.setClient(client);
        dette.setArchived(false);
        detteService.createDebt(dette);
    
        List<Article> articles = new ArrayList<>();
        List<Detail> details = new ArrayList<>();
    
        System.out.println("Combien d'articles ?");
        int nbArticles = scanner.nextInt();
        scanner.nextLine();
    
        // Ajout des articles et création des détails
        for (int i = 0; i < nbArticles; i++) {
            System.out.println("Entrez le nom de l'article " + (i + 1) + ":");
            String articleName = scanner.nextLine();
            Article article = articleService.searchArticle(articleName);
    
            if (article != null) {
                int articleQte;
                do {
                    System.out.println("Entrez la quantité de l'article " + (i + 1) + ":");
                    articleQte = scanner.nextInt();
                    scanner.nextLine();
                    if (articleQte <= 0) {
                        System.out.println("La quantité doit être supérieure à zéro. Réessayez.");
                    } else if (articleQte > article.getQteStock()) {
                        System.out.println("Quantité demandée dépasse le stock disponible de " + article.getQteStock() + ". Réessayez.");
                    }
                } while (articleQte <= 0 || articleQte > article.getQteStock());
    
                // Mise à jour du stock
                article.setQteStock(article.getQteStock() - articleQte);
                articleService.updateArticle(article);
                articles.add(article);
    
                // Création du détail lié à la dette
                Detail detail = new Detail();
                detail.setArticle(article);
                detail.setQte(articleQte);
                detail.setDette(dette);
                details.add(detail);
                detteService.createDetail(detail);
    
            } else {
                System.out.println("Article non trouvé : " + articleName + ". Réessayez.");
                i--; // Reprendre la boucle pour le même index
            }
        }
    
        // Ajouter les paiements si montant versé > 0
        List<Payment> paiements = new ArrayList<>();
        if (montantVerser > 0) {
            Payment payment = new Payment();
            payment.setDate(LocalDateTime.now());
            payment.setMontant(montantVerser);
            payment.setDette(dette);
            paiements.add(payment);
            paymentService.createPayment(payment);
        }
        dette.setPaiements(paiements);
    
        System.out.println("Dette créée avec succès.");
    }
    
    
    

    // public void listerDettes() {
    //     List<Dette> dettes = detteService.findNotArchivedDettes();
    //     //  dettes=detteService.findNotArchivedDettes();

    //     if (dettes.isEmpty()) {
    //         System.out.println("Aucune dette trouvée.");
    //     } else {
    //         dettes.forEach(System.out::println);
    //     }
    // }
    public void listerDettes() {
        List<Dette> dettes = detteService.findNotArchivedDettes();
    
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette trouvée.");
        } else {
            System.out.printf("%-15s %-15s %-15s %-15s\n",  "Client", "Montant", "Versé", "Restant");
            System.out.println("-------------------------------------------------------------------------------");
    
            for (Dette dette : dettes) {
                // Afficher les informations principales de la dette
                System.out.printf(" %-15s %-15.2f %-15.2f %-15.2f\n",
                        
                        dette.getClient().getSurname(),
                        dette.getMontant(),
                        dette.getMontantVerser(),
                        dette.getMontantRestant());
    
                // Afficher les détails des articles
                System.out.println("  Articles :");
                List<Detail> details = detteService.findDetailsByDetteId(dette.getId()); // Charger les détails de la dette
                for (Detail detail : details) {
                    System.out.printf("    - Article: %-15s | Quantité: %-5d\n",
                            detail.getArticle().getNomArticle(),
                            detail.getQte());
                }
    
                // Afficher les paiements
                System.out.println("  Paiements :");
                for (Payment payment : dette.getPaiements()) {
                    System.out.printf("    - Paiement ID: %-5d | Montant: %-10.2f | Date: %-20s\n",
                            payment.getId(),
                            payment.getMontant(),
                            payment.getDate().toString());
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
    }
    


    // public void listerDettesParClient() {
    //     System.out.print("Entrer le téléphone du client: ");
    //                 String telephoneClient = scanner.nextLine();
    //                 Client client1 = clientService.searchClient(telephoneClient);
    //                 if (client1 == null) {
    //                     System.out.println("Client non trouvé.");
                        
    //                 }
    //                 System.out.println("Sous-menu pour lister les dettes");
    //                 System.out.println("1. Lister toutes les dettes");
    //                 System.out.println("2. Lister les dettes soldées");
    //                 System.out.println("3. Lister les dettes non soldées");
    //                 int choixListe = scanner.nextInt();
    //                 scanner.nextLine(); 

    //                 switch (choixListe) {
    //                     case 1: 
    //                         detteService.listAllDebts(client1);
    //                         break;
    //                     case 2: 
    //                         detteService.listPaidDebts(client1);
    //                         break;
    //                     case 3: 
    //                         detteService.listUnpaidDebts(client1);
    //                         break;
    //                     default:
    //                         System.out.println("Choix invalide.");
    //                 }
    // }

    public void listerDettesParClient() {
        Client client1 = null;
    
        // Demander le téléphone tant que le client n'est pas trouvé
        do {
            System.out.print("Entrer le téléphone du client: ");
            String telephoneClient = scanner.nextLine();
            client1 = clientService.searchClient(telephoneClient);
    
            if (client1 == null) {
                System.out.println("Client non trouvé. Veuillez réessayer.");
            }
        } while (client1 == null);
    
        // Sous-menu pour lister les dettes
        System.out.println("Sous-menu pour lister les dettes");
        System.out.println("1. Lister toutes les dettes");
        System.out.println("2. Lister les dettes soldées");
        System.out.println("3. Lister les dettes non soldées");
        System.out.print("Votre choix: ");
        int choixListe = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante
    
        // Exécution selon le choix
        switch (choixListe) {
            case 1: 
                detteService.listAllDebts(client1);
                break;
            case 2: 
                detteService.listPaidDebts(client1);
                break;
            case 3: 
                detteService.listUnpaidDebts(client1);
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }
    
    // public void EnregistrerPayment() {
    //     System.out.println("Enregistrement d'un paiement pour une dette");
    //                 System.out.print("Entrez l'ID de la dette: ");
    //                 int debtId = scanner.nextInt();
    //                 scanner.nextLine(); 
    //                 LocalDateTime paymentDate = LocalDateTime.now();
    //                 System.out.print("Entrez le montant du paiement: ");
    //                 double paymentAmount = scanner.nextDouble();
    //                 detteService.recordPayment(debtId, paymentDate, paymentAmount);
    // }

    public void EnregistrerPayment() {
        System.out.println("Enregistrement d'un paiement pour une dette");
    
        Dette dette = null; // Initialisation de la dette
        int debtId;
    
        // Demander l'ID de la dette tant que l'ID est invalide ou que la dette n'est pas trouvée
        do {
            System.out.print("Entrez un ID valide pour la dette: ");
            debtId = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante
    
            if (debtId <= 0) {
                System.out.println("L'ID de la dette doit être supérieur à 0.");
            } else {
                // Recherche de la dette
                dette = detteService.findDebtById(debtId);
                if (dette == null) {
                    System.out.println("Dette introuvable. Veuillez entrer un ID valide.");
                }
            }
        } while (debtId <= 0 || dette == null);
    
        // Vérifier si la dette est déjà soldée
        if (dette.getMontantRestant() <= 0) {
            System.out.println("La dette est déjà soldée. Aucun paiement supplémentaire n'est nécessaire.");
            return;
        }
    
        LocalDateTime paymentDate = LocalDateTime.now();
    
        // Demander et valider le montant du paiement
        double paymentAmount;
        do {
            System.out.print("Entrez le montant du paiement: ");
            paymentAmount = scanner.nextDouble();
            if (paymentAmount <= 0) {
                System.out.println("Le montant du paiement doit être supérieur à 0.");
            } else if (paymentAmount > dette.getMontantRestant()) {
                System.out.printf("Le montant du paiement ne peut pas dépasser le montant restant de %.2f.\n", dette.getMontantRestant());
            }
        } while (paymentAmount <= 0 || paymentAmount > dette.getMontantRestant());
    
        // Enregistrer le paiement
        detteService.recordPayment(debtId, paymentDate, paymentAmount);
    }
    
    // public void listerDettesParEtat() {
    //     System.out.print("1-Soldées/2-Non Soldées");
    //                 int choixA = scanner.nextInt();
    //                 if (choixA==1) {
    //                     List<Dette> dettesSodées = detteService.getDettesSodées();
    //                     dettesSodées.forEach(System.out::println);
                        
    //                 } else if (choixA == 2) {
    //                     List<Dette> notdettesSodées = detteService.getNotDettesSodées();
    //                     notdettesSodées.forEach(System.out::println);
    //                 } else {
    //                     System.out.print("choix invalide");
    //                 }
        
    // }

    public void listerDettesParEtat() {
        System.out.print("1-Soldées/2-Non Soldées: ");
        int choixA = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante
    
        if (choixA == 1) {
            // Récupérer les dettes soldées
            List<Dette> dettesSodées = detteService.getDettesSodées();
            afficherDetailsDettes(dettesSodées); // Appeler la méthode pour afficher les détails des dettes
        } else if (choixA == 2) {
            // Récupérer les dettes non soldées
            List<Dette> notdettesSodées = detteService.getNotDettesSodées();
            afficherDetailsDettes(notdettesSodées); // Appeler la méthode pour afficher les détails des dettes
        } else {
            System.out.println("Choix invalide.");
        }
    }
    

    private void afficherDetailsDettes(List<Dette> dettes) {
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette trouvée.");
        } else {
            // En-tête de tableau
            System.out.printf("%-15s %-15s %-15s %-15s\n", "Client", "Montant", "Versé", "Restant");
            System.out.println("-------------------------------------------------------------------------------");
    
            for (Dette dette : dettes) {
                // Affichage des informations principales de la dette
                System.out.printf(" %-15s %-15.2f %-15.2f %-15.2f\n",
                        dette.getClient().getSurname(),
                        dette.getMontant(),
                        dette.getMontantVerser(),
                        dette.getMontantRestant());
    
                // Affichage des détails des articles
                System.out.println("  Articles :");
                List<Detail> details =detteService.findDetailsByDetteId(dette.getId()); // Appeler la méthode directement
                if (details.isEmpty()) {
                    System.out.println("    Aucun article associé.");
                } else {
                    for (Detail detail : details) {
                        System.out.printf("    - Article: %-15s | Quantité: %-5d\n",
                                detail.getArticle().getNomArticle(),
                                detail.getQte());
                    }
                }
    
                // Affichage des paiements
                System.out.println("  Paiements :");
                if (dette.getPaiements().isEmpty()) {
                    System.out.println("    Aucun paiement associé.");
                } else {
                    for (Payment payment : dette.getPaiements()) {
                        System.out.printf("    - Paiement ID: %-5d | Montant: %-10.2f | Date: %-20s\n",
                                payment.getId(),
                                payment.getMontant(),
                                payment.getDate().toString());
                    }
                }
    
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
    }
    


    public void archiverDettesSoldées() {
        detteService.archiverDettesSoldées();
        
    }


    public void listerMesDettes(User user) {
        Client client = clientService.findClientByUser(user);

        if (client != null) {
            List<Dette> dettes = detteService.listerDettesParClient(client);
            afficherDetailsDettes(dettes);
        } else {
            System.out.println("Aucun client trouvé pour cet utilisateur.");
        }
    }
    public void listerMesDettesParEtat(User user) {
        System.out.print("1-Soldées/2-Non Soldées");
                    int choixA = scanner.nextInt();
                    if (choixA==1) {
                        List<Dette> dettesSodées = detteService.getMesDettesSodées(user);
                        afficherDetailsDettes(dettesSodées);
                        
                    } else if (choixA == 2) {
                        List<Dette> notdettesSodées = detteService.getMesNotDettesSodées(user);
                        afficherDetailsDettes(notdettesSodées);
                    } else {
                        System.out.print("choix invalide");
                    }
        
    }

    

    
}
