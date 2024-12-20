package repository.list;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import entites.Client;

import entites.Demande;
import entites.Statut;
import repository.DemandeRepository;

public class DemandeRepositoryList extends RepositoryImpl<Demande> implements DemandeRepository {



    public DemandeRepositoryList(ClientRepositoryList clientRepository) {
        super();

        List<Client> clients = clientRepository.selectAll();
        

        Client client1 = clients.get(3); 
        Client client2 = clients.get(1); 

        Demande demande1 = new Demande(1, LocalDateTime.now(), "Demande 1", 500.0, Statut.EnCours, client1);

        Demande demande2 = new Demande(2, LocalDateTime.now().minusDays(1), "Demande 2", 1000.0, Statut.Accepté, client2);
        Demande demande3 = new Demande(3, LocalDateTime.now().minusDays(2), "Demande 3", 750.0, Statut.Annulé, client1);

        insert(demande1);
        insert(demande2);
        insert(demande3);
    }




    @Override
    public List<Demande> findDemandesByStatut(String statut) {
        return list.stream()
                   .filter(demande -> demande.getStatut().name().equalsIgnoreCase(statut))
                   .collect(Collectors.toList());
    }
    @Override
    public List<Demande> findDemandesByStatutAndClient(String statut, Client client) {
        return list.stream()
                .filter(demande -> demande.getStatut().name().equalsIgnoreCase(statut) && demande.getClient().equals(client))
                .collect(Collectors.toList());
    }

    @Override
    public List<Demande> findClientAllDemande(Client client) {
        return list.stream()
                .filter(demande -> demande.getClient().equals(client))
                .collect(Collectors.toList());
    }

    @Override
    public Demande findDemandeById(int demandeId) {
        return list.stream()
                .filter(demande -> demande.getId() == demandeId)
                .findFirst()
                .orElse(null);
    }

    

    

}
    