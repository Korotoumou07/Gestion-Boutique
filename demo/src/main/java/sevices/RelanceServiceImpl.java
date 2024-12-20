package sevices;

import entites.Relance;
import repository.RelanceRepository;

public class RelanceServiceImpl implements RelanceService {
     private RelanceRepository relanceRepository;
    
    public RelanceServiceImpl(RelanceRepository relanceRepository) {
        this.relanceRepository = relanceRepository;
    }

    @Override
    public void createRelance(Relance relance) {
        relanceRepository.insert(relance);
    }

    
}
