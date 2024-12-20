package repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entites.Article;
import entites.Detail;
import repository.DetailRepository;

public class DetailRepositoryBD extends ReopsitoryBDImpl<Detail> implements DetailRepository{
    // Explicit constructor
    public DetailRepositoryBD() {
        super(Detail.class);  
    }

    @Override
    public List<Detail> findByArticle(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByArticle'");
    }

    
    @Override
public List<Detail> findDetailsByDetteId(int detteId) {
    List<Detail> details = new ArrayList<>();
    String query = "SELECT d.id, d.qte, d.article_id, a.nomArticle, a.qteStock "
                 + "FROM detail d "
                 + "JOIN article a ON d.article_id = a.id "
                 + "WHERE d.dette_id = ?";

                 openConnection();
    try {
        initPreparedStatement(query);
        statement.setInt(1, detteId); 
        ResultSet resultSet = executeSelect();
            while (resultSet.next()) {
                Detail detail = new Detail();

                detail.setId(resultSet.getInt("id"));
                detail.setQte(resultSet.getInt("qte"));

                Article article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setNomArticle(resultSet.getString("nomArticle"));
                article.setQteStock(resultSet.getInt("qteStock"));

                detail.setArticle(article);

                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return details;
    }



    
}
