package repository.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entites.Article;
import repository.ArticleRepository;

public class ArticleRepositoryBD extends ReopsitoryBDImpl<Article> implements ArticleRepository {
     public ArticleRepositoryBD() {
        super(Article.class);  
    }


    @Override
public Article selectByName(String articleName) {
    Article article = null;
    String sql = "SELECT * FROM article WHERE nomArticle = ?";
    openConnection();
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, articleName);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("Id"));
                article.setNomArticle(articleName);
                article.setQteStock(resultSet.getInt("qteStock"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return article;
}

   
@Override
public List<Article> findAllArticleDisponible() {
    List<Article> articlesDisponibles = new ArrayList<>();
    String sql = "SELECT * FROM article WHERE qteStock > 0"; 
    openConnection();

    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Article article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setNomArticle(resultSet.getString("nomArticle")); 
            article.setQteStock(resultSet.getInt("qteStock"));
            articlesDisponibles.add(article);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return articlesDisponibles;
}

    @Override
    public Article findByNom(String nom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByNom'");
    }

   

   
    
}
