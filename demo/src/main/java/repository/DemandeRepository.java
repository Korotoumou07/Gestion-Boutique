package repository;

import java.util.List;

import entites.Client;

import entites.Demande;

public interface DemandeRepository  extends Repository<Demande>{
        List<Demande> findDemandesByStatut(String statut);
        List<Demande> findDemandesByStatutAndClient(String statut, Client client);
        List<Demande> findClientAllDemande(Client client);
        Demande findDemandeById(int demandeId);



        


    
}
