package repository;

import java.util.List;

import entites.Article;
import entites.Detail;

public interface DetailRepository extends Repository<Detail>{

    List<Detail> findByArticle(Article article);
    List<Detail> findDetailsByDetteId(int detteId);
    
}
