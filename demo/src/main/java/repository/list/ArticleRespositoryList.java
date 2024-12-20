package repository.list;

import java.util.List;
import java.util.stream.Collectors;

import entites.Article;

import repository.ArticleRepository;


public class ArticleRespositoryList extends RepositoryImpl<Article> implements ArticleRepository {


    public ArticleRespositoryList() {
        super();
        Article article1 = new Article(1,"ROBE ANISSA",100);
        Article article2 = new Article(2,"ENSEMBLE TANTIE",150);
        Article article3 = new Article(3,"COLLECTION JOLIE",200);

        insert(article1);
        insert(article2);
        insert(article3);
    }


    @Override
    public List<Article> findAllArticleDisponible() {
        return list.stream()
                   .filter(article -> article.getQteStock() > 0)  
                   .collect(Collectors.toList());  
    }
   
    @Override
    public Article findByNom(String nom) {
        
        return list.stream()
                   .filter(article -> article.getNomArticle() ==nom)
                   .findFirst()
                   .orElse(null);
    }
    @Override
    public void update(Article article) {
        Article article2 = findByNom(article.getNomArticle());  
        if (article2 != null) {
            int index = list.indexOf(article2);  
            list.set(index, article);  
        } else {
            System.out.println("L'article n'a pas été trouvé.");
        }
    }
    

    @Override
    public Article selectByName(String articleName) {   
        return list.stream()
                   .filter(article -> article.getNomArticle().equals(articleName))
                   .findFirst()
                   .orElse(null);
    }
    
}

    

