package sevices;

import java.util.List;

import entites.Article;
import repository.ArticleRepository;
public class ArticleServicesImpl implements ArticleServices{
    private ArticleRepository articleRepository;
    


    public ArticleServicesImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
       
    }
    @Override
    public Article searchArticle(String article) {
        return articleRepository.selectByName(article);
    }

    
    @Override
    public List<Article> findAllArticle() {  
        return articleRepository.selectAll();
    }
    @Override
    public List<Article> findAllArticleDisponible() {
        return articleRepository.findAllArticleDisponible();
    }
    @Override
    public void createArticle(Article article) {
        articleRepository.insert(article);
    }
   
    @Override
    public void updateArticle(Article article) {
        articleRepository.update(article);
    }

}
