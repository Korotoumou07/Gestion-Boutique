package repository.bd;

import entites.Client;
import entites.Role;
import entites.User;
import repository.ClientRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientRepositoryBD extends ReopsitoryBDImpl<Client> implements ClientRepository{
    
    public ClientRepositoryBD() {
        super(Client.class);  
    }



@Override
public Client selectByTelephone(String telephone) {
    Client client = null;
    String sql = "SELECT * FROM client WHERE telephone = ?";
    openConnection();
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, telephone);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setSurname(resultSet.getString("surname"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setAdresse(resultSet.getString("adresse"));
                
                int userId = resultSet.getInt("user_id");
                if (userId != 0) { 
                    User user = findUserById(userId);
                    client.setUser(user);
                }
                
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return client;
}

public Client selectBySurname(String surname) {
    Client client = null;
    String sql = "SELECT * FROM client WHERE surname = ?";
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    openConnection();
    try {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        statement = connection.prepareStatement(sql);
        statement.setString(1, surname);
        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setSurname(resultSet.getString("surname"));
            client.setTelephone(resultSet.getString("telephone"));
            client.setAdresse(resultSet.getString("adresse"));

            int userId = resultSet.getInt("user_id");
            User user = findUserById(userId); 
            client.setUser(user); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return client;
}

@Override
public List<Client> findClientsWithUserAccounts(boolean withAccount) {
    List<Client> clients = new ArrayList<>();
    String sql = withAccount ? 
        "SELECT * FROM client INNER JOIN user ON client.user_id = user.id" :
        "SELECT * FROM client LEFT JOIN user ON client.user_id = user.id WHERE client.user_id IS NULL";
    
    openConnection();
    
    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
         
        while (resultSet.next()) {
            Client client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setSurname(resultSet.getString("surname"));
            client.setTelephone(resultSet.getString("telephone"));
            client.setAdresse(resultSet.getString("adresse"));
            
            int userId = resultSet.getInt("user_id");
            User user = userId != 0 ? findUserById(userId) : null;
            client.setUser(user);
            
            clients.add(client);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    
    return clients;
}

public User findUserById(int userId) {
    User user = null;
    String sql = "SELECT * FROM user WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));

            String roleString = resultSet.getString("role");
            Role role = Role.valueOf(roleString); 
            user.setRole(role);

            user.setActive(resultSet.getBoolean("active"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}
@Override
public Client findByUser(User user) {
    Client client = null;
    String sql = "SELECT * FROM client WHERE user_id = ?";  

    openConnection();
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, user.getId());  
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setSurname(resultSet.getString("surname"));
            client.setTelephone(resultSet.getString("telephone"));
            client.setAdresse(resultSet.getString("adresse"));
            
            int userId = resultSet.getInt("user_id");
            User foundUser = userId != 0 ? findUserById(userId) : null;
            client.setUser(foundUser);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    
    return client;  
}





    


    
}
