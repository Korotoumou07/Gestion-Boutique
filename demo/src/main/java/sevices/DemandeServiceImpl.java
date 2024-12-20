package sevices;

import java.util.List;

import entites.Client;

import entites.Demande;
import entites.DetailAD;
import repository.DemandeRepository;
import repository.DetailADRepository;

public class DemandeServiceImpl implements DemandeService {

    private DemandeRepository demandeRepository;
    private DetailADRepository detailADRepository;



    public DemandeServiceImpl(DemandeRepository demandeRepository,DetailADRepository detailADRepository) {
        this.demandeRepository = demandeRepository;
        this.detailADRepository = detailADRepository;
       
    }

    @Override
    public void createDemande(Demande demande) {
        demandeRepository.insert(demande);
    }
    @Override
    public void updateDemande(Demande demande) {
        demandeRepository.update(demande);
    }

    @Override
    public List<Demande> findAllDemande() {
        return demandeRepository.selectAll();
    }
    @Override
    public void createDetailAD(DetailAD detailAD) {
        detailADRepository.insert(detailAD);
    }

    @Override
    public List<Demande> findDemandeEnCours() {
        return demandeRepository.findDemandesByStatut("EnCours");
    }

    @Override
    public List<Demande> findDemandeAcceptées() {
        return demandeRepository.findDemandesByStatut("Accepté");

    }

    @Override
    public List<Demande> findDemandeAnnulées() {
        return demandeRepository.findDemandesByStatut("Annulé");
    }

    @Override
    public List<Demande> findClientDemandeEnCours(Client client) {
        return demandeRepository.findDemandesByStatutAndClient("EnCours",client);
    }
    @Override
    public List<Demande> findClientDemandeAcceptées(Client Client) {
        return demandeRepository.findDemandesByStatutAndClient("Accepté",Client);
    }

    @Override
    public List<Demande> findClientDemandeAnnulées(Client Client) {
        return demandeRepository.findDemandesByStatutAndClient("Annulé",Client);
    }

    

    @Override
    public List<Demande> findClientAllDemande(Client Client) {
        return demandeRepository.findClientAllDemande(Client);
    }
    @Override
    public Demande findDemandeById(int demandeId) {
        return demandeRepository.findDemandeById(demandeId);
    }

    @Override
    public List<DetailAD> findDetailsByDemandeId(int id) {
        return detailADRepository.findDetailsByDemandeId(id);
    }
    

    
    
}
