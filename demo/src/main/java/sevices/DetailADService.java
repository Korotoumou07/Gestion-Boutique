package sevices;

import java.util.List;

import entites.Article;
import entites.DetailAD;

public interface DetailADService {

    List<DetailAD> findByArticle(Article article);
    
}
