package repository.list;


import entites.Client;
import entites.Dette;
import entites.Payment;
import entites.User;
import repository.DetteRepository;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DetteRepositoryList extends RepositoryImpl<Dette> implements DetteRepository {

 


public DetteRepositoryList(ClientRepositoryList clientRepository) {
    super();

    List<Client> clients = clientRepository.selectAll();
  

    Client client2 = clients.get(1);
    Client client4 = clients.get(3);

    
    Payment payment1 = new Payment();
    payment1.setId(1);
    payment1.setDate(LocalDateTime.now());
    payment1.setMontant(1000.0);

    Payment payment2 = new Payment();
    payment2.setId(2);
    payment2.setDate(LocalDateTime.now().minusDays(1));
    payment2.setMontant(2000.0);

    Payment payment3 = new Payment();
    payment3.setId(3);
    payment3.setDate(LocalDateTime.now().minusDays(2));
    payment3.setMontant(500.0);


   

    Dette dette1 = new Dette(1, LocalDateTime.now(), 5000.0, 1000.0, 4000.0, client4, 
            new ArrayList<>(List.of(payment1)), 
            false);

    Dette dette2 = new Dette(2, LocalDateTime.now().minusDays(1), 8000.0, 2000.0, 6000.0, client2, 
            new ArrayList<>(List.of(payment2)), 
            false);

    Dette dette3 = new Dette(3, LocalDateTime.now().minusDays(2), 3000.0, 500.0, 2500.0, client4, 
            new ArrayList<>(List.of(payment3)), 
            false);


    
    insert(dette1);
    insert(dette2);
    insert(dette3);
}






    @Override
    public Dette findById(int debtId) {
        return list.stream()
        .filter(dette -> dette.getId() == debtId)
        .findFirst()
        .orElse(null);
    }
    @Override
    public void update(Dette updatedDette) {
        for (int i = 0; i < list.size(); i++) {
            Dette existingDette = list.get(i);
            if (existingDette.getId() == updatedDette.getId()) {
                existingDette.setDate(updatedDette.getDate());
                existingDette.setMontant(updatedDette.getMontant());
                existingDette.setMontantVerser(updatedDette.getMontantVerser());
                existingDette.setMontantRestant(updatedDette.getMontantRestant());
                existingDette.setClient(updatedDette.getClient());
                existingDette.setPaiements(updatedDette.getPaiements());
                list.set(i, existingDette);
                System.out.println("Dette mise à jour avec succès.");
                return;
            }
        }
        System.out.println("Erreur : Dette non trouvée.");
    }
    @Override
    public List<Dette> findByClient(Client client) {
        return list.stream()
                   .filter(dette -> dette.getClient().equals(client))
                   .collect(Collectors.toList());
    }
    @Override
    public List<Dette> findDettesSodées() {
        return list.stream()
                .filter(dette -> dette.getMontantRestant() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dette> findNotDettesSodées() {
        return list.stream()
                .filter(dette -> dette.getMontantRestant() > 0)
                .collect(Collectors.toList());
    }

    @Override
public List<Dette> findMesDettesSoldées(User user) {
    return list.stream()
               .filter(dette -> dette.getClient().getUser().equals(user)) 
               .filter(dette -> dette.getMontantRestant() == 0)
               .collect(Collectors.toList()); 
}

@Override
public List<Dette> findMesDettesNotSoldées(User user) {
    return list.stream()
               .filter(dette -> dette.getClient().getUser().equals(user)) 
               .filter(dette -> dette.getMontantRestant() > 0)
               .collect(Collectors.toList()); 
}

    @Override
    public List<Dette> findNotArchivedDettes() {
        return list.stream()
                .filter(debt -> debt.isArchived()==false) 
                .collect(Collectors.toList());
    }





    

    
    
}
