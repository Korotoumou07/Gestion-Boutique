package view;

import java.util.List;
import java.util.Scanner;
import entites.Article;

import sevices.ArticleServices;
import sevices.DetailADService;
import sevices.DetailService;

public class ArticleView {
    private ArticleServices articleService;
    private DetailService detailService;
    private DetailADService detailADService;

    private Scanner scanner;

    public ArticleView(ArticleServices articleService, DetailService detailService,DetailADService detailADService, Scanner scanner) {
        this.articleService = articleService;
        this.detailService = detailService;
        this.detailADService = detailADService;
        this.scanner = scanner;
    }

    public void creerArticle() {
        String articleName;
        int qtyStock;
    
        do {
            System.out.print("Entrez le nom de l'article : ");
            articleName = scanner.nextLine();
            if (articleService.searchArticle(articleName) != null) {
                System.out.println("Un article avec ce nom existe déjà. Veuillez entrer un nom unique.");
                articleName = null;
            }
        } while (articleName == null);
    
        do {
            System.out.print("Entrez la quantité en stock : ");
            qtyStock = scanner.nextInt();
            scanner.nextLine(); 
            if (qtyStock < 0) {
                System.out.println("La quantité en stock ne peut pas être négative. Veuillez réessayer.");
            }
        } while (qtyStock < 0);
    
        Article article = new Article();
        article.setNomArticle(articleName);
        article.setQteStock(qtyStock);
    
        articleService.createArticle(article);
        System.out.println("Article créé avec succès.");
    }
    

    public void listerArticles() {
        List<Article> articles = articleService.findAllArticle();
        if (articles.isEmpty()) {
            System.out.println("Aucun article trouvé.");
        } else {
           
            afficherTousLesArticles(articles);
        }
    }
    public void afficherTousLesArticles(List<Article> articles) {
        if (articles == null || articles.isEmpty()) {
            System.out.println("Aucun article à afficher.");
            return;
        }
    
        // En-tête du tableau
        System.out.printf(" %-20s %-10s\n", 
             "Nom de l'article", "Quantité");
        System.out.println("-----------------------------------------");
    
        // Affichage des articles
        for (Article article : articles) {
            System.out.printf("%-20s %-10d\n", 
                article.getNomArticle(),
                article.getQteStock());
        }
        System.out.println("-----------------------------------------");
    }
    


    public void listerArticlesDisponibles() {
        List<Article> articles = articleService.findAllArticleDisponible();
        if (articles.isEmpty()) {
            System.out.println("Aucun article trouvé.");
        } else {
            System.out.println("Les Articles disponibles: ");
            System.out.printf("%-20s %-10s\n", "Nom de l'article", "Quantité");
            System.out.println("-----------------------------------------");
    
            
            for (Article article : articles) {
                System.out.printf("%-20s %-10d\n", 
                    article.getNomArticle(), 
                    article.getQteStock()    
                );
            }
        }
    }
    

    public void mettreQteArticleAjour() {
        Article article = null;
        String articleName;
    
        do {
            System.out.print("Entrez le nom de l'article à mettre à jour : ");
            articleName = scanner.nextLine();
            article = articleService.searchArticle(articleName);
    
            if (article == null) {
                System.out.println("Article non trouvé. Veuillez réessayer.");
            }
        } while (article == null);
    
        int nouvelleQte;
    
        do {
            System.out.print("Entrez la nouvelle quantité de stock : ");
            nouvelleQte = scanner.nextInt();
            scanner.nextLine(); 
            if (nouvelleQte < 0) {
                System.out.println("La quantité ne peut pas être négative. Veuillez réessayer.");
            }
        } while (nouvelleQte < 0);
    
        article.setQteStock(nouvelleQte);
    
        articleService.updateArticle(article);
    
        System.out.println("Quantité de l'article mise à jour avec succès.");
    }
    
    

    public void archiverDettesSoldées() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'archiverDettesSoldées'");
    }
}
