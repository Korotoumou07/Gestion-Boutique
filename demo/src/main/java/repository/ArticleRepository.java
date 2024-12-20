package repository;

import java.util.List;

import entites.Article;

public interface ArticleRepository extends Repository<Article>{
    List<Article> findAllArticleDisponible();
    Article findByNom(String nom);
    public Article selectByName(String articleName) ;

    
}
