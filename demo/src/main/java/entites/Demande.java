package entites;

import java.time.LocalDateTime;


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

public class Demande {

    private int id;                 
    private LocalDateTime date;     
    private String description; 
    private double montant;          
    private Statut statut;     
    private Client client;  

    

}
      
    
