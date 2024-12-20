package repository;

import java.util.List;

import entites.Role;
import entites.User;
public interface UserRepository extends Repository<User>{
    List<User> findByRole(Role role);
    List<User> findActiveUsers();
    List<User> findNotActiveUsers();
    // User findByClientTel(String clientId);
     User findByEmail(String userEmail);
     void update(User user);
     public User findById(int userId);
     List<User> findUsersByRoleAndState(Role role,Boolean isActive);
    User authenticate(String login, String password);
    public User searchUserByLogin(String login);
}
