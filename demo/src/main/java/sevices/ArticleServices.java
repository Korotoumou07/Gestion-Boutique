package sevices;

import java.util.List;

import entites.Article;

public interface ArticleServices {

   
    public void createArticle(Article article);
    public List<Article> findAllArticle();
        public Article searchArticle(String article);
        public List<Article> findAllArticleDisponible();
        public void updateArticle(Article article);


}
