
package repository.list;


import entites.Role;
import entites.User;
import repository.UserRepository;
import java.util.stream.Collectors;
import java.util.List;

public class UserRepositoryList extends RepositoryImpl<User> implements UserRepository {
    
    public UserRepositoryList() {
        super();
        User admin = new User();
        admin.setId(1);
        admin.setEmail("admin");
        admin.setLogin("admin");
        admin.setPassword("password");
        admin.setRole(Role.Admin);
        admin.setActive(true);

        User boutiquier = new User();
        boutiquier.setId(2);
        boutiquier.setEmail("boutiquier");
        boutiquier.setLogin("boutiquier");
        boutiquier.setPassword("password");
        boutiquier.setRole(Role.Boutiquier);
        boutiquier.setActive(true);

        User client = new User();
        client.setId(3);
        client.setEmail("client");
        client.setLogin("client");
        client.setPassword("password");
        client.setRole(Role.Client);
        client.setActive(true);

        insert(admin);
        insert(boutiquier);
        insert(client);
    }

   



    @Override
    public List<User> findByRole(Role role) {
        return list.stream()
                .filter(user -> user.getRole().equals(role))
                .collect(Collectors.toList());  
    }
    @Override
    public List<User> findActiveUsers() {
        return list.stream()
                .filter(User::isActive)  
                .collect(Collectors.toList());  
    }
    @Override
    public List<User> findNotActiveUsers() {
        return list.stream()
                .filter(user -> !user.isActive())  
                .collect(Collectors.toList());  
    }
    
    @Override
    public User findByEmail(String userEmail) {
        return list.stream()
                    .filter(user -> user.getEmail().compareTo(userEmail) ==0 )
                    .findFirst()
                    .orElse(null);
    }
    @Override
    public void update(User updated) {
        for (int i = 0; i < list.size(); i++) {
            User existing = list.get(i);
            if (existing.getEmail().equals(updated.getEmail())) {
                existing.setActive(updated.isActive());
                list.set(i, existing);  
                System.out.println("Utilisateur mis à jour avec succès.");
                return;
            }
        }
        System.out.println("Erreur : Utilisateur non trouvé.");
    }
    @Override
    public User findById(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    @Override
    public List<User> findUsersByRoleAndState(Role role, Boolean isActive) {
        return list.stream()
                .filter(user -> user.getRole().equals(role) && user.isActive() == isActive)
                .collect(Collectors.toList());
    }

    @Override
    public User authenticate(String login, String password) {
        return selectAll().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }



   
    @Override
    public User searchUserByLogin(String login) {
        return list.stream()
            .filter(user -> user.getLogin().compareTo(login) ==0 )
            .findFirst()
            .orElse(null);
    }
    


    
}
