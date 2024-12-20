package repository.bd;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entites.Article;
import entites.Demande;
import entites.DetailAD;
import repository.DetailADRepository;

public class DetailADRepositoryBD extends ReopsitoryBDImpl<DetailAD> implements DetailADRepository {
    public DetailADRepositoryBD() {
        super(DetailAD.class);  
    }

    @Override
    public List<DetailAD> findByArticle(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByArticle'");
    }

    @Override
public List<DetailAD> findDetailsByDemandeId(int demandeId) {
    List<DetailAD> details = new ArrayList<>();
    String query = "SELECT d.id, d.qte, d.article_id, a.nomArticle, a.qteStock "
                 + "FROM detailad d "
                 + "JOIN article a ON d.article_id = a.id "
                 + "WHERE d.demande = ?";

    openConnection();
    try {
        initPreparedStatement(query);
        statement.setInt(1, demandeId);
        ResultSet resultSet = executeSelect();
        while (resultSet.next()) {
            DetailAD detail = new DetailAD();

            detail.setId(resultSet.getInt("id"));
            detail.setQte(resultSet.getInt("qte"));

            Article article = new Article();
            article.setId(resultSet.getInt("article_id"));
            article.setNomArticle(resultSet.getString("nomArticle"));
            article.setQteStock(resultSet.getInt("qteStock"));

            detail.setArticle(article);

            Demande demande = new Demande();
            demande.setId(demandeId); 
            detail.setDemande(demande);

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
