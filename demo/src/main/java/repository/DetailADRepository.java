package repository;


import java.util.List;

import entites.Article;
import entites.DetailAD;

public interface DetailADRepository extends Repository<DetailAD>{

    List<DetailAD> findByArticle(Article article);

    List<DetailAD> findDetailsByDemandeId(int id);
    
}
