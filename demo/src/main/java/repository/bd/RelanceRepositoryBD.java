package repository.bd;


import entites.Relance;
import repository.RelanceRepository;

public class RelanceRepositoryBD extends ReopsitoryBDImpl<Relance> implements RelanceRepository{
    public RelanceRepositoryBD() {
        super(Relance.class);
        
    }
    
}
