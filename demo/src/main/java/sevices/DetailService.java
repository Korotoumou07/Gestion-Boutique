package sevices;

import java.util.List;

import entites.Article;
import entites.Detail;

public interface DetailService {

    List<Detail> findByArticle(Article article);
    
}
