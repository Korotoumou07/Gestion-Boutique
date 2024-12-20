
package sevices;

import java.util.List;
import entites.Client;
import entites.User; 
import repository.Repository;
import repository.ClientRepository;



public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository, Repository<User> userRepository) {
        this.clientRepository = clientRepository;
        // this.userRepository = userRepository;
    }

    
    public void createClient(String surname, String telephone,String adresse,User user) {
        
        Client client = new Client();
        client.setSurname(surname);
        client.setTelephone(telephone);
        client.setAdresse(adresse);
        client.setUser(user);

        clientRepository.insert(client);
        
}
    @Override
    public void Update(Client client) {
        clientRepository.update(client);
    }
    
   
    @Override
    public void surnameExist(String surname) {
        
    }

    @Override
    public List<Client> findAllClient() {  
        return clientRepository.selectAll();
    }
    @Override
    public List<Client> findClientsWithUserAccounts(boolean withAccount) {
        return clientRepository.findClientsWithUserAccounts(withAccount);
    }
    @Override
    public Client searchClient(String telephone) {
        return clientRepository.selectByTelephone(telephone);
    }

    @Override
    public Client searchClientBySurname(String surname) {
        return clientRepository.selectBySurname(surname);
    }
    @Override
    public Client findClientByUser(User user) {
        return clientRepository.findByUser(user); 
    }

    
}
