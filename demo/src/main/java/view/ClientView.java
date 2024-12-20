package view;

import java.util.List;
import java.util.Scanner;
import entites.Client;
import entites.Role;
import entites.User;
import sevices.ClientService;
import sevices.UserService;

public class ClientView {
    private ClientService clientService;
    private UserService userService;
    private Scanner scanner;
    Client client;
    public ClientView(ClientService clientService,UserService userService, Scanner scanner) {
        this.clientService = clientService;
        this.userService = userService;
        this.scanner = scanner;
    }

 
    public void creerClient() {
        System.out.println("Création d'un nouveau client");
        client = new Client();
    
        String surname;
        do {
            System.out.println("Entrer le surnom :");
            surname = scanner.nextLine();
            if (clientService.searchClientBySurname(surname) != null) {
                System.out.println("Le surnom existe déjà. Veuillez en choisir un autre.");
                surname = null; 
            }
        } while (surname == null);
        client.setSurname(surname);
    
        
        String telephone;
        do {
            System.out.println("Entrer le Téléphone (9 chiffres max, positif) :");
            telephone = scanner.nextLine();
            if (!isValidTelephone(telephone)) {
                System.out.println("Téléphone invalide. Veuillez entrer un numéro valide (positif, max 9 chiffres).");
                telephone = null; 
            } else if (clientService.searchClient(telephone) != null) {
                System.out.println("Le téléphone existe déjà.");
                telephone = null; 
            }
        } while (telephone == null);
        client.setTelephone(telephone);
    
        // Adresse
        System.out.println("Entrer l'adresse :");
        String adresse = scanner.nextLine();
        client.setAdresse(adresse);
    
        
        System.out.print("Voulez-vous associer ce client à un compte utilisateur ? (oui/non) : ");
        String associerCompte = scanner.nextLine();
    
        if (associerCompte.equalsIgnoreCase("oui")) {
           
            String login;
            do {
                System.out.print("Entrer le login : ");
                login = scanner.nextLine();
                if (userService.searchUserByLogin(login) != null) {
                    System.out.println("Ce login existe déjà. Veuillez en choisir un autre.");
                    login = null; 
                }
            } while (login == null);
    
            System.out.print("Entrer l'email : ");
            String email = scanner.nextLine();
    
            System.out.print("Entrer le mot de passe : ");
            String password = scanner.nextLine();
    
            User user = new User();
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);
            user.setActive(true);
            user.setRole(Role.Client);
            userService.createUser(user);
    
            client.setUser(user);
        } else {
            client.setUser(null);
        }
    
        clientService.createClient(surname, telephone, adresse, client.getUser());
        System.out.println("Client créé avec succès !");
    }
    
    private boolean isValidTelephone(String telephone) {
        if (!telephone.matches("\\d+")) {
            return false; 
        }
        if (telephone.length() > 9) {
            return false; 
        }
        return Long.parseLong(telephone) > 0; 
    }
    

    public void listerClients() {
        System.out.println("Sous-menu :");
                    System.out.println("1 - Lister tous les clients");
                    System.out.println("2 - Lister les clients avec un compte utilisateur");
                    System.out.println("3 - Lister les clients sans compte utilisateur");
                    System.out.print("Votre choix : ");
                    int choixSousMenu = scanner.nextInt();
                    scanner.nextLine();  
                    List<Client> clients;
                    switch (choixSousMenu) {
                        case 1:
                            clients = clientService.findAllClient();
                        break;
                        case 2:
                            clients = clientService.findClientsWithUserAccounts(true);
                        break;
                        case 3:
                            clients = clientService.findClientsWithUserAccounts(false);
                        break;
                        default:
                            System.out.println("Choix non valide.");
                            clients = null;
                        break;
                    }
                    if (clients != null && !clients.isEmpty()) {
                        afficherTableauClients(clients);
                    } else if (clients != null) {
                        System.out.println("Aucun client trouvé.");
                    }
    }
    private void afficherTableauClients(List<Client> clients) {
        System.out.printf("%-5s %-15s %-15s %-20s %-20s\n", "ID", "Surname", "Telephone", "Adresse", "User (Login)");
        System.out.println("-------------------------------------------------------------------------------");
    
        for (Client client : clients) {
            Integer userLogin = (client.getUser() != null) ? client.getUser().getId() : null;
            System.out.printf("%-5d %-15s %-15s %-20s %-20s\n", 
                              client.getId(),
                              client.getSurname(),
                              client.getTelephone(),
                              client.getAdresse(),
                              (userLogin != null ? userLogin : "Aucun")); 
        }
    }

    public void rechercherClientParTelephone() {
        System.out.println("Entrer le téléphone");
        String tel = scanner.nextLine();
        Client client = clientService.searchClient(tel);
        if (client == null) {
            System.out.println("Pas de client trouvé.");
        } else {
            System.out.println("Client trouvé :");
            afficherDetailsClient(client);
            
        }
    }
    private void afficherDetailsClient(Client client) {
        System.out.println("----------------------------------------");
        System.out.printf("ID            : %d\n", client.getId());
        System.out.printf("Surname       : %s\n", client.getSurname());
        System.out.printf("Téléphone     : %s\n", client.getTelephone());
        System.out.printf("Adresse       : %s\n", client.getAdresse());
        if (client.getUser() != null) {
            System.out.println("Compte utilisateur :");
            System.out.printf("  - Login    : %s\n", client.getUser().getLogin());
            System.out.printf("  - Email    : %s\n", client.getUser().getEmail());
            System.out.printf("  - Rôle     : %s\n", client.getUser().getRole());
            System.out.printf("  - Actif    : %b\n", client.getUser().isActive());
        } else {
            System.out.println("Compte utilisateur : Aucun");
        }
        System.out.println("----------------------------------------");
    }


    
    public void AffecterUserClient() {
        Client Uclient = null;
    
        do {
            System.out.println("Entrez le téléphone du client :");
            String telephoneC = scanner.nextLine(); 
            Uclient = clientService.searchClient(telephoneC);
            
            if (Uclient == null) {
                System.out.println("Client non trouvé. Veuillez réessayer.");
            } else if (Uclient.getUser() != null) {
                System.out.println("Ce client a déjà un compte utilisateur. Veuillez choisir un autre client.");
                Uclient = null; 
            }
        } while (Uclient == null);
    
        String login;
        User newUser = null;
    
        do {
            System.out.print("Entrer le login : ");
            login = scanner.nextLine();
    
            if (userService.searchUserByLogin(login) != null) {
                System.out.println("Un utilisateur avec ce login existe déjà. Veuillez réessayer.");
            } else {
                break;
            }
        } while (true);
    
        System.out.println("Entrez l'email du client :");
        String email = scanner.nextLine();
        System.out.println("Entrez le mot de passe :");
        String password = scanner.nextLine();
    
        Role roleU = Role.Client;
    
        newUser = new User();
        newUser.setEmail(email);
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setRole(roleU);
        newUser.setActive(true);
    
        userService.createUser(newUser);
    
        Uclient.setUser(newUser);
        clientService.Update(Uclient);
    
        System.out.println("Compte utilisateur créé et associé avec succès !");
    }
    
    



}
