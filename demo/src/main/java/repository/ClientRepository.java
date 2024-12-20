package repository;

import entites.Client;
import entites.User;

import java.util.List;

public interface ClientRepository extends Repository<Client>{
    Client selectByTelephone(String telephone);
    Client selectBySurname(String surname);
    List<Client> findClientsWithUserAccounts(boolean withAccount);
    Client findByUser(User user);

    
    
}
