package repository;

import entites.Client;
import entites.Dette;
import entites.User;

import java.util.List;

public interface DetteRepository extends Repository<Dette>{

    Dette findById(int debtId);
    List<Dette> findByClient(Client client);
    List<Dette> findDettesSodées();
    List<Dette> findNotDettesSodées();
     List<Dette> findMesDettesSoldées(User user);
     List<Dette> findMesDettesNotSoldées(User user);
    List<Dette> findNotArchivedDettes();
    



    
}
