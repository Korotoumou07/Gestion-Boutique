package repository.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entites.Client;
import entites.Demande;
import entites.Statut;
import repository.DemandeRepository;

public class DemandeRepositoryBD extends ReopsitoryBDImpl<Demande> implements DemandeRepository {
    
     public DemandeRepositoryBD() {
        super(Demande.class);  
    }



    @Override
    public List<Demande> selectAll() {
        List<Demande> demandes = new ArrayList<>();
        String query = "SELECT d.id, d.date, d.description, d.montant, d.statut, d.client_id, "
                     + "c.surname, c.telephone, c.adresse "
                     + "FROM demande d "
                     + "LEFT JOIN client c ON d.client_id = c.id";
    
        openConnection();
        try {
            initPreparedStatement(query);
            ResultSet rs = executeSelect();
            while (rs.next()) {

                Demande demande = new Demande();
                demande.setId(rs.getInt("id"));
                demande.setDate(rs.getTimestamp("date").toLocalDateTime());
                demande.setDescription(rs.getString("description"));
                demande.setMontant(rs.getDouble("montant"));
                demande.setStatut(Statut.valueOf(rs.getString("statut")));
    
                
                int clientId = rs.getInt("client_id");
                if (clientId != 0) {
                    Client client = new Client();
                    client.setId(clientId);
                    client.setSurname(rs.getString("surname"));
                    client.setTelephone(rs.getString("telephone"));
                    client.setAdresse(rs.getString("adresse"));
                    demande.setClient(client);
                }
    
                demandes.add(demande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return demandes;
    }
    


    @Override
    public List<Demande> findDemandesByStatut(String statutRecherche) {
        List<Demande> demandes = new ArrayList<>();
        String sql = "SELECT * FROM demande WHERE statut = ?"; 
        openConnection(); 
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1, statutRecherche); 
            ResultSet rs = preparedStatement.executeQuery(); 
    
            while (rs.next()) {
                Demande demande = new Demande();
                demande.setId(rs.getInt("id"));
                Timestamp timestamp = rs.getTimestamp("date");
                if (timestamp != null) {
                    demande.setDate(timestamp.toLocalDateTime());
                }            
                demande.setDescription(rs.getString("description"));
                demande.setMontant(rs.getDouble("montant")); 
                int clientId = rs.getInt("client_id");
                Client client = findClientById(clientId); 
                demande.setClient(client); 
                String statutString = rs.getString("statut");
                Statut statut = Statut.valueOf(statutString); 
                demande.setStatut(statut);
    
                demandes.add(demande); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            closeConnection(); 
        }
        return demandes;
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
public Demande findDemandeById(int demandeId) {
    Demande demande = null;
    String sql = "SELECT * FROM demande WHERE id = ?";
    
    openConnection(); 
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, demandeId);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            demande = new Demande();
            demande.setId(resultSet.getInt("id"));
            Timestamp timestamp = resultSet.getTimestamp("date");
            if (timestamp != null) {
                demande.setDate(timestamp.toLocalDateTime());
            }
            demande.setDescription(resultSet.getString("description"));
            demande.setMontant(resultSet.getDouble("montant"));
            int clientId = resultSet.getInt("client_id");
            Client client = findClientById(clientId); 
            demande.setClient(client); 
            String statutString = resultSet.getString("statut");
            Statut statut = Statut.valueOf(statutString); 
            demande.setStatut(statut);
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection(); 
    }
    
    return demande;
}



@Override
public List<Demande> findDemandesByStatutAndClient(String statutRecherche, Client client) {
    List<Demande> demandes = new ArrayList<>();
    String sql = "SELECT * FROM demande WHERE statut = ? AND client_id = ?"; 
    openConnection(); 
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);  
        preparedStatement.setString(1, statutRecherche); 
        preparedStatement.setInt(2, client.getId()); 

        ResultSet rs = preparedStatement.executeQuery(); 

        while (rs.next()) {
            Demande demande = new Demande();
            demande.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                demande.setDate(timestamp.toLocalDateTime());
            }
            demande.setDescription(rs.getString("description"));
            demande.setMontant(rs.getDouble("montant")); 
            demande.setClient(client); 
            String statutString = rs.getString("statut");
            Statut statut = Statut.valueOf(statutString); 
            demande.setStatut(statut);

            demandes.add(demande); 
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        closeConnection(); 
    }
    return demandes;
}


@Override
public List<Demande> findClientAllDemande(Client client) {
    List<Demande> demandes = new ArrayList<>();
    String sql = "SELECT * FROM demande WHERE client_id = ?"; 
    openConnection();
    
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, client.getId()); 
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
            Demande demande = new Demande();
            demande.setId(rs.getInt("id"));
            Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                demande.setDate(timestamp.toLocalDateTime());
            }
            demande.setDescription(rs.getString("description"));
            demande.setMontant(rs.getDouble("montant"));
            
            String statutString = rs.getString("statut");
            Statut statut = Statut.valueOf(statutString); 
            demande.setStatut(statut);
           
            demande.setClient(client); 
            
            demandes.add(demande);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    
    return demandes;
}

}




