package entites;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
 @ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Dette {

    private int id;
	private LocalDateTime Date;
     private double montant;
     private double montantVerser;
     private double montantRestant;
      private Client client;
     private List<Payment> paiements;
     private boolean archived;


    //  public Dette(LocalDateTime date, double montant, double montantVerser, double montantRestant, Client client, List<Article> articles) {
    //       // this.id = ++idCounter; // Incrémenter et assigner l'id
    //       this.Date = date;
    //       this.montant = montant;
    //       this.montantVerser = montantVerser;
    //       this.montantRestant = montantRestant;
    //       this.client = client;
    //       this.articles = articles;
    //       // Initialiser la liste des paiements s'il y en a
    //   }

    //   @Override
    //   public String toString() {
    //       StringBuilder sb = new StringBuilder();
      
    //       // Tableau principal pour les informations de Dette
    //       sb.append("+-------+----------------------+------------+-----------------+-----------------+------------+\n");
    //       sb.append("| ID    | Date                 | Montant    | Montant Versé   | Montant Restant | Archivé    |\n");
    //       sb.append("+-------+----------------------+------------+-----------------+-----------------+------------+\n");
    //       sb.append(String.format("| %-5d | %-20s | %-10.2f | %-15.2f | %-15.2f | %-10s |\n",
    //           id, Date, montant, montantVerser, montantRestant, archived ? "Oui" : "Non"));
    //       sb.append("+-------+----------------------+------------+-----------------+-----------------+------------+\n");
      
    //       // Tableau pour les informations du client
    //       if (client != null) {
    //           sb.append("\nClient :\n");
    //           sb.append("+----------------------+\n");
    //           sb.append("| Nom                 |\n");
    //           sb.append("+----------------------+\n");
    //           sb.append(String.format("| %-20s |\n", client)); // Adapter si `Client` a des champs spécifiques
    //           sb.append("+----------------------+\n");
    //       }
      
    //       // Tableau pour les articles
    //       if (articles != null && !articles.isEmpty()) {
    //           sb.append("\nArticles :\n");
    //           sb.append("+----------------------+\n");
    //           sb.append("| Article             |\n");
    //           sb.append("+----------------------+\n");
    //           for (Article article : articles) {
    //               sb.append(String.format("| %-20s |\n", article)); // Adapter si `Article` a des champs spécifiques
    //           }
    //           sb.append("+----------------------+\n");
    //       }
      
    //       // Tableau pour les paiements
    //       if (paiements != null && !paiements.isEmpty()) {
    //           sb.append("\nPaiements :\n");
    //           sb.append("+----------------------+------------+\n");
    //           sb.append("| Date                | Montant    |\n");
    //           sb.append("+----------------------+------------+\n");
    //           for (Payment paiement : paiements) {
    //               sb.append(String.format("| %-20s | %-10.2f |\n", paiement.getDate(), paiement.getMontant())); // Adapter selon les champs de `Payment`
    //           }
    //           sb.append("+----------------------+------------+\n");
    //       }
      
    //       return sb.toString();
    //   }
    }      