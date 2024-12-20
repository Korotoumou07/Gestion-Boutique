package repository.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entites.Article;
import entites.Client;
import entites.Dette;
import entites.Payment;
import entites.User;
import repository.DetteRepository;

public class DetteRepositoryBD extends  ReopsitoryBDImpl<Dette> implements DetteRepository{

    

    public DetteRepositoryBD() {
        super(Dette.class);
        
    }


    @Override
    public Dette findById(int debtId) {
    Dette debt = null;
    String sql = "SELECT * FROM dette WHERE id = ?"; 
    openConnection();
    try {
        initPreparedStatement(sql);
        statement.setInt(1, debtId); 
        ResultSet rs = executeSelect();

        if (rs.next()) {
            debt = new Dette(); 
            debt.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                debt.setDate(timestamp.toLocalDateTime());
            } 
            debt.setMontant(rs.getDouble("montant")); 
            debt.setMontantRestant(rs.getDouble("montantRestant"));
            debt.setMontantVerser(rs.getDouble("montantVerser"));
            int clientId = rs.getInt("client_id");
            Client client = findClientById(clientId); 
            debt.setClient(client); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return debt;
}
public Client findClientById(int clientId) {
    Client client = null;
    String sql = "SELECT * FROM client WHERE id = ?";
    openConnection();
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, clientId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setSurname(resultSet.getString("surname"));
            client.setTelephone(resultSet.getString("telephone"));
            client.setAdresse(resultSet.getString("adresse"));

            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return client;
}

    @Override
public List<Dette> findByClient(Client client) {
    List<Dette> debts = new ArrayList<>();
    String sql = "SELECT * FROM dette WHERE client_id = ?"; 
    openConnection();
    try {
        initPreparedStatement(sql);
        statement.setInt(1, client.getId()); 
        ResultSet rs = executeSelect();

        while (rs.next()) {
            Dette debt = new Dette();
            debt.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                debt.setDate(timestamp.toLocalDateTime());
            }            
            debt.setMontant(rs.getDouble("montant"));
            debt.setMontantVerser(rs.getDouble("montantVerser")); 
            debt.setMontantRestant(rs.getDouble("montantRestant")); 
            
            int clientId = rs.getInt("client_id");
            Client client1 = findClientById(clientId); 
            debt.setClient(client1);

            debt.setPaiements(findPaymentsByDetteId(debt.getId()));
            
            debts.add(debt);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return debts;
}



private List<Payment> findPaymentsByDetteId(int detteId) {
    List<Payment> paiements = new ArrayList<>();
    String sql = "SELECT * FROM payment WHERE dette_id = ?";

    try {
        initPreparedStatement(sql);
        statement.setInt(1, detteId);
        ResultSet rs = executeSelect();

        while (rs.next()) {
            Payment paiement = new Payment();
            paiement.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                paiement.setDate(timestamp.toLocalDateTime());
            }
            paiement.setMontant(rs.getDouble("montant"));
            
            paiements.add(paiement);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return paiements;
}


public Article findArticleById(int articleId) {
    Article article = null;
    String sql = "SELECT * FROM article WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, articleId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setNomArticle(resultSet.getString("nomArticle"));
            article.setQteStock(resultSet.getInt("qteStock"));

            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return article;
}


@Override
public List<Dette> findDettesSodées() {
    List<Dette> dettesSodees = new ArrayList<>();
    String sql = "SELECT * FROM dette WHERE montantRestant = 0"; 

    openConnection(); 
    try {
        initPreparedStatement(sql); 
        ResultSet rs = executeSelect(); 

        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                dette.setDate(timestamp.toLocalDateTime());
            }           
             dette.setMontant(rs.getDouble("montant"));
            dette.setMontantVerser(rs.getDouble("montantVerser")); 
            dette.setMontantRestant(rs.getDouble("montantRestant")); 
            int clientId = rs.getInt("client_id");
            Client client = findClientById(clientId); 
            dette.setClient(client);

            dette.setPaiements(findPaymentsByDetteId(dette.getId()));
            dettesSodees.add(dette); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection(); 
    }
    return dettesSodees; 
}



@Override
public List<Dette> findNotDettesSodées() {
    List<Dette> notDettesSodees = new ArrayList<>();
    String sql = "SELECT * FROM dette WHERE montantRestant > 0"; 

    openConnection();
    try {
        initPreparedStatement(sql); 
        ResultSet rs = executeSelect(); 

        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                dette.setDate(timestamp.toLocalDateTime());
            }            
            dette.setMontant(rs.getDouble("montant"));
            dette.setMontantVerser(rs.getDouble("montantVerser")); 
            dette.setMontantRestant(rs.getDouble("montantRestant")); 
            int clientId = rs.getInt("client_id");
            Client client = findClientById(clientId); 
            dette.setClient(client);

            dette.setPaiements(findPaymentsByDetteId(dette.getId()));
           
            notDettesSodees.add(dette); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection(); 
    }
    return notDettesSodees; 
}
@Override
public List<Dette> findMesDettesSoldées(User user) {
    List<Dette> dettesParEtat = new ArrayList<>();
    String sql = "SELECT * FROM dette WHERE client_id IN (SELECT id FROM client WHERE user_id = ?) AND montantRestant = 0"; 
    openConnection(); 
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, user.getId()); 
        ResultSet rs = statement.executeQuery(); 

        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                dette.setDate(timestamp.toLocalDateTime());
            }           
            dette.setMontant(rs.getDouble("montant"));
            dette.setMontantVerser(rs.getDouble("montantVerser")); 
            dette.setMontantRestant(rs.getDouble("montantRestant")); 
            
            int clientId = rs.getInt("client_id");
            Client client = findClientById(clientId); 
            dette.setClient(client);

            dette.setPaiements(findPaymentsByDetteId(dette.getId()));
            
           

            dettesParEtat.add(dette); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection(); 
    }
    return dettesParEtat; 
}


    
@Override
public List<Dette> findMesDettesNotSoldées(User user) {
    List<Dette> dettesParEtat = new ArrayList<>();
    String sql = "SELECT * FROM dette WHERE client_id IN (SELECT id FROM client WHERE user_id = ?) AND montantRestant > 0"; 
    openConnection(); 
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, user.getId()); 
        ResultSet rs = statement.executeQuery(); 

        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                dette.setDate(timestamp.toLocalDateTime());
            }           
            dette.setMontant(rs.getDouble("montant"));
            dette.setMontantVerser(rs.getDouble("montantVerser")); 
            dette.setMontantRestant(rs.getDouble("montantRestant")); 
            
            int clientId = rs.getInt("client_id");
            Client client = findClientById(clientId); 
            dette.setClient(client);

            dette.setPaiements(findPaymentsByDetteId(dette.getId()));
            
           

            dettesParEtat.add(dette); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection(); 
    }
    return dettesParEtat; 
}


@Override
public List<Dette> findNotArchivedDettes() {
    List<Dette> notDettesArchived = new ArrayList<>();
    String sql = "SELECT * FROM dette WHERE archived =0"; 

    openConnection(); 
    try {
        initPreparedStatement(sql); 
        ResultSet rs = executeSelect(); 

        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                dette.setDate(timestamp.toLocalDateTime());
            }            
            dette.setMontant(rs.getDouble("montant"));
            dette.setMontantVerser(rs.getDouble("montantVerser")); 
            dette.setMontantRestant(rs.getDouble("montantRestant")); 
            int clientId = rs.getInt("client_id");
            Client client = findClientById(clientId); 
            dette.setClient(client);

            dette.setPaiements(findPaymentsByDetteId(dette.getId()));
            notDettesArchived.add(dette); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection(); 
    }
    return notDettesArchived; 


    
}
}
