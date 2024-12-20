package sevices;

import entites.Client;
import entites.User;

import java.util.List;

public interface ClientService {
    public void createClient(String surname, String telephone,String adresse,User user);
    // public void createClient(Client client);
    List<Client> findAllClient();
    Client searchClient(String telephone);
    Client searchClientBySurname(String surname);
    List<Client> findClientsWithUserAccounts(boolean withAccount);
    public void Update(Client client);
    public void surnameExist(String surname);
    public Client findClientByUser(User user);


}
