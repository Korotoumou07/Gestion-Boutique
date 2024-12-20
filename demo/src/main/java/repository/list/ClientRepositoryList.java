package repository.list;

import entites.Client;
import entites.Role;
import entites.User;
import repository.ClientRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryList extends RepositoryImpl<Client> implements ClientRepository {
      
    public ClientRepositoryList() {
        super();
        Client client1 = new Client(1,"Philippe","772345678","Point E",
        new User(4, "Philippe", "Philippe", "password", Role.Client, true));

        Client client2 = new Client(2,"Santos","772345679","Medina",
        new User(5, "Santos", "Santos", "password", Role.Client, true));

        Client client3 = new Client(3,"Lamine","772345677","Fass",
        new User(6, "Lamine", "Lamine", "password", Role.Client, true));

        Client client4 = new Client(4,"samir","772345667","Fass",
        new User(7, "client", "client", "password", Role.Client, true));

        Client client5 = new Client(5, "Fatou", "772345688", "Ouakam", null);

        Client client6 = new Client(6, "Cheikh", "772345699", "Mermoz", null);
 
        insert(client1);
        insert(client2);
        insert(client3);
        insert(client4);
        insert(client5);
        insert(client6);
    }

    
    
    @Override
    public Client selectByTelephone(String telephone){
        return list.stream()
            .filter(client -> client.getTelephone().compareTo(telephone) == 0)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Client selectBySurname(String surname) {
        return list.stream()
            .filter(client -> client.getSurname().compareTo(surname) == 0)
            .findFirst()
            .orElse(null);
    
    }
    @Override
    public List<Client> findClientsWithUserAccounts(boolean withAccount) {
        return list.stream()
            .filter(client -> withAccount ? client.getUser() != null : client.getUser() == null)
            .collect(Collectors.toList());
    }
    @Override
    public void update(Client updated) {
        
        for (int i = 0; i < list.size(); i++) {
            Client existing = list.get(i);
            
            if (existing.getTelephone() == updated.getTelephone()) {
               
                existing.setSurname(updated.getSurname());
                existing.setTelephone(updated.getTelephone());
                existing.setAdresse(updated.getAdresse());
                existing.setUser(updated.getUser());
        
                list.set(i, existing);
                System.out.println("Client mise à jour avec succès.");
                return;
            }
        }
        System.out.println("Erreur : Client non trouvée.");
    }

    @Override
public Client findByUser(User user) {
    return list.stream()
               .filter(client -> client.getUser().equals(user)) 
               .findFirst() 
               .orElse(null); 
}


}
