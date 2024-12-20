package repository.list;



import java.util.List;
import java.util.stream.Collectors;

import entites.Article;
import entites.Demande;
import entites.DetailAD;

import repository.DetailADRepository;

public class DetailADRepositoryList extends RepositoryImpl<DetailAD> implements DetailADRepository  {

    public DetailADRepositoryList(DemandeRepositoryList demandeRepository, ArticleRespositoryList articleRepository) {
        super();


        List<Demande> demandes = demandeRepository.selectAll();
        Demande demande1 = demandes.get(0);
        Demande demande2 = demandes.get(1);
        Demande demande3 = demandes.get(2);

        List<Article> articles = articleRepository.selectAll();
        Article article1 = articles.get(0);
        Article article2 = articles.get(1);
        Article article3 = articles.get(2);

        DetailAD detail1 = new DetailAD();
        detail1.setId(1);
        detail1.setDemande(demande1);
        detail1.setArticle(article1);
        detail1.setQte(5);

        DetailAD detail2 = new DetailAD();
        detail2.setId(2);
        detail2.setDemande(demande1);
        detail2.setArticle(article2);
        detail2.setQte(10);

        DetailAD detail3 = new DetailAD();
        detail3.setId(3);
        detail3.setDemande(demande2);
        detail3.setArticle(article3);
        detail3.setQte(7);

        DetailAD detail4 = new DetailAD();
        detail4.setId(4);
        detail4.setDemande(demande3);
        detail4.setArticle(article1);
        detail4.setQte(3);

        DetailAD detail5 = new DetailAD();
        detail5.setId(5);
        detail5.setDemande(demande3);
        detail5.setArticle(article2);
        detail5.setQte(2);

        insert(detail1);
        insert(detail2);
        insert(detail3);
        insert(detail4);
        insert(detail5);
    }


    @Override
public List<DetailAD> findByArticle(Article article) {
    return list.stream() 
        .filter(detailAD -> detailAD.getArticle().equals(article))
        .collect(Collectors.toList());
}


    @Override
    public List<DetailAD> findDetailsByDemandeId(int id) {
        return list.stream()
            .filter(detailAD -> detailAD.getDemande().getId() == id) 
            .collect(Collectors.toList());
    }
   

    

    
    

    
    
}
