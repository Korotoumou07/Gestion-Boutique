package entites;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString(of = {"date","montant"})
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private int id;
    private LocalDateTime date;
    private double montant;
    private Dette dette; // Lien avec la dette
    
}
