package sevices;

import java.util.List;

import entites.Article;
import entites.DetailAD;
import repository.DetailADRepository;

public class DetailADServiceImpl implements DetailADService {
        private DetailADRepository detailADRepository;
        
    public DetailADServiceImpl(DetailADRepository detailADRepository) {
        this.detailADRepository = detailADRepository;
       
    }


    @Override
    public List<DetailAD> findByArticle(Article article) {
        return detailADRepository.findByArticle(article);
    }
    
}
