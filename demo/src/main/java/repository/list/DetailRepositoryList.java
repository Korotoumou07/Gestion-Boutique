package repository.list;



import java.util.List;
import java.util.stream.Collectors;

import entites.Article;
import entites.Detail;
import entites.Dette;
import repository.DetailRepository;

public class DetailRepositoryList extends RepositoryImpl<Detail> implements DetailRepository  {

    public DetailRepositoryList(ArticleRespositoryList articleRepository,DetteRepositoryList detteRepository) {
        super();

        List<Article> articles = articleRepository.selectAll();
        List<Dette> dettes = detteRepository.selectAll();

        Article article1 = articles.get(0);
        Article article2 = articles.get(1);
        Article article3 = articles.get(2);

        Dette dette1 = dettes.get(0);
        Dette dette2 = dettes.get(1);
        Dette dette3 = dettes.get(2);

        Detail detail1 = new Detail();
        detail1.setId(1);
        detail1.setArticle(article1);
        detail1.setQte(2);
        detail1.setDette(dette1);
    
        Detail detail2 = new Detail();
        detail2.setId(2);
        detail2.setArticle(article2);
        detail2.setQte(3);
        detail2.setDette(dette1);
    
        Detail detail3 = new Detail();
        detail3.setId(3);
        detail3.setArticle(article3);
        detail3.setQte(1);
        detail3.setDette(dette2);
    
        Detail detail4 = new Detail();
        detail4.setId(4);
        detail4.setArticle(article1);
        detail4.setQte(4);
        detail4.setDette(dette3);
    

        insert(detail1);
        insert(detail2);
        insert(detail3);
        insert(detail4);
    }


    @Override
public List<Detail> findByArticle(Article article) {
    return list.stream() 
        .filter(detail -> detail.getArticle().equals(article))
        .collect(Collectors.toList());
}
@Override
public List<Detail> findDetailsByDetteId(int detteId) {
    return list.stream()
            .filter(detail -> detail.getDette().getId() == detteId) 
            .collect(Collectors.toList()); 
}
    


    
    

    
    
}
