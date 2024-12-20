package view;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import entites.Article;
import entites.Client;
import entites.Demande;

import entites.DetailAD;

import entites.Relance;
import entites.Statut;
import entites.User;
import sevices.ArticleServices;
import sevices.ClientService;
import sevices.DemandeService;
import sevices.RelanceService;


public class DemandeView {

    private DemandeService demandeService;
    private ClientService clientService;
    private ArticleServices articleServices;
    private RelanceService relanceService;

    private Scanner scanner;

    public DemandeView(DemandeService demandeService, ClientService clientService,ArticleServices articleServices, RelanceService relanceService,Scanner scanner) {
        this.demandeService = demandeService;
        this.clientService = clientService;
        this.articleServices = articleServices;
        this.relanceService = relanceService;
        this.scanner = scanner;
    }

    


    public void creerDemande(User utilisateurConnecte) {
        Client client = clientService.findClientByUser(utilisateurConnecte);
        if (client == null) {
            System.out.println("Aucun client associé à cet utilisateur. Impossible de créer une demande.");
            return;
        }
    
        Double montant;
        do {
            System.out.print("Entrez le montant de la dette : ");
            montant = scanner.nextDouble();
            scanner.nextLine();
            if (montant <= 0) {
                System.out.println("Le montant doit être supérieur à 0.");
            }
        } while (montant <= 0);
    
        LocalDateTime date = LocalDateTime.now();
    
        System.out.print("Entrez la description de la dette : ");
        String description = scanner.nextLine();
    
        Statut statut = Statut.EnCours;
        Demande demande = new Demande();
        demande.setMontant(montant);
        demande.setDate(date);
        demande.setDescription(description);
        demande.setStatut(statut);
        demande.setClient(client);
    
        demandeService.createDemande(demande);
    
        listerArticlesDisponibles();
        System.out.print("Combien d'articles voulez-vous ajouter ? ");
        int nombreArticles = scanner.nextInt();
        scanner.nextLine();
    
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < nombreArticles; i++) {
            System.out.println("Entrez le nom de l'article " + (i + 1) + ":");
            String articleName = scanner.nextLine();
            Article article = articleServices.searchArticle(articleName);
            if (article != null) {
                articles.add(article);
                System.out.println("Entrez la quantité de l'article " + (i + 1) + ":");
                int articleQte = scanner.nextInt();
                scanner.nextLine();
                if (articleQte <= 0) {
                    System.out.println("La quantité doit être supérieure à zéro.");
                    i--;
                    continue;
                }
    
                DetailAD detailAD = new DetailAD();
                detailAD.setArticle(article);
                detailAD.setQte(articleQte);
                detailAD.setDemande(demande);
                demandeService.createDetailAD(detailAD);
            } else {
                System.out.println("Article non trouvé: " + articleName);
                i--;
            }
        }
    
        System.out.println("Demande de dette créée avec succès.");
    }
    



    public void listerArticlesDisponibles() {
        List<Article> articlesDisponibles = articleServices.findAllArticle();
        if (articlesDisponibles.isEmpty()) {
            System.out.println("Aucun article disponible.");
        } else {
            System.out.println("Articles disponibles :");
            articlesDisponibles.forEach(article -> 
                System.out.println("ID: " + article.getId() + ", Nom: " + article.getNomArticle() + ", Quantité: " + article.getQteStock())
            );
        }
    }
   
    
    
    public void listerDemandesParClient() {
        Client client1 = null;
    
        do {
            System.out.print("Entrer le téléphone du client: ");
            String telephoneClient = scanner.nextLine();
            client1 = clientService.searchClient(telephoneClient);
    
            if (client1 == null) {
                System.out.println("Client non trouvé. Veuillez réessayer.");
            }
        } while (client1 == null);
    
        int choixListe;
        do {
            System.out.println("Sous-menu pour lister les Demandes");
            System.out.println("1. Lister toutes les Demandes");
            System.out.println("2. Lister les Demandes En Cours");
            System.out.println("3. Lister les Demandes Acceptées");
            System.out.println("4. Lister les Demandes Annulées");
            System.out.print("Votre choix : ");
            choixListe = scanner.nextInt();
            scanner.nextLine();
    
            if (choixListe < 1 || choixListe > 4) {
                System.out.println("Choix invalide. Veuillez sélectionner une option entre 1 et 4.");
            }
        } while (choixListe < 1 || choixListe > 4);
    
        List<Demande> demandes = new ArrayList<>();
        switch (choixListe) {
            case 1:
                demandes = demandeService.findClientAllDemande(client1);
                break;
            case 2:
                demandes = demandeService.findClientDemandeEnCours(client1);
                break;
            case 3:
                demandes = demandeService.findClientDemandeAcceptées(client1);
                break;
            case 4:
                demandes = demandeService.findClientDemandeAnnulées(client1);
                break;
        }
    
        
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande trouvée.");
        } else {
            afficherDemande(demandes); 
        }
    }
    


    public void listerDemandes() {
        List<Demande> demandes = demandeService.findAllDemande();
        if (demandes.isEmpty()) {
            System.out.println("Aucune Demande trouvée.");
        } else {
            afficherDemande(demandes);
        }
    }



    private void afficherDemande(List<Demande> demandes) {
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande trouvée.");
        } else {
           
        
            System.out.printf("%-5s %-15s %-20s %-20s %-15s %-15s\n", "ID", "Client", "Date", "Description", "Montant", "Statut");
            System.out.println("--------------------------------------------------------------------------------------------------------");
    
            for (Demande demande : demandes) {

                System.out.printf("%-5d %-15s %-20s %-20s %-15.2f %-15s\n",
                        demande.getId(), 
                        demande.getClient() != null ? demande.getClient().getSurname() : "N/A",
                        demande.getDate().toString(), 
                        demande.getDescription(),
                        demande.getMontant(),
                        demande.getStatut());
    
                System.out.println("  Articles :");
                List<DetailAD> detailADs = demandeService.findDetailsByDemandeId(demande.getId()); 
                if (detailADs.isEmpty()) {
                    System.out.println("    Aucun article associé.");
                } else {
                    for (DetailAD detail : detailADs) {
                        System.out.printf("    - Article: %-15s | Quantité: %-5d\n",
                                detail.getArticle().getNomArticle(),
                                detail.getQte());
                    }
                }
                System.out.println("--------------------------------------------------------------------------------------------------------");
            }
        }
    }
    

    
    public void listerDemandesParEtat() {
        int choixListe;
    
        do {
            System.out.println("Sous-menu pour lister les Demandes");
            System.out.println("1. Lister toutes les Demandes");
            System.out.println("2. Lister les Demandes En Cours");
            System.out.println("3. Lister les Demandes Acceptées");
            System.out.println("4. Lister les Demandes Annulées");
            System.out.print("Votre choix : ");
            choixListe = scanner.nextInt();
            scanner.nextLine(); 
    
            if (choixListe < 1 || choixListe > 4) {
                System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 4.");
            }
        } while (choixListe < 1 || choixListe > 4);
    
        List<Demande> demandes = new ArrayList<>();
    
        switch (choixListe) {
            case 1:
                demandes = demandeService.findAllDemande();
                break;
            case 2:
                demandes = demandeService.findDemandeEnCours();
                break;
            case 3:
                demandes = demandeService.findDemandeAcceptées();
                break;
            case 4:
                demandes = demandeService.findDemandeAnnulées();
                break;
        }
    
       
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande trouvée.");
        } else {
            afficherDemande(demandes); 
        }
    }
    

    
    public void validationDemande() {
        Demande demande = null;
    
       
        do {
            System.out.print("Entrer l'ID de la demande à valider : ");
            int demandeId = scanner.nextInt();
            scanner.nextLine(); 
    
            demande = demandeService.findDemandeById(demandeId);
    
            if (demande == null) {
                System.out.println("Demande non trouvée. Veuillez réessayer.");
            } else if (demande.getStatut() != Statut.EnCours) {
                System.out.println("La demande ne peut pas être modifiée car son statut est déjà " + demande.getStatut() + ".");
                demande = null; 
            }
        } while (demande == null);
    
        int choix;
        
        do {
            System.out.println("Voulez-vous accepter ou annuler cette demande ?");
            System.out.println("1. Accepter");
            System.out.println("2. Annuler");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); 
    
            if (choix != 1 && choix != 2) {
                System.out.println("Choix invalide. Veuillez sélectionner 1 ou 2.");
            }
        } while (choix != 1 && choix != 2);
    
       
        Statut nouveauStatut = (choix == 1) ? Statut.Accepté : Statut.Annulé;
        demande.setStatut(nouveauStatut);
        demandeService.updateDemande(demande); 
    
        System.out.println("Demande mise à jour avec succès.");
    }
    
    
    


    public void listerMesDemandes(User user) {
        Client client1 = null;
    
        do {
            client1 = clientService.findClientByUser(user); 
            if (client1 == null) {
                System.out.println("Client non trouvé. Veuillez vérifier les informations utilisateur.");
                return; 
            }
        } while (client1 == null);
    
        int choixListe;
        
        do {
            System.out.println("Sous-menu pour lister les Demandes");
            System.out.println("1. Lister toutes les Demandes");
            System.out.println("2. Lister les Demandes En Cours");
            System.out.println("3. Lister les Demandes Acceptées");
            System.out.println("4. Lister les Demandes Annulées");
            System.out.print("Votre choix : ");
            choixListe = scanner.nextInt();
            scanner.nextLine(); 
    
            if (choixListe < 1 || choixListe > 4) {
                System.out.println("Choix invalide. Veuillez entrer un numéro entre 1 et 4.");
            }
        } while (choixListe < 1 || choixListe > 4);
    
        List<Demande> demandes = new ArrayList<>();
    
        switch (choixListe) {
            case 1:
                demandes = demandeService.findClientAllDemande(client1); 
                break;
            case 2:
                demandes = demandeService.findClientDemandeEnCours(client1);
                break;
            case 3:
                demandes = demandeService.findClientDemandeAcceptées(client1); 
                break;
            case 4:
                demandes = demandeService.findClientDemandeAnnulées(client1); 
                break;
        }
    
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande trouvée.");
        } else {
            afficherDemande(demandes); 
        }
    }
    
    


    public void listerMesDemandesParEtat(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listerMesDemandesParEtat'");
    }





public void EnvoyerRelance(User user) {
    Client client1 = null;
    client1 = clientService.findClientByUser(user);

    List<Demande> demandesAnnulees = demandeService.findClientDemandeAnnulées(client1);
    if (demandesAnnulees == null || demandesAnnulees.isEmpty()) {
        System.out.println("Aucune demande annulée disponible pour envoyer une relance.");
        return; 
    }

    Demande demande = null;

    do {
        System.out.print("Entrer l'ID de la demande à relancer : ");
        int demandeId = scanner.nextInt();
        scanner.nextLine(); 

        demande = demandeService.findDemandeById(demandeId);

        if (demande == null) {
            System.out.println("Demande non trouvée. Veuillez réessayer.");
        } else if (!demande.getStatut().equals(Statut.Annulé)) {
            System.out.println("La demande ne peut pas être relancée car elle n'est pas Annulée.");
            demande = null; 
        }
    } while (demande == null);

    String message;
    do {
        System.out.print("Entrer le message de relance : ");
        message = scanner.nextLine();
        if (message.isBlank()) {
            System.out.println("Le message de relance ne peut pas être vide. Veuillez réessayer.");
        }
    } while (message.isBlank());

    Relance relance = new Relance();
    relance.setDemande(demande); 
    relance.setDescription(message); 
    relance.setDate(LocalDateTime.now()); 

    relanceService.createRelance(relance); 

    System.out.println("Relance envoyée avec succès !");
}



    
}
