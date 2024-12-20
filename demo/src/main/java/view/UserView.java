package view;

import java.util.List;
import java.util.Scanner;
import entites.User;
import entites.Role;
import sevices.UserService;

public class UserView {
    private UserService userService;
    private Scanner scanner;

    public UserView(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public void creerUser() {
        System.out.print("Entrez l'email de l'utilisateur : ");
        String email = scanner.nextLine();
        System.out.print("Entrez le login de l'utilisateur : ");
        String login = scanner.nextLine();
        System.out.print("Entrez le mot de passe : ");
        String password = scanner.nextLine();
        System.out.print("Choisissez un rôle (1: Admin, 2: Boutiquier) : ");
        int roleChoix = scanner.nextInt();
        scanner.nextLine();
        Role role = (roleChoix == 1) ? Role.Admin : Role.Boutiquier;
        userService.createUserAccount(email, login, password, role);
        System.out.println("Compte utilisateur créé avec succès.");
    }
    public void activerDesactiverUser() {
        
        System.out.print("Entrez l'email de l'utilisateur : ");
        String email = scanner.nextLine();
        User user = userService.getUsersByEmail(email);
        if (user != null) {
            System.out.print("Action sur le User: 1-Activer/2-Désactiver: ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            userService.UserEtat(user, choix);
        } else {
            System.out.println("Utilisateur introuvable.");
        }
    }
    public void listerUsers() {
        List<User> users = userService.findAllUser();
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            // users.forEach(System.out::println);
            afficherTousLesUsers(users);
        }
    }
    public void afficherTousLesUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("Aucun utilisateur à afficher.");
            return;
        }
    
        // En-tête du tableau
        System.out.printf("%-5s %-20s %-20s %-15s %-10s %-10s\n", 
            "ID", "Email", "Login", "Role", "Statut", "Password");
        System.out.println("-----------------------------------------------------------------------------------");
    
        // Affichage des utilisateurs
        for (User user : users) {
            String statut = user.isActive() ? "Actif" : "Inactif";
            System.out.printf("%-5d %-20s %-20s %-15s %-10s %-10s\n", 
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getRole(),
                statut,
                user.getPassword());
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }
    
    
    public void listerUsersByEtat() {
            System.out.print("1-Activés/2-Deactivés");
                    int choixA = scanner.nextInt();
                    if (choixA==1) {
                        List<User> activeUsers = userService.getActiveUsers();
                        // activeUsers.forEach(System.out::println);
                        afficherTousLesUsers(activeUsers);
                    } else if (choixA == 2) {
                        List<User> notactiveUsers = userService.getNotActiveUsers();
                        // notactiveUsers.forEach(System.out::println);
                        afficherTousLesUsers(notactiveUsers);
                    } else {
                        System.out.print("choix invalide");
                    }
    }
    public void listerUsersByRole() {
        System.out.print("Choisissez un rôle (1: Admin, 2: Boutiquier, 3: Client) : ");
        int roleChoix = scanner.nextInt();
        scanner.nextLine();
        
        Role role;
        switch (roleChoix) {
            case 1:
                role = Role.Admin;
                break;
            case 2:
                role = Role.Boutiquier;
                break;
            case 3:
                role = Role.Client;
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }
        
        List<User> usersByRole = userService.getUsersByRole(role);
        if (usersByRole.isEmpty()) {
            System.out.println("Aucun utilisateur pour ce rôle.");
        } else {
            // usersByRole.forEach(System.out::println);
            afficherTousLesUsers(usersByRole);
        }
    }
    
    public void listerUsersByEtatAndRole() {
        System.out.print("Choisissez un rôle (1: Admin, 2: Boutiquier, 3: Client) : ");
        int roleChoix = scanner.nextInt();
        scanner.nextLine();
        
        Role role;
        switch (roleChoix) {
            case 1:
                role = Role.Admin;
                break;
            case 2:
                role = Role.Boutiquier;
                break;
            case 3:
                role = Role.Client;
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }
    
        System.out.print("Choisissez l'état (1: Activé, 2: Désactivé) : ");
        int etatChoix = scanner.nextInt();
        boolean isActive = (etatChoix == 1);
        
        List<User> usersByRoleAndState = userService.getUsersByRoleAndState(role, isActive);
        if (usersByRoleAndState.isEmpty()) {
            System.out.println("Aucun utilisateur pour ce rôle et cet état.");
        } else {
            // usersByRoleAndState.forEach(System.out::println);
            afficherTousLesUsers(usersByRoleAndState);
        }
    }
    
}
