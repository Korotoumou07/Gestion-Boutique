package entites;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString()
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"surname","telephone"})
public class Client {
    // static private int compteurId;
    private int id;
    private String surname;
    private String telephone;
    private String adresse;
    private User user;

   
    // public Client(String surname, String telephone, String adresse, User user) {
    //     compteurId++;
    //     id = compteurId;
    //     this.surname = surname;
    //     this.telephone = telephone;
    //     this.adresse = adresse;
    //     this.user = user;
    // }

    // public Client() {
    //     compteurId++;
    //     id = compteurId;
    // }
    
    
}
