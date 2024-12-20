package entites;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
 @NoArgsConstructor
@EqualsAndHashCode()


public class Article {
    private int id;
    private String nomArticle;
   
    private int qteStock;

  

}
