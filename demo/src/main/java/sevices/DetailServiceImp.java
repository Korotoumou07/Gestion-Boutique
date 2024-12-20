package sevices;

import java.util.List;

import entites.Article;
import entites.Detail;
import repository.DetailRepository;

public class DetailServiceImp implements DetailService {
       private DetailRepository detailRepository;
        
    public DetailServiceImp(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
       
    }


    @Override
    public List<Detail> findByArticle(Article article) {
        return detailRepository.findByArticle(article);
    }
    

}
