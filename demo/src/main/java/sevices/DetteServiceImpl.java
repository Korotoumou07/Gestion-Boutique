package sevices;


import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import entites.Client;
import entites.Detail;
import entites.Dette;
import entites.Payment;
import entites.User;
import repository.DetailRepository;
import repository.DetteRepository;
import repository.PaymentRepository;
import java.util.stream.Collectors;

public class DetteServiceImpl implements DetteServices{
   
    private DetteRepository detteRepository;
    private PaymentRepository paymentRepository;
    private DetailRepository detailRepository;

    

    public DetteServiceImpl(DetteRepository detteRepository,PaymentRepository paymentRepository,DetailRepository detailRepository) {
        this.detteRepository = detteRepository;
        this.paymentRepository = paymentRepository;
        this.detailRepository = detailRepository;
       
    }
    @Override
    public List<Dette> findAllDettes() {  
        return detteRepository.selectAll();
    }
    @Override
    public void createDebt(Dette dette) {
        detteRepository.insert(dette);
    
    }
    @Override
    public void createDetail(Detail detail) {
        detailRepository.insert(detail);
    }
    
    @Override
    public void recordPayment(int debtId, LocalDateTime paymentDate, double amount) {
        Dette debt = detteRepository.findById(debtId); 
        if (debt == null) {
            System.out.println("Aucune dette trouvée avec cet ID.");
            return;
        }
    
        if (amount > debt.getMontantRestant()) {
            System.out.println("Le montant versé ne peut pas être supérieur au montant restant.");
            return;
        }
    
        Payment payment = new Payment();
            payment.setDate(LocalDateTime.now());
            payment.setMontant(amount);
            payment.setDette(debt);  
            paymentRepository.insert(payment);
        
        List<Payment> payments = debt.getPaiements();
        if (payments == null) {
            payments = new ArrayList<>(); 
        }
        payments.add(payment);
        debt.setPaiements(payments);
        
        double montantVerser = debt.getMontantVerser() + amount;
        debt.setMontantVerser(montantVerser);
        debt.setMontantRestant(debt.getMontant() - montantVerser); 
        detteRepository.update(debt);
        
        System.out.println("Paiement enregistré avec succès !");
    }

    @Override
    public void listAllDebts(Client client) {
        List<Dette> debts = detteRepository.findByClient(client);
        if (debts.isEmpty()) {
            System.out.println("Aucune dette trouvée pour ce client.");
            return;
        }
       
        afficherDetailsDettes(debts);

        
    }
    
    @Override
    public void listPaidDebts(Client client) {
        
        List<Dette> paidDebts = detteRepository.findByClient(client).stream()
            .filter(debt -> debt.getMontantRestant() == 0)
            .collect(Collectors.toList());
    
        if (paidDebts.isEmpty()) {
            System.out.println("Aucune dette soldée pour ce client.");
            return;
        }
    
        afficherDetailsDettes(paidDebts);

    }
    
    @Override
    public void listUnpaidDebts(Client client) {
        List<Dette> unpaidDebts = detteRepository.findByClient(client).stream()
            .filter(debt -> debt.getMontantRestant() != 0)
            .collect(Collectors.toList());
    
        if (unpaidDebts.isEmpty()) {
            System.out.println("Aucune dette non soldée pour ce client.");
            return;
        }
    
        afficherDetailsDettes(unpaidDebts);
    }
    
   

    private void afficherDetailsDettes(List<Dette> dettes) {
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette trouvée.");
        } else {
            System.out.printf("%-15s %-15s %-15s %-15s\n", "Client", "Montant", "Versé", "Restant");
            System.out.println("-------------------------------------------------------------------------------");
    
            for (Dette dette : dettes) {
                System.out.printf(" %-15s %-15.2f %-15.2f %-15.2f\n",
                        dette.getClient().getSurname(),
                        dette.getMontant(),
                        dette.getMontantVerser(),
                        dette.getMontantRestant());
    
                System.out.println("  Articles :");
                List<Detail> details = findDetailsByDetteId(dette.getId()); 
                if (details.isEmpty()) {
                    System.out.println("    Aucun article associé.");
                } else {
                    for (Detail detail : details) {
                        System.out.printf("    - Article: %-15s | Quantité: %-5d\n",
                                detail.getArticle().getNomArticle(),
                                detail.getQte());
                    }
                }
    
                // Affichage des paiements
                System.out.println("  Paiements :");
                if (dette.getPaiements().isEmpty()) {
                    System.out.println("    Aucun paiement associé.");
                } else {
                    for (Payment payment : dette.getPaiements()) {
                        System.out.printf("    - Paiement ID: %-5d | Montant: %-10.2f | Date: %-20s\n",
                                payment.getId(),
                                payment.getMontant(),
                                payment.getDate().toString());
                    }
                }
    
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
    }
    
    
    @Override
    public List<Dette> getDettesSodées() {
        return detteRepository.findDettesSodées();
    }
    @Override
    public List<Dette> getNotDettesSodées() {
        return detteRepository.findNotDettesSodées();

    }
    @Override
    public List<Dette> getMesDettesSodées(User user) {
        return detteRepository.findMesDettesSoldées(user);
    }
    @Override
    public List<Dette> getMesNotDettesSodées(User user) {
        return detteRepository.findMesDettesNotSoldées(user);

    }
    @Override
    public void archiverDettesSoldées() {
        List<Dette> dettes = detteRepository.selectAll(); 
    
        boolean aucuneDetteSoldée = true;
    
        for (Dette dette : dettes) {
            if (dette.getMontantRestant() == 0) { 
                dette.setArchived(true); 
                detteRepository.update(dette); 
                aucuneDetteSoldée = false; 
            }
        }
    
        if (aucuneDetteSoldée) {
            System.out.println("Aucune dette soldée à archiver.");
        } else {
            System.out.println("Les dettes soldées ont été archivées avec succès.");
        }
    }
    @Override
    public List<Dette> listerDettesParClient(Client client) {
        return detteRepository.findByClient(client);
    }
    @Override
    public List<Dette> findNotArchivedDettes() {
        return detteRepository.findNotArchivedDettes();
    }
    @Override
    public List<Detail> findDetailsByDetteId(int id) {
        return detailRepository.findDetailsByDetteId(id);
    }
    @Override
    public Dette findDebtById(int debtId) {
        return detteRepository.findById(debtId);
    }
    
    
    

}
