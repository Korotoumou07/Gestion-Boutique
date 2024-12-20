package repository.bd;

import java.util.List;

import entites.Role;
import entites.User;
import repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepositoryBD extends ReopsitoryBDImpl<User> implements UserRepository{
    public UserRepositoryBD() {
        super(User.class);  
    }



    

@Override
public List<User> findByRole(Role role) {
    List<User> usersByRole = new ArrayList<>();
    String sql = "SELECT * FROM user WHERE role = ?";  

    openConnection();  
    try {
        initPreparedStatement(sql);  
        statement.setString(1, role.toString());  
        
        ResultSet rs = executeSelect();  

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            user.setRole(Role.valueOf(rs.getString("role")));  

           
            usersByRole.add(user);  
        }
    } catch (SQLException e) {
        e.printStackTrace();  
    } finally {
        closeConnection();  
    }
    return usersByRole;  
}

    @Override
    public List<User> findActiveUsers() {
        User user = null;
    List<User> activeUsers = new ArrayList<>();
    String sql = "SELECT * FROM user WHERE active = 1"; 

    openConnection();  
    try {
        initPreparedStatement(sql); 
        ResultSet rs = executeSelect();  

        while (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            user.setRole(Role.valueOf(rs.getString("role"))); 

            
            activeUsers.add(user); 
        }
    } catch (SQLException e) {
        e.printStackTrace();  
    } finally {
        closeConnection();  
    }
    return activeUsers; }

    @Override
public List<User> findNotActiveUsers() {
    User user = null;
    List<User> inactiveUsers = new ArrayList<>();
    String sql = "SELECT * FROM user WHERE active = 0"; 

    openConnection();  
    try {
        initPreparedStatement(sql); 
        ResultSet rs = executeSelect();  

       
        while (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            user.setRole(Role.valueOf(rs.getString("role"))); 

           
            inactiveUsers.add(user);  
        }
    } catch (SQLException e) {
        e.printStackTrace();  
    } finally {
        closeConnection();  
    }
    return inactiveUsers;  
}


@Override
public User findByEmail(String userEmail) {
    User user = null;
    String sql = "SELECT * FROM user WHERE email = ?";
    openConnection(); 
    try {
        initPreparedStatement(sql); 
        statement.setString(1, userEmail);  
        ResultSet rs = executeSelect();  

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            user.setRole(Role.valueOf(rs.getString("role"))); 

            
        }
    } catch (SQLException e) {
        e.printStackTrace();  
    } finally {
        closeConnection();  
    }
    return user;
}
@Override
public User searchUserByLogin(String login) {
    User user = null;
    String sql = "SELECT * FROM user WHERE email = ?";
    openConnection();  
    try {
        initPreparedStatement(sql);  
        statement.setString(1, login);  
        ResultSet rs = executeSelect();  

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            user.setRole(Role.valueOf(rs.getString("role"))); 

           
        }
    } catch (SQLException e) {
        e.printStackTrace();  
    } finally {
        closeConnection();  
    }
    return user;
}

@Override
public User findById(int userId) {
    User user = null;
    String sql = "SELECT * FROM user WHERE id = ?"; 
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    openConnection();
    try {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        statement = connection.prepareStatement(sql);
        statement.setInt(1, userId); 
        resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            user = new User(); 
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setActive(resultSet.getBoolean("active"));
            user.setRole(Role.valueOf(resultSet.getString("role"))); 
          
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(); 
    }
    return user; 
}

@Override
public List<User> findUsersByRoleAndState(Role role, Boolean isActive) {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM user WHERE role = ? AND active = ?"; 

    openConnection(); 
    try {
        initPreparedStatement(sql); 
        statement.setString(1, role.name()); 
        statement.setBoolean(2, isActive); 

        ResultSet rs = executeSelect(); 

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            user.setRole(Role.valueOf(rs.getString("role"))); 

            users.add(user); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection();
    }
    return users; 
}

@Override
public User authenticate(String login, String password) {
    for (User u : selectAll()) {
                if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                    return u;
                }
            }
            return null; 
}



    
}
