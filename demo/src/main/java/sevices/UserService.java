package sevices;

import entites.Role;
import entites.User;
import java.util.List;

public interface UserService {
    public void createUser(User user);
    public List<User> findAllUser();
    public List<User> getUsersByRole(Role role);
    public User getUserByEmailAndRole(String email, Role role);
    public List<User> getActiveUsers();
    public List<User> getNotActiveUsers();
    public void createUserAccount(String email, String login,String password, Role role);
    public void UserEtat(User user,int choix);
    public User getUsersByEmail(String email);
    public List<User> getUsersByRoleAndState(Role role, boolean isActive);
    public User findUserById(int userId);
    public User authenticate(String login, String password);
    public Object searchUserByLogin(String login);

    


}
