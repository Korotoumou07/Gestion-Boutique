package sevices;

import java.util.List;

import entites.Client;

import entites.Demande;
import entites.DetailAD;

public interface DemandeService {
    public void createDemande(Demande demande);
    public void updateDemande(Demande demande);
    List<Demande> findAllDemande();
    List<Demande> findClientAllDemande(Client Client);
    public void createDetailAD(DetailAD detailAD);
    List<Demande> findDemandeEnCours();
    List<Demande> findDemandeAcceptées();
    List<Demande> findDemandeAnnulées();
    List<Demande> findClientDemandeEnCours(Client Client);
    List<Demande> findClientDemandeAcceptées(Client Client);
    List<Demande> findClientDemandeAnnulées(Client Client);
     Demande findDemandeById(int demandeId);
    public List<DetailAD> findDetailsByDemandeId(int id);




    
}
