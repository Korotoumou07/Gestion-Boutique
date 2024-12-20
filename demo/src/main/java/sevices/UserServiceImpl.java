

package sevices;

import entites.User;
import entites.Role;
import java.util.List;
import repository.UserRepository;


public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.insert(user);
    }

@Override
public User findUserById(int userId) {
    return userRepository.findById(userId); 
}


    @Override
    public List<User> findAllUser() {
        return userRepository.selectAll();
    }
    
    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
    @Override
    public User getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override

    public User getUserByEmailAndRole(String email, Role role) {
        User user = userRepository.findByEmail(email); 
        if (user != null && user.getRole().equals(role)) { 
            return user;
        }
        return null; 
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.findActiveUsers();
    }
    @Override
    public List<User> getNotActiveUsers() {
        return userRepository.findNotActiveUsers();
    }
    
    @Override
    public void createUserAccount(String email, String login,String password, Role role) {
        if (role == Role.Boutiquier || role == Role.Admin) {

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setRole(role);
            newUser.setActive(true);

            userRepository.insert(newUser);
        } else {
            System.out.println("Invalid role.");
    }
    }
    @Override
    public void UserEtat(User user, int choix) {
        if (choix == 1) {
            user.setActive(true);
            userRepository.update(user);
            System.out.println("L'utilisateur a été activé.");
        } else if (choix == 2) {
            user.setActive(false);
            userRepository.update(user);
            System.out.println("L'utilisateur a été désactivé.");
        } else {
            System.out.println("Choix invalide.");
        }
    }
    @Override
    public List<User> getUsersByRoleAndState(Role role, boolean isActive) {
        return userRepository.findUsersByRoleAndState(role,isActive);


}

@Override
    public User authenticate(String login, String password) {
    
    return userRepository.authenticate( login,password);  
}

@Override
public Object searchUserByLogin(String login) {
    return userRepository.searchUserByLogin( login);  
}




 }
