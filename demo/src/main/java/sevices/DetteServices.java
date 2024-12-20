package sevices;

import java.time.LocalDateTime;
import java.util.List;
import entites.Client;
import entites.Detail;
import entites.Dette;
import entites.User;

public interface DetteServices {
    public void createDebt(Dette dette);
    public void recordPayment(int debtId, LocalDateTime paymentDate, double amount);
    public void listUnpaidDebts(Client client);
    public void listPaidDebts(Client client);
    public void listAllDebts(Client client);
    List<Dette> findAllDettes();
    public void createDetail(Detail detail);
    List<Dette> getDettesSodées();
    List<Dette> getNotDettesSodées();
    List<Dette> getMesDettesSodées(User user);
    List<Dette> getMesNotDettesSodées(User user);
    public void archiverDettesSoldées();
     List<Dette> listerDettesParClient(Client client);
    public List<Dette> findNotArchivedDettes();
    public List<Detail> findDetailsByDetteId(int id);
    public Dette findDebtById(int debtId);

    
} 
